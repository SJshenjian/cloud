package online.shenjian.cloud.api.utils;

import java.util.Scanner;

public class ToRomeUtils {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("请输入长度小于10的数字!");
        boolean flag = true;
        String str1 = "";
        while (flag) {
            str1 = sc.next();
            if(!ifOver(str1)){
                flag = ifNotNum(str1);
            }
        }
        str1 = turnToRome(str1);
        System.out.println(str1);
    }

    public static boolean ifOver(String str) {
        if(str.length()>10){
            System.out.println("您输入的字符超过最大长度!请重新输入!");
            return true;
        }
        else
            return false;
    }

    public static boolean ifNotNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                System.out.println("有字符不在0-9的范围，请重新输入!");
                return true;
            }
        }
        return false;
    }

    public static String turnToRome(String str){
        String[] arr = new String[]{"","Ⅰ","Ⅱ","Ⅲ","Ⅳ","Ⅴ","Ⅵ","Ⅶ","Ⅷ","Ⅸ"};
//        StringBuilder sb = new StringBuilder(str);
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
//            System.out.println(str.charAt(i));
            num = str.charAt(i) - '0';
            sb.append(arr[num]);
        }
        return sb.toString();
    }
}
