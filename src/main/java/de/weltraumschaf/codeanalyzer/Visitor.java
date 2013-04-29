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

import com.google.common.collect.Maps;
import de.weltraumschaf.codeanalyzer.Unit.Visibility;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Visitor extends ASTVisitor {

    private final UnitCollector data;
    private final Package pkg;
    private final CompilationUnit cu;
    private final File file;

    public Visitor(final UnitCollector data, final Package pkg, final CompilationUnit cu, final File file) {
        super();
        this.data = data;
        this.pkg = pkg;
        this.cu = cu;
        this.file = file;
    }

    /**
     * Maps Type to their package.
     */
    private final Map<String, String> imports = Maps.newHashMap();

    @Override
    public boolean visit(ImportDeclaration node) {
        final String name = node.getName().toString();
        imports.put(Package.splitBaseName(name), Package.splitParentName(name));
        return true;
    }


    @Override
    public boolean visit(final TypeDeclaration node) {
        final Unit unit;

//        Type sup = node.getSuperclassType();
//        TypeDeclaration[] types = node.getTypes();

        if (node.isInterface()) {
            unit = visitInterfaceDeclaration(node);
        } else {
            unit = visitClassDeclaration(node);
        }

        return true;
    }

    private Unit visitInterfaceDeclaration(final TypeDeclaration node) {
        final Interface iface = new Interface(pkg, node.getName().toString(), determineVisibility(node));
        iface.setPosition(createPosition(node.getName()));

        if (data.hasInterface(iface.getFullQualifiedName())) {
            data.getInterface(iface.getFullQualifiedName()).update(iface);
        } else {
            data.addInterface(iface);
        }

        return iface;
    }

    private Unit visitClassDeclaration(final TypeDeclaration node) {
        final Class clazz = new Class(
            pkg,
            node.getName().toString(),
            determineVisibility(node),
            determineAbstractness(node));
        clazz.setPosition(createPosition(node.getName()));
        data.addClass(clazz);

        for (final SimpleType t : (List<SimpleType>) node.superInterfaceTypes()) {
            final String name = t.getName().toString();
            Interface implemented;

            if (data.hasInterface(name)) {
                implemented = data.getInterface(name);
            } else {
                if (imports.containsKey(name)) {
                    implemented = new Interface(Package.create(imports.get(name)), name);
                } else {
                    // No imports -> same package.
                    implemented = new Interface(pkg, name);
                }

                data.addInterface(implemented);
            }

            implemented.addImplementation(clazz);
        }

        return clazz;
    }

    private boolean determineAbstractness(final TypeDeclaration node) {
        final List<Modifier> mods = node.modifiers();

        for (final Modifier m : mods) {
            if (m.isAbstract()) {
                return true;
            }
        }

        return false;
    }
    private Visibility determineVisibility(final TypeDeclaration node) {
        final List<Modifier> mods = node.modifiers();

        for (final Modifier m : mods) {
            if (m.isPrivate()) {
                return Visibility.PRIVATE;
            } else if (m.isProtected()) {
                return Visibility.PROTECTED;
            } else if (m.isPublic()) {
                return Visibility.PUBLIC;
            }
        }

        return Visibility.PACKAGE;
    }

    private Position createPosition(final Name name) {
        return new Position(file.getAbsolutePath(), cu.getLineNumber(name.getStartPosition()));
    }
}
