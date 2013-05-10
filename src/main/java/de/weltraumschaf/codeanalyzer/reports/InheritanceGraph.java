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
import de.weltraumschaf.codeanalyzer.Class;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class InheritanceGraph extends BaseReport {

    public InheritanceGraph() {
        super(Formatters.createDot());
    }

    @Override
    public String generate() {
        final StringBuilder buf = new StringBuilder();
        buf.append(format.start());
        format.indent();

        for (final Interface iface : data.getInterfaces()) {
            buf.append(format.iface(iface));
        }

        for (final Class clazz : data.getClasses()) {
            buf.append(format.implementation(clazz));
        }

        // TODO implement edges
        format.exdent();
        buf.append(format.end());
        return buf.toString();
    }

}
