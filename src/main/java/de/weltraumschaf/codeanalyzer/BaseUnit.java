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
abstract class BaseUnit implements Unit {

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

    /**
     * Dedicated constructor.
     *
     * @param containingPackage package which contains the unit
     * @param name of the unit
     * @param visibility visibility of the unit
     */
    public BaseUnit(final Package containingPackage, final String name, final Visibility visibility) {
        super();
        this.containingPackage = containingPackage;
        this.name = name;
        this.visibility = visibility;
    }

    @Override
    public Package getContainingPackage() {
        return containingPackage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullQualifiedName() {
        return containingPackage.getFullQualifiedName() + Package.SEPARATOR + name;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void update(final Unit other) {
        if (equals(other)) {
            return;
        }

        if (!(other instanceof BaseUnit)) {
            return;
        }

        final BaseUnit unit = (BaseUnit) other;
        visibility = unit.visibility;
        position = unit.position;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(containingPackage, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseUnit)) {
            return false;
        }

        final BaseUnit other = (BaseUnit) obj;
        return Objects.equal(containingPackage, other.containingPackage)
            && Objects.equal(name, other.name)
            && Objects.equal(visibility, other.visibility)
            && Objects.equal(position, other.position);
    }

    @Override
    public String toString() {
        return getFullQualifiedName();
    }

}
