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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Markdown implements Formatter {

    @Override
    public String title(String title) {
        return String.format("# %s%n%n", title);
    }

    @Override
    public String text(final String text) {
        return String.format("%s%n%n", text);
    }

    @Override
    public String iface(Interface iface) {
        return String.format(TAB + "%s %s: ", iface.getVisibility(), iface.getFullQualifiedName());
    }

    @Override
    public String iementation(final de.weltraumschaf.codeanalyzer.Class clazz) {
        return String.format(TAB + "  +- %s %s%n", clazz.getVisibility(), clazz.getFullQualifiedName());
    }

    @Override
    public String nl() {
        return String.format("%n");
    }

}
