package sample.av.Hhh;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.XpathSelector;

/** cctv 2017/9/15 */
@Slf4j
public class HhhVideoInfoTest {
  @Test
  public void testSelect() throws Exception {
    Html html =
        Html.create(
            "\n"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "    <head>\n"
                + "    <META content=ie=7 http-equiv=x-ua-compatible>\n"
                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                + "    <title>技师穿上高跟网袜玩冰火一条龙-国产自拍-</title>\n"
                + "    <meta name=\"robots\" content=\"all\" />\n"
                + "    <meta name=\"renderer\" content=\"ie-comp\">\n"
                + "    <meta name=\"keywords\" content=\"技师穿上高跟网袜玩冰火一条龙免費在線觀看,技师穿上高跟网袜玩冰火一条龙劇情介紹,技师穿上高跟网袜玩冰火一条龙電影海報\" />\n"
                + "    <meta name=\"description\" content=\"技师穿上高跟网袜玩冰火一条龙劇情\" />\n"
                + "    <link type=\"text/css\" rel=\"stylesheet\" href=\"/css/style.css\" />\n"
                + "<script type=\"text/javascript\" src=\"/js/downurl.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"/js/base64_cdn.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"/js/clipboard.min.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"/js/downConfig.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"/js/config.js\"></script>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "    <div id=\"header1\"></div>\n"
                + "    <div class=\"layout mt10\">\n"
                + "    <div class=\"tbg10\"></div>\n"
                + "    <div class=\"mainArea px19\">\n"
                + "    <div class=\"position\"><span "
                + "class=\"playerTip\"><a href=\"/help/help.html\" target=\"_blank\" class=\"white\">提示：如果無法播放，點擊獲取更多幫助！</a></span><a href='/'>首页</a"
                + ">&nbsp;&nbsp;&raquo;&nbsp;&nbsp;<a href=\"/htm/downlist6/\">国产自拍</a>>&nbsp;&nbsp;&raquo;&nbsp;&nbsp;技师穿上高跟网袜玩冰火一条龙</div>\n"
                + "</div>\n"
                + "<div class=\"bbg10\"></div>\n"
                + "    </div>\n"
                + "    <div class=\"layout mt10\">\n"
                + "    <div class=\"tbg20\"></div>\n"
                + "    <div class=\"mainArea\">\n"
                + "    <div class=\"movieInfo\">\n"
                + "    <div class=\"poster\"><img src=\"https://gg.385gg.com/pic/img3/2017-6/2017626025471687.jpg\" title=\"技师穿上高跟网袜玩冰火一条龙\" "
                + "alt=\"技师穿上高跟网袜玩冰火一条龙\" /></div>\n"
                + "    <ul class=\"movieInfoList\">\n"
                + "    <li class=\"title\">技师穿上高跟网袜玩冰火一条龙</li>\n"
                + "    <li><em>類型：</em><a href=\"/htm/downlist6/\"><strong>国产自拍</strong></a></li>\n"
                + "    <li><em>更新：</em>2017/6/26 0:03:04</li>\n"
                + "<li><em>推薦：</em><a href=\"javascript:camLink();\"><font color=\"#4e4e4e\">與美女同城交友~馬上體驗</font></a></li>\n"
                + "<li><em>推薦：</em><a href=\"javascript:camLink();\"><font color=\"#4e4e4e\">與美女激情裸聊~馬上體驗</font></a></li>\n"
                + "<li><div class=\"bdsharebuttonbox\"><a href=\"#\" class=\"bds_more\" data-cmd=\"more\">分享到：</a"
                + "><a href=\"#\" class=\"bds_copy\" data-cmd=\"copy\" title=\"分享到複制網址\">複制網址</a"
                + "><a href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\" title=\"分享到QQ空間\">QQ空間</a"
                + "><a href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\" title=\"分享到新浪微博\">新浪微博</a"
                + "><a href=\"#\" class=\"bds_tqq\" data-cmd=\"tqq\" title=\"分享到騰訊微博\">騰訊微博</a"
                + "><a href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\" title=\"分享到微信\">微信</a></div></li>\n"
                + "<script>window._bd_share_config={\"common\":{\"bdSnsKey\":{},\"bdText\":\"\",\"bdMini\":\"2\",\"bdMiniList\":false,\"bdPic\":\"\","
                + "\"bdStyle\":\"0\",\"bdSize\":\"16\"},\"share\":{\"bdSize\":16},\"image\":{\"viewList\":[\"copy\",\"qzone\",\"tsina\",\"tqq\","
                + "\"weixin\"],\"viewText\":\"分享到：\",\"viewSize\":\"16\"},\"selectShare\":{\"bdContainerClass\":null,\"bdSelectMiniList\":[\"copy\","
                + "\"qzone\",\"tsina\",\"tqq\",\"weixin\"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement"
                + "('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>\n"
                + "</ul>\n"
                + "<div id=\"movieInfoRight\"></div>\n"
                + "    </div>\n"
                + "\n"
                + "\n"
                + "    <DIV class=\"endpage clearfixpage\">\n"
                + "\t<div class=\"shareBlock\" align=\"center\"></div>\n"
                + "\t<DIV class=mox>\n"
                + "    <DIV class=title><SPAN><IMG src=\"/images/bt.png\">下载地址 <a href=\"/help/ios/index.html\" target=\"_blank\">【苹果手机教程】</a> "
                + "<a href=\"/help/anzuo/index.html\" target=\"_blank\">【安卓手机教程】</a> "
                + "<a href=\"/help/pc/index.html\" target=\"_blank\">【电脑版教程】</a></SPAN><P><FONT "
                + "color=red>此处支持迅雷、QQ旋风、小米路由下载，同时支持迅雷影音播放</FONT></P></DIV>\n"
                + "<DIV class=ndownlist>\n"
                + "\t\t\t\t<UL id=\"downUL\">\n"
                + "\t\t\t\t</UL>\n"
                + "\t\t\t</DIV>\n"
                + "    </DIV>\n"
                + "    <!--/mox-->\n"
                + "    <DIV class=mox id=\"btplay\">\n"
                + "    <DIV class=title><SPAN><IMG src=\"/images/bt.png\">B T 种子 <a href=\"/help/bt.html\" target=\"_blank\">【下载教程】</a></SPAN><P><FONT "
                + "color=red>【推荐安卓版手机和电脑版用户使用】最新资源强烈推荐BT下载，电脑和手机下载速度超级快</FONT></P></DIV>\n"
                + "<DIV class=play-list><A title=\"电脑版下载\" href=\"https://123456bt.com/link.php?ref=CO1RwUhIXM\" target=_blank>电脑版下载</A><A "
                + "title=\"手机版下载\" href=\"https://123456bt.com/link.php?ref=CO1RwUhIXM\" target=_blank>手机版下载</A><A title=\"高清BT种子\" href=\"https://www"
                + ".gavbus666.com\" target=_blank>高清BT种子</A><A title=\"Gavbus老司机种子\" href=\"https://www.gavbus666.com\" "
                + "target=_blank>Gavbus老司机种子</A></DIV>\n"
                + "    <DIV class=play-list>采集地址：https://123456bt.com/link.php?ref=CO1RwUhIXM</DIV>\n"
                + "</DIV>\n"
                + "<!--/mox-->\n"
                + "<DIV class=mox>\n"
                + "    <DIV class=title><SPAN><IMG src=\"/images/bt.png\">西瓜播放器 <a href=\"/help/xigua.html\" target=\"_blank\">【下载教程】</a></SPAN><P><A "
                + "href=\"http://s1.xiguaplayer.com/xigua_Install.exe\" target=_blank>下载西瓜影音电脑版</A> <A href=\"http://www.xigua.com/phone.html\" "
                + "target=_blank>下载西瓜影音手机版</A></P></DIV>\n"
                + "    <DIV class=play-list><A title=\"电脑版播放\" href=\"/htm/downxigua6/8748.htm\" target=_blank>电脑版播放</A><A title=\"手机版播放\" "
                + "href=\"/htm/downxigua6/8748.htm\" target=_blank>手机版播放</A></DIV>\n"
                + "    <DIV class=play-list>采集地址：ftp://f:<a "
                + "href=\"/cdn-cgi/l/email-protection\" class=\"__cf_email__\" data-cfemail=\"b7c7f7d1c3c79ac7d6d999d4d8da\">[email&#160;"
                + "protected]</a>:88/7016/jgbjqyyag.rmvb</DIV>\n"
                + "</DIV>\n"
                + "<!--/mox-->\n"
                + "<DIV class=mox>\n"
                + "    <DIV class=title><SPAN><IMG src=\"/images/bt.png\">肥佬影音 <a href=\"/help/feilao.html\" target=\"_blank\">【下载教程】</a></SPAN><P><A "
                + "href=\"http://dl.feilao.com:86/FPlayer.exe\" target=_blank>下载肥佬影音-肥佬只能支持电脑版</A></P></DIV>\n"
                + "    <DIV class=play-list><A title=\"电脑版播放\" href=\"/htm/downfvod6/8748.htm\" target=_blank>电脑版播放</A></DIV>\n"
                + "    <DIV class=play-list>采集地址：FVOD://465027016|189BCNCIMU7T5ACA3A8U1T3X9JAH8BFADF5H4H3NFMFF5F3OFM2FCLDCDD5D17D9EC741E|jgbjqyyag"
                + ".rmvb|</DIV>\n"
                + "</DIV>\n"
                + "<!--/mox-->\n"
                + "\n"
                + "    <div class=\"moviePic\"><font color=\"#000\" style=\"font-size:16px\">注：肥佬播放器没有手机版，如需手机下载请点击BT下载或者手机迅雷下载</font></div>\n"
                + "</div>\n"
                + "<div class=\"bbg20\"></div>\n"
                + "    </div>\n"
                + "    </div>\n"
                + "    <div id=\"footer1\"></div>\n"
                + "    <script>!function(e,t,r,n,c,h,o){function a(e,t,r,n){for(r='',n='0x'+e.substr(t,2)|0,t+=2;t<e.length;t+=2)r+=String.fromCharCode"
                + "('0x'+e.substr(t,2)^n);return r}try{for(c=e.getElementsByTagName('a'),o='/cdn-cgi/l/email-protection#',n=0;n<c.length;n++)try{(t="
                + "(h=c[n]).href.indexOf(o))>-1&&(h.href='mailto:'+a(h.href,t+o.length))}catch(e){}for(c=e.querySelectorAll('.__cf_email__'),n=0;n<c"
                + ".length;n++)try{(h=c[n]).parentNode.replaceChild(e.createTextNode(a(h.getAttribute('data-cfemail'),0)),h)}catch(e){}}catch(e){}}"
                + "(document);</script><script type=\"text/javascript\">generate_down(downurl_69_2 + \"/guochan/201706/23/jgbjqyyag/jgbjqyyag.rmvb\");"
                + "</script>\n"
                + "\t<script type=\"text/javascript\" src=\"/js/layout.js\"></script>\n"
                + "    <script type=\"text/javascript\">makhtml(\"movieInfoRight\");</script>\n"
                + "\t<script type=\"text/javascript\" src=\"/js/down_xflib2.0.js\"></script>\n"
                + "\t<script type=\"text/javascript\" src='/js/tongji.htm'></script>\n"
                + "\t<script type=\"text/javascript\" src='/js/webThunderDetect.js'></script>\n"
                + "\t<script type=\"text/javascript\" src='/js/thunderForumLinker.js'></script>\n"
                + "    <script type=\"text/javascript\">\n"
                + "        var thunderPid=\"88888888\";\n"
                + "        var thunderExceptPath=\"\";\n"
                + "        thunderFuncType=false;\n"
                + "        thunderLinker();\n"
                + "    </script>\n"
                + "</body>\n"
                + "</html>\n");
    XpathSelector selector1 = new XpathSelector("ul[@id='downUL']/li[1]/p/html()");
    String str = selector1.select(html.getDocument());
    log.info(str);
    selector1 = new XpathSelector("ul[@class='movieInfoList']/li[1]/html()");
    str = selector1.select(html.getDocument());
    log.info(str);
  }

  @Test
  public void testSelenium() {
    System.getProperties().setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
//    System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
    WebDriver webDriver = new ChromeDriver();
    webDriver.get("https://www.uuu944.com/htm/down6/8748.htm");
    WebElement webElement = webDriver.findElement(By.xpath("/html"));
    System.out.println(webElement.getAttribute("outerHTML"));
    webDriver.close();
  }
}
