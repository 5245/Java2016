package com.dangdang.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

/**
 * @description 汉语转拼音工具类。 
 * @author sxk
 * @date 2016年12月24日
 */
public final class PinyinUtils {

    private PinyinUtils() {
    }

    /**
     * 汉语转拼音缩写.
     * @param chineseStr
     * @return
     */

    public static String toPinyinInitial(String chineseStr) {
        if (StringUtils.isEmpty(chineseStr))
            return null;

        char[] chars = chineseStr.toCharArray();
        StringBuilder result = new StringBuilder();

        HanyuPinyinOutputFormat format = getDefaultOutputFormat();

        for (char c : chars) {
            // 如果是汉字，则执行转换
            String[] pinyin = null;
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
            }
            if (pinyin == null || pinyin.length == 0 || StringUtils.isEmpty(pinyin[0])) {
                result.append(c);
            } else {
                result.append(pinyin[0].charAt(0));
            }
        }
        return result.toString();
    }

    /**
     * 
     * @param chineseStr
     * @return
     * 
     */
    public static String toPinyin(String chineseStr) {
        if (StringUtils.isEmpty(chineseStr))
            return null;

        char[] chars = chineseStr.toCharArray();
        StringBuilder result = new StringBuilder();

        HanyuPinyinOutputFormat format = getDefaultOutputFormat();

        for (char c : chars) {
            // 如果是汉字，则执行转换
            String[] pinyin = null;
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
            }
            if (pinyin == null || pinyin.length == 0 || StringUtils.isEmpty(pinyin[0])) {
                result.append(c);
            } else {
                result.append(captureStr(pinyin[0]) + " ");
            }
        }
        return result.toString();
    }

    /**
     * 设计拼音转换的输出格式.
     * 
     * @param
     * @return HanyuPinyinOutputFormat 输出格式
     */
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式等
        hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        return hanYuPinOutputFormat;
    }
    
    //首字符大写
    public static String captureStr(String str) {
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}