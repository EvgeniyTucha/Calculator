import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA. sergeychukjtf 6764User: Evgeniy Tucha Date:
 * 06.11.13 Time: 20:23 To change this template use File | Settings | File
 * Templates.
 */

/**
 * @author Evgeniy Tucha
 */

public class Calculator {
    private final static char PLUS = '+';
    private final static char MINUS = '-';
    private final static char MULTIPLY = '*';
    private final static char DIVISION = '/';
    private final static char SQUARE = '^';
    private final static char LEFT_BRACKET = '(';
    private final static char RIGHT_BRACKET = ')';
    // private final static String regexp = "([-+]?[0-9]*)\\.?([0-9]+)([-+\\seE0-9]+)?";
    private final static String NUM_REGEXP = "\\d+(\\.|\\,)?\\d?";
    private static Pattern pattern = Pattern.compile(NUM_REGEXP);

    /**
     * @param expression string from input
     * @return expressionInRPN expression convert in reverse Polish Notation
     * @throws
     */
    private static ArrayList<String> reversePolishNotation(String expression) {
        expression = expression.replaceAll(" ", "");
        Matcher matcher = pattern.matcher(expression);
        Stack<Character> stack = new Stack<Character>();// temporary stack of operation
        Stack<String> outputString = new Stack<String>();// stack of numbers and operations
        for (int i = 0; i < expression.length(); i++) {
            char currentSymbol = expression.charAt(i);
            if (currentSymbol == RIGHT_BRACKET) {
                while (String.valueOf(stack.peek()).charAt(0) != LEFT_BRACKET) {
                    outputString.push(String.valueOf(stack.pop()));
                }
                stack.pop();
            } else if (currentSymbol == LEFT_BRACKET) {
                stack.push(LEFT_BRACKET);

            } else if (currentSymbol == PLUS || currentSymbol == MINUS
                    || currentSymbol == SQUARE || currentSymbol == MULTIPLY
                    || currentSymbol == DIVISION) {
                if (stack.size() == 0) {
                    stack.push(currentSymbol);
                } else if (priority(currentSymbol) > priority(String.valueOf(
                        stack.peek()).charAt(0))) {
                    stack.push(currentSymbol);
                } else {
                    while ((stack.size() != 0)
                            && (priority(String.valueOf(stack.peek()).charAt(0)) >= priority(currentSymbol))) {
                        outputString.push(String.valueOf(stack.pop()));
                    }
                    stack.push(currentSymbol);
                }
            } else if (matcher.find()) {
                String number = matcher.group();
                i += number.length() - 1;
                outputString.push(number);
            } else
                throw new IllegalArgumentException("Wrong expression");
        }
        ArrayList<String> expressionInRPN = new ArrayList<String>();
        for (int j = 0; j < outputString.size(); j++) {
            expressionInRPN.add(String.valueOf(outputString.get(j)));
        }
        while (!stack.isEmpty()) {
            expressionInRPN.add(String.valueOf(stack.pop()));
        }
        System.out.println(expressionInRPN);
        return expressionInRPN;

    }

    /**
     * This function
     *
     * @param operation
     * @return returnValue priority of current operation
     */
    private static int priority(char operation) {
        int returnValue;
        switch (operation) {
            case SQUARE:
                returnValue = 4;
                break;
            case MULTIPLY:
                returnValue = 3;
                break;
            case DIVISION:
                returnValue = 3;
                break;
            case PLUS:
                returnValue = 2;
                break;
            case MINUS:
                returnValue = 2;
                break;
            case LEFT_BRACKET:
                returnValue = 1;
                break;
            case RIGHT_BRACKET:
                returnValue = 1;
                break;
            default:
                returnValue = -1;
                break;
        }
        return returnValue;
    }

    /**
     * @param expression String
     */
    public static Double calculate(String expression) {
        Stack<String> stack = new Stack<String>();
        Double result;
        ArrayList<String> exp = reversePolishNotation(expression);
        Matcher matcher = pattern.matcher(expression);

        for (String str : exp) {
            if (str.equals("+")) {
                double secondN = Double.parseDouble(stack.pop());
                double firstN = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(firstN + secondN));
            } else if (str.equals("-")) {
                double firstN = Double.parseDouble(stack.pop());
                double secondN = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(secondN - firstN));
            } else if (str.equals("^")) {
                double firstN = Double.parseDouble(stack.pop());
                double secondN = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(Math.pow(secondN, firstN)));
            } else if (str.equals("*")) {
                double firstN = Double.parseDouble(stack.pop());
                double secondN = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(secondN * firstN));
            } else if (str.equals("/")) {
                double firstN = Double.parseDouble(stack.pop());
                double secondN = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(secondN / firstN));
            } else if (matcher.find()) {
                stack.push(matcher.group());
            } else {
                throw new IllegalArgumentException("Operation not supported");
            }
        }
        result = Double.parseDouble(stack.peek());
        return result;
    }

    public static void Window() {

        JFrame f = new JFrame("Calculator");
        f.setSize(280, 200);
        f.setVisible(true);
        final JTextArea inputDisplay = new JTextArea();
        final JTextArea outputDisplay = new JTextArea();

        JLabel label = new JLabel("Please input your expression");
        label.setPreferredSize(new Dimension(30, 15));
        f.getContentPane().add(label, BorderLayout.NORTH);
        f.add(inputDisplay);
        inputDisplay.setLocation(5, 15);
        inputDisplay.setSize(250, 30);
        f.add(outputDisplay);
        outputDisplay.setLocation(80, 105);
        outputDisplay.setSize(100, 20);
        JMenuBar mb = new JMenuBar();
        f.setJMenuBar(mb);

        JMenu mFile = new JMenu("File");
        mb.add(mFile);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });
        mFile.add(exit);

        final JMenuItem info = new JMenuItem("info");
        mb.add(info);
        info.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JOptionPane.showMessageDialog(info, "Evgeniy Tucha");
            }
        });

        final JButton but1 = new JButton("Calculate");
        f.add(but1);
        but1.setVisible(true);
        but1.setLocation(80, 50);
        but1.setSize(100, 50);
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    String tmp = inputDisplay.getText();
                    outputDisplay.setText(String.valueOf(calculate(tmp)));
                } catch (IllegalArgumentException e1) {
                    JOptionPane.showMessageDialog(but1, "Wrong string format!",
                            "warning", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Wrong string format!");
                }
            }
        });

        JButton but3 = new JButton("1");
        f.add(but3);
        but3.setVisible(false);
    }
}
