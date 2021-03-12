package com.example.f;

import java.util.ArrayList;

public class Stack<type> {
    protected ArrayList<type> elems = new ArrayList<>();

    public void remove(type elem){
        elems.remove(elem);
    }
    public type pop(type elem){
        for(int i=elems.size()-1; i>=0; i--)
            if(elems.get(i)==elem)
            {
                elems.remove(i);
                return elem;
            }
        return null;
    }
    public type pop(){
        type t=peek();
        elems.remove(elems.size()-1);
        return t;
    }

    public type push(type elem) {
        elems.add(elem);
        return elem;
    }
    public type peek(){
        if(elems.size()==0)
            return null;
        return elems.get(elems.size()-1);
    }
}
