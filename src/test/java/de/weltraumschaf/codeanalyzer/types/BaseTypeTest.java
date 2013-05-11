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

package de.weltraumschaf.codeanalyzer.types;

import de.weltraumschaf.codeanalyzer.types.Package;
import de.weltraumschaf.codeanalyzer.types.BaseType;
import de.weltraumschaf.codeanalyzer.types.Visibility;
import de.weltraumschaf.codeanalyzer.types.Position;
import de.weltraumschaf.codeanalyzer.types.Type;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Type tests for {@link BaseType}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseTypeTest {

    @Test
    public void getFullQualifiedName() {
        Type sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getFullQualifiedName(), is(equalTo("foo.bar.Baz")));
        sut = new StubbedBaseUnit(Package.NULL, "Baz", Visibility.PRIVATE);
        assertThat(sut.getFullQualifiedName(), is(equalTo("Baz")));
    }

    @Test
    public void testToString() {
        final Type sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.toString(), is(equalTo("PRIVATE foo.bar.Baz")));
    }

    @Test
    public void update_visibility() {
        final Type sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));

        sut.update(new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PUBLIC));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PUBLIC)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));
    }

    @Test
    public void update_position() {
        final Type sut = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(Position.DEFAULT)));

        final Position pos = new Position("foo", 23);
        final Type other = new StubbedBaseUnit(Package.create("foo.bar"), "Baz", Visibility.PRIVATE);
        other.setPosition(pos);
        sut.update(other);
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
        assertThat(sut.getPosition(), is(equalTo(pos)));
    }

    @Test
    public void update_notNameAndPackage() {
        final Package pkg = Package.create("foo.bar");
        final Type sut = new StubbedBaseUnit(pkg, "Baz", Visibility.PRIVATE);
        assertThat(sut.getPackage(), is(equalTo(pkg)));
        assertThat(sut.getName(), is(equalTo("Baz")));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));

        sut.update(new StubbedBaseUnit(Package.create("snafu"), "Foo", Visibility.PUBLIC));
        assertThat(sut.getPackage(), is(equalTo(pkg)));
        assertThat(sut.getName(), is(equalTo("Baz")));
        assertThat(sut.getVisibility(), is(equalTo(Visibility.PRIVATE)));
    }

    private static final class StubbedBaseUnit extends BaseType {

        public StubbedBaseUnit(final Package containingPackage, final String name, final Visibility visibility) {
            super(containingPackage, name, visibility);
        }

    }
}
