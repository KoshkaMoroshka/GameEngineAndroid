package com.example.f;

public class Scene extends Object_group {
    public Scene(String name){
        this.name=name;
        interactive=true;
    }


    @Override
    public boolean add(Game_object obj) {
        obj.containing=this;
        return super.add(obj);
    }
    @Override
    public boolean del(Game_object obj) {
        if(!super.del(obj))
            return false;
        obj.containing=null;
        return true;
    }

    public void replaceObject(Game_object obj){
        if(obj.containing!=null)
            obj.containing.del(obj);
        add(obj);
    }



    public Motion_drive.MD_complex drives=new Motion_drive.MD_complex(null);
    public Predicate.Predicate_array predicates =new Predicate.Predicate_array();
    @Override
    public void update() {
        drives.update();
        predicates.update();
        super.update();
    }



    public boolean started=false, finished=false;
    public void onStart(){
        started=true;
    }
    public void onFinish(){
        finished=true;
    }
}
