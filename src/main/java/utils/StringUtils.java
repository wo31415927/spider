package utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class StringUtils {
    /**
     * 移除`或'符号
     */
    public static String removeBackquote(String str) {
        // 删除名字中的`tablename`和'value'
        if (!Strings.isNullOrEmpty(str)) {
            StringBuilder sb = new StringBuilder(str);
            if (sb.charAt(0) == '`' || sb.charAt(0) == '\'') {
                sb.deleteCharAt(0);
            }
            if (sb.charAt(sb.length() - 1) == '`' || sb.charAt(sb.length() - 1) == '\'') {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        }
        return str;
    }

    public static String addQuote(String str, String quote) {
        if (null != str) {
            return quote + str + quote;
        }
        return str;
    }

    public static Iterable<String> addQuote(Iterable<String> iterable, String quote) {
        if (null != iterable) {
            List<String> strList = Lists.newArrayList();
            for (String str : iterable) {
                strList.add(addQuote(str, quote));
            }
            return strList;
        }
        return null;
    }

    public static String toHexString(byte[] array) {
        return ("0x" + DatatypeConverter.printHexBinary(array));
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}
