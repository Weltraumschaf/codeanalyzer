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
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class UnitCollector {

    private Map<String, Interface> interfaces = Maps.newHashMap();
    private Map<String, Class> classes = Maps.newHashMap();

    public boolean hasInterface(final String fullQualifiedName) {
        return interfaces.containsKey(fullQualifiedName);
    }

    public Interface getInterface(final String fullQualifiedName) {
        if (!hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(String.format("Does not have interface with name %s!", fullQualifiedName));
        }
        return interfaces.get(fullQualifiedName);
    }

    public void addInterface(final Interface i) {
        final String fullQualifiedName = i.getFullQualifiedName();
        if (hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(String.format("Does already have interface with name %s!", fullQualifiedName));
        }
        interfaces.put(fullQualifiedName, i);
    }

    public boolean hasClass(final String fullQualifiedName) {
        return classes.containsKey(fullQualifiedName);
    }

    public Class getClass(final String fullQualifiedName) {
        if (!hasClass(fullQualifiedName)) {
            throw new IllegalArgumentException(String.format("Does not have class with name %s!", fullQualifiedName));
        }
        return classes.get(fullQualifiedName);
    }

    public void addClass(final Class c) {
        final String fullQualifiedName = c.getFullQualifiedName();
        if (hasInterface(fullQualifiedName)) {
            throw new IllegalArgumentException(String.format("Does already have interface with name %s!", fullQualifiedName));
        }
        classes.put(fullQualifiedName, c);
    }

}
