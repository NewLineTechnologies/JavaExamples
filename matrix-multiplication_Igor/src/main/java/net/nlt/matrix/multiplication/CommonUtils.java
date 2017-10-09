package net.nlt.matrix.multiplication;

public class CommonUtils {

    private CommonUtils() {
    }

    public static int nextMultiple(int num, int divide) {
        if (num < divide) {
            return divide;
        }

        int remainder = num % divide;
        return remainder == 0
                ? num
                : num + (divide - remainder);
    }
}
