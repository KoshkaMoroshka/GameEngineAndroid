package com.example.f;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Game_object extends Point.Physical_point
{
    protected static Resources res;
    protected static Context context;
    public static void attach(Resources res, Context context){
        Game_object.res=res;
        Game_object.context=context;
        return;
    }
    public static Bitmap load(int id){
        return BitmapFactory.decodeResource(res, id).copy(Bitmap.Config.ARGB_8888, true);
    }

    public boolean find(Game_object obj){
        return obj==this;
    }

    public String name = "obj";
    public float hp = 100, maxhp = 100;


    public int w = 0, h = 0;
    public int layer = 0;
    protected Scene containing;


    protected Bitmap film;
    public void setFilm(Bitmap film0){
        film=film0;
        w=(int)(film.getWidth()*scale);
        h=(int)(film.getHeight()*scale);
        return;
    }




    public void setCenter(Point pt){
        setCenter(pt.x, pt.y);
    }
    public void setCenter(float x, float y){
        set(x-w/2, y-w/2);
    }
    public Point getCenter(){
        return new Point(x+w/2, y+h/2);
    }
    public void centalize(Game_object target){
        setCenter(target.getCenter());
    }


    public Game_object(int id, int coll_id) {
        this(id);
        COLL.add(new Collider.Collider_bitmap(load(coll_id)));
        return;
    }
    public Game_object(int id){
        setFilm(load(id));
        ANIM.update();
    }
    public Game_object(){
        return;
    }


    Action.Action_stack ACTS=new Action.Action_stack(this);
    Motion_drive.MD_complex MD=new Motion_drive.MD_complex(this);
    Collider.Collider_complex COLL=new Collider.Collider_complex(this);
    Animation.Animation_stack ANIM=new Animation.Animation_stack(this);


    public boolean disable = false;
    public void update(){
        if (Graphic_system.keyboard.hide)
            hide = hide;
        if(disable)
            return;
        ANIM.update();
        super.update();

    }
    public void turn(boolean active){
        this.hide=!active;
        this.disable=!active;

        MD.active=active;
        COLL.active=active;
        ANIM.active=active;
    }


    public float action_radius = 30;
    public boolean interactive=false;
    public boolean onAction(Game_object target, Action act){ return false; }
    public boolean onCollision(Game_object target) {
        return false;
    }


    protected float scale = 1, scaleRev = 1;
    public void setScale(float scale){
        this.scale=scale;
        scaleRev =1/scale;

        w=(int)(film.getWidth()*scale);
        h=(int)(film.getHeight()*scale);
        return;
    }

    public boolean hide = false;
    public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
        if (Graphic_system.keyboard.hide)
            hide = hide;
        if (hide)
            return false;
        float x=(this.x-x0)*scaleRev, y=(this.y-y0)*scaleRev;

        canvas.scale(scale, scale);
        canvas.drawRect(x, y, x+w*scaleRev, y+h*scaleRev, paint);
        if(film!=null)
            canvas.drawBitmap(film, x, y, paint);
        canvas.scale(scaleRev, scaleRev);
        return true;
    }

    @Override
    public String toString() {
        return name+" ("+super.toString()+")";
    }
}
