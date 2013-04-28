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

import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;

/**
 * Represents a Java package.
 *
 * A package has a full qualified name like {@code de.weltraumschaf.codeanalyzer}.
 * This FQN has a base name {@code codeanlayzer} and a parent package {@code de.weltraumschaf}.
 * The parent package of {@code de} is {@link Null}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Package {

    /**
     * Package name separator.
     */
    static final char SEPARATOR = '.';
    /**
     * Used as top of the package hierarchy.
     */
    static final Package NULL = new Null();
    /**
     * Parent package.
     *
     * Maybe {@code null} in case of {@link Null}.
     */
    private final Package parent;
    /**
     * The package base name itself.
     *
     * This is the part from the last {@value #SEPARATOR}.
     */
    private final String baseName;

    /**
     * Initializes the {@link #parent} with {@value #NULL}.
     *
     * Use {@link #create(java.lang.String)} to create instance.
     *
     * @param baseName package base name.
     */
    private Package(final String baseName) {
        this(NULL, baseName);
    }

    /**
     * Dedicated constructor.
     *
     * Use {@link #create(java.lang.String)} to create instance.
     *
     * @param parent parent package
     * @param baseName package base name.
     */
    private Package(final Package parent, final String baseName) {
        super();
        this.parent = parent;
        this.baseName = baseName;
    }

    /**
     * Get the base name.
     *
     * @return return the part from the last {@value #SEPARATOR} of the {@link #getFullQualifiedName()}.
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * Returns the whole package name up to the root package.
     *
     * @return full qualified name
     */
    public String getFullQualifiedName() {
        if (isRoot()) {
            return baseName;
        }

        return parent.getFullQualifiedName() + SEPARATOR + baseName;
    }

    /**
     * Get the parent package.
     *
     * @return may return {@code null} in case of the {@link Null} package
     */
    public Package getParent() {
        return parent;
    }

    /**
     * Creates a sub package of the package.
     *
     * @param name base name of the new sub package.
     * @return new package which has this package as parent
     */
    public Package createSub(final String name) {
        validateBaseName(name);
        return new Package(this, name);
    }

    /**
     * Whether this is the top most package of the hierarchy.
     *
     * @return @code true} if {@link #parent} is {@value #NULL}; else {@code false}
     */
    public boolean isRoot() {
        return NULL.equals(getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parent, baseName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Package)) {
            return false;
        }

        final Package other = (Package) obj;
        return Objects.equal(parent, other.parent)
            && Objects.equal(baseName, other.baseName);
    }

    @Override
    public String toString() {
        return getFullQualifiedName();
    }

    /**
     * Validates that a given name is a valid package base name (parts between {@value #SEPARATOR}.
     *
     * @param name name to validate
     * CHECKSTYLE:OFF
     * @throws NullPointerException if {@code null} passed in
     * @throws IllegalArgumentException if empty string passed in
     *                                  if name starts with number
     *                                  if string contains something else than letters, digits or '_'
     * CHECKSTYLE:ON
     */
    public static void validateBaseName(final String name) {
        if (null == name) {
            throw new NullPointerException("Name must not be null!");
        }

        final String trimmedName = name.trim();

        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty!");
        }

        if (CharMatcher.DIGIT.indexIn(trimmedName) == 0) {
            throw new IllegalArgumentException(String.format("Name '%s' must not start with number!", trimmedName));
        }

        for (final char c : trimmedName.toCharArray()) {
            if (CharMatcher.JAVA_LETTER_OR_DIGIT.matches(c)) {
                continue;
            }

            if ('_' == c) {
                continue;
            }

            throw new IllegalArgumentException(
                String.format("Name '%s' contains invalid character '%s'! Allowed are letters and digits.",
                              trimmedName, c));
        }
    }

    /**
     * Creates a package with all parent packages.
     *
     * If a package name like 'foo.bar.baz' is passed in a package with base name 'foo' is returned,
     * which will have a parent package with base name 'bar'. And so on up to the parent packe {@value #NULL}.
     *
     * This method calls itself recursively to build up package hierarchy.
     *
     * @param fullQualifiedName full qualified package name
     * @return new package object
     * CHECKSTYLE:OFF
     * @throws NullPointerException if null passed in
     * @throws IllegalArgumentException if empty string passed in
     *                                  if any base name part does not comply to
     *                                  {@link #validateBaseName(java.lang.String)}
     * CHECKSTYLE:ON
     */
    public static Package create(final String fullQualifiedName) {
        if (null == fullQualifiedName) {
            throw new NullPointerException("Name must not be null!");
        }

        final String trimmedName = fullQualifiedName.trim();

        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty!");
        }

        final String baseName = splitBaseName(fullQualifiedName);
        final String parentName = splitParentName(fullQualifiedName);

        if (parentName.isEmpty()) {
            return new Package(baseName);
        }

        return new Package(create(parentName), baseName);
    }

    /**
     * Returns the base name part of a full qualified package name.
     *
     * For 'foo.bar.baz' you will get 'baz'.
     *
     * @param fullQualifiedName name to split base name of
     * @return part from the last {@value #SEPARATOR}
     */
    static String splitBaseName(final String fullQualifiedName) {
        final int lastDot = fullQualifiedName.lastIndexOf(SEPARATOR);
        return fullQualifiedName.substring(lastDot + 1);
    }

    /**
     * Returns the parent package name.
     *
     * @param fullQualifiedName name to split parent package of
     * @return part up to the last {@value #SEPARATOR}
     */
    static String splitParentName(final String fullQualifiedName) {
        final int lastDot = fullQualifiedName.lastIndexOf(SEPARATOR);

        if (-1 == lastDot) {
            return "";
        }

        return fullQualifiedName.substring(0, lastDot);
    }

    /**
     * Special kind of package to represent that there is no more parent package.
     */
    public static final class Null extends Package {

        /**
         * Initializes base name with empty string and parent package is {@code null}.
         */
        private Null() {
            super("");
        }

        /**
         * Return itself to prevent null pointer exceptions.
         *
         * @return always itself
         */
        @Override
        public Package getParent() {
            return this;
        }

    }
}
