package ua.pt;

import java.util.LinkedList;

public class TqsStack<T> {
    private LinkedList<T> l = new LinkedList<T>();
    
    public TqsStack(){

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
        l.push(value);
    }

    public boolean isEmpty(){
        return l.isEmpty();
    }
}
