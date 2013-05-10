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
import de.weltraumschaf.commons.Version;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Main application class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App {
    private static final String ENCODING = "utf-8";
    /**
     * Usage header.
     *
     * FIXME Better formatting with newlines.
     */
    private static final String HEADER = String.format("%n"
        + "This command line tool analyzes Java source files and generates various reports.%n%n");

    /**
     * Author name and email address.
     */
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";

    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/code-analyzer/issues";
    /**
     * Usage footer.
     */
    private static final String FOOTER = String.format("%nWritten 2013 by %s%nReport bugs to %s",
                                                       AUTHOR, ISSUE_TRACKER);

    /**
     * Short option for help.
     */
    private static final String OPT_SHORT_HELP = "h";
    /**
     * Short option for version.
     */
    private static final String OPT_SHORT_VERSION = "v";
    /**
     * Short option for inheritance graph.
     */
    private static final String OPT_SHORT_INHERITANCE_GRAPH = "i";
    /**
     * Short option for package encapsulation.
     */
    private static final String OPT_SHORT_PACKAGE_ENCAPSULATION = "p";
    /**
     * Long option for help.
     */
    private static final String OPT_LONG_HELP = "help";
    /**
     * Long option for version.
     */
    private static final String OPT_LONG_VERSION = "version";
    /**
     * Long option for inheritance graph.
     */
    private static final String OPT_LONG_INHERITANCEGRAPH = "inheritance-graph";
    /**
     * Long option for package encapsulation.
     */
    private static final String OPT_LONG_PACKAGE_ENCAPSULATION = "package-encapsulation";

    /**
     * Command line arguments.
     */
    private final String[] args;
    /**
     * STDOUT.
     */
    private final PrintStream out;
    /**
     * STDERR.
     */
    private final PrintStream err;
    /**
     * Command line options.
     */
    private final Options options = new Options();
    /**
     * Command line argument parser.
     */
    private final CommandLineParser parser = new PosixParser();
    /**
     * Version information.
     */
    private final Version version;

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments
     * @param out print stream for STDOUT
     * @param err print stream for STDERR
     */
    private App(final String[] args, final PrintStream out, final PrintStream err) throws UnsupportedEncodingException {
        super();
        this.args = args;
        this.out = new PrintStream(out, true, ENCODING);
        this.err = new PrintStream(err, true, ENCODING);
        this.version = new Version("/de/weltraumschaf/codeanalyzer/version.properties");
    }

    /**
     * Invoked by JVM.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) throws UnsupportedEncodingException {
        final App app = new App(args, System.out, System.err);
        System.exit(app.run());
    }

    /**
     * Run the program.
     *
     * @return exit code, everything else than 0 means error
     */
    private int run() {
        try {
            version.load();
        } catch (IOException ex) {
            err.println(ex.getMessage());
            return 1;
        }

        configureOptions();

        try {
            final CommandLine cmd = parser.parse(options, args);
            return run(cmd);
        } catch (ParseException ex) {
            err.println(ex.getMessage());
            return 1;
        }
    }

    private int run(CommandLine cmd) {
        if (cmd.hasOption(OPT_SHORT_HELP) || cmd.hasOption(OPT_LONG_HELP)) {
            help(new HelpFormatter(), out);
            return 0;
        }

        if (cmd.hasOption(OPT_SHORT_VERSION) || cmd.hasOption(OPT_LONG_VERSION)) {
            out.println(version);
            return 0;
        }

        final Collection<File> files = readSourceFiles(cmd.getArgList());
        final UnitCollector data;

        try {
            data = collectData(files);
        } catch (IOException ex) {
            err.println(ex.getMessage());
            return 1;
        }

        if (cmd.hasOption(OPT_SHORT_INHERITANCE_GRAPH) || cmd.hasOption(OPT_LONG_INHERITANCEGRAPH)) {
            inheritanceGraph(data);
        } else if (cmd.hasOption(OPT_SHORT_INHERITANCE_GRAPH) || cmd.hasOption(OPT_LONG_INHERITANCEGRAPH)) {
            packageEncapsulationReport(data);
        } else {
            out.println("Do nothing.");
        }

        return 0;
    }

    /**
     * Configure option parsing.
     *
     * <pre>
     * -h|--help
     * -v|--version
     * -e|--encoding ENCODING
     * </pre>
     */
    private void configureOptions() {
        // w/ argument
        options.addOption(OptionBuilder.withDescription("Input file encoding.")
                                       .withArgName("ENCODING")
                                       .hasArg()
                                       .withLongOpt("--encoding")
                                       .create("e"));
        // w/o argument
        options.addOption(OPT_SHORT_HELP, OPT_LONG_HELP, false, "Show this help.");
        options.addOption(OPT_SHORT_VERSION, OPT_LONG_VERSION, false, "Show version.");
        options.addOption(OPT_SHORT_INHERITANCE_GRAPH, OPT_LONG_INHERITANCEGRAPH, false,
                "Generates inheritance graph in DOT language format.");
        options.addOption(OPT_SHORT_PACKAGE_ENCAPSULATION, OPT_LONG_PACKAGE_ENCAPSULATION, false,
                "Generates package encapsualtion report.");
    }

    /**
     * Print help message to output stream.
     *
     * @param formatter used to format
     * @param out where to print help
     */
    private void help(final HelpFormatter formatter, final PrintStream out) {
        final PrintWriter writer = new PrintWriter(out);
        formatter.printHelp(
            writer,
            HelpFormatter.DEFAULT_WIDTH,
            "ca.sh",
            HEADER,
            options,
            HelpFormatter.DEFAULT_LEFT_PAD,
            HelpFormatter.DEFAULT_DESC_PAD,
            FOOTER,
            true);
        writer.flush();
    }

    /**
     * Read all files from the directory given as first CLI argument.
     *
     * @param leftOverArgs all not jet parsed arguments are treated as files
     * @return collection of files, never {@code null}, maybe empty collection
     */
    private Collection<File> readSourceFiles(final List<String> leftOverArgs) {
        if (leftOverArgs.isEmpty()) {
            return Collections.emptyList();
        }

        final Collection<File> files = new ArrayList<File>();
        final FileFinder finder = new FileFinder();

        for (final String arg : leftOverArgs) {
            files.addAll(finder.findJava(new File(arg)));
        }

        return files;
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
        final JavaFileAnalyzer analyzer = new JavaFileAnalyzer(data);

        for (final File file : files) {
            analyzer.analyze(file);
        }

        generateTestData(data);
        return data;
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
        classFooImplB.implement(ifaceBar);

        final Interface ifaceBaz = new Interface(pkg, "Baz");
        data.addInterface(ifaceBaz);
        ifaceBaz.extend(ifaceBar);

        final Class classBazImplA = new Class(pkg, "BazImplA");
        data.addClass(classBazImplA);
        classBazImplA.implement(ifaceBaz);
    }

    private void inheritanceGraph(final UnitCollector data) {
        final Report report = Reports.createInheritanceGraph();
        report.setData(data);
        out.print(report.generate());
    }

    /**
     * Generate and print out the report.
     *
     * @param data to generate report from
     */
    private void packageEncapsulationReport(final UnitCollector data) {
        final Report report = Reports.createPackageEncapsulation();
        report.setData(data);
        out.print(report.generate());
    }


}
