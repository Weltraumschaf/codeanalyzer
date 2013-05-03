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
import java.util.Collection;
import java.util.Map;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Represents a Java class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Class extends BaseUnit {

    /**
     * Holds the implemented interfaces.
     *
     * <dl>
     * <dt>Key
     * <dd>full qualified name.
     * <dt>Value
     * <dd>Instance of interface object.
     * </dl>
     */
    private final Map<String, Interface> implementedInterfaces = Maps.newHashMap();
    /**
     * Whether it is an abstract class or not.
     */
    private final boolean isAbstract;
    /**
     * Class from which this one is derived.
     *
     * May be {@code null} if class does not inherit from other class than {@link java.lang.Object}.
     */
    private Class extendedClass;

    /**
     * Convenience constructor initializes {@link BaseUnit#visibility} with {@link Visibility#PACKAGE}.
     *
     * @param containingPackage package in which the class s declared
     * @param name pure name without package
     */
    public Class(final Package containingPackage, final String name) {
        this(containingPackage, name, Visibility.PACKAGE);
    }

    /**
     * Convenience constructor initializes {@link #isAbstract} with {@code false}.
     *
     * @param containingPackage package in which the class s declared
     * @param name pure name without package
     * @param visibility visibility of the class
     */
    public Class(final Package containingPackage, final String name, final Visibility visibility) {
        this(containingPackage, name, visibility, false);
    }

    /**
     * Dedicated constructor.
     *
     * @param containingPackage package in which the class s declared
     * @param name pure name without package
     * @param visibility visibility of the class
     * @param isAbstract whether the class is abstract or not
     */
    public Class(final Package containingPackage,
            final String name,
            final Visibility visibility,
            final boolean isAbstract) {
        super(containingPackage, name, visibility);
        this.isAbstract = isAbstract;
    }

    /**
     * Add an {@link Interface interface} which the class implements.
     *
     * @param iface implemented interface
     */
    public void implement(final Interface iface) {
        implementedInterfaces.put(iface.getFullQualifiedName(), iface);
        // Must be afterwards to prevent endless loop.
        if (!iface.hasImplementation(this)) {
            iface.addImplementation(this);
        }
    }

    /**
     * Whether the class implements an {@link Interface interface} or not.
     *
     * @see #doesImplement(java.lang.String)
     * @param iface to check for
     * @return {@code true} if the class implements the interface; else {@code false}
     */
    @SuppressWarnings(value = "OCP_OVERLY_CONCRETE_PARAMETER", justification = "Only interfaces can be implemented.")
    public boolean doesImplement(final Interface iface) {
        return doesImplement(iface.getFullQualifiedName());
    }

    /**
     * Whether the class implements an {@link Interface interface} or not.
     *
     * An implemented interface must be added by {@link #implement(de.weltraumschaf.codeanalyzer.Interface)} so that
     * this method returns {@code true} for the interface.
     *
     * @param fullQualifiedName name constructed of package and name
     * @return {@code true} if the class implements the interface; else {@code false}
     */
    public boolean doesImplement(final String fullQualifiedName) {
        return implementedInterfaces.containsKey(fullQualifiedName);
    }

    /**
     * Get a particular implemented interface.
     *
     * An implemented interface must be added by {@link #implement(de.weltraumschaf.codeanalyzer.Interface)} so that
     * this method returns the interface.
     *
     * @param fullQualifiedName name constructed package and name
     * @return never returns {@code null}
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if class does not implement the interface
     * CHECKSTYLE:ON
     */
    public Interface getInterface(final String fullQualifiedName) {
        if (!doesImplement(fullQualifiedName)) {
            throw new IllegalArgumentException(
                    String.format("Class %s does not implement %s!", getFullQualifiedName(), fullQualifiedName));
        }

        return implementedInterfaces.get(fullQualifiedName);
    }

    /**
     * Return collection of all implemented interfaces.
     *
     * @return never {@code null}, maybe empty collection
     */
    public Collection<Interface> interfaces() {
        return implementedInterfaces.values();
    }

    /**
     * Get extended class.
     *
     * @return may return {@code null} if class only extends {@link java.lang.Object}
     */
    public Class extend() {
        return extendedClass;
    }

    /**
     * Set extended class.
     *
     * @param clazz which is extended
     */
    public void extend(final Class clazz) {
        this.extendedClass = clazz;
    }

    /**
     * Whether this class is abstract or not.
     *
     * @return {@code true} if class is abstract, else {@code false}
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public void update(final Unit unit) {
        if (!(unit instanceof Class)) {
            return;
        }

        super.update(unit);
    }
}
