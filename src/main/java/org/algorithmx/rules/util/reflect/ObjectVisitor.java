package org.algorithmx.rules.util.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public interface ObjectVisitor {

    default boolean shouldVisitClass(Class<?> clazz) { return true; }

    default boolean visitObjectStart(Object target) { return true; }

    default void visitObjectEnd(Object target) {}

    default boolean shouldVisitField(Field field) { return true; }

    default boolean visitNull(Field field, Object value, Object parent) { return false; }

    default boolean visitField(Field field, Object value, Object parent) { return true; }

    default boolean visitArray(Field field, Object[] values, Object parent) { return true; }

    default boolean visitCollection(Field field, Collection<?> values, Object parent) { return true; }

    default boolean visitMap(Field field, Map<?, ?> values, Object parent) { return true; }

    default boolean shouldVisitProperty(PropertyDescriptor field) {
        return true;
    }

    default boolean visitNull(PropertyDescriptor property, Object value, Object parent) { return false; }

    default boolean visitProperty(PropertyDescriptor property, Object value, Object parent) { return true; }

    default boolean visitArray(PropertyDescriptor property, Object[] values, Object parent) { return true; }

    default boolean visitCollection(PropertyDescriptor property, Collection<?> values, Object parent) { return true; }

    default boolean visitMap(PropertyDescriptor property, Map<?, ?> values, Object parent) { return true; }
}