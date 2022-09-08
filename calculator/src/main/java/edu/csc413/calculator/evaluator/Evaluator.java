package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.operators.*;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private final String delimiters = " +/*-^()"; //don't forget, if we add new operator must add to delimiter, added ()

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int evaluateExpression(String expression ) throws InvalidTokenException {
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer( expression, this.delimiters, true );

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators

    while ( this.expressionTokenizer.hasMoreTokens() ) {
      // filter out spaces
      if ( !( expressionToken = this.expressionTokenizer.nextToken() ).equals( " " )) {
        // check if token is an operand
        if ( Operand.check( expressionToken )) {
          operandStack.push( new Operand( expressionToken ));
        }
        //else if statement to check if it is a closed parenthesis
        else if (expressionToken.equals(")")){
            //create compare Operator to check stack for open parenthesis
            Operator compare = Operator.getOperator("(");
            //while compare isn't an open parenthesis, use equate function to process equation
            while (!operatorStack.peek().equals(compare)) {
              equate(operandStack, operatorStack);
            }
            operatorStack.pop(); //do this to get rid of the open parenthesis object in the stack
          }
        else {
          if ( ! Operator.check( expressionToken )) {
            throw new InvalidTokenException(expressionToken);
          }

          Operator newOperator = Operator.getOperator(expressionToken);

          //if token = "(" then skip the equate function and go straight to add the parenthesis operator onto stack
            while ( !operatorStack.isEmpty() && !expressionToken.equals("(") &&
                    operatorStack.peek().priority() >= newOperator.priority()) {
                equate(operandStack, operatorStack);
            }
          operatorStack.push( newOperator );
        }
      }
    }

    // Control gets here when we've picked up all the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks,
    // that is, we should keep evaluating the operator stack until it is empty;
    // Suggestion: create a method that processes the operator stack until empty.
    while( !operatorStack.isEmpty() ){
      equate(operandStack, operatorStack);
    }
    return operandStack.pop().getValue();
  }

  //Function to process stacks and perform equations
  //takes a stack type operand and stack type operator as arguments
  //void return type, will alter stacks in function
   static void equate(Stack<Operand> a, Stack<Operator> b) {
      Operator operatorFromStack = b.pop();
      Operand operandTwo = a.pop();
      Operand operandOne = a.pop();
      Operand result = operatorFromStack.execute(operandOne, operandTwo);
      a.push(result);
  }

  }



