package com.github.nyukhalov.ga03n.engine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class C1 extends Component {}
class C2 extends Component {}
class C2_1 extends C2 {}

public class GameObjectTest {
    @Test
    public void testGetComponent() {
        GameObject gameObject = new GameObject();

        C1 c1 = gameObject.addComponent(C1.class);
        C2 c2 = gameObject.addComponent(C2.class);

        assertEquals(c1, gameObject.getComponent(C1.class));
        assertEquals(c2, gameObject.getComponent(C2.class));
    }

    @Test
    public void testGetBaseClass() {
        GameObject gameObject = new GameObject();

        C2_1 c2 = gameObject.addComponent(C2_1.class);

        assertEquals(c2, gameObject.getComponent(C2.class));
    }
}
