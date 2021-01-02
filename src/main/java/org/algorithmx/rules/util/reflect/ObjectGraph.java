package org.algorithmx.rules.util.reflect;

import org.algorithmx.rules.lib.spring.util.Assert;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ObjectGraph {

    private static final Predicate<Class<?>> JAVA_CORE_CLASSES = (clazz) ->
            clazz == null || clazz.isPrimitive() || clazz.getPackage() == null
                    || clazz.getPackage().getName().startsWith("java.")
                    || clazz.getPackage().getName().startsWith("javax.")
                    || clazz.getClassLoader() == null;


    private final static Map<Class<?>, ClassFields> classCache = new HashMap<>();
    private final Map<Object, Class<?>> breadCrumbs = new IdentityHashMap<>();
    private final Deque<Object> candidates = new ArrayDeque<>();

    public ObjectGraph() {
        super();
    }

    public void traverse(Object target, ObjectVisitor visitor) throws ObjectGraphTraversalException {
        Assert.notNull(visitor, "visitor cannot be null.");
        candidates.clear();
        breadCrumbs.clear();
        // Add the root object
        addCandidate(target, visitor);

        while (!candidates.isEmpty()) {
            Object candidate = candidates.remove();

            try {
                if (candidate.getClass().isArray()) {
                    traverseArray(candidate, visitor);
                } else if (candidate instanceof Collection) {
                    traverseCollection(candidate, visitor);
                } else if (candidate instanceof Map) {
                    traverseMap(candidate, visitor);
                } else {
                    traverseObject(candidate, visitor);
                }
            } catch (Exception e) {
                throw new ObjectGraphTraversalException("Error trying to traverse [" + target.getClass() + "]", e);
            }
        }
    }

    private void addCandidate(Object candidate, ObjectVisitor visitor) {
        // Nothing to traverse
        if (candidate == null) return;
        // It's a java core class; no need to traverse further.
        if (JAVA_CORE_CLASSES.test(candidate.getClass())) return;
        // Have have already visited this object?
        if (breadCrumbs.containsKey(candidate)) return;
        // Check to see if we need to introspect this target class
        if (!visitor.shouldVisitClass(candidate.getClass())) return;
        candidates.add(candidate);
        breadCrumbs.put(candidate, candidate.getClass());
    }


    private void traverseArray(Object target, ObjectVisitor visitor) {
        Assert.notNull(visitor, "visitor cannot be null.");

        // Nothing to traverse
        if (target == null) return;
        // It's not an array
        if (!target.getClass().isArray())
            throw new IllegalStateException("Invalid type. It should be an Array. " +
                    "Given [" + target.getClass() + "]");
        // Looks like a primitive array. No need to traverse any further.
        if (target.getClass().getComponentType().isPrimitive()) return;

        for (int i = 0; i < Array.getLength(target); i++) {
            Object element = Array.get(target, i);
            addCandidate(element, visitor);
        }
    }

    private void traverseCollection(Object target, ObjectVisitor visitor) {
        Assert.notNull(visitor, "visitor cannot be null.");

        // Nothing to traverse
        if (target == null) return;
        // It's not an Collection
        if (!(target instanceof Collection))
            throw new IllegalStateException("Invalid type. It should be an Collection. " +
                    "Given [" + target.getClass() + "]");

        Collection collection = (Collection) target;

        for (Object element : collection) {
            addCandidate(element, visitor);
        }
    }

    private void traverseMap(Object target, ObjectVisitor visitor) {
        Assert.notNull(visitor, "visitor cannot be null.");

        // Nothing to traverse
        if (target == null) return;
        // It's not an Map
        if (!(target instanceof Map))
            throw new IllegalStateException("Invalid type. It should be an Map. " +
                    "Given [" + target.getClass() + "]");

        Map<?, ?> map = (Map) target;

        for (Map.Entry<?, ?> e : map.entrySet()) {
            // First visit the Key
            addCandidate(e.getKey(), visitor);
            // First visit the Value
            addCandidate(e.getValue(), visitor);
        }
    }

    private void traverseObject(Object target, ObjectVisitor visitor) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        try {
            boolean introspectObject = visitor.visitObjectStart(target);
            if (introspectObject) {
                processFields(target, visitor);
                processProperties(target, visitor);
            }
        } finally {
            visitor.visitObjectEnd(target);
        }
    }

    private void processFields(Object target, ObjectVisitor visitor) throws IllegalAccessException {

        ClassFields classFields = classCache.get(target);

        if (classFields == null) {
            Field[] declaredFields = ReflectionUtils.getDeclaredFields(target.getClass(),
                    (Field field) -> visitor.shouldVisitField(field));
            classFields = new ClassFields(declaredFields);
            classCache.put(target.getClass(), classFields);
        }

        for (Field field : classFields.fields) {
            field.setAccessible(true);
            Object value = field.get(target);
            boolean introspectField = false;

            if (value == null) {
                visitor.visitNull(field, null, target);
            } else if (value instanceof Collections) {
                introspectField = visitor.visitCollection(field, (Collection) value, target);
            } else if (value instanceof Map) {
                introspectField = visitor.visitMap(field, (Map) value, target);
            } else if (value.getClass().isArray()) {
                introspectField = visitor.visitArray(field, (Object[]) value, target);
            } else {
                introspectField = visitor.visitField(field, value, target);
            }

            if (introspectField) addCandidate(value, visitor);
        }
    }

    private void processProperties(Object target, ObjectVisitor visitor) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        for (PropertyDescriptor property : Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors()) {
            if (!visitor.shouldVisitProperty(property)) continue;

            Object value = property.getReadMethod().invoke(target);
            boolean introspectProperty = false;

            if (value == null) {
                visitor.visitNull(property, null, target);
            } else if (value instanceof Collections) {
                introspectProperty = visitor.visitCollection(property, (Collection) value, target);
            } else if (value instanceof Map) {
                introspectProperty = visitor.visitMap(property, (Map) value, target);
            } else if (value.getClass().isArray()) {
                introspectProperty = visitor.visitArray(property, (Object[]) value, target);
            } else {
                introspectProperty = visitor.visitProperty(property, value, target);
            }

            if (introspectProperty) addCandidate(value, visitor);
        }
    }

    private static class ClassFields {
        private Field[] fields;

        public ClassFields(Field[] fields) {
            super();
            this.fields = fields;
        }

        public Field[] getFields() {
            return fields;
        }
    }
}