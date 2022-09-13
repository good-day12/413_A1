package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {

    private JTextField expressionTextField = new JTextField();
    private JPanel buttonPanel = new JPanel();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] buttonText = {
        "7", "8", "9", "+",
        "4", "5", "6", "-", 
        "1", "2", "3", "*", 
        "(", "0", ")", "/", 
        "C", "CE", "=", "^"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private JButton[] buttons = new JButton[buttonText.length];

    public static void main(String argv[]) {
        new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.expressionTextField.setPreferredSize(new Dimension(600, 50));
        this.expressionTextField.setFont(new Font("Courier", Font.BOLD, 24));
        this.expressionTextField.setHorizontalAlignment(JTextField.CENTER);

        add(expressionTextField, BorderLayout.NORTH);
        expressionTextField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        JButton tempButtonReference;
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            tempButtonReference = new JButton(buttonText[i]);
            tempButtonReference.setFont(new Font("Courier", Font.BOLD, 24));
            buttons[i] = tempButtonReference;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed
     * on our Calculator GUI.
     * @param actionEventObject Event object generated when a
     *                    button is pressed.
     */
    public void actionPerformed(ActionEvent actionEventObject) {
        System.out.println(actionEventObject.getActionCommand()); //will print button
        String command = actionEventObject.getActionCommand();

        //switch to handle what to do for each command input
        switch (command) {
            case "C" ->
                    //use substring of expression text field starting with first char at 0 and ending 1 before
                    //the end of the old string, so we can get rid of the most recently inputted character
                    this.expressionTextField.setText(
                            this.expressionTextField.getText().substring
                                    (0, this.expressionTextField.getText().length() - 1)); //get rid of one slot
            case "CE" -> this.expressionTextField.setText(""); //clear text field
            case "=" -> { //solve equation currently held in expressionTextField
                Evaluator evaluator = new Evaluator(); //evaluator object so we can call function
                int answer = -1; //int to hold our result
                try {   //try/catch to catch InvalidTokenException
                    answer = evaluator.evaluateExpression(this.expressionTextField.getText());
                } catch (InvalidTokenException e) {
                    e.printStackTrace();
                }
                this.expressionTextField.setText(Integer.toString(answer)); //set the display to show our result
            }
            //since we are using a UI that only allows the user to click on the buttons we set, we know
            //we can safely set the default for all the other buttons to do the same thing since the
            //above buttons are the only ones that do anything different
            //this will add the number or operator given to the string in the display and show it
            default -> this.expressionTextField.setText(this.expressionTextField.getText() + command);
        }
    }
}
