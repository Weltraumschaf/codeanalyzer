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

    /**
     * Spaces used as tabulator.
     */
    protected static final String TAB = "    ";

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

}
