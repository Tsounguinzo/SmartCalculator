/*
 * Copyright (c) 2023 Beaudelaire Tsoungui Nzodoumkouo. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under My consent.
 *
 * This code is shared on GitHub in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Please contact Me at +1 438 509 3906
 * or LinkedIn: https://www.linkedin.com/in/beaudelaire-tsoungui-nzodoumkouo-809744231
 * if you need additional information or have any questions.
 */

package calculator;

import java.util.*;

/**
 * The Calculator class performs arithmetic operations on expressions using infix-to-postfix conversion and a stack.
 */
public class Calculator {
    /** A map containing arithmetic operators and their corresponding Operation objects. */
    private final Map<String, Operation> operations;

    /**
     * Constructs a new Calculator object with pre-defined arithmetic operators.
     */
    public Calculator() {
        this.operations = Map.of(
                "^", new Power(),
                "*", new Multiplication(),
                "/", new Division(),
                "+", new Addition(),
                "-", new Subtraction()
        );
    }

    /**
     * Evaluates an arithmetic expression and returns the result.
     *
     * @param input the input arithmetic expression to evaluate.
     * @return the result of the evaluation.
     * @throws IllegalArgumentException if the input expression is invalid.
     */
    public String evaluateExpression(String input) {
        // Tokenize the input expression
        List<String> tokens = InputParser.tokenize(input);
        // Parse the tokenized expression into a list of objects
        List<Object> expression = InputParser.parseExpression(tokens);
        // Convert the infix expression to postfix notation
        List<Object> postfixExpression = infixToPostfix(expression);

        // Initialize a stack to hold operands
        ArrayList<Double> stack = new ArrayList<>();
        // Initialize the result to 0
        double result = 0;

        // Loop through each token in the postfix expression
        for (Object token : postfixExpression) {
            if (token instanceof Double operand) {
                // If the token is a number, push it onto the stack and set the result to the operand
                stack.add(operand);
                result = operand;
            } else if (token instanceof Variable.VariableEntry var) {
                // If the token is a variable, return the variable value sign with its name
                String name = var.getName();
                double value = var.getValue();
                return name + "|" + value;

            } else if (token instanceof String operator) {
                // If the token is an operator, calculate the result using the last two operands on the stack
                if (operations.containsKey(operator)) {
                    Operation operation = operations.get(operator);
                    result = operation.calculate(stack.get(stack.size()-2), stack.get(stack.size()-1));
                    stack.remove(stack.size()-1);
                    stack.remove(stack.size()-1);
                    stack.add(result);
                } else {
                    // If the operator is not recognized, throw an exception with a message indicating an invalid expression
                    throw new IllegalArgumentException("Invalid expression");
                }
            } else {
                // If the token is not a number, variable, or operator, throw an exception with a message indicating an invalid token
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        // Return the final result
        return String.valueOf(result);
    }


    /**
     * Converts an infix expression to postfix notation.
     *
     * @param infix the infix expression to convert.
     * @return a list representing the postfix notation of the input expression.
     * @throws IllegalArgumentException if the input expression is invalid.
     */
    private List<Object> infixToPostfix(List<Object> infix){
        // Initialize a list to hold the postfix expression
        List<Object> result = new ArrayList<>();
        // Initialize a stack to hold operators and parentheses
        Deque<Object> stack = new ArrayDeque<>();

        // Process each symbol in the infix expression
        for (Object symbol : infix){
            if (symbol instanceof Variable.VariableEntry var) {
                // If the symbol is a variable, add it to the output list
                result.add(var);
            }else if (symbol instanceof Double num){
                // If the symbol is a number, add it to the output list
                result.add(num);
            } else if (symbol instanceof String str) {
                if(str.equals("(")){
                    // If the symbol is a left parenthesis, push it onto the stack
                    stack.push(str);
                } else if (str.equals(")")) {
                    // If the symbol is a right parenthesis, pop operators off the stack
                    // and add them to the output list until a left parenthesis is found
                    Object next = stack.peek();
                    while (true){
                        try {
                            assert next != null;
                            if (next.equals("(")) break;
                            result.add(stack.pop());
                            next = stack.peek();
                        }catch (AssertionError e){
                            throw new IllegalArgumentException("Invalid expression");
                        }
                    }
                    stack.pop(); // Remove the left parenthesis from the stack
                } else if (operations.containsKey(str)) {
                    // If the symbol is an operator, pop operators off the stack and add them
                    // to the output list until an operator with lower precedence is found,
                    // or the stack is empty, or a left parenthesis is encountered
                    while (!stack.isEmpty() &&
                            precidence(stack.peek()) >= precidence(str)){
                        result.add(stack.pop());
                    }
                    stack.push(str);
                } else {
                    // If the symbol is none of the above, it is invalid
                    throw new IllegalArgumentException("Invalid expression");
                }
            } else {
                // If the symbol is none of the above, it is invalid
                throw new IllegalArgumentException("Invalid token: " + symbol);
            }
        }

        // Pop any remaining operators off the stack and add them to the output list
        while (!stack.isEmpty()){
            result.add(stack.pop());
        }

        return result;
    }

/**
 * Returns the precedence value of an operator.
 *
 * @param test the operator to check the precedence of.
 * @return an integer representing the precedence
 */
    private int precidence(Object test){
        return switch ((String) test) {
            case "^" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }
}
