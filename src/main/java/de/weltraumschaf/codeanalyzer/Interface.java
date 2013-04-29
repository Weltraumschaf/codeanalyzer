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

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Represents a Java interface.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Interface extends Unit {

    private final Map<String, Interface> extendingInterfaces = Maps.newHashMap();
    private final Map<String, Class> implementations = Maps.newHashMap();

    public Interface(final Package containingPackage, final String name) {
        this(containingPackage, name, Visibility.PACKAGE);
    }

    public Interface(final Package containingPackage, final String name, final Visibility visibility) {
        super(containingPackage, name, visibility);
    }

    public void extend(final Interface iface) {
        extendingInterfaces.put(iface.getFullQualifiedName(), iface);
    }

    public boolean doesExtend(final String fullQualifiedName) {
        return extendingInterfaces.containsKey(fullQualifiedName);
    }

    public Interface getInterface(final String fullQualifiedName) {
        if (!doesExtend(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Interface %s does not extend %s!", getFullQualifiedName(), fullQualifiedName));
        }

        return extendingInterfaces.get(fullQualifiedName);
    }

    public void addImplementation(final Class implementation) {
        implementation.implement(this);
        implementations.put(implementation.getFullQualifiedName(), implementation);
    }

    @Override
    public void update(final Unit unit) {
        if (!(unit instanceof Interface)) {
            return;
        }

        super.update(unit);
        final Interface other = (Interface) unit;
    }
}
