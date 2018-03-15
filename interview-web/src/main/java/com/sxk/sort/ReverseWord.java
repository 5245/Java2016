package com.sxk.sort;

public class ReverseWord {
    public static void main(String[] args) {
        ReverseWord rw = new ReverseWord();
        char[] data = "I am a stutend".toCharArray();
        char[] data2 = rw.reverseSentence2(data);
        System.out.println(new String(data2));
    }

    public char[] reverseSentence(char[] data) {
        if (data == null || data.length < 1)
            return data;
        reverse(data, 0, data.length - 1);
        System.out.println("reverse后的顺序为：" + new String(data));

        int start = 0; //start用于寻找每个单词的开头
        int end = 0; //end用于寻找每个单词后面的空格
        while (start < data.length) {
            end++;
            if (data[start] == ' ') {
                start++;
            } else if (end == data.length || data[end] == ' ') {
                reverse(data, start, end - 1); //找到空格后，将空格前面的单词翻转，这样单词的顺序就对了
                start = end; //把空格处赋给start，让start重新指向下一个单词的开始
            }
        }
        return data; //返回的data即为最后旋转后的结果
    }

    public char[] reverseSentence2(char[] data) {
        if (data == null || data.length < 1)
            return data;
        reverse(data, 0, data.length - 1);
        System.out.println("reverse后的顺序为：" + new String(data));

        int start = 0; //start用于寻找每个单词的开头
        int end = 0; //end用于寻找每个单词后面的空格
        while (start < data.length) {
            if (data[start] == ' ') {
                start++;
                end++;
            } else if (end == data.length || data[end] == ' ') { //判断end是否找到了句子的开头或者是否到了句子的结尾
                reverse(data, start, end - 1); //找到空格后，将空格前面的单词翻转，这样单词的顺序就对了
                end++; //end继续向后移动
                start = end; //把空格处赋给start，让start重新指向下一个单词的开始
            } else {
                end++;
            }
        }
        return data; //返回的data即为最后旋转后的结果
    }

    private void reverse(char[] data, int start, int end) {
        while (start < end) {
            char temp = data[start];
            data[start] = data[end];
            data[end] = temp;
            start++;
            end--;
        }

    }
}
