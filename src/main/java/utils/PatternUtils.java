package utils;
/** */
import com.google.common.cache.CacheLoader;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

/** @author zeyu */
public class PatternUtils {
  public static final RegularMatcherCacheLoader cacheLoader = new RegularMatcherCacheLoader();

  public static boolean match(String name, String pattern) {
    return cacheLoader.load(pattern).matcher(name).matches();
  }

  public static String groupOnlyOne(String name, String pattern) {
    Matcher matcher = cacheLoader.load(pattern).matcher(name);
    //必须先find，group才能获取到内容
    if (matcher.find()) {
      return matcher.group(1);
    }
    return name;
  }

  public static boolean find(String name, String pattern) {
    return cacheLoader.load(pattern).matcher(name).find();
  }

  public static String replaceAll(String oldName, String newName, String pattern) {
    return cacheLoader.load(pattern).matcher(oldName).replaceAll(newName);
  }

  /**
   * @param schemaName
   * @param exclusiveSchema
   * @return
   * @throws Exception
   */
  public static boolean match(String schemaName, Set<String> exclusiveSchema) {
    for (String patternStr : exclusiveSchema) {
      if (match(schemaName, patternStr)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param tableName
   * @return
   * @throws Exception
   */
  public static boolean indexOf(String tableName, Set<String> tables) {
    for (String patternStr : tables) {
      if (tableName.indexOf(patternStr) > -1) {
        return true;
      }
    }
    return false;
  }

  public static class RegularMatcherCacheLoader extends CacheLoader<String, Pattern> {
    @Override
    public Pattern load(@Nonnull String key) {
      return Pattern.compile(key);
    }
  }
}
