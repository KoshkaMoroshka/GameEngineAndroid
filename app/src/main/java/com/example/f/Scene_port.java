package com.example.f;

import java.util.ArrayList;

public class Scene_port extends Game_object {
    public Scene_port(int id, Scene dst, float x0, float y0) {
        this(id, dst);
        this.x0=x0;
        this.y0=y0;
    }
    public Scene_port(int id, Scene dst){
        super(id);
        name="portel";
        this.dst=dst;
        rigid=false;
        //hide=true;



        coll=new Collider.Collider_rect();
        COLL.add(coll);
    }
    private Collider.Collider_rect coll;


    public float x0, y0;
    public void setPos(float x0, float y0){
        this.x0=x0;
        this.y0=y0;
    }


    public Scene dst;
    protected Scene_port connected_port;


    public static void attach_LR(Scene_port l, Scene_port r){
        Point p1=r.getCenter(), p2=l.getCenter();
        l.setPos(p1.x+r.w, p1.y);
        r.setPos(p2.x-l.w, p2.y);
        l.dst=r.containing;
        r.dst=l.containing;
    }
    public static void attach_UD(Scene_port u, Scene_port d){
        Point p1=d.getCenter(), p2=u.getCenter();
        u.setPos(p1.x, p1.y+d.h);
        d.setPos(p2.x, p2.y-u.h);
        u.dst=d.containing;
        d.dst=u.containing;
    }


    public static void join_scenes_RtoL(Scene_port l, Scene_port r){
        r.containing.moveBy(r, l);
        r.x-=l.w;
        l.x+=r.w;
        attach_LR(l, r);
    }
    public static void join_scenes_LtoR(Scene_port l, Scene_port r){
        l.containing.moveBy(l, r);
        r.x-=l.w;
        l.x+=r.w;
        attach_LR(l, r);
    }
    public static void join_scenes_DtoU(Scene_port u, Scene_port d){
        d.containing.moveBy(d, u);
        d.y-=u.h;
        u.y+=d.h;
        attach_UD(u, d);
    }
    public static void join_scenes_UtoD(Scene_port u, Scene_port d){
        u.containing.moveBy(u, d);
        d.y-=u.h;
        u.y+=d.h;
        attach_UD(u, d);
    }



    public ArrayList<Game_object> ignore=new ArrayList<>();


    public boolean teleport(Game_object obj){
        if(obj.containing==dst)
            return false;

        if(connected_port !=null)
            connected_port.ignore.add(obj);

        dst.replaceObject(obj);

        if(obj==Graphic_system.camera.focus.peek() && !dst.started)
            dst.onStart();
        return true;
    }


    public boolean disable_port = false;
    @Override
    public boolean onCollision(Game_object target) {
        if(disable_port || ignore.contains(target))
            return false;
        return teleport(target);
    }


}
