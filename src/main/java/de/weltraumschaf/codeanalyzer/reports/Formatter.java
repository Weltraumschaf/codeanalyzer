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
     * Format a new line.
     *
     * @return formatted string
     */
    String nl();

}
