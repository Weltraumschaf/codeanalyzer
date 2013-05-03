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
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for {@link Formatters}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FormattersTest {

    @Test
    public void testCreateDefault() {
        final Formatter first = Formatters.createDefault();
        assertThat(first, is(notNullValue()));
        assertThat(first, is(instanceOf(PlainText.class)));
        final Formatter second = Formatters.createDefault();
        assertThat(second, is(notNullValue()));
        assertThat(second, is(instanceOf(PlainText.class)));
        assertThat(first, is(sameInstance(second)));
    }

    @Test
    public void testCreateMarkdown() {
        final Formatter first = Formatters.createMarkdown();
        assertThat(first, is(notNullValue()));
        assertThat(first, is(instanceOf(Markdown.class)));
        final Formatter second = Formatters.createMarkdown();
        assertThat(second, is(notNullValue()));
        assertThat(second, is(instanceOf(Markdown.class)));
        assertThat(first, is(not(sameInstance(second))));
    }

    @Test
    public void testCreatePlainText() {
        final Formatter first = Formatters.createPlainText();
        assertThat(first, is(notNullValue()));
        assertThat(first, is(instanceOf(PlainText.class)));
        final Formatter second = Formatters.createPlainText();
        assertThat(second, is(notNullValue()));
        assertThat(second, is(instanceOf(PlainText.class)));
        assertThat(first, is(not(sameInstance(second))));
    }

}
