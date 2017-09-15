package pojo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

/** cctv 2017/9/15 */
@Getter
@Setter
public class SimpleTask implements Task {
  protected Site site;
  protected String uuid;

    public SimpleTask(String domain) {
        this.site = Site.me().setDomain(domain);
    }

    @Override
  public String getUUID() {
    if (uuid != null) {
      return uuid;
    }
    if (site != null) {
      return site.getDomain();
    }
    uuid = UUID.randomUUID().toString();
    return uuid;
  }

  @Override
  public Site getSite() {
    return site;
  }
}
