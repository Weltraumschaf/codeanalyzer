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
import de.weltraumschaf.codeanalyzer.Position;
import de.weltraumschaf.codeanalyzer.Visibility;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
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
    public void title() {
        assertThat(sut.title(null), is(equalTo("" + NL + NL)));
        assertThat(sut.title(""), is(equalTo("" + NL + NL)));
        assertThat(sut.title("   "), is(equalTo("" + NL + NL)));
        assertThat(sut.title("Foo"), is(equalTo("Foo" + NL + NL)));
    }

    @Test
    public void text() {
        assertThat(sut.text(null), is(equalTo(" " + NL)));
        assertThat(sut.text(""), is(equalTo(" " + NL)));
        assertThat(sut.text("   "), is(equalTo(" " + NL)));
        assertThat(sut.text("Foo"), is(equalTo(" Foo" + NL)));
    }

    @Test
    public void iface_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Interface must not be null!");
        sut.iface(null);
    }

    @Test
    public void iface_publicWithDefaultPositionAndNoChilds() {
        final Interface iface = new Interface(Package.create("foo.bar"), "Baz");
        assertThat(sut.iface(iface), is(equalTo("I PUBLIC foo.bar.Baz (unknown:0)" + NL)));
    }

    @Test
    public void iface_packageWithPositionAndNoChilds() {
        final Interface iface = new Interface(Package.create("foo.bar"), "Baz", Visibility.PACKAGE);
        iface.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.iface(iface), is(equalTo("I PACKAGE foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void iface_withOneImplementations() {
        final Interface iface = new Interface(Package.create("foo.bar"), "Foo");
        iface.setPosition(new Position("foo/bar/Foo.java", 23));
        final Class clazz = new Class(Package.create("foo.bar"), "Bar");
        clazz.setPosition(new Position("foo/bar/Bar.java", 23));
        clazz.implement(iface);
        assertThat(sut.iface(iface), is(equalTo(
              "I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Bar (foo/bar/Bar.java:23)" + NL)));
    }

    @Test
    public void iface_withTwoImplementations() {
        final Interface iface = new Interface(Package.create("foo.bar"), "Foo");
        iface.setPosition(new Position("foo/bar/Foo.java", 23));
        final Class clazz1 = new Class(Package.create("foo.bar"), "Bar");
        clazz1.setPosition(new Position("foo/bar/Bar.java", 23));
        clazz1.implement(iface);
        final Class clazz2 = new Class(Package.create("foo.bar"), "Baz");
        clazz2.setPosition(new Position("foo/bar/Baz.java", 23));
        clazz2.implement(iface);
        assertThat(sut.iface(iface), is(equalTo(
              "I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Bar (foo/bar/Bar.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void iface_withOneExtendingInterface() {
        final Interface iface1 = new Interface(Package.create("foo.bar"), "Foo");
        iface1.setPosition(new Position("foo/bar/Foo.java", 23));
        final Interface iface2 = new Interface(Package.create("foo.bar"), "Bar");
        iface2.setPosition(new Position("foo/bar/Bar.java", 23));
        iface2.extend(iface1);
        assertThat(sut.iface(iface1), is(equalTo(
              "I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)" + NL
            + "     +- I PUBLIC foo.bar.Bar (foo/bar/Bar.java:23)" + NL)));
    }

    @Test
    public void implementation_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Class must not be null!");
        sut.implementation(null);
    }

    @Test
    public void implementation_packageWithDefaultPositionAndNoChilds() {
        final Class clazz = new Class(Package.create("foo.bar"), "Baz");
        assertThat(sut.implementation(clazz), is(equalTo("C PACKAGE foo.bar.Baz (unknown:0)" + NL)));
    }

    @Test
    public void implementation_publicWithPositionAndNoChilds() {
        final Class clazz = new Class(Package.create("foo.bar"), "Baz", Visibility.PUBLIC);
        clazz.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.implementation(clazz), is(equalTo("C PUBLIC foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void implementation_packageAbstractWithPositionAndNoChilds() {
        final Class clazz = new Class(Package.create("foo.bar"), "Baz", Visibility.PUBLIC, true);
        clazz.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.implementation(clazz), is(equalTo("C ABSTRACT PUBLIC foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void nl() {
        assertThat(sut.nl(), is(equalTo(NL)));
    }

}
