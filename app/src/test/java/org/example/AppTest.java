package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void testInsertionAndSerialization() {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        assertEquals("20,10,nil,nil,30,nil,nil,", tree.serialize());
    }

    @Test
    void testDeletion() {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.delete(20);
        assertEquals("30,10,nil,nil,nil,", tree.serialize());
    }
}
