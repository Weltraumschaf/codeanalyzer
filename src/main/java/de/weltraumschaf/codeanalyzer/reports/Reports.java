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

/**
 * Factory to create {@link Report reports}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Reports {

    /**
     * Hidden because pure static factory.
     */
    private Reports() {
        super();
    }

    /**
     * Gives a {@link Report} which analyzes {@link PackageEncapsulation package encapsulation}.
     *
     * @return always new instance
     */
    public static Report createPackageEncapsulation() {
        return new PackageEncapsulation();
    }

    public static Report createInheritanceGraph() {
        return new InheritanceGraph();
    }

}
