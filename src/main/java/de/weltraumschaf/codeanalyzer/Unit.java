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

/**
 * Abstract base class for {@link Class} and {@link Interface}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class Unit {

    /**
     * Package in which the unit is declared.
     */
    private final Package containingPackage;
    /**
     * Name of the class or interface.
     */
    private final String name;
    /**
     * Visibility of unit.
     */
    private Visibility visibility;
    /**
     * Position in the source code where it is declared.
     */
    private Position position = Position.DEFAULT;

    public Unit(final Package containingPackage, final String name, final Visibility visibility) {
        super();
        this.containingPackage = containingPackage;
        this.name = name;
        this.visibility = visibility;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Took an other unit and checks if it has newer information than itself.
     *
     * @param unit unit to get new information from
     */
    public void update(final Unit unit) {
        if (equals(unit)) {
            return;
        }

        visibility = unit.visibility;
        position = unit.position;
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
        return Objects.equal(containingPackage, other.containingPackage)
            && Objects.equal(name, other.name)
            && Objects.equal(visibility, other.visibility)
            && Objects.equal(position, other.position);
    }

    @Override
    public String toString() {
        return getFullQualifiedName();
    }

    public static enum Visibility {
        PRIVATE, PACKAGE, PROTECTED, PUBLIC;
    }
}
