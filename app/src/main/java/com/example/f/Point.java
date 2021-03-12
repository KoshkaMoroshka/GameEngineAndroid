package com.example.f;

public class Point {
    public float x, y;
    public float dx = 0, dy = 0;
    public void set(float x, float y){
        this.x=x;
        this.y=y;
    }
    public void set(Point pt){
        set(pt.x, pt.y);
    }
    public void add(float dx, float dy, float speed) {
        this.dx+=dx*speed;
        this.dy+=dy*speed;
    }
    public void update() {
        x+=dx;  dx=0;
        y+=dy;  dy=0;
    }
    public float distance(Point pt){
        return distance(this, pt);
    }
    public static float distance(Point p1, Point p2){
        return (float)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }
    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }
    public Point(){
        x=0;
        y=0;
    }
    public String toString() { return x+", "+y; }

    public static class Physical_point extends Point {
        public boolean rigid=true, fixed=true;
        public static void onCollision(Physical_point o1, Physical_point o2){
            if(!o1.rigid || !o2.rigid)
                return;

            float dx1=0, dy1=0, dx2=0, dy2=0;
            if(!o2.fixed)
            {
                dx2+=o1.dx; dy2+=o1.dy;
                dx1+=o1.dx; dy1+=o1.dy;
            }
            if(!o1.fixed)
            {
                dx1+=o2.dx; dy1+=o2.dy;
                dx2+=o2.dx; dy2+=o2.dy;
            }
            o1.dx=dx1; o1.dy=dy1;
            o2.dx=dx2; o2.dy=dy2;
        }
        public float speed = 1;
        public void add(float dx, float dy){
            add(dx, dy, speed);
        }
        public boolean go(Point pt){
            float dx=pt.x-x, dy=pt.y-y;
            float r = (float)Math.sqrt(dx*dx+dy*dy);
            if(r<speed)
            {
                set(pt);
                return false;
            }
            add(dx/r, dy/r);
            return dx!=0 || dy!=0;
        }
        public String toString(){
            return super.toString()+"<"+(rigid?"r":"-")+(fixed?"f":"-")+">";
        }
    }
}
