package pojo;

import com.google.common.truth.Truth;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static utils.JsonUtils.gson;

/**
 * cctv
 * 2017/9/15
 */
@Slf4j
public class SpiderResTypeTest {
  @Test
  public void testGsonEnum() throws Exception {
      String resStr = gson.toJson(Dic.SpiderResType.IMG, Dic.SpiderResType.class);
      Truth.assertThat(resStr).isEqualTo("\"IMG\"");
      Dic.SpiderResType resType = gson.fromJson("FILM",Dic.SpiderResType.class);
      Truth.assertThat(resType).isEqualTo(Dic.SpiderResType.FILM);
  }
}