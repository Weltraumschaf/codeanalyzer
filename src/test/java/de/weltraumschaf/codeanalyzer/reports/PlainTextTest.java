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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for {@link PlainText}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PlainTextTest {

    private final Formatter sut = new PlainText();

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
