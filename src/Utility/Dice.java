package Utility;

import java.util.Random;

public class Dice {

    /**
     * Generates a random number withing an interval, including the lower and upper bounds.
     * @param minVal lower bound
     * @param maxVal upper bound
     * @return Random int number
     */
    public static int throwDice(int minVal, int maxVal) {
        int bound = maxVal - minVal + 1;
        return new Random().nextInt(bound) + minVal;
    }
}
