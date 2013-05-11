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

import de.weltraumschaf.codeanalyzer.types.ClassType;
import de.weltraumschaf.codeanalyzer.types.InterfaceType;
import de.weltraumschaf.codeanalyzer.types.Package;
import de.weltraumschaf.codeanalyzer.types.Position;
import com.google.common.collect.Maps;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Visits the AST from a parsed Java file.
 *
 * Each compilation unit (from file) get an own instance of a visitor.
 * The found data is collected by a shared {@link UnitCollector}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Visitor extends ASTVisitor {

    /**
     * Collect found classes and interfaces.
     */
    private final UnitCollector data;
    /**
     * Visited code was declared in this package.
     */
    private final Package pkg;
    /**
     * Used to create the position from.
     */
    private final CompilationUnit compilationUnit;
    /**
     * Used to create the position from.
     */
    private final File file;
    /**
     * Maps Type to their package.
     */
    private final Map<String, String> imports = Maps.newHashMap();

    /**
     * Dedicated constructor.
     *
     * @param data reference to data collector shared over multiple visitors
     * @param compilationUnit according to the parsed file, used to create a {@link Position} from
     * @param file used to create a {@link Position} from
     */
    public Visitor(final UnitCollector data, final CompilationUnit compilationUnit, final File file) {
        super();
        this.data = data;
        this.compilationUnit = compilationUnit;
        this.file = file;
        this.pkg = Package.create(this.compilationUnit.getPackage().getName());
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        final String name = node.getName().toString();
        imports.put(Package.splitBaseName(name), Package.splitParentName(name));
        return true;
    }


    @Override
    public boolean visit(final TypeDeclaration node) {
//        Type sup = node.getSuperclassType();
//        TypeDeclaration[] types = node.getTypes();

        if (node.isInterface()) {
            visitInterfaceDeclaration(node);
        } else {
            visitClassDeclaration(node);
        }

        return true;
    }

    /**
     * Visits interface declarations node of the AST.
     *
     * @param node a type declaration node for which the method
     *             {@link TypeDeclaration#isInterface()} return {@code true}
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if passed in node is not an interface type
     * CHECKSTYLE:ON
     */
    private void visitInterfaceDeclaration(final TypeDeclaration node) {
        if (!node.isInterface()) {
            throw new IllegalArgumentException("Not an interfcae type declaration given!");
        }

        final Modifiers modifiers = Modifiers.create(node);
        final InterfaceType iface = new InterfaceType(pkg, node.getName().toString(), modifiers.getVisibility());
        iface.setPosition(createPosition(node.getName()));

        if (data.hasInterface(iface.getFullQualifiedName())) {
            data.getInterface(iface.getFullQualifiedName()).update(iface);
        } else {
            data.addInterface(iface);
        }
    }

    /**
     * Visits class declarations node of the AST.
     *
     * @param node a type declaration node for which the method
     *             {@link TypeDeclaration#isInterface()} return {@code false}
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if passed in node is an interface type
     * CHECKSTYLE:ON
     */
    private void visitClassDeclaration(final TypeDeclaration node) {
        if (node.isInterface()) {
            throw new IllegalArgumentException("Not an class type declaration given!");
        }

        final Modifiers modifiers = Modifiers.create(node);
        final ClassType clazz = new ClassType(
            pkg,
            node.getName().toString(),
            modifiers.getVisibility(),
            modifiers.isAbstract());
        clazz.setPosition(createPosition(node.getName()));
        data.addClass(clazz);

        for (final SimpleType t : (List<SimpleType>) node.superInterfaceTypes()) {
            final String name = t.getName().toString();
            InterfaceType implemented;

            if (data.hasInterface(name)) {
                implemented = data.getInterface(name);
            } else {
                if (imports.containsKey(name)) {
                    implemented = new InterfaceType(Package.create(imports.get(name)), name);
                } else {
                    // No imports -> same package.
                    implemented = new InterfaceType(pkg, name);
                }

                data.addInterface(implemented);
            }

            implemented.addImplementation(clazz);
        }
    }

    /**
     * Creates a source code position for a given name.
     *
     * @param name used get line number from {@link #compilationUnit}
     * @return always new object
     */
    private Position createPosition(final Name name) {
        return new Position(file.getAbsolutePath(), compilationUnit.getLineNumber(name.getStartPosition()));
    }
}
