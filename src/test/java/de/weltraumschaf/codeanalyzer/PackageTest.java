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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Tests for {@link Package}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PackageTest {

    //CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test public void validateBaseName_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Name must not be null!");
        Package.validateBaseName(null);
    }

    @Test public void validateBaseName_throwsExceptionIfEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name must not be empty!");
        Package.validateBaseName("");
    }

    @Test public void validateBaseName_throwsExceptionIfTrimmedEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name must not be empty!");
        Package.validateBaseName("    ");
    }

    @Test public void validateBaseName_throwsExceptionIfStartsWithNumber() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name '1foo' must not start with number!");
        Package.validateBaseName("1foo");
    }

    @Test public void validateBaseName_throwsExceptionIfContainsMinus() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name 'foo-bar' contains invalid character '-'! Allowed are letters and digits.");
        Package.validateBaseName("foo-bar");
    }

    @Test public void validateBaseName_throwsExceptionIfContainsDot() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name 'foo.bar' contains invalid character '.'! Allowed are letters and digits.");
        Package.validateBaseName("foo.bar");
    }


    @Test public void validateBaseName_notThrowsExceptionIfContainsOnlyLettersDigitsAndUndersocre() {
        try {
            Package.validateBaseName("fobarbaz");
            Package.validateBaseName("foo_bar42_baz");
        } catch (IllegalArgumentException ex) {
            fail("Must not throw exception! " + ex.toString());
        }
    }

    @Test public void create_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Name must not be null!");
        Package.create((String) null);
    }

    @Test public void create_throwsExceptionIfEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name must not be empty!");
        Package.create("");
    }

    @Test public void create_onePackageHirarchy() {
        final Package p = Package.create("foo");
        assertThat(p.getBaseName(), is("foo"));
        assertThat(p.isRoot(), is(true));
        assertThat(p.getParent(), is(Package.NULL));
        assertThat(p.getFullQualifiedName(), is("foo"));
        assertThat(p.toString(), is("foo"));
    }

    @Test public void create_twoPackageHirarchy() {
        final Package p = Package.create("foo.bar");
        assertThat(p.getBaseName(), is("bar"));
        assertThat(p.isRoot(), is(false));
        assertThat(p.getParent(), is(Package.create("foo")));
        assertThat(p.getFullQualifiedName(), is("foo.bar"));
        assertThat(p.toString(), is("foo.bar"));
    }

    @Test public void create_threePackageHirarchy() {
        final Package p = Package.create("foo.bar.baz");
        assertThat(p.getBaseName(), is("baz"));
        assertThat(p.isRoot(), is(false));
        assertThat(p.getParent(), is(Package.create("foo.bar")));
        assertThat(p.getFullQualifiedName(), is("foo.bar.baz"));
        assertThat(p.toString(), is("foo.bar.baz"));
    }

    @Test public void splitBaseName() {
        assertThat(Package.splitBaseName("foo.bar.baz"), is("baz"));
        assertThat(Package.splitBaseName("foo.bar"), is("bar"));
        assertThat(Package.splitBaseName("foo"), is("foo"));
    }

    @Test public void splitParentName() {
        assertThat(Package.splitParentName("foo.bar.baz"), is("foo.bar"));
        assertThat(Package.splitParentName("foo.bar"), is("foo"));
        assertThat(Package.splitParentName("foo"), is(""));
    }

    @Test public void createSub() {
        final Package p = Package.create("foo");
        final Package sub = p.createSub("bar");
        assertThat(sub.getBaseName(), is("bar"));
        assertThat(sub.getParent(), is(p));
        assertThat(sub.getFullQualifiedName(), is("foo.bar"));
    }

    @Test public void testHashCode() {
        final Package p1 = Package.create("foo.bar");
        final Package p2 = Package.create("foo.bar");
        final Package p3 = Package.create("foo.baz");

        assertThat(p1.hashCode(), is(equalTo(p1.hashCode())));
        assertThat(p1.hashCode(), is(equalTo(p2.hashCode())));
        assertThat(p2.hashCode(), is(equalTo(p1.hashCode())));
        assertThat(p2.hashCode(), is(equalTo(p2.hashCode())));
        assertThat(p3.hashCode(), is(equalTo(p3.hashCode())));

        assertThat(p1.hashCode(), is(not(equalTo(p3.hashCode()))));
        assertThat(p2.hashCode(), is(not(equalTo(p3.hashCode()))));
    }

    @Test public void testHequals() {
        final Package p1 = Package.create("foo.bar");
        final Package p2 = Package.create("foo.bar");
        final Package p3 = Package.create("foo.baz");

        assertThat(p1.equals(p1), is(equalTo(true)));
        assertThat(p1.equals(p2), is(equalTo(true)));
        assertThat(p2.equals(p1), is(equalTo(true)));
        assertThat(p2.equals(p2), is(equalTo(true)));
        assertThat(p3.equals(p3), is(equalTo(true)));

        assertThat(p1.equals(p3), is(equalTo(false)));
        assertThat(p3.equals(p1), is(equalTo(false)));
        assertThat(p2.equals(p3), is(equalTo(false)));
        assertThat(p3.equals(p2), is(equalTo(false)));

        assertThat(p1.equals(new Object()), is(equalTo(false)));
        assertThat(p2.equals(new Object()), is(equalTo(false)));
        assertThat(p3.equals(new Object()), is(equalTo(false)));
        //CHECKSTYLE:OFF
        assertThat(p1.equals(null), is(equalTo(false)));
        assertThat(p2.equals(null), is(equalTo(false)));
        assertThat(p3.equals(null), is(equalTo(false)));
        //CHECKSTYLE:ON
    }

    @Test public void nullPackage() {
        final Package sut = Package.NULL;
        assertThat(sut.getBaseName(), is(""));
        assertThat(sut.getFullQualifiedName(), is(""));
        assertThat(sut.getParent(), is(sameInstance(sut)));
    }

}