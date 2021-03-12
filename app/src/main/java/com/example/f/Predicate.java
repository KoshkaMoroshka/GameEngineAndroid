package com.example.f;

import java.util.ArrayList;

public class Predicate {
    public Action action;
    public Predicate(Action action, boolean start){
        this(action, start, true);
    }
    public Predicate(Action action, boolean start, boolean auto_remove){
        this(action, start, auto_remove, Graphic_system.overlay);
    }
    public Predicate(Action action, boolean start, boolean auto_remove, Scene location){
        this.action=action;
        this.auto_remove=auto_remove;
        this.location=location;
        if(start) start();
    }
    protected Scene location;
    public boolean auto_remove;
    public void start(){
        location.predicates.add(this);
        finished=false;
    }
    public void finish() {
        finished=true;
        action.post();
        if(auto_remove)
            location.predicates.remove(this);
    }
    public boolean disable=false, finished=false;
    public boolean predicate(){
        return true;
    }
    public boolean update(){
        if(disable)
            return false;
        if(!predicate())
            return false;
        finish();
        return true;
    }
    public static class Delay extends Predicate {
        public Delay(Action action, boolean start, int time) {
            super(action, start);
            this.time=time;
        }

        public int timer=0, time;


        @Override
        public boolean predicate() {
            return timer>=time;
        }


        @Override
        public boolean update() {
            if(disable)
                return false;
            timer++;
            return super.update();
        }
    }
    public static class Predicate_array {
        public ArrayList<Predicate> predicates=new ArrayList<>();
        public ArrayList<Predicate> toRemove=new ArrayList<>();

        public void add(Predicate predicate){
            predicates.add(predicate);
        }
        public void remove(Predicate predicate){
            toRemove.add(predicate);
        }


        public void update(){
            for(Predicate p: predicates)
                p.update();

            predicates.removeAll(toRemove);
            toRemove.clear();
        }
    }
}
