package biz;

public class isNum {
    public static int convert(String str) {
        if (str == null || str.trim().equalsIgnoreCase("")) {
            return 0;
        }
        char[] chars = str.toCharArray();
        if (!isValid(chars)) {
            throw new RuntimeException(str + "is not num");
        }


        int minr = Integer.MIN_VALUE % 10;
        int minq = Integer.MIN_VALUE / 10;
        boolean isNegive = chars[0] == '-';

        int res = 0;
        for (int i = isNegive ? 1 : 0; i < chars.length; i++) {
            int cur = '0' - chars[i];
            if (res < minq || (res == minq && cur < minr)) {
                throw new RuntimeException(str + "can not covert");
            }

            res = res * 10 + cur;
        }
        if(!isNegive && res==Integer.MIN_VALUE){
            throw new RuntimeException(str + "can not covert");
        }


        return isNegive ? res : -res;
    }

    public static boolean isValid(char[] str) {
        if (str[0] != '-' && (str[0] < '0' || str[0] > '9')) {
            return false;
        }

        if (str[0] == '-' && (str.length == 1 || str[1] == '0')) {
            return false;
        }

        if (str[0] == '0' && str.length > 1) {
            return false;
        }

        for (int i = 1; i < str.length; i++) {
            if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(convert("-796"));
        System.out.println(convert("796"));
    }
}
