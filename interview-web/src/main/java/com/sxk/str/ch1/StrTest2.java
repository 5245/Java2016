package com.sxk.str.ch1;
/**
 * 
 * @description 金额转能大写 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年9月7日
 */
public class StrTest2 {
    public static char[] NUMBER_CHAR = "零壹贰叁肆伍陆柒捌玖".toCharArray();

    public static char[] UNIT_CHAR   = "元拾佰仟万拾佰仟亿拾佰仟".toCharArray();

    public static void main(String[] args) {
        //System.out.println((int) Math.pow(10, 2));
        System.out.println(1 << 16);
        System.out.println(StrTest2.num2Voice(2009));
    }

    public static String num2Voice(int amount) {
        StringBuilder retSb = new StringBuilder();
        String retStr = "";
        int length = String.valueOf(amount).length() - 1;//长度
        int pos = length;//当前位置
        int posValue = 0;//当前位置对应的值
        int dividend = (int) Math.pow(10, length);//被除数
        boolean flag = false;

        if (amount < 10) {
            retSb.append(NUMBER_CHAR[amount]);
            retSb.append(UNIT_CHAR[pos]);
        } else {
            while (pos > 0) {
                posValue = amount / dividend;
                amount = amount % dividend;
                if (posValue > 0) {
                    if (flag && (pos != 3 && pos != 7)) {
                        retSb.append(NUMBER_CHAR[0]);
                    }
                    flag = false;
                    retSb.append(NUMBER_CHAR[posValue]);
                    retSb.append(UNIT_CHAR[pos]);
                }
                if (posValue == 0) {
                    flag = true;
                    if (pos == 4 || pos == 8) {
                        retSb.append(UNIT_CHAR[pos]);
                    }
                }
                pos--;
                dividend = dividend / 10;
            }
            if (amount > 0) {
                if (flag && (pos != 3 && pos != 7)) {
                    retSb.append(NUMBER_CHAR[0]);
                }
                retSb.append(NUMBER_CHAR[amount]);
            }
            retSb.append(UNIT_CHAR[0]);
        }
        retSb.append("整");

        retStr = retSb.toString().replaceAll("亿万", "亿");

        System.out.println(retStr);
        return retStr;
    }
}
