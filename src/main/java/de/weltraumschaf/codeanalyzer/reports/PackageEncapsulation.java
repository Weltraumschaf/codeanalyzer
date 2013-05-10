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
class PackageEncapsulation extends BaseReport {

    /**
     * Convenience constructor which initializes {@link #formatt} with {@link Formatters#createDefault()}.
     */
    public PackageEncapsulation() {
        super(Formatters.createDefault());
    }

    @Override
    public String generate() {
        final StringBuilder buf = new StringBuilder();
        buf.append(format.title("Package encapsulation"));
        final Collection<Interface> ifaces = data.getInterfaces();

        if (ifaces.isEmpty()) {
            buf.append(format.text("No data to generate report for."));
            return buf.toString();
        }

        for (final Interface iface : ifaces) {
            buf.append(format.iface(iface));
            final Collection<Class> implementations = iface.getImplementations();

            if (implementations.isEmpty()) {
                buf.append(format.text("No implementations."));
            } else {
                buf.append(format.implementation());

                for (final Class clazz : implementations) {
                    buf.append(format.implementation(clazz));
                }
            }

            buf.append(format.nl());
        }

        return buf.toString();
    }

}
