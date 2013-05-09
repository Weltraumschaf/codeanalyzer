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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Wraps the modifiers of a type.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Modifiers {

    /**
     * Modifiers of the type.
     */
    private final Set<ModifierAdapter> modifiers;

    /**
     * Dedicated constructor.
     *
     * @param modifiers set of modifiers
     */
    public Modifiers(final Set<ModifierAdapter> modifiers) {
        super();
        this.modifiers = modifiers;
    }

    /**
     * Create modifiers from a type declaration AST node.
     *
     * @param node create modifiers from
     * @return always new instance
     */
    public static Modifiers create(final TypeDeclaration node) {
        return create(node.modifiers());
    }

    /**
     * Create modifiers from modifier AST node.
     *
     * Wraps {@link Modifier} with {@link ModifierAdapter}.
     *
     * @param modifiers list to wrap
     * @return always new instance
     */
    public static Modifiers create(final List<Modifier> modifiers) {
        final Set<ModifierAdapter> wrappedModifiers = new HashSet<ModifierAdapter>();

        for (final Modifier modifier : modifiers) {
            wrappedModifiers.add(new ModifierAdapter(modifier));
        }

        return new Modifiers(wrappedModifiers);
    }

    /**
     * Return if the type which this modifiers is created from is abstract.
     *
     * @return {@code true} if one of the wrapped modifier returns {@code true} on {@link Modifier#isAbstract()}; else
     * {@code false}
     */
    public boolean isAbstract() {
        for (final ModifierAdapter modifier : modifiers) {
            if (modifier.isAbstract()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the visibility of the type which this modifiers is created from.
     *
     * @return default visibility is {@link Visibility#PACKAGE}
     */
    public Visibility getVisibility() {
        for (final ModifierAdapter modifier : modifiers) {
            if (modifier.isPrivate()) {
                return Visibility.PRIVATE;
            } else if (modifier.isProtected()) {
                return Visibility.PROTECTED;
            } else if (modifier.isPublic()) {
                return Visibility.PUBLIC;
            }
        }

        return Visibility.PACKAGE;
    }

    /**
     * Wraps {@link Modifier} for better testability.
     */
    static class ModifierAdapter {

        /**
         * Wrapped modifier.
         */
        private final Modifier adapted;

        /**
         * Dedicated constructor.
         *
         * @param adapted wrapped modifier
         */
        public ModifierAdapter(final Modifier adapted) {
            super();
            this.adapted = adapted;
        }

        /**
         * Delegates to {@link Modifier#isAbstract()}.
         *
         * @return true if the receiver is the abstract modifier, false otherwise
         */
        public boolean isAbstract() {
            return adapted.isAbstract();
        }

        /**
         * Delegates to {@link Modifier#isPrivate()}.
         *
         * @return true if the receiver is the private modifier, false otherwise
         */
        public boolean isPrivate() {
            return adapted.isPrivate();
        }

        /**
         * Delegates to {@link Modifier#isProtected()}.
         *
         * @return true if the receiver is the protected modifier, false otherwise
         */
        public boolean isProtected() {
            return adapted.isProtected();
        }

        /**
         * Delegates to {@link Modifier#isPublic()}.
         *
         * @return true if the receiver is the public modifier, false otherwise
         */
        public boolean isPublic() {
            return adapted.isPublic();
        }
    }
}
