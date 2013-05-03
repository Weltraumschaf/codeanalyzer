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

import de.weltraumschaf.codeanalyzer.Interface;
import de.weltraumschaf.codeanalyzer.Class;
import de.weltraumschaf.codeanalyzer.UnitCollector;
import java.util.Collection;

/**
 * Generates a report about package encapsulation.
 *
 * Package encapsulation means:
 * If there is an public interface Foo with some implementations (e.g. FooImplA, FooImplB),
 * then the implementations should be package private. So outside the package only the type
 * Foo is referenced. For creating the concrete instances there should be a factory.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class PackageEncapsulation implements Report {

    /**
     * Collected data from which the report will be generated.
     */
    private UnitCollector data;
    /**
     * Used to format the output string.
     */
    private Formatter formatt;

    /**
     * Convenience constructor which initializes {@link #formatt} with {@link Formatters#createDefault()}.
     */
    public PackageEncapsulation() {
        this(Formatters.createDefault());
    }

    /**
     * Dedicated constructor.
     *
     * @param fmt used to format the output string
     * CHECKSTYLE:OFF
     * @throws NullPointerException if fmt is {@code null}
     * CHECKSTYLE:ON
     */
    public PackageEncapsulation(final Formatter fmt) {
        super();

        if (null == fmt) {
            throw new NullPointerException("Formatter must not be null!");
        }

        this.formatt = fmt;
    }


    @Override
    public void setData(final UnitCollector data) {
        this.data = data;
    }

    @Override
    public void setFormatter(final Formatter fmt) {
        this.formatt = fmt;
    }

    @Override
    public String generate() {
        final StringBuilder buf = new StringBuilder();
        buf.append(formatt.title("Package encapsulation"));
        final Collection<Interface> ifaces = data.getInterfaces();

        if (ifaces.isEmpty()) {
            buf.append(formatt.text("No data to generate report for."));
            return buf.toString();
        }

        for (final Interface iface : ifaces) {
            buf.append(formatt.iface(iface));
            final Collection<Class> implementations = iface.getImplementations();

            if (implementations.isEmpty()) {
                buf.append(formatt.text("No implementations."));
            } else {
                buf.append(formatt.implementation());

                for (final Class clazz : implementations) {
                    buf.append(formatt.implementation(clazz));
                }
            }

            buf.append(formatt.nl());
        }

        return buf.toString();
    }

}
