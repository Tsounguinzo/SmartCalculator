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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides methods for tokenizing and parsing input expressions.
 */
public class InputParser {

    /**
     * Tokenizes input string and returns a list of tokens.
     * If the input is a variable declaration, it returns a special list with "newVariable!" as the first element.
     *
     * @param input the input expression
     * @return a list of tokens
     * @throws IllegalArgumentException if the input is invalid
     */
    public static List<String> tokenize(String input) {
        if (input.matches(".*=+.*")) { //variable declaration

            if (input.matches(".*={2,}.*")) {
                //throw new IllegalArgumentException("Invalid assignment: your expression has more than one '='");
                throw new IllegalArgumentException("Invalid assignment");
            }

            String[] variable = input.split("=", 2);
            String varName = variable[0].strip();
            String varValue = variable[1].strip();

            if (!Variable.isValidIdentifier(varName)) {
                //throw new IllegalArgumentException("Invalid identifier name: " + varName);
                throw new IllegalArgumentException("Invalid identifier");
            }

            //if the value contain letters or might be an expression
                if (varValue.matches(".*[a-zA-Z]+.*")) {
                varValue = String.join(" ", tokenize(varValue));
            }

            return List.of("newVariable!", varName, varValue);
        } else { // expression
            String signRepetitionChecker = "([+\\-/*])\\1+";
            String evenNumberOfMinusChecker = "(-{2})+";
            String plusMinusChecker = "([+]-)|(-[+])";

            //******************** to be removed *******************/
            String multiplicationSequenceCheck = ".*\\*{2,}.*";
            String divisionSequenceCheck = ".*/{2,}.*";

            // If the input contains more than one consecutive multiplication or division operator
            if (input.matches(multiplicationSequenceCheck) || input.matches(divisionSequenceCheck)) {
                // throw new IllegalArgumentException("Invalid expression");
                throw new IllegalArgumentException("Invalid expression");
            }

            //******************************************************/

            String newString = input.replaceAll(evenNumberOfMinusChecker, "+")
                    .replaceAll(signRepetitionChecker, "$1")
                    .replaceAll(plusMinusChecker, "-")
                    .strip();

            String[] patterns = {
                    "\\d+(\\.\\d+)?", // Matches numbers
                    "[a-zA-Z_]\\w*", // Matches variable names
                    "\\(", // Matches left parentheses
                    "\\)", // Matches right parentheses
                    "\\*", // Matches multiplication operators
                    "/", // Matches division operators
                    "\\+", // Matches addition operators
                    "-", // Matches subtraction operators
                    "\\^" // Matches exponentiation operators
            };
            String regex = String.join("|", patterns);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newString);
            List<String> tokens = new ArrayList<>();
            while (matcher.find()) {
                tokens.add(matcher.group());
            }
            return tokens;
        }
    }

        /**
        * Parses a list of tokens into an expression.
        * @param tokens the list of tokens to be parsed
        * @return a list of objects representing the parsed expression
        * @throws IllegalArgumentException if the expression is invalid or contains unknown variables
         */
    public static List<Object> parseExpression(List<String> tokens) {
        List<Object> expression = new ArrayList<>();
        Variable variable = new Variable();
        if (tokens.get(0).equals("newVariable!")) {
            String varName = tokens.get(1);
            String varValue = tokens.get(2);
            double value;
            // If the variable value is a number, parse it as a double
            try {
                value = Double.parseDouble(varValue);
            } catch (NumberFormatException e) {
                // If the variable value is an expression, evaluate it using the calculator
                value = Double.parseDouble(new Calculator().evaluateExpression(varValue));
            }
            expression.add(variable.new VariableEntry(varName,value));
        } else {
            for (String token : tokens) {
                if (token.matches(".*\\d+(\\.\\d+)?.*")) {
                    try {
                        expression.add(Double.parseDouble(token));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid expression");
                    }
                } else if (token.matches(".*[a-zA-Z].*")) {
                    if (!Variable.isValidIdentifier(token)) {
                        throw new IllegalArgumentException("Invalid identifier");
                    } else if (!Variable.doesVariableExist(token)) {
                        throw new IllegalArgumentException("Unknown variable '" + token + '\'');
                    } else {
                        expression.add(Variable.getVariableValue(token));
                    }
                } else {
                    expression.add(token);
                }
            }
        }
        return expression;
    }
}
