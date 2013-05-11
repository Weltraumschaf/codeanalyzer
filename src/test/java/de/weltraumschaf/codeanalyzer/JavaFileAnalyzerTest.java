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

import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.Visibility;
import de.weltraumschaf.codeanalyzer.types.Position;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link JavaFileAnalyzer} and implicit for {@link Visitor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JavaFileAnalyzerTest {

    private static final String PACKAGE_BASE = "/de/weltraumschaf/codeanalyzer/test";
    private final UnitCollector data = new UnitCollector();
    private final JavaFileAnalyzer sut = new JavaFileAnalyzer(data);

    @Test
    public void analyzeFile_onePackagePrivateAbstractClass() throws URISyntaxException, IOException {
        final URL resource = getClass().getResource(PACKAGE_BASE + "/AbstractOne.java");
        final File file = new File(resource.toURI());
        sut.analyze(file);
        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.AbstractOne"), is(true));
        final ClassType abstractOne = data.getClass("de.weltraumschaf.codeanalyzer.test.AbstractOne");
        assertThat(abstractOne, notNullValue());
        assertThat(abstractOne.isAbstract(), is(true));
        assertThat(abstractOne.getVisibility(), is(Visibility.PACKAGE));
        assertThat(abstractOne.getPosition(), equalTo(new Position(file.getAbsolutePath(), 23)));
        assertThat(abstractOne.doesImplement("de.weltraumschaf.codeanalyzer.test.One"), is(true));
        final InterfaceType one = data.getInterface("de.weltraumschaf.codeanalyzer.test.One");
        assertThat(one, notNullValue());
        assertThat(one.getVisibility(), is(Visibility.PUBLIC));
        assertThat(one.getPosition(), equalTo(Position.DEFAULT));
    }

    @Test
    public void analyzeFile_fiveFiles() throws URISyntaxException, IOException {
        final FileFinder finder = new FileFinder();
        final URL resource = getClass().getResource(PACKAGE_BASE);
        final List<File> files = finder.findJava(new File(resource.toURI()));
        assertThat(files, hasSize(5));
        final Map<String, File> nameToFile = Maps.newHashMap();

        for (final File f : files) {
            sut.analyze(f);
            nameToFile.put(f.getName(), f);
        }

        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.AbstractOne"), is(true));
        final ClassType abstractOne = data.getClass("de.weltraumschaf.codeanalyzer.test.AbstractOne");
        assertThat(abstractOne, notNullValue());
        assertThat(abstractOne.isAbstract(), is(true));
        assertThat(abstractOne.getVisibility(), is(Visibility.PACKAGE));
        assertThat(abstractOne.getPosition(),
            equalTo(new Position(nameToFile.get("AbstractOne.java").getAbsolutePath(), 23)));

        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.OneImplA"), is(true));
        final ClassType oneImplA = data.getClass("de.weltraumschaf.codeanalyzer.test.OneImplA");
        assertThat(oneImplA, notNullValue());
        assertThat(oneImplA.isAbstract(), is(false));
        assertThat(oneImplA.getVisibility(), is(Visibility.PACKAGE));
        assertThat(oneImplA.getPosition(),
            equalTo(new Position(nameToFile.get("OneImplA.java").getAbsolutePath(), 20)));

        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.OneImplB"), is(true));
        final ClassType oneImplB = data.getClass("de.weltraumschaf.codeanalyzer.test.OneImplB");
        assertThat(oneImplB, notNullValue());
        assertThat(oneImplB.isAbstract(), is(false));
        assertThat(oneImplB.getVisibility(), is(Visibility.PACKAGE));
        assertThat(oneImplB.getPosition(),
            equalTo(new Position(nameToFile.get("OneImplB.java").getAbsolutePath(), 20)));

        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.Ones"), is(true));
        final ClassType ones = data.getClass("de.weltraumschaf.codeanalyzer.test.Ones");
        assertThat(ones, notNullValue());
        assertThat(ones.isAbstract(), is(false));
        assertThat(ones.getVisibility(), is(Visibility.PUBLIC));
        assertThat(ones.getPosition(),
            equalTo(new Position(nameToFile.get("Ones.java").getAbsolutePath(), 20)));

        assertThat(data.hasInterface("de.weltraumschaf.codeanalyzer.test.One"), is(true));
        final InterfaceType one = data.getInterface("de.weltraumschaf.codeanalyzer.test.One");
        assertThat(one, notNullValue());
        assertThat(one.getVisibility(), is(Visibility.PUBLIC));
        assertThat(one.getPosition(),
            equalTo(new Position(nameToFile.get("One.java").getAbsolutePath(), 20)));
    }
}
