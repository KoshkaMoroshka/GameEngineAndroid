package com.example.f;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Keyboard_test extends Game_object {

    public String input = "";
    public static char rus_alpha[]={
            'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ',
            'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э',
            'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю', 'ё', ' '
    };
    public static char eng_alpha[]={
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
            'z', 'x', 'c', 'v', 'b', 'n', 'm', ' '
    };
    public class key extends Sensitive_object{
        public key(char ch){
            setFilm(Label.symbol(ch));
            name=String.valueOf(ch);
            interactive=false;
        }


        @Override
        public boolean onAction(Game_object target, Action act) {
            Keyboard_test.this.input+=name;
            return true;
        }
    }

    public Event onEnter;
    public class enter_key extends Sensitive_object{
        enter_key(){
            setFilm(load(R.drawable.num_ent));
            name="enter";
            interactive=false;
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            Keyboard_test.this.turn(false);
            if(onEnter==null)
                return false;
            onEnter.post();
            return true;
        }
    }


    public key[] rus;
    public key[] eng;
    public enter_key enter;
    /*public Keyboard_test(){
        rus =new key[33];
        for(int i=0; i<33; i++)
            rus[i]=new key(rus_alpha[i]);

        set(0, 0);
        w=(int)(rus[11].x+rus[11].w-rus[0].x);
        h=(int)(rus[23].y+rus[23].h-rus[0].y);
        return;
    }*/

    public Keyboard_test(){
        eng =new key[27];
        for(int i=0; i<26; i++)
            eng[i]=new key(eng_alpha[i]);

        eng[26]=new key(eng_alpha[26]);

        enter=new enter_key();
        set(0, 0);
        w=(int)(eng[9].x+eng[9].w-eng[0].x);
        h=(int)(eng[19].y+eng[19].h-eng[0].y);

        return;
    }


    public void set(float x, float y) {
        super.set(0, 0);

        float xx=x, yy=y;
        for(int i=0; i<10; i++)
        {
            eng[i].set(xx, yy);
            xx+=eng[i].film.getWidth()*eng[i].scale+4;
        }
        yy+=eng[0].film.getHeight()*eng[0].scale+4;


        xx=x;
        for(int i=10; i<19; i++)
        {
            eng[i].set(xx, yy);
            xx+=eng[i].film.getWidth()*eng[i].scale+4;
        }
        enter.set(xx, yy);
        yy+=eng[10].film.getHeight()*eng[12].scale+4;


        xx=x;
        for(int i=19; i<26; i++)
        {
            eng[i].set(xx, yy);
            xx+=eng[i].film.getWidth()*eng[i].scale+4;
        }
        eng[26].set(xx + eng[25].film.getWidth()*eng[25].scale+4, yy);

        return;
    }


    @Override
    public void setScale(float scale) {
        for(key k: eng)
            k.setScale(scale);
        enter.setScale(scale);

        this.scale=scale;
        w=(int)((eng[9].x+eng[9].w-x)*scale);
        h=(int)((eng[19].x+eng[19].h-y)*scale)+4;
        set(-w/2, 0);
        return;
    }

    @Override
    public void turn(boolean active) {
        super.turn(active);
        MainActivity.activateControllers(!active);
        Graphic_system.camera.turn(!active);
        Graphic_system.hp_bar.turn(!active);
    }

    @Override
    public void update() {
        if(disable)
            return;
        super.update();
        set(-w/scale, Graphic_system.h-h);
        for(key k: eng)
            k.update();
        enter.update();
    }
    @Override
    public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
        if(hide)
            return false;
        super.render(canvas, paint, x0, y0);
        for(key k: eng)
            k.render(canvas, paint, x0, y0);
        enter.render(canvas, paint, x0, y0);
        return true;
    }
}
