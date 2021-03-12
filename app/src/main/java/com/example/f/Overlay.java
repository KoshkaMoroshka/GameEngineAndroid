package com.example.f;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Overlay extends Scene {
    public Overlay(){
        super("overlay");
        interactive=false;

        hp_bar.set(-w, -h);
        add(hp_bar);


        camera=new Camera();
        camera.setFocus(Graphic_system.player);
        camera.layer=-10;
        add(camera);


        dialogue=new Dialogue(300, 80);
        dialogue.name="dialogue";
        dialogue.setFilm(Label.createTextbox(240, 80));
        dialogue.setScale(0.5f);
        dialogue.setText("null");
        dialogue.turn(false);
        add(dialogue);


        keyboard=new Keyboard();
        add(keyboard);
        keyboard.setScale(2);
        keyboard.turn(false);
    }


    public Dialogue dialogue;
    public Camera camera;
    public Keyboard keyboard;


    public static class HP_Bar extends Game_object {
        public Game_object target;
        public Bitmap frame, fill;
        public HP_Bar(Game_object target){
            this.target=target;
            frame=load(R.drawable.hp_frame);
            fill=load(R.drawable.hp_fill);
            setFilm(frame);
            return;
        }


        protected int fillw = 0, fillw2 = 0;
        public void update_bitmap(){
            fillw2=fillw;
            fillw=(int)(target.hp/target.maxhp*frame.getWidth());
            if(fillw==fillw2)
                return;
            film=Bitmap.createBitmap(frame);
            for(int x=0; x<fillw; x++)
                for(int y=0; y<fill.getHeight(); y++)
                    film.setPixel(x, y, fill.getPixel(x, y));
            return;
        }

        @Override
        public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
            update_bitmap();
            return super.render(canvas, paint, x0, y0);
        }
    }
    public HP_Bar hp_bar = new HP_Bar(Graphic_system.player){
        @Override
        public void update_bitmap() {
            x=-Graphic_system.w;
            y=-Graphic_system.h;
            super.update_bitmap();
        }
    };



}
