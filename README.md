# Java Code Analyzer

![This project is not actively maintained!](https://img.shields.io/badge/Development-inactive-red?style=for-the-badge)

# Reports

    I PUBLIC foo.bar.Foo (foo/bar/Foo.java:23)
        +- I PUBLIC foo.bar.Bar (foo/bar/Bar.java:23)
        |      +- C ABSTRACT PACKAGE foo.bar.AbstractBar (foo/bar/AbstractBar.java:23)
        |             +- C PACKAGE foo.bar.BarImpl1 (foo/bar/BarImpl1.java:23)
        |             +- C PACKAGE foo.bar.BarImpl2 (foo/bar/BarImpl2.java:23)
        +- I PUBLIC foo.bar.Baz (foo/bar/Baz.java:23)
        |      +- C PACKAGE foo.bar.BazImpl1 (foo/bar/BazImpl1:23)
        |      +- C PACKAGE foo.bar.BazImpl2 (foo/bar/BazImpl2:23)
        +- C PACKAGE foo.bar.FooImpl (foo/bar/FooImpl.java:23)
