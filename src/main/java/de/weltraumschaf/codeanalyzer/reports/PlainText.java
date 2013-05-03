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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class PlainText implements Formatter {


    @Override
    public String title(final String title) {
        return String.format("%s%n%n", title);
    }

    @Override
    public String text(final String text) {
        return String.format(" %s%n", text);
    }

    @Override
    public String iface(final Interface iface) {
        return String.format("%s %s%n", iface.getVisibility(), iface.getFullQualifiedName());
    }

    @Override
    public String iementation(final Class clazz) {
        return String.format("  +- %s %s%n", clazz.getVisibility(), clazz.getFullQualifiedName());
    }

    @Override
    public String nl() {
        return String.format("%n");
    }

}
