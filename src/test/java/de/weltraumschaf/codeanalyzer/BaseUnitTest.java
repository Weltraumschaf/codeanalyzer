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

/**
 * Unit tests for {@link BaseUnit}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseUnitTest {

    @Test
    public void getFullQualifiedName() {
        final Unit sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getFullQualifiedName(), is(equalTo("foo.bar.Baz")));
    }

    @Test
    public void testToString() {
        final Unit sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.toString(), is(equalTo("PRIVATE foo.bar.Baz")));
    }

    @Test
    public void update_visibility() {
        final Unit sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));

        sut.update(new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PUBLIC));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PUBLIC)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));
    }

    @Test
    public void update_position() {
        final Unit sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));

        final Position pos = new Position("foo", 23);
        final Unit other = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        other.setPosition(pos);
        sut.update(other);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(pos)));
    }

    @Test
    public void update_notNameAndPackage() {
        final Package pkg = Package.create("foo.bar");
        final Unit sut = new StubbedBaseUnit(pkg, "Baz", Visibility.PRIVATE);
        assertThat(sut.getPackage(), is(equalTo(pkg)));
        assertThat(sut.getName(), is(equalTo("Baz")));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));

        sut.update(new StubbedBaseUnit(Package.create("snafu"), "Foo", Visibility.PUBLIC));
        assertThat(sut.getPackage(), is(equalTo(pkg)));
        assertThat(sut.getName(), is(equalTo("Baz")));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
    }

    private static final class StubbedBaseUnit extends BaseUnit {

        public StubbedBaseUnit(final Package containingPackage, final String name, final Visibility visibility) {
            super(containingPackage, name, visibility);
        }

    }
}