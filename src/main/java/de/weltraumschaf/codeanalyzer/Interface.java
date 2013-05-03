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
     * Hold all interface which extends this one.
     *
     * <dl>
     *  <dt>Key
     *  <dd>Full qualified name
     *  <dt>Value
     *  <dd>Interface object
     * </dl>
     */
    private final Map<String, Interface> extendedByInterfaces = Maps.newHashMap();
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
     * Convenience constructor which initializes the visibility w/ {@link Visibility#PUBLIC}.
     *
     * @param containingPackage package which contains the unit
     * @param name of the interface
     */
    public Interface(final Package containingPackage, final String name) {
        this(containingPackage, name, Visibility.PUBLIC);
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

    public void extendedBy(final Interface iface) {
        extendedByInterfaces.put(iface.getFullQualifiedName(), iface);
    }

    public boolean hasExtendingInterfaces() {
        return !extendedByInterfaces.isEmpty();
    }

    public Collection<Interface> getExtendingInterfaces() {
        return extendedByInterfaces.values();
    }

    /**
     * Set an interface as extending on to this.
     *
     * @param iface extended interface
     */
    public void extend(final Interface iface) {
        extendingInterfaces.put(iface.getFullQualifiedName(), iface);
        iface.extendedBy(this);
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

    /**
     * Get the interface which this one extends.
     *
     * Check with {@link #doesExtend(java.lang.String)} before invoke this method.
     *
     * @param fullQualifiedName of the extended interface
     * @return extending interface object
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if this interface does not extend the given interface
     * CHECKSTYLE:ON
     */
    public Interface getExtendedInterface(final String fullQualifiedName) {
        if (!doesExtend(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Interface %s does not extend %s!", getFullQualifiedName(), fullQualifiedName));
        }

        return extendingInterfaces.get(fullQualifiedName);
    }

    /**
     * Add class which implements this interface.
     *
     * If this interface was not already added to the given class as implemented interface,
     * this method also add itself as implemented interface to the class.
     *
     * @param implementation implementing class
     */
    public void addImplementation(final Class implementation) {
        implementations.put(implementation.getFullQualifiedName(), implementation);
        // Must be afterwards to prevent endless loop.
        if (!implementation.doesImplement(this)) {
            implementation.implement(this);
        }
    }

    /**
     * Return whether this interface has implementations or not.
     *
     * @return {@code true} if this interface has implementations, else {@code false}
     */
    public boolean hasImplementations() {
        return !implementations.isEmpty();
    }

    /**
     * Get all classes implementing this interface.
     *
     * @return collection of implementing classes
     */
    public Collection<Class> getImplementations() {
        return implementations.values();
    }

    /**
     * Return whether this interface has a class as implementation or not.
     *
     * @see #hasImplementation(java.lang.String)
     * @param clazz class to ask for
     * @return {@code true} if the interface has this class as implementation, else {@code false}
     */
    @SuppressWarnings(value = "OCP_OVERLY_CONCRETE_PARAMETER", justification = "Only class can be an implementaion.")
    public boolean hasImplementation(final Class clazz) {
        return hasImplementation(clazz.getFullQualifiedName());
    }

    /**
     * Return whether this interface has a class as implementation or not.
     *
     * @param fullQualifiedName of the class
     * @return {@code true} if the interface has this class as implementation, else {@code false}
     */
    public boolean hasImplementation(final String fullQualifiedName) {
        return implementations.containsKey(fullQualifiedName);
    }

    @Override
    public void update(final Unit unit) {
        if (!(unit instanceof Interface)) {
            return;
        }

        super.update(unit);
    }

}
