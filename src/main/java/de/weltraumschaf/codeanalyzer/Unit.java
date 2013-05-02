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

/**
 * Interface for a found unit like classes or interfaces.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Unit {

    /**
     * Get the containing package.
     *
     * @return containing package
     */
    Package getContainingPackage();

    /**
     * Get the name.
     *
     * @return base name w/o package
     */
    String getName();

    /**
     * Get the full qualified name.
     *
     * @return package name combined w/ name
     */
    String getFullQualifiedName();

    /**
     * Get the position where in source the unit was found.
     *
     * @return position object
     */
    Position getPosition();

    /**
     * Set the position.
     *
     * @param position where the unit was found
     */
    void setPosition(Position position);

    /**
     * Visibility of the unit.
     *
     * @return visibility of the unit
     */
    Visibility getVisibility();

    /**
     * Took an other unit and checks if it has newer information than itself.
     *
     * @param other unit to get new information from
     */
    void update(Unit other);

}
