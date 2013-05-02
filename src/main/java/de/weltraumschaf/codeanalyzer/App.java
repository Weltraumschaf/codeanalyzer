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
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Main application class.
 *
 * TODO
 * - Add CLI options
 *      - -h|--help
 *      - -v|--version
 *      - -e|--encoding ENCODING
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App {

    /**
     * Command line arguments.
     */
    private final List<String> args;
    /**
     * Collect parsed units.
     */
    private final UnitCollector data = new UnitCollector();
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
    private App(final List<String> args, final PrintStream out, final PrintStream err) {
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

    /**
     * Read all files from the directory given as first CLI argument.
     *
     * @return collection of files, never {@code null}, maybe empty collection
     */
    private Collection<File> readSourceFiles() {
        if (args.isEmpty()) {
            return Collections.emptyList();
        }

        return new FileFinder().findJava(new File(args.get(0)));
    }

    /**
     * Analyzes the found files and collect data from them.
     *
     * @param files files to inspect
     * @throws IOException if IO errors occurs
     */
    private void collectData(final Collection<File> files) throws IOException {
        final JavaFileAnalyzer analyzer = new JavaFileAnalyzer(data);
        for (final File file : files) {
            analyzer.analyze(file);
        }
    }

}
