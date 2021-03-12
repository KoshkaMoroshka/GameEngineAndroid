package com.example.f;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Keyboard extends Object_group {
    public String input = "";
    public static String rus_alpha="йцукенгшщзхъфывапролджэячсмитьбюё";

    public class key extends Sensitive_object{
        public key(char ch){
            setFilm(Label.symbol(ch));
            name=String.valueOf(ch);
            interactive=false;
            Keyboard.this.add(this);
        }
        public key(){
            interactive=false;
            Keyboard.this.add(this);
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            Keyboard.this.input+=name;
            if(onWrite!=null)
                onWrite.post();
            return true;
        }
    }

    public Event onEnter;
    public Event onWrite=new Event(null){
        @Override
        public boolean post() {
            Graphic_system.no_sex.setText(input);
            return true;
        }
    };



    public class enter_key extends key{
        enter_key(){
            setFilm(load(R.drawable.num_ent));
            name="enter";
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            Keyboard.this.turn(false);
            Graphic_system.no_sex.setText("");
            if(onEnter==null)
                return false;
            onEnter.post();
            return true;
        }
    }
    public class backspace_key extends key{
        backspace_key(){
            setFilm(load(R.drawable.num_bks));
            name="backspace";
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            if(input.length()==0)
                return false;
            input=input.substring(0, input.length()-1);
            if(onWrite!=null)
                onWrite.post();
            return true;
        }
    }
    public class space_key extends key{
        space_key(){
            setFilm(Label.symbol(' '));
            name="space";
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            input+=" ";
            if(onWrite!=null)
                onWrite.post();
            return true;
        }
    }



    public key[] rus;
    public key enter, backspace, space;

    public Keyboard(){
        rus =new key[33];
        for(int i=0; i<33; i++)
            rus[i]=new key(rus_alpha.charAt(i));

        enter=new enter_key();
        backspace=new backspace_key();
        space=new space_key();

        set(0, 0);
        w=(int)(rus[11].x+rus[11].w-rus[0].x);
        h=(int)(rus[23].y+rus[23].h-rus[0].y);
        return;
    }
    public void set(float x, float y) {
        super.set(x, y);


        float d=4;
        float xx=x, yy=y;
        for(int i=0; i<12; i++)
        {
            rus[i].set(xx, yy);
            xx+=rus[i].film.getWidth()*rus[i].scale+d;
        }
        backspace.set(xx, yy);
        yy+=rus[0].film.getHeight()*rus[0].scale+d;


        xx=x;
        for(int i=12; i<23; i++)
        {
            rus[i].set(xx, yy);
            xx+=rus[i].film.getWidth()*rus[i].scale+d;
        }
        enter.set(xx, yy);
        yy+=rus[12].film.getHeight()*rus[12].scale+d;


        xx=x;
        for(int i=23; i<33; i++)
        {
            rus[i].set(xx, yy);
            xx+=rus[i].film.getWidth()*rus[i].scale+d;
        }
        space.set(xx, yy);

        return;
    }
    public void wh_update(){
        float x1=objs.first().x, y1=objs.first().y, x2=x1+objs.first().w, y2=y1+objs.first().h;
        for(Game_object key: objs)
        {
            x1=key.x<x1?key.x:x1;
            y1=key.y<y1?key.y:y1;
            x2=key.x+key.w>x2?key.x+key.w:x2;
            y2=key.y+key.h>y2?key.y+key.h:y2;
        }
        w=(int)(x2-x1);
        h=(int)(y2-y1);
    }


    @Override
    public void setScale(float scale) {
        for(Game_object key: objs)
            key.setScale(scale);
        this.scale=scale;

        set(x, y);
        wh_update();
        return;
    }


    public void start(Event onEnter){
        turn(true);
        this.onEnter=onEnter;
    }


    private Controller_locker locker=new Controller_locker(true, true);
    @Override
    public void turn(boolean active) {
        super.turn(active);
        locker.turn(active);
    }


    @Override
    public void update() {
        if(disable)
            return;
        set(-w/2, Graphic_system.h-h-4);
        super.update();
    }

    @Override
    public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
        if(hide)
            return false;
        boolean res = false;
        for(Game_object obj: objs)
            res = res | obj.render(canvas, paint, x0, y0);
        return res;
    }

    public class Event_start extends Event{
        Event_start(Event onEnter){
            onFinish=onEnter;
        }

        @Override
        public boolean post() {
            Keyboard.this.start(this.onFinish);
            return true;
        }
    }
}
