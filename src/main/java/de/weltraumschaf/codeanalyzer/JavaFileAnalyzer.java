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
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Read a given file, parse it and runs {@link Visitor} over the AST to collect class and
 * interface data.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class JavaFileAnalyzer {

    /**
     * Default input encoding.
     */
    private static final String DEFAULT_ENCODING = "utf-8";
    /**
     * Parses the code.
     */
    private final ASTParser parser = ASTParser.newParser(AST.JLS4);
    /**
     * Collect found classes and interfaces.
     */
    private final UnitCollector data;

    /**
     * Dedicated constructor.
     *
     * @param data object to collect class and interface data
     */
    public JavaFileAnalyzer(final UnitCollector data) {
        this.data = data;
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
    }

    /**
     * Analyze a file and add found classes and interfaces to the data collector.
     *
     * @param file file to analyze
     * @throws IOException if any I/O error occurs
     */
    public void analyze(final File file) throws IOException {
        final String source = FileUtils.readFileToString(file, DEFAULT_ENCODING);
        parser.setSource(source.toCharArray());
        final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
        compilationUnit.accept(new Visitor(data, compilationUnit, file));
    }
}
