package org.algorithmx.rules.core.rules;

@FunctionalInterface
public interface Rule10<A, B, C, D, E, F, G, H, I, J> extends Rule {

    boolean when(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j);
}