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
package de.weltraumschaf.codeanalyzer.reports.fmt;

import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;

/**
 * Formatter to generate plain text reports.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class PlainText extends BaseFormatter {
    private static final String SPLITTER = " +- ";

    @Override
    public String title(final String title) {
        return String.format("%s%n%n", trim(title));
    }

    @Override
    public String text(final String text) {
        return String.format(" %s%n", trim(text));
    }

    @Override
    public String iface(final InterfaceType iface) {
        if (null == iface) {
            throw new NullPointerException("Interface must not be null!");
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append(singleInterface(iface));

        if (iface.hasExtendingInterfaces()) {
            indent();

            for (final InterfaceType extender : iface.getExtendingInterfaces()) {
                buffer.append(indention())
                      .append(SPLITTER)
                      .append(singleInterface(extender));
            }

            exdent();
        }
        if (iface.hasImplementations()) {
            indent();

            for (final ClassType impls : iface.getImplementations()) {
                buffer.append(indention())
                      .append(SPLITTER)
                      .append(singleClass(impls));
            }

            exdent();
        }

        return buffer.toString();
    }

    /**
     * Formats single interface w/o implementations or extending interfaces.
     *
     * @param iface interface to format
     * @return formatted string
     */
    private String singleInterface(final InterfaceType iface) {
        return String.format("I %s %s (%s)%n",
                iface.getVisibility(),
                iface.getFullQualifiedName(),
                iface.getPosition());
    }

    @Override
    public String implementation(final ClassType clazz) {
        if (null == clazz) {
            throw new NullPointerException("Class must not be null!");
        }

        return singleClass(clazz);
    }

    private String singleClass(final ClassType clazz) {
        final String format;

        if (clazz.isAbstract()) {
            format = "C ABSTRACT %s %s (%s)%n";
        } else {
            format = "C %s %s (%s)%n";
        }

        return String.format(format, clazz.getVisibility(), clazz.getFullQualifiedName(), clazz.getPosition());
    }

    @Override
    public String implementation() {
        return String.format(" ^%n");
    }

}
