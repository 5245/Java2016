package com.sxk.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
    public static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
    // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
    public static final String regEx_style  = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

    // 定义HTML标签的正则表达式  
    public static final String regEx_html   = "<[^>]+>";
    public static final String regEx_html1  = "<[^>]+";

    public static boolean containsHtmlOrJavaScript(String inputString) {
        String htmlStr = inputString;
        boolean isContains;
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;

        try {
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);

            isContains = (m_html.find() || m_html1.find() || m_script.find() || m_style.find());
        } catch (Exception e) {
            isContains = false;
            System.err.println("Html2Text: " + e.getMessage());

        }

        return isContains;
    }

    public static String Html2Text(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;

        try {
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);

            htmlStr = m_script.replaceAll(""); // 过滤script标签  

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签  

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签  

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签  

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串  
    }

    public static void main(String[] args) {
        String html = "<div style=\"text-align: start;\"><span style=\"text-align: -webkit-center; font-size: 17px; -webkit-text-size-adjust: 119%; background-color: rgba(255, 255, 255, 0);\">武汉东湖新技术开发区关山大道589号</span><span style=\"-webkit-text-size-adjust: 119%;\">武汉理工大学华夏学院</span></div>";
        String text = HtmlUtils.Html2Text(html);
        System.out.println(text);
        System.out.println(Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE).pattern());
        System.out.println(Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE).matcher(html).find());
        System.out.println(html.matches(regEx_script));
        System.out.println(html.matches(regEx_style));
        System.out.println(html.matches(regEx_html));
        System.out.println(html.matches(regEx_html1));

        System.out.println(HtmlUtils.containsHtmlOrJavaScript("<div 孙"));
        System.out.println(HtmlUtils.containsHtmlOrJavaScript("<sapn 孙"));
        System.out.println(HtmlUtils.containsHtmlOrJavaScript("<script 孙"));
        System.out.println(HtmlUtils.containsHtmlOrJavaScript(" 孙 asdf asdfiewfksd "));
        System.out.println(HtmlUtils.containsHtmlOrJavaScript("南京省 201 街道 "));
    }
}
