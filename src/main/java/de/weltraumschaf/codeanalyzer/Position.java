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
 * Immutable represents a source code position consisting of a file name and line number.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Position {

    /**
     * Default position.
     */
    static final Position DEFAULT = new Position("unknown", 0);
    /**
     * File name.
     */
    private final String fileName;
    /**
     * Line number.
     */
    private final int lineNumber;

    /**
     * Dedicated constructor.
     *
     * @param fileName file name
     * @param lineNumber line number
     */
    public Position(final String fileName, final int lineNumber) {
        super();
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    /**
     * Get the file name.
     *
     * @return file name string
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the line number.
     *
     * @return line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fileName, lineNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }

        final Position other = (Position) obj;
        return Objects.equal(fileName, other.fileName)
            && Objects.equal(lineNumber, other.lineNumber);
    }

    @Override
    public String toString() {
        return getFileName() + ":" + getLineNumber();
    }

}
