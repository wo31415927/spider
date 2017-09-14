import com.google.common.truth.Truth;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/** cctv 2017/8/23 */
@Slf4j
public class RegexTest {
  @Test
  public void testRegex() throws Exception {
    Pattern pattern =
        Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
    Matcher matcher = pattern.matcher("<img src=\"https://img.581gg.com/picdata-watermark/a1/225/22534-1.png\">");
    Truth.assertThat(matcher.find()).isTrue();
    log.info("GroupCount:"+matcher.groupCount());
    log.info(matcher.group(0));
    log.info(matcher.group(1));

  }
}
