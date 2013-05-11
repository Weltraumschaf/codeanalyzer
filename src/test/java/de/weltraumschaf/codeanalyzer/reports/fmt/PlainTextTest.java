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

import de.weltraumschaf.codeanalyzer.reports.fmt.PlainText;
import de.weltraumschaf.codeanalyzer.reports.fmt.Formatter;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.Package;
import de.weltraumschaf.codeanalyzer.types.Position;
import de.weltraumschaf.codeanalyzer.types.Visibility;
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
        final InterfaceType iface = new InterfaceType(Package.create("foo.bar"), "Baz");
        assertThat(sut.iface(iface), is(equalTo("I PUBLIC foo.bar.Baz (unknown:0)" + NL)));
    }

    @Test
    public void iface_packageWithPositionAndNoChilds() {
        final InterfaceType iface = new InterfaceType(Package.create("foo.bar"), "Baz", Visibility.PACKAGE);
        iface.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.iface(iface), is(equalTo("I PACKAGE foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void iface_withOneImplementations() {
        final InterfaceType iface = new InterfaceType(Package.create("foo.bar"), "Foo");
        iface.setPosition(new Position("foo/bar/Foo.java", 23));
        final ClassType clazz = new ClassType(Package.create("foo.bar"), "Bar");
        clazz.setPosition(new Position("foo/bar/Bar.java", 23));
        clazz.implement(iface);
        assertThat(sut.iface(iface), is(equalTo(
              "I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Bar (foo/bar/Bar.java:23)" + NL)));
    }

    @Test
    public void iface_withTwoImplementations() {
        final InterfaceType iface = new InterfaceType(Package.create("foo.bar"), "Foo");
        iface.setPosition(new Position("foo/bar/Foo.java", 23));
        final ClassType clazz1 = new ClassType(Package.create("foo.bar"), "Bar");
        clazz1.setPosition(new Position("foo/bar/Bar.java", 23));
        clazz1.implement(iface);
        final ClassType clazz2 = new ClassType(Package.create("foo.bar"), "Baz");
        clazz2.setPosition(new Position("foo/bar/Baz.java", 23));
        clazz2.implement(iface);
        assertThat(sut.iface(iface), is(equalTo(
              "I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Bar (foo/bar/Bar.java:23)" + NL
            + "     +- C PACKAGE foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void iface_withOneExtendingInterface() {
        final InterfaceType iface1 = new InterfaceType(Package.create("foo.bar"), "Foo");
        iface1.setPosition(new Position("foo/bar/Foo.java", 23));
        final InterfaceType iface2 = new InterfaceType(Package.create("foo.bar"), "Bar");
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
        final ClassType clazz = new ClassType(Package.create("foo.bar"), "Baz");
        assertThat(sut.implementation(clazz), is(equalTo("C PACKAGE foo.bar.Baz (unknown:0)" + NL)));
    }

    @Test
    public void implementation_publicWithPositionAndNoChilds() {
        final ClassType clazz = new ClassType(Package.create("foo.bar"), "Baz", Visibility.PUBLIC);
        clazz.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.implementation(clazz), is(equalTo("C PUBLIC foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void implementation_packageAbstractWithPositionAndNoChilds() {
        final ClassType clazz = new ClassType(Package.create("foo.bar"), "Baz", Visibility.PUBLIC, true);
        clazz.setPosition(new Position("foo/bar/Baz.java", 23));
        assertThat(sut.implementation(clazz), is(equalTo("C ABSTRACT PUBLIC foo.bar.Baz (foo/bar/Baz.java:23)" + NL)));
    }

    @Test
    public void nl() {
        assertThat(sut.nl(), is(equalTo(NL)));
    }

}
