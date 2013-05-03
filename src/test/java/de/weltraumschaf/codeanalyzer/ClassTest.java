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

package de.weltraumschaf.codeanalyzer;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for {@link Class}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ClassTest {

    //CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void getVisibility_isPackagePrivateByDefault() {
        Class sut = new Class(Package.NULL, "Foo");
        assertThat(sut.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void isIsAbstract() {
        Class sut = new Class(Package.NULL, "Foo");
        assertThat(sut.isAbstract(), is(equalTo(false)));
        sut = new Class(Package.NULL, "Foo", Visibility.PRIVATE, true);
        assertThat(sut.isAbstract(), is(equalTo(true)));
    }

    @Test
    public void getAndSetExtendedClass() {
        final Class sut = new Class(Package.NULL, "Foo");
        assertThat(sut.extend(), is(equalTo(null)));
        final Class extended = new Class(Package.NULL, "Bar");
        sut.extend(extended);
        assertThat(sut.extend(), is(equalTo(extended)));
    }

    @Test
    public void implement() {
        final Package pkg = Package.create("foo.bar");
        final Class sut = new Class(pkg, "Foo");
        assertThat(sut.interfaces(), hasSize(0));

        final Interface iface1 = new Interface(pkg, "Bar");
        assertThat(iface1.getImplementations(), hasSize(0));
        assertThat(sut.doesImplement(iface1), is(equalTo(false)));

        sut.implement(iface1);
        assertThat(sut.doesImplement(iface1), is(equalTo(true)));
        assertThat(sut.getInterface("foo.bar.Bar"), is(sameInstance(iface1)));
        assertThat(sut.interfaces(), hasSize(1));
        assertThat(sut.interfaces(), hasItem(iface1));
        assertThat(iface1.getImplementations(), hasSize(1));
        assertThat(iface1.getImplementations(), hasItem(sut));

        final Interface iface2 = new Interface(pkg, "Baz");
        assertThat(iface2.getImplementations(), hasSize(0));
        assertThat(sut.doesImplement(iface2), is(equalTo(false)));

        sut.implement(iface2);
        assertThat(sut.doesImplement(iface2), is(equalTo(true)));
        assertThat(sut.getInterface("foo.bar.Bar"), is(sameInstance(iface1)));
        assertThat(sut.getInterface("foo.bar.Baz"), is(sameInstance(iface2)));
        assertThat(sut.interfaces(), hasSize(2));
        assertThat(sut.interfaces(), hasItems(iface1, iface2));
        assertThat(iface1.getImplementations(), hasSize(1));
        assertThat(iface1.getImplementations(), hasItem(sut));
        assertThat(iface2.getImplementations(), hasSize(1));
        assertThat(iface2.getImplementations(), hasItem(sut));
    }

    @Test
    public void getInterface_throwsExceptionIfNotImplementing() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Class Foo does not implement foo.bar.Baz!");
        final Class sut = new Class(Package.NULL, "Foo");
        sut.getInterface("foo.bar.Baz");
    }
}