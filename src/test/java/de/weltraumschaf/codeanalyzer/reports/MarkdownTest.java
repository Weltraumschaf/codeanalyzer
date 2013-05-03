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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Unit tests for {@link Markdown}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MarkdownTest {

    private final Formatter sut = new Markdown();

    @Test @Ignore
    public void testTitle() {
    }

    @Test @Ignore
    public void testText() {
    }

    @Test @Ignore
    public void testIface() {
    }

    @Test @Ignore
    public void testIementation() {
    }

    @Test
    public void testNl() {
        assertThat(sut.nl(), is(equalTo(System.getProperty("line.separator"))));
    }

}