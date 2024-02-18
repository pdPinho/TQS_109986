package ua.pt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTest {
    TqsStack<String> wordStack; // to be reused in different tests


    @BeforeEach
    void setUp(){
        wordStack = new TqsStack<>(3);
    }


    @DisplayName("A stack is empty on construction")
    @Test
    void isEmpty(){
        // arrange (required objects, initial state, ...)
            // BeforeEach already creates an empty stack; nothing to do here

        // act
            // nothing to do here

        // assess
        assertTrue(wordStack.isEmpty());
    }


    @DisplayName("A stack has size 0 on construction")
    @Test
    void initialSize(){
        assertTrue(wordStack.size() == 0);
    }


    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    void nPushes(){
        wordStack.push("One");
        wordStack.push("Two");

        assertFalse(wordStack.isEmpty());
    }


    @DisplayName("If one pushes x then pops, the value popped is x.\r")
    @Test
    void checkPopAfterPush(){
        wordStack.push("One");
        wordStack.push("Two");

        assertEquals("Two", wordStack.pop());
        assertEquals("One", wordStack.pop());
    }


    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    @Test
    void checkPeekAfterPush(){
        wordStack.push("One");

        assertEquals("One", wordStack.peek());
        assertEquals(1, wordStack.size());
    }


    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    @Test
    void sizeChangeAfterPop(){
        wordStack.push("One");
        wordStack.push("Two");
        wordStack.push("Three");

        wordStack.pop();
        wordStack.pop();
        wordStack.pop();

        assertTrue(wordStack.isEmpty());
        assertEquals(0, wordStack.size());
    }


    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    @Test
    void popEmptyThrowsException(){
        assertThrows(NoSuchElementException.class, () -> wordStack.pop());
    }


    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    @Test
    void peekEmptyThrowsException(){
        assertThrows(NoSuchElementException.class, () -> wordStack.pop());
    }


    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    @Test
    void boundStackPushThrowsException(){
        wordStack.push("1");
        wordStack.push("2");
        wordStack.push("Next one will throw an error");
        
        assertThrows(IllegalStateException.class, () -> wordStack.push("And another one"));
    }
}
