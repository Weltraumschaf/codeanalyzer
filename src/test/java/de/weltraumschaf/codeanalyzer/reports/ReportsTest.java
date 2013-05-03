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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for {@link Reports}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ReportsTest {

    @Test
    public void testCreatePackageEncapsulation() {
        final Report first = Reports.createPackageEncapsulation();
        assertThat(first, is(notNullValue()));
        assertThat(first, is(instanceOf(PackageEncapsulation.class)));
        final Report second = Reports.createPackageEncapsulation();
        assertThat(second, is(notNullValue()));
        assertThat(second, is(instanceOf(PackageEncapsulation.class)));
        assertThat(first, is(not(sameInstance(second))));
    }

}
