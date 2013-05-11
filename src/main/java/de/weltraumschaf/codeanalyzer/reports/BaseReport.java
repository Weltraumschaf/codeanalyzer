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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseReport implements Report {

    /**
     * Collected data from which the report will be generated.
     */
    protected UnitCollector data;
    /**
     * Used to format the output string.
     */
    protected Formatter format;

    /**
     * Dedicated constructor.
     *
     * @param fmt used to format the output string
     * CHECKSTYLE:OFF
     * @throws NullPointerException if fmt is {@code null}
     * CHECKSTYLE:ON
     */
    public BaseReport(final Formatter fmt) {
        super();
        setFormatter(fmt);
    }


    @Override
    public void setData(final UnitCollector data) {
        this.data = data;
    }

    @Override
    public final void setFormatter(final Formatter fmt) {
        if (null == fmt) {
            throw new NullPointerException("Formatter must not be null!");
        }

        this.format = fmt;
    }
}
