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

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for {@link Interface}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InterfaceTest {

    @Test
    public void getVisibility_isPackagePrivateByDefault() {
        final Interface sut = new Interface(Package.NULL, "Foo");
        assertThat(sut.getVisibility(), is(Visibility.PUBLIC));
    }

    @Test
    public void implementations() {
        final Interface sut = new Interface(Package.NULL, "Foo");
        assertThat(sut.getImplementations(), hasSize(0));

        final Class impl1 = new Class(Package.NULL, "Bar");
        assertThat(impl1.doesImplement(sut), is(equalTo(false)));
        sut.addImplementation(impl1);
        assertThat(impl1.doesImplement(sut), is(equalTo(true)));
        assertThat(sut.hasImplementation(impl1), is(equalTo(true)));
        assertThat(sut.getImplementations(), hasSize(1));
        assertThat(sut.getImplementations(), hasItem(impl1));

        final Class impl2 = new Class(Package.NULL, "Baz");
        assertThat(impl2.doesImplement(sut), is(equalTo(false)));
        sut.addImplementation(impl2);
        assertThat(impl1.doesImplement(sut), is(equalTo(true)));
        assertThat(sut.hasImplementation(impl1), is(equalTo(true)));
        assertThat(sut.hasImplementation(impl2), is(equalTo(true)));
        assertThat(sut.getImplementations(), hasSize(2));
        assertThat(sut.getImplementations(), hasItems(impl1, impl2));
    }

}