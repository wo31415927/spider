package pipeline;

import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/** cctv 2017/9/14 */
@Getter
@Setter
public abstract class AbstractPageModelPipeline<T> implements PageModelPipeline<T> {
  protected Spider spider;
}
