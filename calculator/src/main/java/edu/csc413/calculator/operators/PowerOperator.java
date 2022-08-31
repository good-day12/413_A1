package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {
    @Override
    public int priority() {
        return 3;
    }

    @Override
    public Operand execute(Operand operandOne, Operand operandTwo) {
        //to hold our result value
        int result = operandOne.getValue();
        //for loop to multiply operandOne value by itself for operandTwo number of times
        for (int i = 0; i < operandTwo.getValue(); i++){
            result *= result;
        }
        return new Operand(result);
    }
}
