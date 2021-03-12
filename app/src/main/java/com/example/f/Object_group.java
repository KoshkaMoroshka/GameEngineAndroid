package com.example.f;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class Object_group extends Game_object {
    public static Comparator<Game_object> comparator = new Comparator<Game_object>() {
        @Override
        public int compare(Game_object o1, Game_object o2) {
            if(o1==o2)
                return 0;
            if(o1.layer>o2.layer)
                return 1;
            if(o1.layer<o2.layer)
                return -1;
            return o1.y+o1.h>o2.y+o2.h?1:-1;
        }
    };
    protected TreeSet<Game_object> objs = new TreeSet<>(comparator);
    public void sortObjects(){
        dynamicAddList.addAll(objs);
        objs.clear();
        dynamicAddList.removeAll(dynamicDelList);
        dynamicDelList.clear();
        objs.addAll(dynamicAddList);
        dynamicAddList.clear();
    }
    public Game_object findObject(String name){
        for(Game_object obj: objs)
            if(obj.name.equals(name))
                return obj;
        return null;
    }
    public Object_group(){
        MD.add(new Motion_drive.MD_Object_group(this));
    }


    @Override
    public void set(float x, float y) {
        for(Game_object obj: objs)
            obj.set(obj.x+(x-this.x), obj.y+(y-this.y));
        super.set(x, y);
    }
    public void moveBy(Game_object by, Game_object to){
        float x=this.x, y=this.y;
        x-=by.x;
        y-=by.y;
        x+=to.x;
        y+=to.y;
        set(x, y);
    }


    public ArrayList<Game_object> dynamicAddList =new ArrayList<>();
    public ArrayList<Game_object> dynamicDelList =new ArrayList<>();

    public boolean add(Game_object obj) {
        if(obj.find(this))
            return false;
        if(updating)
            dynamicAddList.add(obj);
        else objs.add(obj);
        return true;
    }
    public boolean del(Game_object obj){
        if(!objs.contains(obj))
            return false;
        if(updating)
            dynamicDelList.add(obj);
        else objs.remove(obj);
        return true;
    }
    public String getString(){
        String t=super.toString()+"[";
        for(Game_object object: objs)
            t+=object.toString()+", ";
        t+="]";
        return t;
    }
    @Override
    public boolean find(Game_object obj){
        for(Game_object t: objs)
            if(t==obj)
                return true;
        return super.find(obj);
    }


    public void turnAll(boolean active){
        for(Game_object obj: objs)
            obj.turn(!active);
    }
    public void turnExcept(Game_object exception, boolean active) {
        for (Game_object obj : objs)
            if (obj != exception)
                obj.turn(active);
    }

    public Game_object closest_target(Game_object obj){
        float min = Float.MAX_VALUE, temp;
        Game_object res = null;

        for(Game_object target: objs)
        {
            if(!target.interactive || target.disable)
                continue;
            if(target==obj)
                continue;
            temp=action_distance(obj, target);
            if(temp<min && temp<=target.action_radius*target.action_radius)
            {
                min=temp;
                res=target;
            }
        }
        return res;
    }
    private float action_distance(Game_object obj, Game_object target){
        float x=obj.x+obj.w/2, y=obj.y+obj.h/2;

        float t1, t2, t3, t4;
        t1=x-(target.x-target.action_radius);
        t2=y-(target.y);
        t3=(target.x+target.action_radius/2+target.w)-x;
        t4=(target.y+target.action_radius/2+target.h)-y;

        if(t1<0 || t2<0 || t3<0 || t4<0)
            return Float.MAX_VALUE;

        return target.distance(obj);
    }
    private boolean dispatch_action(Game_object obj, Action a) {
        Game_object target = closest_target(obj);
        if(target==null)
        {
            obj.onAction(obj, null);
            return false;
        }
        target.onAction(obj, a);
        return true;
    }

    @Override
    public boolean onAction(Game_object target, Action act) {
        if(disable)
            return false;
        return dispatch_action(target, act);
    }
    private boolean updating=false;
    public void update() {
        if(disable)
            return;
        updating=true;
        super.update();

        for(Game_object obj: objs)
            obj.MD.update();
        MD.update();
        for(Game_object obj: objs)
            obj.update();

        sortObjects();
        updating=false;
    }
    public boolean render(Canvas canvas, Paint paint, float x0, float y0){
        if(hide)
            return false;
        boolean res = super.render(canvas, paint, x0, y0);
        for(Game_object obj: objs)
            res = res | obj.render(canvas, paint, x0, y0);
        return res;
    }
}
