package com.example.f;

public class Inventory extends Object_list.Object_list_TR {
    protected Game_object owner;
    public Inventory(Game_object owner) {
        super(100);
        this.owner=owner;
    }


    @Override
    public boolean get(Game_object object) {
        object.containing=owner.containing;
        object.set(owner.x+owner.w+20, owner.y);
        return super.get(object);
    }
}
