package com.example.f;

public class Game_object_clone extends Game_object {
    protected Game_object original;
    public Game_object_clone(Game_object original){
        this.original=original;
        interactive=original.interactive;
        name=original.name+"_clone";

        setFilm(original.film);
        layer=original.layer;

        rigid=original.rigid;
        fixed=original.fixed;

        COLL=(Collider.Collider_complex)original.COLL.clone();
        COLL.attach(this);
    }


    @Override
    public void update() {
        if(disable)
            return;
        super.update();
        setFilm(original.film);
    }


    @Override
    public boolean onAction(Game_object target, Action act) {
        if(disable)
            return false;
        return original.onAction(target, act);
    }
}
