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

import de.weltraumschaf.codeanalyzer.Modifiers.ModifierAdapter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link Modifiers}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ModifiersTest {

    @Test
    public void notAbstractAndPackagePrivateByDefault() {
        final Modifiers sut = new Modifiers(Collections.<ModifierAdapter>emptySet());
        assertThat(sut.isAbstract(), is(false));
        assertThat(sut.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void notAbstractAndPackagePrivate() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(false));
        assertThat(sut.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void isAbstractAndPackagePrivate() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        final ModifierAdapter isAbstract = mock(ModifierAdapter.class);
        when(isAbstract.isAbstract()).thenReturn(Boolean.TRUE);
        modifiers.add(isAbstract);
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(true));
        assertThat(sut.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void privateMofider() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        final ModifierAdapter isPrvate = mock(ModifierAdapter.class);
        when(isPrvate.isPrivate()).thenReturn(Boolean.TRUE);
        modifiers.add(isPrvate);
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(false));
        assertThat(sut.getVisibility(), is(Visibility.PRIVATE));
    }

    @Test
    public void protectedMofider() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        final ModifierAdapter isProtected = mock(ModifierAdapter.class);
        when(isProtected.isProtected()).thenReturn(Boolean.TRUE);
        modifiers.add(isProtected);
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(false));
        assertThat(sut.getVisibility(), is(Visibility.PROTECTED));
    }

    @Test
    public void publicModifier() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        modifiers.add(mock(ModifierAdapter.class));
        final ModifierAdapter isPublic = mock(ModifierAdapter.class);
        when(isPublic.isPublic()).thenReturn(Boolean.TRUE);
        modifiers.add(isPublic);
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(false));
        assertThat(sut.getVisibility(), is(Visibility.PUBLIC));
    }

    @Test
    public void publicAndAbstract() {
        final Set<ModifierAdapter> modifiers = new HashSet<ModifierAdapter>();
        modifiers.add(mock(ModifierAdapter.class));
        final ModifierAdapter isAbstract = mock(ModifierAdapter.class);
        when(isAbstract.isAbstract()).thenReturn(Boolean.TRUE);
        modifiers.add(isAbstract);
        final ModifierAdapter isPublic = mock(ModifierAdapter.class);
        when(isPublic.isPublic()).thenReturn(Boolean.TRUE);
        modifiers.add(isPublic);
        final Modifiers sut = new Modifiers(modifiers);
        assertThat(sut.isAbstract(), is(true));
        assertThat(sut.getVisibility(), is(Visibility.PUBLIC));
    }

}