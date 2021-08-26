package com.tsystems.javaschool.tasks.calculator;

import java.util.*;

public class Calculator {

    private static String operators;
    private static String delimiters;

    public Calculator() {
        operators = "+-*/";
        delimiters = "() " + operators;

    }

    private boolean isDelimiter(String token) {
        if (token.length() != 1)
            return false;

        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i))
                return true;
        }

        return false;
    }

    private boolean isOperator(String token) {
        if (token.equals("u-"))
            return true;

        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i))
                return true;
        }

        return false;
    }

    private boolean isFloatRight(String token) {
        if (token.contains(","))
            return false;

        int numberOfDots = 0;

        for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) == '.')
                numberOfDots++;
        }

        if (numberOfDots == 1)
            return true;

        return false;
    }

    private int priority(String token) {
        if (token.equals("("))
            return 1;

        if (token.equals("+") || token.equals("-"))
            return 2;

        if (token.equals("*") || token.equals("/"))
            return 3;

        return 4;
    }

    public List<String> parse(String originalInput) {
        List<String> adaptInput = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(originalInput, delimiters, true);
        String previous = "";
        String current = "";

        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();

            if ((!previous.equals("")) && (isOperator(current) && isOperator(previous))) {
                return null;
            }

            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                return null;
            }
            else if (isDelimiter(current)) {

                if (current.equals("("))
                    stack.push(current);

                else if (current.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        adaptInput.add(stack.pop());

                        if (stack.isEmpty()) {
                            return null;
                        }
                    }
                    stack.pop();

                    if (!stack.isEmpty()) {
                        adaptInput.add(stack.pop());
                    }
                }
                else {
                    if (current.equals("-") && (previous.equals("") || (isDelimiter(previous)
                            && !previous.equals(")")))) {
                        current = "u-";
                    }
                    else {
                        while (!stack.isEmpty() && (priority(current) <= priority(stack.peek()))) {
                            adaptInput.add(stack.pop());
                        }

                    }
                    stack.push(current);
                }

            }

            else {
                if (current.contains(".") || current.contains(",")) {
                    if (!isFloatRight(current))
                        return null;

                }
                adaptInput.add(current);
            }
            previous = current;
        }

        while (!stack.isEmpty()) {

            if (isOperator(stack.peek())) adaptInput.add(stack.pop());
            else {
                return null;
            }
        }
        return adaptInput;
    }

    public String sum(String oneIn, String twoIn) {
        float one = Float.parseFloat(oneIn);
        float two = Float.parseFloat(twoIn);
        return String.valueOf(one + two);
    }

    public String subtraction(String oneIn, String twoIn) {
        float one = Float.parseFloat(oneIn);
        float two = Float.parseFloat(twoIn);
        return String.valueOf(one - two);
    }

    public String increase(String oneIn, String twoIn) {
        float one = Float.parseFloat(oneIn);
        float two = Float.parseFloat(twoIn);
        return String.valueOf(one * two);
    }

    public String divide(String oneIn, String twoIn) {
        float one = Float.parseFloat(oneIn);
        float two = Float.parseFloat(twoIn);
        if (two == 0.0F)
            return null;
        return String.valueOf(one / two);
    }

    public String invert(String oneIn) {
        float one = Float.parseFloat(oneIn);
        return String.valueOf(one * (-1));
    }

    private int operation(String token) {
        if (token.equals("+"))
            return 1;
        if (token.equals("-"))
            return 2;
        if (token.equals("*"))
            return 3;
        if (token.equals("/"))
            return 4;

        return 5;
    }

    public String removeZeros(String input) {
        StringBuilder result = new StringBuilder();
        char[] tmp = input.toCharArray();

        for (int i = 0; i < tmp.length; i++) {
            boolean flagOnNumbers = false;
            for (int k = i; k < tmp.length; k++) {
                if (tmp[k] != '0')
                    flagOnNumbers = true;
            }

            if (!flagOnNumbers) {
                for (int k = 0; k < i; k++) {
                    result.append(tmp[k]);
                }
                return result.toString();
            }

        }
        return input;
    }

    public String roundNum(String token) {
        if (token.length() > 4) {
            int tmpNumber = Integer.parseInt(String.valueOf(token.charAt(4)));
            if (tmpNumber > 4) {
                int tmpNumberToChange = Integer.parseInt(String.valueOf(token.charAt(3)));
                tmpNumberToChange++;
                StringBuilder finalValue = new StringBuilder();

                for (int i = 0; i < 3; i++) {
                    finalValue.append(token.charAt(i));
                }

                finalValue.append(tmpNumberToChange);
                return finalValue.toString();
            } else {
                StringBuilder finalValue = new StringBuilder();

                for (int i = 0; i < 4; i++) {
                    finalValue.append(token.charAt(i));
                }

                return removeZeros(finalValue.toString());
            }
        }
        return token;
    }

    public String count(List<String> input) {
        Deque<String> stack = new ArrayDeque<>();
        String current = "";
        for (int i = 0; i < input.size(); i++) {
            current = input.get(i);
            if (!isOperator(current)) {
                stack.add(current);
            } else {
                String two = "";
                String one = "";
                if (current.equals("u-")) {
                    two = stack.pollLast();
                } else {
                    two = stack.pollLast();
                    one = stack.pollLast();

                }
                int operator = operation(current);
                switch (operator) {
                    case 1:
                        stack.add(sum(one, two));
                        continue;

                    case 2:
                        stack.add(subtraction(one, two));
                        continue;

                    case 3:
                        stack.add(increase(one, two));
                        continue;

                    case 4:
                        String res = divide(one, two);
                        if (res == null)
                            return null;
                        stack.add(res);
                        continue;

                    default:
                        stack.add(invert(two));
                }
            }
        }
        if (!stack.isEmpty()) {
            String res = stack.pop();
            String[] splitter = res.split("\\.");
            if (splitter[1].equals("0")) {
                return splitter[0];
            } else {
                String newEnd = roundNum(splitter[1]);
                return splitter[0] + "." + newEnd;
            }

        } else {
            return null;
        }
    }

    public String evaluate(String input) {
        if (input == null)
            return null;
        List<String> expression = parse(input);
        if (expression == null)
            return null;
        return count(expression);
    }
}
