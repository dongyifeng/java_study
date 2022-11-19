package dyf.Random;

public class test {
    public static void main(String[] args) {
        double s = 1111.222;
        double f = 222.222222;

        String tickSize = s + "";
        int pos = tickSize.length() - tickSize.indexOf(".") - 1;
        String current = String.format("%." + pos + "f", f);
        System.out.println(current);
    }
}
