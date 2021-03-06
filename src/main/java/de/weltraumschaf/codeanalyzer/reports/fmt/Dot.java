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

import com.google.common.collect.Maps;
import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import de.weltraumschaf.codeanalyzer.types.Type;
import de.weltraumschaf.codeanalyzer.types.Visibility;
import java.util.Map;

/**
 * Formatter to generate Dot language.
 *
 * @see http://en.wikipedia.org/wiki/DOT_language
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Dot extends BaseFormatter {

    /**
     * Start of every Dot file.
     */
    private static final String START_FORMAT = "/*%n"
            + " * Auto generated type hierarchy dot file.%n"
            + " *%n"
            + " * @see http://en.wikipedia.org/wiki/DOT_language%n"
            + " * @see http://www.graphviz.org/content/dot-language%n"
            + " */%n"
            + "digraph TypesHierarchy {%n%n";
    /**
     * End of every Dot file.
     */
    private static final String END_FORMAT = "%n}%n";
    /**
     * Format of Dot node.
     */
    private static final String NODE_FORMAT = "%s [label=\"%s%s\", shape=box];%n";
    private static final String STYLE_DOTTED = "[style=dotted]";
    /**
     * Format of Dot edge.
     */
    private static final String EDGE_FORMAT = "%s -> %s;%n";
    /**
     * Maps {@link Visibility} to {@link Stereotype}.
     */
    private static final Map<Visibility, Stereotype> lookup = Maps.newHashMap();
    static {
        lookup.put(Visibility.PUBLIC, Stereotype.PUBLIC);
        lookup.put(Visibility.PACKAGE, Stereotype.PACKAGE);
        lookup.put(Visibility.PROTECTED, Stereotype.PROTECTED);
        lookup.put(Visibility.PRIVATE, Stereotype.PRIVATE);
    }

    /**
     * Maps {@link Visibility} to a according {@link Stereotype}.
     *
     * @param visibility mapped visibility
     * @return according enum
     */
    static Stereotype mapVisibility(final Visibility visibility) {
        return lookup.get(visibility);
    }

    @Override
    public String start() {
        return String.format(START_FORMAT);
    }

    @Override
    public String end() {
        return String.format(END_FORMAT);
    }

    @Override
    public String iface(final InterfaceType iface) {
        final StringBuilder label = new StringBuilder();
        label.append(Stereotype.INTERFACE);
        label.append("\\n");
        label.append(mapVisibility(iface.getVisibility()));
        label.append("\\n");
        return indention() + formatNode(iface, label.toString());
    }

    @Override
    public String implementation(final ClassType clazz) {
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

    /**
     * Formats a {@link Type} as Dot node.
     *
     * @param t
     * @param label
     * @return
     */
    String formatNode(final Type t, final String label) {
        return String.format(NODE_FORMAT, t.getName(), label, t.getFullQualifiedName());
    }

    String formatEdge(final Type t1, final Type t2) {
        return String.format(EDGE_FORMAT, t1.getName(), t2.getName());
    }

    /**
     * Helps formatting stereotypes of a {@link Type type}.
     *
     * A stereotype is represented by its value preceded by '≪' and followed by '≫'.
     */
    private enum Stereotype {

        /**
         * InterfaceType stereotype.
         */
        INTERFACE("interface"),
        /**
         * ClassType stereotype.
         */
        CLASS("class"),
        /**
         * Abstract stereotype.
         */
        ABSTRACT("abstract"),
        /**
         * Public stereotype.
         */
        PUBLIC("public"),
        /**
         * Package stereotype.
         */
        PACKAGE("package"),
        /**
         * Protected stereotype.
         */
        PROTECTED("protected"),
        /**
         * Private stereotype.
         */
        PRIVATE("private");

        /**
         * Special right double arrow character '≪'.
         *
         * @see http://www.fileformat.info/info/unicode/char/ab/index.htm
         */
        private static final String LAQUO = "\u00ab";
        /**
         * Special left double arrow character '≫'.
         *
         * @see http://www.fileformat.info/info/unicode/char/00bb/index.htm
         */
        private static final String RAQUO = "\u00bb";
        /**
         * Stereo type value.
         */
        private final String str;

        /**
         * Dedicated constructor.
         *
         * @param str value of stereotype
         */
        private Stereotype(final String str) {
            this.str = String.format(str);
        }

        @Override
        public String toString() {
            return LAQUO + str + RAQUO;
        }
    }

}
