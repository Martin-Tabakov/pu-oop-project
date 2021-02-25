package Utility;

import java.util.Random;

public class Dice {

    public static int throwDice(int minVal, int maxVal) {
        int bound = maxVal - minVal + 1;
        return new Random().nextInt(bound) + minVal;
    }
}
