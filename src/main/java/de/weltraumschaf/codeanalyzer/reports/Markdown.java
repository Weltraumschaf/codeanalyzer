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

import de.weltraumschaf.codeanalyzer.Interface;

/**
 * Formatter to generate Markdown reports.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Markdown extends BaseFormatter {

    @Override
    public String title(String title) {
        return String.format("# %s%n%n", trim(title));
    }

    @Override
    public String text(final String text) {
        return String.format("%s%n%n", trim(text));
    }

    @Override
    public String iface(Interface iface) {
        if (null == iface) {
            throw new NullPointerException("Interface must not be null!");
        }

        return String.format(TAB + "%s %s%n", iface.getVisibility(), iface.getFullQualifiedName());
    }

    @Override
    public String implementation(final de.weltraumschaf.codeanalyzer.Class clazz) {
        if (null == clazz) {
            throw new NullPointerException("Class must not be null!");
        }

        return String.format(TAB + " +- %s %s%n", clazz.getVisibility(), clazz.getFullQualifiedName());
    }

    @Override
    public String implementation() {
        return String.format(" ^%n");
    }

    @Override
    public String nl() {
        return String.format("%n");
    }
    
}
