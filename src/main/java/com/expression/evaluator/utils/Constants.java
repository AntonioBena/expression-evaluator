package com.expression.evaluator.utils;

public class Constants {
    private Constants() {
    }

    public static final String CONDITIONS_REGEX = ">=|<=|==|!=|>|<";
    public static final String LOGICAL_REGEX = "(?i)\\sOR\\s|\\sAND\\s|\\sNOT\\s";
    public static final String AND_NOT_REGEX = "(?i)\\s&&\\s|\\s!\\s";
    public static final String OR_REGEX = "\\|\\|";
    public static final String DOT_REGEX = "\\.";

    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

    public static final String EQUAL = "==";
    public static final String NOT_EQUAL = "!=";
    public static final String LESS_THAN = "<";
    public static final String MORE_THAN = ">";
    public static final String LESS_THAN_OR_EQUAL = "<=";
    public static final String MORE_THAN_OR_EQUAL = ">=";

    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";
}