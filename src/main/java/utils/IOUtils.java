package utils;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;

@Slf4j
public class IOUtils {
  public static final int cbufLen = 1024;

  public static boolean checkThreadInterruptedException() throws InterruptedException {
    if (Thread.currentThread().isInterrupted()) {
      throw new InterruptedException();
    }
    return false;
  }

  public static boolean checkThreadRuntimeException() {
    if (Thread.currentThread().isInterrupted()) {
      throw new RuntimeException("ThreadIsInterrupted");
    }
    return false;
  }

  public static boolean checkThreadInterrupted() {
    return Thread.currentThread().isInterrupted();
  }

  public static HttpResponse http(String url) throws IOException {
    Site site = Site.me();
    CloseableHttpClient httpClient = new HttpClientDownloader().getHttpClient(site);
    HttpClientRequestContext requestContext =
        new HttpUriRequestConverter().convert(new Request(url), site, null);
    return httpClient.execute(
        requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
  }
  /**
   * 存在目录，则先删除后创建
   *
   * @param path
   * @throws IOException
   */
  public static void mkdirD(Path path) throws IOException {
    if (null == path) {
      throw new IllegalArgumentException("Path shoud not be null!");
    }
    if (path.toFile().exists()) {
      IOUtils.delete(path);
    }
    Files.createDirectory(path);
  }

  public static void delete(String path) throws IOException {
    delete(Paths.get(path));
  }

  /** rm -rf xx */
  public static void delete(Path path) throws IOException {
    Files.walkFileTree(
        path,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            //假如file此时在某程序中打开，则delete会失败
            Files.delete(file);
            return super.visitFile(file, attrs);
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            try {
              // 连续执行多个testcase时，虽然上一次testcase的fileChannel资源已经close了，
              // 但下一个testcase执行时会出现Files.delete(file)有的时候已经执行完了，但是此处会出现目录非空的情形，等500ms之后重试就好了
              Files.delete(dir);
            } catch (DirectoryNotEmptyException e) {
              log.warn("", e);
              try {
                TimeUnit.MILLISECONDS.sleep(500);
              } catch (InterruptedException e1) {
              }
              Files.delete(dir);
            }
            return super.postVisitDirectory(dir, exc);
          }
        });
  }
}
