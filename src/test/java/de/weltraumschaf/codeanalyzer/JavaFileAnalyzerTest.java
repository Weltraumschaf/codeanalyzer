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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JavaFileAnalyzerTest {

    public class JavaFileAnalyzer {

        private static final String DEFAULT_ENCODING = "utf-8";
        /**
         * Parses the code.
         */
        private final ASTParser parser = ASTParser.newParser(AST.JLS4);
        private final UnitCollector data;

        private JavaFileAnalyzer(final UnitCollector data) {
            this.data = data;
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
        }

        private void analyze(final File file) throws IOException {
            final String source = FileUtils.readFileToString(file, DEFAULT_ENCODING);
            parser.setSource(source.toCharArray());
            final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
            compilationUnit.accept(new Visitor(data,
                                               Package.create(compilationUnit.getPackage().getName()),
                                               compilationUnit,
                                               file));
        }
    }

    private static final String PACKAGE_BASE = "/de/weltraumschaf/codeanalyzer/test";
    private final UnitCollector data = new UnitCollector();
    private final JavaFileAnalyzer sut = new JavaFileAnalyzer(data);

    @Test
    public void analyzeFile() throws URISyntaxException, IOException {
        final URL resource = getClass().getResource(PACKAGE_BASE + "/AbstractOne.java");
        sut.analyze(new File(resource.toURI()));
        assertThat(data.hasClass("de.weltraumschaf.codeanalyzer.test.AbstractOne"), is(true));
        final Class abstractOne = data.getClass("de.weltraumschaf.codeanalyzer.test.AbstractOne");
        assertThat(abstractOne, notNullValue());
        assertThat(abstractOne.isIsAbstract(), is(true));
        assertThat(abstractOne.getVisibility(), is(Unit.Visibility.PACKAGE));
    }

}
