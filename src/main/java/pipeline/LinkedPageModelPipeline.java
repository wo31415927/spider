package pipeline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/** cctv 2017/8/25 */
@AllArgsConstructor
@Getter
public class LinkedPageModelPipeline<T> implements PageModelPipeline<T> {
  protected PageModelPipeline<T> curPipeline;
  protected LinkedPageModelPipeline<T> nextPipeline;

  public LinkedPageModelPipeline(PageModelPipeline pageModelPipeline) {
    this(pageModelPipeline, null);
  }

  @Override
  public void process(T t, Task task) {
    if (null != curPipeline) {
      curPipeline.process(t, task);
    }
    if (null != nextPipeline) {
      nextPipeline.process(t, task);
    }
  }
}
