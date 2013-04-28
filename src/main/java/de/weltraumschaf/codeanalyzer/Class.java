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
 * Represents a Java class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Class extends Unit {

    private final Map<String, Interface> implementedInterfaces = Maps.newHashMap();
    private final boolean isAbstract;
    private Class extendedClass;

    public Class(final Package containingPackage, final String name, final Visibility visibility, final boolean isAbstract) {
        super(containingPackage, name, visibility);
        this.isAbstract = isAbstract;
    }

    public void implement(final Interface iface) {
        implementedInterfaces.put(iface.getFullQualifiedName(), iface);
    }

    public boolean doesImplement(final String fullQualifiedName) {
        return implementedInterfaces.containsKey(fullQualifiedName);
    }

    public Interface getInterface(final String fullQualifiedName) {
        if (!doesImplement(fullQualifiedName)) {
            throw new IllegalArgumentException(
                String.format("Class %s does not implement %s!", getFullQualifiedName(), fullQualifiedName));
        }

        return implementedInterfaces.get(fullQualifiedName);
    }

    public Class getExtendedClass() {
        return extendedClass;
    }

    public void setExtendedClass(Class extendedClass) {
        this.extendedClass = extendedClass;
    }

    public boolean isIsAbstract() {
        return isAbstract;
    }

    @Override
    public void update(final Unit unit) {
        if (equals(unit)) {
            return;
        }
        
        if (!(unit instanceof Class)) {
            return;
        }

        final Class other = (Class) unit;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
