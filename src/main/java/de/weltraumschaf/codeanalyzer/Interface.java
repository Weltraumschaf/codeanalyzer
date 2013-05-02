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
public final class Interface extends BaseUnit {

    /**
     * Hold all interface which this one extends.
     *
     * <dl>
     *  <dt>Key
     *  <dd>Full qualified name
     *  <dt>Value
     *  <dd>Interface object
     * </dl>
     */
    private final Map<String, Interface> extendingInterfaces = Maps.newHashMap();
    /**
     * Holds all classes which implement this interface.
     *
     * <dl>
     *  <dt>Key
     *  <dd>Full qualified name
     *  <dt>Value
     *  <dd>Class object
     * </dl>
     */
    private final Map<String, Class> implementations = Maps.newHashMap();

    /**
     * Convenience constructor which initializes the visibility w/ {@link Visibility#PACKAGE}.
     *
     * @param containingPackage package which contains the unit
     * @param name of the interface
     */
    public Interface(final Package containingPackage, final String name) {
        this(containingPackage, name, Visibility.PACKAGE);
    }

    /**
     * Dedicated constructor.
     *
     * @param containingPackage package which contains the unit
     * @param name the interface
     * @param visibility visibility of the interface
     */
    public Interface(final Package containingPackage, final String name, final Visibility visibility) {
        super(containingPackage, name, visibility);
    }

    /**
     * Set an interface as extending on to this.
     *
     * @param iface extended interface
     */
    public void extend(final Interface iface) {
        extendingInterfaces.put(iface.getFullQualifiedName(), iface);
    }

    /**
     * Whether this interface extends an interface or not.
     *
     * @param fullQualifiedName full qualified name of interface
     * @return {@code true} if this interface extends the given one; else {@code false}
     */
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
//        final Interface other = (Interface) unit;
    }
}
