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

import de.weltraumschaf.codeanalyzer.Class;
import de.weltraumschaf.codeanalyzer.Interface;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for {@link BaseFormatter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseFormatterTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final BaseFormatter sut = new BaseFormatterStub();

    @Test
    public void trim() {
        assertThat(sut.trim(null), is(equalTo("")));
        assertThat(sut.trim(""), is(equalTo("")));
        assertThat(sut.trim("   "), is(equalTo("")));
        assertThat(sut.trim("foo"), is(equalTo("foo")));
        assertThat(sut.trim("  foo "), is(equalTo("foo")));
    }

    @Test
    public void testIndent_withDefaultPattern() {
        assertThat(sut.indention(), is(equalTo("")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo(" ")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo("  ")));
        assertThat(sut.indention(), is(equalTo("  ")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo("   ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("  ")));
        assertThat(sut.indention(), is(equalTo("  ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo(" ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("")));
    }

    @Test
    public void testIndent_withCustomPattern() {
        sut.setIndentationPattern("    ");
        assertThat(sut.indention(), is(equalTo("")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo("    ")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo("        ")));
        assertThat(sut.indention(), is(equalTo("        ")));
        sut.indent();
        assertThat(sut.indention(), is(equalTo("            ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("        ")));
        assertThat(sut.indention(), is(equalTo("        ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("    ")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("")));
        sut.exindent();
        assertThat(sut.indention(), is(equalTo("")));
    }

    @Test
    public void setIndentationPattern_throsExceptionOnNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Pattern must not be null!");
        sut.setIndentationPattern(null);
    }

    @Test
    public void setIndentationPattern_throsExceptionOnEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Pattern must not be empty string!");
        sut.setIndentationPattern("");
    }

    @Test
    public void setIndentationPattern_throsExceptionIfIndentionLevelIsNotZero() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("You can only change indnetation pattern if indentation level is 0! Current level is 1.");
        sut.indent();
        sut.setIndentationPattern("  ");
    }

    private static final class BaseFormatterStub extends BaseFormatter {

        @Override
        public String title(String title) {
            return "";
        }

        @Override
        public String text(String text) {
            return "";
        }

        @Override
        public String iface(Interface iface) {
            return "";
        }

        @Override
        public String implementation(Class clazz) {
            return "";
        }

        @Override
        public String nl() {
            return "";
        }

        @Override
        public String implementation() {
            return "";
        }
    }
}
