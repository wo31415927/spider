import org.junit.Test;

import java.util.List;

/**
 * cctv
 * 2017/8/25
 */
public class TemplateTest<T> {
   public void init(T t){
    System.out.println(t.toString());
   }

  @Test
  public void testTemplate() throws Exception {
       TemplateTest templateTest = new TemplateTest<List>();
       templateTest.init("123");
       Object o = "object";
       Integer i = 1;
       templateTest.init(i);
  }
}
