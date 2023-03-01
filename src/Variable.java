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

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Variable, which is used to store and manage variables in the calculator program.
 */
public class Variable {

    /**
     * A map to store all the variables and their corresponding values.
     */
    private static Map<String, Double> variables = new HashMap<>();

    /**
     * Determines if a variable already exist.
     * @param name the name of the variable
     * @return true if the variable exist and false otherwise
     */
    public static boolean doesVariableExist(String name) {
        return variables.containsKey(name);
    }

    /**
     * Return the value of a variable.
     * @param name the name of the variable
     * @return the value associated with the variable, or -1 if it doesn't exist
     */
    public static double getVariableValue(String name) {
        if(!doesVariableExist(name)) return -1;
        return variables.get(name);
    }

    /**
     * Checks if a string is a valid variable name.
     * @param name the name to check
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidIdentifier(String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    /**
     * This inner class represents a variable's name and value.
     */
     class VariableEntry{
        private String name;
        private double value;

        /**
         * Constructor to create a new VarValue object with a given name and value.
         * @param name The name of the variable.
         * @param value The value of the variable.
         */
        VariableEntry(String name, double value){
            this.name = name;
            this.value = value;
            variables.put(name,value);
        }

        /**
         * Getter method to retrieve the variable's name.
         * @return The variable's name.
         */
        public String getName() {
            return name;
        }

        /**
         * Setter method to set the variable's name.
         * @param name The variable's new name.
         * @throws IllegalArgumentException if the name is invalid
         */
        public void setName(String name) throws IllegalArgumentException {
            if (!isValidIdentifier(name)) {
                throw new IllegalArgumentException("Invalid variable name: " + name);
            }
            this.name = name;
        }

        /**
         * Getter method to retrieve the variable's value.
         * @return The variable's value.
         */
        public double getValue() {
            return value;
        }

        /**
         * Setter method to set the variable's value.
         * @param value The variable's new value.
         */
        public void setValue(double value) {
            this.value = value;
        }
    }

}
