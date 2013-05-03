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

import de.weltraumschaf.codeanalyzer.Class;
import de.weltraumschaf.codeanalyzer.Interface;

/**
 * Formatter for a report.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Formatter {

    /**
     * Format a title.
     *
     * @param title to format
     * @return formatted string
     */
    String title(String title);
    /**
     * Format a text.
     *
     * @param text to format
     * @return formatted string
     */
    String text(String text);
    /**
     * Format a interface.
     *
     * @param iface to format
     * @return formatted string
     */
    String iface(Interface iface);
    /**
     * Format a implementation.
     *
     * @param clazz to format
     * @return formatted string
     */
    String implementation(Class clazz);
    /**
     * Formats a string used before the first {@link #implementation(de.weltraumschaf.codeanalyzer.Class)
     * implementation}.
     *
     * @return formatted string
     */
    String implementation();
    /**
     * Format a new line.
     *
     * @return formatted string
     */
    String nl();
    /**
     * Set the pattern used for indentation.
     *
     * Implementing classes must provide a default pattern.
     *
     * @param pattern usually some white spaces
     */
    void setIndentationPattern(String pattern);
    /**
     * Increments the indentation level.
     */
    void indent();
    /**
     * Decrements the indentation level.
     */
    void exdent();
    /**
     * Returns the indentation string based on the indentation level and the pattern.
     *
     * If {@link #indent()} was never called or {@link #exdent()} was called as much so that
     * the indentation level is {@code 0}, then an empty string will be returned. In fact the
     * indentation string is generated by indentation level times indentation pattern. So
     * if you called {@link #indent()} two times you will get the indentation pattern two times.
     *
     * @return formatted string
     */
    String indention();

}
