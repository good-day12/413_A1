package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class ParanthesisOperator extends Operator {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Operand execute(Operand operandOne, Operand operandTwo) {
        return null;
    }

//    public boolean isClosed(ParanthesisOperator){
//        return false;
//    }
}
