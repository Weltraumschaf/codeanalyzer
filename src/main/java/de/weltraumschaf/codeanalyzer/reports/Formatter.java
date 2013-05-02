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

    String TAB = "    ";

    public String title(String title);

    public String text(String text);

    public String iface(Interface iface);

    public String iementation(Class clazz);

    public Object nl();

}
