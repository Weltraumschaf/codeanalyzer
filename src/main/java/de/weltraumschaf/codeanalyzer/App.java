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

import de.weltraumschaf.codeanalyzer.reports.Report;
import de.weltraumschaf.codeanalyzer.reports.Reports;
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
        final UnitCollector data;

        try {
            data = collectData(files);
        } catch (IOException ex) {
            err.println(ex.getMessage());
            return 1;
        }

        generateReport(data);
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
     * @return a new collector object with data
     * @throws IOException if IO errors occurs
     */
    private UnitCollector collectData(final Collection<File> files) throws IOException {
        final UnitCollector data = new UnitCollector();
//        final JavaFileAnalyzer analyzer = new JavaFileAnalyzer(data);

//        for (final File file : files) {
//            analyzer.analyze(file);
//        }

        generateTestData(data);
        return data;
    }

    /**
     * Generate and print out the report.
     *
     * @param data to generate report from
     */
    private void generateReport(final UnitCollector data) {
        final Report report = Reports.createPackageEncapsulation();
        report.setData(data);
        out.print(report.generate());
    }

    /**
     * Generate some test data.
     *
     * @param data to collect the test data
     */
    private void generateTestData(final UnitCollector data) {
        final Package pkg = Package.create("foo.bar.baz");
        final Interface ifaceFoo = new Interface(pkg, "Foo");
        data.addInterface(ifaceFoo);
        final Class classFooImplA = new Class(pkg, "FooImplA");
        classFooImplA.implement(ifaceFoo);
        data.addClass(classFooImplA);
        final Class classFooImplB = new Class(pkg, "FooImplB");
        classFooImplB.implement(ifaceFoo);
        data.addClass(classFooImplB);
        final Interface ifaceBar = new Interface(pkg, "Bar");
        data.addInterface(ifaceBar);
    }

}
