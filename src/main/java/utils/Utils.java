package utils;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Splitter;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class Utils {
  public static final String line_separator = System.getProperties().getProperty("line.separator");
  public static final Splitter splitterCenterLine =
      Splitter.on("-").omitEmptyStrings().trimResults();
  public static final Splitter splitterUnderLine =
      Splitter.on("_").omitEmptyStrings().trimResults();
  public static final Splitter splitterSemicolon =
      Splitter.on(";").omitEmptyStrings().trimResults();
  public static final Splitter splitterComma = Splitter.on(",").omitEmptyStrings().trimResults();
  public static final Splitter splitterSpace = Splitter.on(" ").omitEmptyStrings().trimResults();
  public static final Splitter splitterWrap = Splitter.on("\n").omitEmptyStrings().trimResults();
  public static final Splitter splitterUnixFilePath =
      Splitter.on("/").omitEmptyStrings().trimResults();
  public static final Splitter splitterAt = Splitter.on("@").omitEmptyStrings().trimResults();
  public static final Splitter splitterDoubleAt =
      Splitter.on("@@").omitEmptyStrings().trimResults();
  public static final Joiner joinerComma = Joiner.on(",");
  public static final Joiner joinerSemicolon = Joiner.on(";");
  public static final Joiner joinerEqual = Joiner.on("=").skipNulls();
  public static final MapJoiner jdbcParamJoiner = Joiner.on("&").withKeyValueSeparator("=");
  public static final Joiner joinerFileSep = Joiner.on(File.separator).skipNulls();
  public static final Joiner joinerT = Joiner.on("\t").skipNulls();
  public static final Joiner joinerCenterLine = Joiner.on("-").skipNulls();
  public static final Joiner joinerUnderLine = Joiner.on("_").skipNulls();
  public static final Joiner joinerAt = Joiner.on("@").skipNulls();
  public static final String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
  public static final ThreadLocal<DateFormat> dateFormat =
      new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
          return new SimpleDateFormat(dateFormatStr);
        }
      };
  public static final ThreadLocal<Calendar> calendarPoll =
      new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
          return Calendar.getInstance();
        }
      };

  @SuppressWarnings("unchecked")
  public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
    Object handler = Proxy.getInvocationHandler(annotation);
    Field f;
    try {
      f = handler.getClass().getDeclaredField("memberValues");
    } catch (NoSuchFieldException | SecurityException e) {
      throw new IllegalStateException(e);
    }
    f.setAccessible(true);
    Map<String, Object> memberValues;
    try {
      memberValues = (Map<String, Object>) f.get(handler);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
    Object oldValue = memberValues.get(key);
    if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
      throw new IllegalArgumentException();
    }
    memberValues.put(key, newValue);
    return oldValue;
  }
}
