/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.codeanalyzer.reports;

/**
 * Basic formatter functionality.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseFormatter implements Formatter {

    private static final String DEFAULT_INDENTATION_PATTERN = " ";
    /**
     * Spaces used as tabulator.
     */
    protected static final String TAB = "    ";
    private int indentionLevel = 0;
    private String indentationPattern = DEFAULT_INDENTATION_PATTERN;

    /**
     * Null safe trim method.
     *
     * @param input trimmed input
     * @return trimmed string, if {@code null} was given an empty string will be returned
     */
    String trim(final String input) {
        return null == input
                    ? ""
                    : input.trim();
    }



    @Override
    public void setIndentationPattern(final String pattern) {
        if (indentionLevel > 0) {
            throw new IllegalStateException(
                String.format("You can only change indnetation pattern if indentation level is 0! Current level is %d.",
                              indentionLevel));
        }

        if (null == pattern) {
            throw new NullPointerException("Pattern must not be null!");
        }

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be empty string!");
        }

        indentationPattern = pattern;
    }

    @Override
    public void indent() {
        ++indentionLevel;
    }

    @Override
    public void exindent() {
        --indentionLevel;

        if (indentionLevel < 0) {
            indentionLevel = 0;
        }
    }

    @Override
    public String indention() {
        final StringBuilder buf = new StringBuilder();

        for (int i = 0; i < indentionLevel; ++i) {
            buf.append(indentationPattern);
        }

        return buf.toString();
    }

}