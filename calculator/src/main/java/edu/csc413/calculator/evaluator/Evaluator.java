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
      outterLoop: //break will return code here
      // filter out spaces
      if ( !( expressionToken = this.expressionTokenizer.nextToken() ).equals( " " )) {
        //check for closed parenthesis
        if (expressionToken.equals(")")){
          //create compare Operator to check if stack has another open parenthesis
          Operator compare = Operator.getOperator("(");
          //while it isn't an open parenthesis, use equate function to process equation
          while (!operatorStack.peek().equals(compare)) {
            equate(operandStack, operatorStack);
          }
          operatorStack.pop(); //do this to get rid of the open parenthesis object in the stack
          break outterLoop; //break to get out of loop and change expressionToken
        }
        //break while loop to change expression token??
        // Problem I have right now is that the expressionToken is still a parenthesis when leaving this while loop

        /**************************************************************************************************************
        * Should I create two classes? One for open parenthesis and one for closed parenthesis? That way I can avoid
        * having to compare expressionToken directly to a ( and I might be able to work new code that avoids using
        * a break? Or since it isn't broke should I not try to fix it?
         * */

        // check if token is an operand
        if ( Operand.check( expressionToken )) {
          operandStack.push( new Operand( expressionToken ));
        } else {
          if ( ! Operator.check( expressionToken )) {
            throw new InvalidTokenException(expressionToken);
          }


          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.

          /*  TODO Process parenthesis
          * If we get a ( open parenthesis, we should put this into the stack continue and add operators
          * and operands into the stack as normal
          * If we get a ) closed parenthesis, we must have an open one in the stack earlier as well
          * so we should process and empty the stack until we get the open parenthesis
          * */
 //         if (expressionToken != '('){
          Operator newOperator = Operator.getOperator(expressionToken);

          //add if statement before to ensure stack is not empty
            while ( !operatorStack.isEmpty() && !expressionToken.equals("(") &&          //add open paranthesis check here?
                    operatorStack.peek().priority() >= newOperator.priority()) {
              // note that when we eval the expression 1 - 2 we will
              // push the 1 then the 2 and then do the subtraction operation
              // This means that the first number to be popped is the
              // second operand, not the first operand - see the following code

              //check if empty first!!! for peek too!!!!
              //check for open parenthesis

                equate(operandStack, operatorStack);

            }
          operatorStack.push( newOperator );
        }
      }
    }


    // Control gets here when we've picked up all of the tokens; you must add
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


//              Operator operatorFromStack = operatorStack.pop();
//              Operand operandTwo = operandStack.pop();
//              Operand operandOne = operandStack.pop();
//              Operand result = operatorFromStack.execute(operandOne, operandTwo);
//              operandStack.push(result);
  }

  }



