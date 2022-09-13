package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

import java.util.HashMap;
import java.util.Map;

public abstract class Operator {
    // The Operator class should contain an instance of a HashMap
    // This map will use keys as the tokens we're interested in,
    // and values will be instances of the Operators.
    // ALL subclasses of operator MUST be in their own file.
    // Example:
    // Where does this declaration go? What should its access level be?
    // Class or instance variable? Is this the right declaration?
    // HashMap operators = new HashMap();
    // operators.put( "+", new AdditionOperator() );
    // operators.put( "-", new SubtractionOperator() );

    //called flyweight pattern, reuse objects that are SHARABLE
//    static Map<String,Operator> operators;
//    static {
//        operators.put("+", new AddOperator()); //<-will create add operator object and put into map
//        operators.get("+"); //<- will get the same object over and over again, with different references to it
//        operators.get("+"); //more efficient for memory, reuse the objects
//        operators.get("+");
//        operators.get("+");
//    }

    static private final Map<String,Operator> operators;
    static { //static will run once while class is initially loaded
        //load up hashmap with our operators needed to perform equation
        operators = new HashMap<>();
        operators.put("+", new AddOperator());
        operators.put("-", new SubtractOperator());
        operators.put("/", new DivideOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("^", new PowerOperator());
        operators.put("(", new ParenthesesOperator());
    }

    /**
     * retrieve the priority of an Operator
     * @return priority of an Operator as an int
     */
    public abstract int priority();

    /**
     * Abstract method to execute an operator given two operands.
     * @param operandOne first operand of operator
     * @param operandTwo second operand of operator
     * @return an operand of the result of the operation.
     */
    public abstract Operand execute(Operand operandOne, Operand operandTwo);

    /**
     * used to retrieve an operator from our HashMap.
     * This will act as out publicly facing function,
     * granting access to the Operator HashMap.
     *
     * @param token key of the operator we want to retrieve
     * @return reference to a Operator instance.
     */
    public static Operator getOperator(String token) {
        return operators.get(token);
    }
    
     /**
     * determines if a given token is a valid operator.
      * @param token key of the operator we want to check
      * @return bool
      */
    public static boolean check(String token) {
        return operators.containsKey(token);
    }
}
