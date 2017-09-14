package utils;

import com.google.common.truth.Truth;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/** cctv 2017/9/7 */
@Slf4j
public class PatternUtilsTest {
  @Test
  public void find() throws Exception {
    log.info(
        ""
            + PatternUtils.find(
                "https://img.581gg.com/picdata-watermark/a1/232/23239-1.jpg",
                ".+/([\\S]+\\.[a-zA-Z]+)$"));
  }

  @Test
  public void groupOnlyOne() throws Exception {
    String imgName =
        PatternUtils.groupOnlyOne(
            "https://img.581gg.com/picdata-watermark/a1/232/23239-1.jpg",
            ".+/([\\S]+\\.[a-zA-Z]+)$");
    log.info("ImgName:" + imgName);
    Truth.assertThat(imgName).isEqualTo("23239-1.jpg");
  }
}
