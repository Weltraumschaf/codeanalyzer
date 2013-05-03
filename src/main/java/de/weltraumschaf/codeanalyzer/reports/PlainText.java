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
 * Formatter to generate plain text reports.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class PlainText extends BaseFormatter {

    @Override
    public String title(final String title) {
        return String.format("%s%n%n", trim(title));
    }

    @Override
    public String text(final String text) {
        return String.format(" %s%n", trim(text));
    }

    @Override
    public String iface(final Interface iface) {
        if (null == iface) {
            throw new NullPointerException("Interface must not be null!");
        }

        return String.format("%s %s%n", iface.getVisibility(), iface.getFullQualifiedName());
    }

    @Override
    public String implementation(final Class clazz) {
        if (null == clazz) {
            throw new NullPointerException("Class must not be null!");
        }

        return String.format("  +- %s %s%n", clazz.getVisibility(), clazz.getFullQualifiedName());
    }

    @Override
    public String nl() {
        return String.format("%n");
    }

}
