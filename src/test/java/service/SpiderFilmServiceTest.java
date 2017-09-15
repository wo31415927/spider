package service;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import pojo.SimpleTask;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Response;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * cctv
 * 2017/9/15
 */
@Slf4j
public class SpiderFilmServiceTest {
  @Test
  public void download() throws Exception {
//      HttpResponse httpResponse = IOUtils.http();
      String host = "down.maomixia.com:888";
      String url = "http://down.maomixia.com:888/guochan/201706/23/jgbjqyyag/jgbjqyyag.rmvb";
      HttpClientDownloader downloader = new HttpClientDownloader();
      Response response = downloader.download(new Request(url), new SimpleTask(host));
      log.info("VideoLength:" + response.getBytes().length);
  }
}