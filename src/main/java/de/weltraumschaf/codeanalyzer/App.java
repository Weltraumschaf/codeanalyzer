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

import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Main application class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class App {

    /**
     * Command line arguments.
     */
    private final List<String> args; // NOPMD
    private final UnitCollector data = new UnitCollector(); // NOPMD
    /**
     * STDOUT.
     */
    private final PrintStream out;
    /**
     * STDERR.
     */
    private final PrintStream err;

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments
     * @param out print stream for STDOUT
     * @param err print stream for STDERR
     */
    public App(final List<String> args, final PrintStream out, final PrintStream err) {
        super();
        this.args = args;
        this.out = out;
        this.err = err;
    }

    /**
     * Invoked by JVM.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final App app = new App(Arrays.asList(args), System.out, System.err);
        System.exit(app.run());
    }

    /**
     * Run the program.
     *
     * @return exit code, everything else than 0 means error
     */
    private int run() {
        final Collection<File> files = readSourceFiles();
        try {
            collectData(files);
        } catch (IOException ex) {
            err.println(ex.getMessage());
            return 1;
        }
        return 0;
    }

    private Collection<File> readSourceFiles() {
        if (args.isEmpty()) {
            return Collections.emptyList();
        }

        return FileUtils.listFiles(
                new File(args.get(0)),
                new RegexFileFilter("^.*\\.java$"),
                DirectoryFileFilter.DIRECTORY);
    }

    private void collectData(final Collection<File> files) throws IOException {
        for (final File file : files) {
            parseFile(file);
        }
    }

    private void parseFile(final File file) throws IOException {
        // Example http://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
        final ASTParser parser = ASTParser.newParser(AST.JLS4);
        final String source = FileUtils.readFileToString(file, "utf-8");
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        final Package pkg = Package.create(cu.getPackage().getName().toString());
        cu.accept(new ASTVisitor() {
            final Set names = Sets.newHashSet();

            @Override
            public boolean visit(final TypeDeclaration node) {
                final Name name = node.getName();
                final String type;

                if (node.isInterface()) {
                    type = "interface";
                    final Interface iface = new Interface(pkg, name.toString());
                    data.addInterface(iface);
                } else {
                    type = "class";
                    final Class clazz = new Class(pkg, name.toString());
                    data.addClass(clazz);
                }

                out.println(String.format("Found %s '%s.%s' at line %d in %s.",
                    type,
                    pkg.getFullQualifiedName(),
                    name.toString(),
                    cu.getLineNumber(name.getStartPosition()),
                    file.getAbsolutePath()));
                return true;
            }
        });
    }
}
