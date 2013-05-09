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

import com.google.common.collect.Maps;
import de.weltraumschaf.codeanalyzer.Class;
import de.weltraumschaf.codeanalyzer.Interface;
import de.weltraumschaf.codeanalyzer.Unit;
import de.weltraumschaf.codeanalyzer.Visibility;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Dot extends BaseFormatter {

    private static final String START = "/*%n"
            + " * Auto generated type hierarchy dot file.%n"
            + " *%n"
            + " * @see http://en.wikipedia.org/wiki/DOT_language%n"
            + " * @see http://www.graphviz.org/content/dot-language%n"
            + " */%n"
            + "digraph TypesHierarchy {%n%n";
    private static final String END = "%n}%n";

    private static final Map<Visibility, Stereotype> lookup = Maps.newHashMap();

    static {
        lookup.put(Visibility.PUBLIC, Stereotype.PUBLIC);
        lookup.put(Visibility.PACKAGE, Stereotype.PACKAGE);
        lookup.put(Visibility.PROTECTED, Stereotype.PROTECTED);
        lookup.put(Visibility.PRIVATE, Stereotype.PRIVATE);
    }

    static Stereotype mapVisibility(final Visibility visibility) {
        return lookup.get(visibility);
    }
    
    @Override
    public String start() {
        return START;
    }

    @Override
    public String end() {
        return END;
    }

    @Override
    public String iface(final Interface iface) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String implementation(final Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String formatNode(final Interface iface) {
        final StringBuilder label = new StringBuilder();
        label.append(Stereotype.INTERFACE);
        label.append(mapVisibility(iface.getVisibility()));
        return formatNode(iface, label.toString());
    }

    String formatNode(final Class clazz) {
        final StringBuilder label = new StringBuilder();
        label.append(Stereotype.CLASS);
        label.append(mapVisibility(clazz.getVisibility()));

        if (clazz.isAbstract()) {
            label.append(Stereotype.ABSTRACT);
        }

        return formatNode(clazz, label.toString());
    }

    String formatNode(final Unit unit, final String label) {
        return String.format("%s [label=\"%s%s\", shape=box];%n", unit.getName(), label, unit.getFullQualifiedName());
    }

    private enum Stereotype {

        INTERFACE("≪interface≫%n"),
        CLASS("≪class≫%n"),
        ABSTRACT("≪abstract≫%n"),
        PUBLIC("≪public≫%n"),
        PACKAGE("≪package≫%n"),
        PROTECTED("≪protected≫%n"),
        PRIVATE("≪private≫%n");
        private final String str;

        private Stereotype(String str) {
            this.str = String.format(str);
        }

        @Override
        public String toString() {
            return str;
        }
    }

}
