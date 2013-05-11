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

/**
 * Factory to create {@link Formatter formatters}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Formatters {

    /**
     * Default formatter.
     */
    private static final Formatter DEFAULT = createPlainText();

    /**
     * Hidden for static factory.
     */
    private Formatters() {
        super();
    }

    /**
     * Creates a default formatter.
     *
     * @return always same instance
     */
    public static Formatter createDefault() {
        return  DEFAULT;
    }

    /**
     * Creates a <a href="http://daringfireball.net/projects/markdown/">markdown
     * </a> formatter.
     *
     * @return always new instance
     */
    public static Formatter createMarkdown() {
        return new Markdown();
    }

    /**
     * Creates a plain text formatter.
     *
     * @return always new instance
     */
    public static Formatter createPlainText() {
        return new PlainText();
    }

    public static Formatter createDot() {
        return new Dot();
    }

}
