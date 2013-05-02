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

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * Find files recursively in a directory.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class FileFinder {

    /**
     * Pattern to match Java files.
     */
    private static final String JAVA_FILE_PATTERN = "^.*\\.java$";

    /**
     * Find all Java files in given directory.
     *
     * @param baseDirectory directory to crawl
     * @return list of directories
     * CHECKSTYLE:OFF
     * @throws NullPointerException if {@code null} passed in
     * CHECKSTYLE:ON
     */
    public List<File> findJava(final File baseDirectory) {
        if (null == baseDirectory) {
            throw new NullPointerException();
        }

        return Lists.newLinkedList(FileUtils.listFiles(
                baseDirectory,
                new RegexFileFilter(JAVA_FILE_PATTERN),
                DirectoryFileFilter.DIRECTORY));
    }

}
