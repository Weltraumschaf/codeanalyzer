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
        return String.format(START);
    }

    @Override
    public String end() {
        return String.format(END);
    }

    @Override
    public String iface(final Interface iface) {
        final StringBuilder label = new StringBuilder();
        label.append(Stereotype.INTERFACE);
        label.append("\\n");
        label.append(mapVisibility(iface.getVisibility()));
        label.append("\\n");
        return indention() + formatNode(iface, label.toString());
    }

    @Override
    public String implementation(final Class clazz) {
        final StringBuilder label = new StringBuilder();
        label.append(Stereotype.CLASS);
        label.append("\\n");
        label.append(mapVisibility(clazz.getVisibility()));
        label.append("\\n");

        if (clazz.isAbstract()) {
            label.append(Stereotype.ABSTRACT);
            label.append("\\n");
        }

        return  indention() + formatNode(clazz, label.toString());
    }

    String formatNode(final Unit unit, final String label) {
        return String.format("%s [label=\"%s%s\", shape=box];%n", unit.getName(), label, unit.getFullQualifiedName());
    }

    private enum Stereotype {

        INTERFACE("interface"),
        CLASS("class"),
        ABSTRACT("abstract"),
        PUBLIC("public"),
        PACKAGE("package"),
        PROTECTED("protected"),
        PRIVATE("private");

        /**
         * ≪.
         *
         * http://www.fileformat.info/info/unicode/char/ab/index.htm
         */
        private static final String LAQUO = "\u00ab";
        /**
         * ≫.
         *
         * http://www.fileformat.info/info/unicode/char/00bb/index.htm
         */
        private static final String RAQUO = "\u00bb";
        private final String str;

        private Stereotype(String str) {
            this.str = String.format(str);
        }

        @Override
        public String toString() {
            return LAQUO + str + RAQUO;
        }
    }

}
