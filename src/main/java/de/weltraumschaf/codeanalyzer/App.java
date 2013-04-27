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

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

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
     * Dedicated constructor.
     *
     * @param args command line arguments
     * @param out print stream for STDOUT
     */
    public App(final List<String> args, final PrintStream out) {
        super();
        this.args = args;
        this.out = out;
    }

    /**
     * Invoked by JVM.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final App app = new App(Arrays.asList(args), System.out);
        System.exit(app.run());
    }

    /**
     * Run the program.
     *
     * @return exit code, everything else than 0 means error
     */
    private int run() {
        collectData();
        return 0;
    }

    /**
     * parse source from given directory and put data in collector.
     */
    private void collectData() {
        // Example http://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
        final ASTParser parser = ASTParser.newParser(AST.JLS3);
        final String source = "public class A {\n"
                + "int i = 9;  \n"
                + "int j; \n"
                + "ArrayList<Integer> al = new ArrayList<Integer>();\n"
                + "j=1000;\n"
                + "}";
        parser.setSource(source.toCharArray());
        //parser.setSource("/*abc*/".toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        //ASTNode node = parser.createAST(null);


        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        cu.accept(new ASTVisitor() {
            Set names = new HashSet();

            @Override
            public boolean visit(final VariableDeclarationFragment node) {
                final SimpleName name = node.getName();
                this.names.add(name.getIdentifier());
                out.println("Declaration of '" + name + "' at line" + cu.getLineNumber(name.getStartPosition()));
                return false; // do not continue to avoid usage info
            }

            @Override
            public boolean visit(final SimpleName node) {
                if (this.names.contains(node.getIdentifier())) {
                    out.println("Usage of '" + node + "' at line " + cu.getLineNumber(node.getStartPosition()));
                }
                return true;
            }
        });
    }
}
