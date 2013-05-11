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

import de.weltraumschaf.codeanalyzer.reports.fmt.Formatter;
import de.weltraumschaf.codeanalyzer.UnitCollector;

/**
 * Generates reports.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Report {

    /**
     * Analyzed data.
     *
     * @param data holds the analyzed data.
     */
    void setData(UnitCollector data);
    /**
     * Formatter to generate output.
     *
     * @param fmt formatter instance
     */
    void setFormatter(Formatter fmt);
    /**
     * Generates report and returns a formatted string.
     *
     * @return report as formatted string
     */
    String generate();

}
