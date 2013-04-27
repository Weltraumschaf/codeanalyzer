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

import java.util.Arrays;
import java.util.List;

public class App {

    private final List<String> args;
    private final UnitCollector data = new UnitCollector();

    public App(List<String> args) {
        this.args = args;
    }

    public static void main(String[] args) {
        final App app = new App(Arrays.asList(args));
        System.exit(app.run());
    }

    private int run() {
        collectData();
        return 0;
    }

    private void collectData() {
        // parse source from given directory and put data in collector
    }

}
