package globalcode.forex.utils;

import java.util.Arrays;

public class Parser {
    public static int[] toInteger(String[] list){
        return Arrays.stream(list).mapToInt(Integer::parseInt).toArray();
    }
}
