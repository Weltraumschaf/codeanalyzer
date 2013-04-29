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

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test for {@link FileFinder}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FileFinderTest {
    private static final String PACKAGE_BASE = "/de/weltraumschaf/codeanalyzer/test";

    //CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final FileFinder sut = new FileFinder();

    @Test
    public void findJava_throwsExceptionIfNullPassed() {
        thrown.expect(NullPointerException.class);
        sut.findJava(null);
    }

    @Test
    public void findJava_dirWithNoJavaFiles() throws URISyntaxException {
        final URL resource = getClass().getResource(PACKAGE_BASE + "/empty");
        final List<File> files = sut.findJava(new File(resource.toURI()));
        assertThat(files, is(empty()));
    }

    @Test
    public void findJava() throws URISyntaxException {
        final URL resource = getClass().getResource(PACKAGE_BASE);
        final List<File> files = sut.findJava(new File(resource.toURI()));
        assertThat(files, is(not(empty())));
        assertThat(files, hasSize(5));

        for (final File f : files) {
            assertThat(
                f.getName(),
                anyOf(
                    equalTo("AbstractOne.java"),
                    equalTo("One.java"),
                    equalTo("OneImplA.java"),
                    equalTo("OneImplB.java"),
                    equalTo("Ones.java")));
        }
    }

}
