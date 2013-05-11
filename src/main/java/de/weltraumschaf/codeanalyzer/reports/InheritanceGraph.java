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

import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import de.weltraumschaf.codeanalyzer.types.ClassType;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class InheritanceGraph extends BaseReport {

    private static final String EDGE_FORMAT = "%s -> %s;%n";

    public InheritanceGraph() {
        super(Formatters.createDot());
    }

    @Override
    public String generate() {
        final StringBuilder buf = new StringBuilder();
        buf.append(format.start());
        format.indent();

        for (final InterfaceType iface : data.getInterfaces()) {
            buf.append(format.iface(iface));
        }

        for (final ClassType clazz : data.getClasses()) {
            buf.append(format.implementation(clazz));
        }

        buf.append(format.nl());

        for (final InterfaceType iface : data.getInterfaces()) {
            for (final InterfaceType extended : iface.getExtendedInterfaces()) {
                buf.append(format.indention()).append(String.format(EDGE_FORMAT, iface.getName(), extended.getName()));
            }
        }

        for (final ClassType clazz : data.getClasses()) {
            if (clazz.doesExtendClass()) {
                buf.append(format.indention()).append(String.format(EDGE_FORMAT, clazz.getName(), clazz.extendedClass().getName()));
            }

            for (final InterfaceType implemented : clazz.interfaces()) {
                buf.append(format.indention()).append(String.format(EDGE_FORMAT, clazz.getName(), implemented.getName()));
            }
        }

        format.exdent();
        buf.append(format.end());
        return buf.toString();
    }

}
