package com.github.nyukhalov.ga03n.engine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComponentTest {

    static class FooComponent extends Component {
        public int a;
    }

    @Test
    public void testCloneCustomField() {
        FooComponent c1 = new FooComponent();
        c1.a = 5;
        FooComponent c2 = new FooComponent();
        c2.a = 10;

        FooComponent c1Clone = (FooComponent) c1.clone();
        FooComponent c2Clone = (FooComponent) c2.clone();

        assertEquals(c1.a, c1Clone.a);
        assertEquals(c2.a, c2Clone.a);
    }
}
