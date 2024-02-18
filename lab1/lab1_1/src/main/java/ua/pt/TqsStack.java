package ua.pt;

import java.util.LinkedList;

public class TqsStack<T> {
    private LinkedList<T> l;
    private int maxSize;

    // Unsure if I should have created two constructors - one with required size and one without
    public TqsStack(int stackSize){
        this.l = new LinkedList<T>();
        this.maxSize = stackSize;
    }

    public T pop(){
        return l.pop();
    }

    public int size(){
        return l.size();
    }

    public T peek(){
        return l.peek();
    }

    public void push(T value){
        if(l.size() == this.maxSize)
            throw new IllegalStateException("Stack is currently full");
        else
            l.push(value);
    }

    public boolean isEmpty(){
        return l.isEmpty();
    }
}
