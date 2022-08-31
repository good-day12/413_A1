package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {
    /**
     * construct operand from string token.
     */
    private int num;

    public Operand(String token) {
        num = Integer.parseInt(token);
    }

    /**
     * construct operand from integer
     */
    public Operand(int value) {
        num = value;
    }

    /**
     * return value of operand
     */
    public int getValue() {
        return num;
    }

    /**
     * Check to see if given token is a valid
     * operand.
     */
    public static boolean check(String token) {
        //use try/catch along with Integer parseInt to check if Int,

        try {
            Integer.parseInt(token);
        } catch(NumberFormatException ex) {
            //if exception is thrown, our number is not an Int so return false
            return false;
        }
        //if no exception thrown
        return true;
    }
}
