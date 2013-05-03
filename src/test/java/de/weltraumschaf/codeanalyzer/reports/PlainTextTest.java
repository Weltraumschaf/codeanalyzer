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

import de.weltraumschaf.codeanalyzer.Interface;
import de.weltraumschaf.codeanalyzer.Class;
import de.weltraumschaf.codeanalyzer.Package;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for {@link PlainText}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PlainTextTest {

    private static final String NL = System.getProperty("line.separator");
    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final Formatter sut = new PlainText();

    @Test
    public void testTitle() {
        assertThat(sut.title(null), is(equalTo("" + NL + NL)));
        assertThat(sut.title(""), is(equalTo("" + NL + NL)));
        assertThat(sut.title("   "), is(equalTo("" + NL + NL)));
        assertThat(sut.title("Foo"), is(equalTo("Foo" + NL + NL)));
    }

    @Test
    public void testText() {
        assertThat(sut.text(null), is(equalTo(" " + NL)));
        assertThat(sut.text(""), is(equalTo(" " + NL)));
        assertThat(sut.text("   "), is(equalTo(" " + NL)));
        assertThat(sut.text("Foo"), is(equalTo(" Foo" + NL)));
    }

    @Test
    public void testIface_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Interface must not be null!");
        sut.iface(null);
    }

    @Test
    public void testIface() {
        final Interface iface = new Interface(Package.create("foo.bar"), "Baz");
        assertThat(sut.iface(iface), is(equalTo("PUBLIC foo.bar.Baz" + NL)));
    }

    @Test
    public void testIementation_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Class must not be null!");
        sut.implementation(null);
    }

    @Test
    public void testIementation() {
        final Class clazz = new Class(Package.create("foo.bar"), "Baz");
        assertThat(sut.implementation(clazz), is(equalTo(" +- PACKAGE foo.bar.Baz" + NL)));
    }

    @Test
    public void testNl() {
        assertThat(sut.nl(), is(equalTo(NL)));
    }

}
