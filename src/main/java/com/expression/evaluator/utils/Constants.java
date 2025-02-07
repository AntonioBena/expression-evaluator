package com.expression.evaluator.utils;

public class Constants {
    private Constants() {
    }

    public static final String CONDITIONS_REGEX = ">=|<=|==|!=|>|<";
    public static final String LOGICAL_REGEX = "\\)\\s*(?i)(AND|OR|NOT|&&|\\|\\||!)\\s*\\(";

    public static final String AND_REGEX_MATCH = ".*(?i)\\s&&\\s.*";
    public static final String OR_REGEX_MATCH = ".*(?i)\\s\\|\\|\\s.*";
    public static final String NOT_REGEX_MATCH = ".*(?i)\\s!\\s.*";

    public static final String AND_KEYWORD_REGEX_MATCH = ".*(?i)\\sAND\\s.*";
    public static final String OR_KEYWORD_REGEX_MATCH = ".*(?i)\\sOR\\s.*";
    public static final String NOT_KEYWORD_REGEX_MATCH = ".*(?i)\\sNOT\\s.*";

    public static final String AND_REGEX = "(?i)\\s&&\\s";
    public static final String OR_REGEX = "(?i)\\s\\|\\|\\s";
    public static final String NOT_REGEX = "(?i)\\s!\\s";
    public static final String AND_KEYWORD_REGEX = "(?i)\\sAND\\s";
    public static final String OR_KEYWORD_REGEX = "(?i)\\sOR\\s";
    public static final String NOT_KEYWORD_REGEX = "(?i)\\sNOT\\s";

    public static final String DOT_REGEX = "\\.";
    public static final String ALL_PARENTHESIS_REGEX = "\\(|\\)";

    public static final String EQUAL = "==";
    public static final String NOT_EQUAL = "!=";
    public static final String LESS_THAN = "<";
    public static final String MORE_THAN = ">";
    public static final String LESS_THAN_OR_EQUAL = "<=";
    public static final String MORE_THAN_OR_EQUAL = ">=";

    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";
    public static final String AND_K = "&&";
    public static final String OR_K = "||";
    public static final String NOT_K = "!";
}