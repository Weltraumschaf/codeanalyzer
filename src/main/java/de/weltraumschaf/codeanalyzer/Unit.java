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

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Unit {

    private final Package containingPackage;
    private final String name;
    private Set<Class> extensions = Sets.newHashSet(); // NOPMD

    public Unit(Package containingPackage, String name) {
        super();
        this.containingPackage = containingPackage;
        this.name = name;
    }

    public Package getContainingPackage() {
        return containingPackage;
    }

    public String getName() {
        return name;
    }

    public String getFullQualifiedName() {
        return containingPackage.getFullQualifiedName() + Package.SEPARATOR + name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(containingPackage, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Unit)) {
            return false;
        }

        final Unit other = (Unit) obj;
        return Objects.equal(containingPackage, other.containingPackage) && Objects.equal(name, other.name);
    }

    @Override
    public String toString() {
        return containingPackage.toString() + Package.SEPARATOR + name;
    }

}
