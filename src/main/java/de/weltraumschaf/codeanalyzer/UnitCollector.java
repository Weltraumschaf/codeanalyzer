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

import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Object to collect found interfaces and classes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class UnitCollector {

    /**
     * Found interfaces.
     *
     * <dl>
     *  <dt>Key
     *  <dd>Full qualified name
     *  <dt>Value
     *  <dd>InterfaceType object
     * </dl>
     */
    private Map<String, InterfaceType> interfaces = Maps.newHashMap();
    /**
     * Found classes.
     *
     * <dl>
     *  <dt>Key
     *  <dd>Full qualified name
     *  <dt>Value
     *  <dd>ClassType object
     * </dl>
     */
    private Map<String, ClassType> classes = Maps.newHashMap();

    /**
     * Whether an interface is present in the collector.
     *
     * @param fullQualifiedName full qualified interface name
     * @return {@code true} is interface exists; else {@code false}
     */
    public boolean hasInterface(final String fullQualifiedName) {
        return interfaces.containsKey(fullQualifiedName);
    }

    /**
     * Get an interface from the collector.
     *
     * @param fullQualifiedName full qualified interface name
     * @return interface object
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if interface was not added
     * CHECKSTYLE:ON
     */
    public InterfaceType getInterface(final String fullQualifiedName) {
        if (!hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Does not have interface with name %s!", fullQualifiedName));
        }

        return interfaces.get(fullQualifiedName);
    }

    /**
     * Add an interface to the collector.
     *
     * @param iface interface to add
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if interface w/ same full qualified name was already added
     * CHECKSTYLE:ON
     */
    public void addInterface(final InterfaceType iface) {
        final String fullQualifiedName = iface.getFullQualifiedName();

        if (hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Does already have interface with name %s!", fullQualifiedName));
        }

        interfaces.put(fullQualifiedName, iface);
    }

    /**
     * Get all collected interfaces.
     *
     * @return collection of interfaces
     */
    public Collection<InterfaceType> getInterfaces() {
        return new ArrayList<InterfaceType>(interfaces.values());
    }

    /**
     * Whether an class is present in the collector.
     *
     * @param fullQualifiedName full qualified class name
     * @return {@code true} is class exists; else {@code false}
     */
    public boolean hasClass(final String fullQualifiedName) {
        return classes.containsKey(fullQualifiedName);
    }

    /**
     * Get an class from the collector.
     *
     * @param fullQualifiedName full qualified class name
     * @return class object
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if class was not added
     * CHECKSTYLE:ON
     */
    public ClassType getClass(final String fullQualifiedName) {
        if (!hasClass(fullQualifiedName)) {
            throw new IllegalArgumentException(String.format("Does not have class with name %s!", fullQualifiedName));
        }

        return classes.get(fullQualifiedName);
    }

    /**
     * Add an class to the collector.
     *
     * @param clazz class to add
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if class w/ same full qualified name was already added
     * CHECKSTYLE:ON
     */
    public void addClass(final ClassType clazz) {
        final String fullQualifiedName = clazz.getFullQualifiedName();

        if (hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Does already have interface with name %s!", fullQualifiedName));
        }

        classes.put(fullQualifiedName, clazz);
    }

    /**
     * Get all collected classes.
     *
     * @return collection of classes
     */
    public Collection<ClassType> getClasses() {
        return new ArrayList<ClassType>(classes.values());
    }
}
