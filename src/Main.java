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

import java.util.Scanner;

/**
 * The main class for the Smart Calculator program.
 * This program allows the user to perform arithmetic calculations and store variables.
 */
public class Main {

    /**
     * The main method of the program.
     * Reads input from the user, evaluates expressions, and prints the results.
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // create objects for variable storage and command handling
        Command command = new Command();

        // create scanner for reading user input
        Scanner scanner = new Scanner(System.in);

        // print welcome message
        System.out.println("Welcome to Smart Calculator!");

        // loop until user enters command to exit
        while (true) {
            System.out.print("\nEnter an expression: ");
            // read input from user
            String input = scanner.nextLine();

            // check if input is a command and handle it if necessary
            if (command.isCommand(input)) {
                command.getMessage();
                if (command.is("/exit")) break;
                continue;
            }

            // skip empty input
            if (input.isBlank() || input.isEmpty()) continue;

            try {
                // evaluate expression and print result
                String result = evaluateExpression(input);
                if (result.matches(".*\\|.*")){
                    String varName = result.split("\\|",2)[0];
                    String varValue = result.split("\\|",2)[1];
                   System.out.printf("%s ==> %s%n",varName,varValue);
                    continue;
                }
                System.out.println("Answer: " + result);
            } catch (IllegalArgumentException e) {
                // print error message if expression is invalid
                 System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Evaluates an arithmetic expression using the Calculator class.
     * @param expression the expression to be evaluated
     * @return the result of the evaluation
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static String evaluateExpression(String expression) throws IllegalArgumentException {
        // create a new Calculator object and use it to evaluate the expression
        Calculator calculator = new Calculator();
        return calculator.evaluateExpression(expression);
    }
}