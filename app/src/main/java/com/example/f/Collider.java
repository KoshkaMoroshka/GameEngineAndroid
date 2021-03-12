package com.example.f;

import android.graphics.Bitmap;
import java.util.ArrayList;

public abstract class Collider {
    protected Game_object obj;
    public void attach(Game_object obj){
        this.obj=obj;
    }


    protected int difficulty=0;
    public boolean active=true;
    public abstract boolean collision(float x, float y);
    public abstract boolean collision(Collider other);


    public static boolean collision(Game_object o1, Game_object o2){
        if(!o1.COLL.active || !o2.COLL.active)
            return false;

        float dx1=o1.dx, dy1=o1.dy;
        float dx2=o2.dx, dy2=o2.dy;
        o1.x+=dx1;
        o1.y+=dy1;
        o2.x+=dx2;
        o2.y+=dy2;


        boolean res;
        if(o2.COLL.difficulty<o1.COLL.difficulty)
            res=o2.COLL.collision(o1.COLL);
        else res=o1.COLL.collision(o2.COLL);


        o1.x-=dx1;
        o1.y-=dy1;
        o2.x-=dx2;
        o2.y-=dy2;
        return res;
    }
    public boolean collision(Game_object other){
        return collision(obj, other);
    }

    protected Collider clone(){
        return null;
    }


    public static class Collider_complex extends Collider {
        public Collider_complex(Game_object obj){
            attach(obj);
        }
        @Override
        public void attach(Game_object obj) {
            super.attach(obj);
            for(Collider collider: colliders)
                collider.attach(obj);
        }

        protected ArrayList<Collider> colliders=new ArrayList<>();
        public void add(Collider collider){
            difficulty+=collider.difficulty;
            if(colliders.add(collider))
                collider.attach(obj);
        }
        public void remove(Collider collider){
            if(colliders.remove(collider))
                difficulty-=collider.difficulty;
        }

        @Override
        public boolean collision(float x, float y) {
            for(Collider collider: colliders)
                if(collider.active && collider.collision(x, y))
                    return true;
            return false;
        }
        @Override
        public boolean collision(Collider other) {
            if(!active || !other.active || other==this)
                return false;
            for(Collider collider: colliders)
                if(collider.collision(other))
                    return true;
            return false;
        }
        @Override
        protected Collider clone() {
            Collider_complex res=new Collider_complex(obj);
            for(Collider c: colliders)
                res.add(c.clone());
            return res;
        }
    }
    public static class Collider_rect extends Collider {
        public Collider_rect(){
            difficulty=4;
        }

        @Override
        public boolean collision(float x, float y) {
            float x1=obj.x, y1=obj.y, x2=x1+obj.w, y2=y1+obj.h;
            return x>=x1 && y>=y1 && x<x2 && y<y2;
        }
        @Override
        public boolean collision(Collider other) {
            if(!other.active)
                return false;

            float x1=obj.x, y1=obj.y, x2=x1+obj.w, y2=y1+obj.h;
            return other.collision(x1, y1)||other.collision(x1, y2)
                    ||other.collision(x2, y1)||other.collision(x2, y2);
        }

        @Override
        protected Collider clone() {
            return new Collider_rect();
        }
    }
    public static class Collider_bitmap extends Collider {
        protected Bitmap collider;
        public int coll_color=0xFF000000;
        public Collider_bitmap(Bitmap collider){
            this.collider=collider;
            difficulty=collider.getWidth()*collider.getHeight();
        }


        protected boolean rectangular=true;
        public void setRectangular(boolean rectangular){
            this.rectangular=rectangular;
            if(rectangular)
                difficulty=4;
            else difficulty=collider.getWidth()*collider.getHeight();
        }


        @Override
        public boolean collision(float x, float y) {
            float xx=x-obj.x, yy=y-obj.y;
            if(xx<0 || yy<0 || xx>=obj.w || yy>=obj.h)
                return false;
            xx=xx/obj.w * collider.getWidth();
            yy=yy/obj.h * collider.getHeight();
            return collider.getPixel((int)xx, (int)yy)==coll_color;
        }
        @Override
        public boolean collision(Collider other) {
            if(!active || !other.active)
                return false;

            if(rectangular) {
                float x1=obj.x, y1=obj.y, x2=x1+obj.w, y2=y1+obj.h;
                return other.collision(x1, y1)||other.collision(x1, y2)
                        ||other.collision(x2, y1)||other.collision(x2, y2);
            }

            float sx=obj.w/collider.getWidth();
            float sy=obj.h/collider.getHeight();

            for(int x=0; x<collider.getWidth(); x++)
                for(int y=0; y<collider.getHeight(); y++)
                    if(collider.getPixel(x, y)==coll_color)
                        if(other.collision(obj.x+x*sx, obj.y+y*sy))
                            return true;
            return false;
        }

        @Override
        protected Collider clone() {
            Collider_bitmap res=new Collider_bitmap(collider);
            res.rectangular=rectangular;
            res.coll_color=coll_color;
            return res;
        }
    }
    public static class Collider_bottom extends Collider {
        public float h;
        public Collider_bottom(float h){
            difficulty=4;
            this.h=h;
        }
        public Collider_bottom(){
            this(10);
        }


        @Override
        public boolean collision(float x, float y) {
            float x1=obj.x, y1=obj.y+obj.h-h, x2=x1+obj.w, y2=y1+h;
            return x>=x1 && y>=y1 && x<x2 && y<y2;
        }
        @Override
        public boolean collision(Collider other) {
            if(!active || !other.active)
                return false;
            float x1=obj.x, y1=obj.y+obj.h-h, x2=x1+obj.w, y2=y1+h;
            if(other.collision(x1, y1)||other.collision(x1, y2)||other.collision(x2, y1)||other.collision(x2, y2))
                return true;
            float cx=(x1+x2)/2;
            float cy=(y1+y2)/2;
            return other.collision(x1, cy)||other.collision(x2, cy)||other.collision(cx, y1)||other.collision(cx, y2);
        }

        @Override
        protected Collider clone() {
            return new Collider_bottom(h);
        }
    }
}
