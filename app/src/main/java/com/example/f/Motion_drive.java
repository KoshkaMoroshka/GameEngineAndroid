package com.example.f;

import java.util.ArrayList;

abstract public class Motion_drive {
    public Game_object obj;

    public boolean active=true;
    abstract public void update();


    public static class MD_inerting extends Motion_drive {
        public float damping;
        public MD_inerting(float damping){
            this.damping=damping;
        }


        private float dx=0, dy=0;
        @Override
        public void update() {
            obj.dx=(dx=dx*damping+obj.dx);
            obj.dy=(dy=dy*damping+obj.dy);
        }
    }
    public static class MD_gravity extends Motion_drive {
        public float g=0.2f;
        @Override
        public void update() {
            obj.dy+=-g;
        }
    }


    public static class MD_patrol extends Motion_drive{
        public Point p1, p2;

        public MD_patrol(Point p1, Point p2){
            this.p1=p1;
            this.p2=p2;
        }

        @Override
        public void update() {
            if(obj.distance(p1)<10)
            {
                Point t=p1;
                p1=p2;
                p2=t;
            }

            obj.go(p1);
        }
    }
    public static class MD_complex extends Motion_drive{
        public ArrayList<Motion_drive> drives=new ArrayList<>();
        public MD_complex(Game_object obj) {
            this.obj=obj;
        }


        public void add(Motion_drive drive){
            drives.add(drive);
            if(obj!=null)
                drive.obj=obj;
        }
        public void remove(Motion_drive drive){
            drives.remove(drive);
        }


        @Override
        public void update() {
            if(!active)
                return;
            for(Motion_drive d: drives)
                if(d.active)
                    d.update();
            return;
        }
    }
    public static class MD_directive extends Motion_drive{
        public Point target;
        public MD_directive(Point target) {
            this.target=target;
        }

        @Override
        public void update() {
            if(!active)
                return;
            if(obj.distance(target)<=obj.speed)
                obj.set(target);
            else obj.go(new Point(target.x, target.y));
        }
    }
    public static class MD_controllers extends Motion_drive{
        @Override
        public void update() {
            if(!active)
                return;
            obj.add(Graphic_system.dx, Graphic_system.dy, obj.speed);
        }
    }

    public static class MD_camera_limiter extends Motion_drive{
        public Scene cur;
        public int edge=40;
        @Override
        public void update() {
            cur=((Camera)obj).focus.peek().containing;
            if(cur==null)
                return;
            if(cur.w==0 && cur.h==0)
                return;

            float x1=obj.x+obj.dx-obj.w/2, y1=obj.y+obj.dy-obj.h/2, x2=x1+obj.w, y2=y1+obj.h;
            float xx1=cur.x+cur.dx, yy1=cur.y+cur.dy, xx2=xx1+cur.w, yy2=yy1+cur.h;
            xx1-=edge;  yy1-=edge;  xx2+=edge;     yy2+=edge;
            if(x1<xx1)
                obj.dx+=xx1-x1;
            if(x2>xx2)
                obj.dx+=xx2-x2;
            if(y1<yy1)
                obj.dy+=yy1-y1;
            if(y2>yy2)
                obj.dy+=yy2-y2;

            if(obj.w>cur.w)
                obj.dx=cur.getCenter().x-obj.x;
            if(obj.h>cur.h)
                obj.dy=cur.getCenter().y-obj.y;
        }
    }

    public static class MD_Object_group extends MD_complex {
        public MD_Object_group(Object_group obj) {
            super(obj);
        }

        public void onCollision(Game_object o1, Game_object o2){
            Point.Physical_point.onCollision(o1, o2);
            o1.onCollision(o2);
            o2.onCollision(o1);
        }

        @Override
        public void update() {
            Game_object[] objs=new Game_object[((Object_group)obj).objs.size()];
            ((Object_group)obj).objs.toArray(objs);

            Game_object o1, o2;
            for(int i=0; i<objs.length; i++)
            {
                o1=objs[i];
                if(!o1.COLL.active)
                    continue;

                for(int k=i+1; k<objs.length; k++)
                {
                    o2=objs[k];
                    if(!o2.COLL.active)
                        continue;
                    if(o1.COLL.collision(o2))
                        onCollision(o1, o2);
                }
            }
        }
    }
}
