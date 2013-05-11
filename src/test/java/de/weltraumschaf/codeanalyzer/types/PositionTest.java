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

import de.weltraumschaf.codeanalyzer.types.Position;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PositionTest {

    @Test
    public void testToString() {
        assertThat(Position.DEFAULT.toString(), is(equalTo("unknown:0")));
        assertThat(new Position("foobar", 23).toString(), is(equalTo("foobar:23")));
    }

    @Test public void testHashCode() {
        final Position p1 = new Position("foobar", 23);
        final Position p2 = new Position("foobar", 23);
        final Position p3 = new Position("snafu", 42);

        assertThat(p1.hashCode(), is(equalTo(p1.hashCode())));
        assertThat(p1.hashCode(), is(equalTo(p2.hashCode())));
        assertThat(p2.hashCode(), is(equalTo(p1.hashCode())));
        assertThat(p2.hashCode(), is(equalTo(p2.hashCode())));
        assertThat(p3.hashCode(), is(equalTo(p3.hashCode())));

        assertThat(p1.hashCode(), is(not(equalTo(p3.hashCode()))));
        assertThat(p2.hashCode(), is(not(equalTo(p3.hashCode()))));
    }

    @Test public void testHequals() {
        final Position p1 = new Position("foobar", 23);
        final Position p2 = new Position("foobar", 23);
        final Position p3 = new Position("snafu", 42);

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

}
