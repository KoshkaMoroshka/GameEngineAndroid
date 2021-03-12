package com.example.f;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class GO_Games {

    public static class GO_Darts_F extends Sensitive_object {
        GO_Darts_F(){
            super(R.drawable.darts);
            desk=film;
            shot=load(R.drawable.darts_shot);
            points=load(R.drawable.darts_points);
            interactive=true;


            int line_r=1, color= Color.argb(127, 255, 0, 0);

            hor=Bitmap.createBitmap(film.getWidth(), line_r, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
            for(int x=0; x<hor.getWidth(); x++)
                for(int y=0; y<hor.getHeight(); y++)
                    hor.setPixel(x, y, color);

            ver=Bitmap.createBitmap(line_r, film.getHeight(), Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
            for(int x=0; x<ver.getWidth(); x++)
                for(int y=0; y<ver.getHeight(); y++)
                    ver.setPixel(x, y, color);

            return;
        }

        protected Bitmap hor, ver;
        protected Bitmap shot, points, desk;
        public void reset(){
            film= Bitmap.createBitmap(desk).copy(Bitmap.Config.ARGB_8888, true);
            score=0;
            return;
        }
        public void draw_shot(float x0, float y0){
            x0-=x+shot.getWidth()/2;  y0-=y+shot.getHeight()/2;
            int x1=(int)x0, y1=(int)y0, x2=x1+shot.getWidth(), y2=y1+shot.getHeight();
            x1=x1<0?0:x1;   x2=x2>film.getWidth()?film.getWidth():x2;
            y1=y1<0?0:y1;   y2=y2>film.getHeight()?film.getHeight():y2;

            for(int x=x1; x<x2; x++)
                for(int y=y1; y<y2; y++)
                    if(shot.getPixel(x-x1, y-y1)==Color.RED)
                        film.setPixel(x, y,  film.getPixel(x, y)== Color.RED?Color.BLACK:Color.RED);
            return;
        }


        protected int phase = 3;
        public float score = 0, xshot = -1, yshot = -1, delta = 1;
        public float shot(float x0, float y0){
            draw_shot(x0, y0);
            x0-=x;  y0-=y;
            if(x0<0 || y0<0 || x0>=points.getWidth() || y0>=points.getHeight())
                return 0;
            int c=points.getPixel((int)x0, (int)y0) & 0x00FF0000;
            c=((c<<7)>>23) / 25;

            score+=c;
            if(c==10 && apple!=null)
                apple.post();
            return c;
        }


        public Action apple;
        @Override
        public boolean onAction(Game_object target, Action act) {
            if(phase==2)
            {
                shot(x+xshot* scaleRev, y+yshot* scaleRev);
                xshot=-1;
                yshot=-1;
            }
            phase=(1+phase)%4;
            return true;
        }


        @Override
        public void update() {
            if(phase==0)
                yshot+=delta;
            if(phase==1)
                xshot+=delta;
            if(yshot>h)
            {
                phase=1;
                yshot=h;
            }
            if(xshot>w)
            {
                phase=2;
                xshot=w;
            }
            if(phase==2)
                onAction(null, null);
            super.update();
        }

        @Override
        public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
            if(hide)
                return false;
            super.render(canvas, paint, x0, y0);
            if(xshot==-1 && yshot==-1)
                return true;

            canvas.scale(scale, scale);
            if(yshot!=-1)
                canvas.drawBitmap(hor, (x-x0)*scaleRev, (y-y0+yshot)*scaleRev, paint);
            if(xshot!=-1)
                canvas.drawBitmap(ver, (x-x0+xshot)*scaleRev, (y-y0)*scaleRev, paint);
            canvas.scale(scaleRev, scaleRev);
            return true;
        }
    }
    public static class GO_Dice extends Sensitive_object {

        protected int pos = 0;
        protected Bitmap[] dice;
        public GO_Dice(){
            dice=Bitmap_modifier.split(load(R.drawable.dices), 28, 28);
            setFilm(dice[0]);
            interactive=true;
            return;
        }


        public int result = -1;
        @Override
        public boolean onAction(Game_object target, Action act) {
            if(result!=-1)
            {
                result = -1;
                return false;
            }
            result=1+pos;
            return true;
        }

        @Override
        public void update() {
            if(result==-1)
                film=dice[pos=(1+pos)%7];
            super.update();
        }
    }
    public static class GO_Punch_mole_F extends Game_object {
        GO_Punch_mole_F() {
            mole=load(R.drawable.pm_mole);
            hole=load(R.drawable.pm_hole);

            random=new Random();
            pool=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            bonk=pool.load(context, R.raw.bonk, 1);

            for(int i=0; i<9; i++)
                moles.add(new PM_Mole());
            set(0, 0);

            wh_update();
        }

        @Override
        public void set(Point pt) {
            set(pt.x, pt.y);
        }
        @Override
        public void set(float x, float y) {
            PM_Mole t;
            super.set(x, y);
            for(int i=0; i<9; i++)
            {
                t=moles.get(i);
                t.set(x+(i%3)*t.w, y+(i/3)*t.h);
            }
        }



        public Bitmap mole, hole;
        public Random random;
        public float score = 0;

        public void wh_update(){
            float x1=Float.MAX_VALUE, y1=x1, x2=Float.MIN_VALUE, y2=x2;
            for(PM_Mole m: moles){
                x1=m.x<x1? m.x: x1;
                y1=m.y<y1? m.y: y1;
                x2=m.x+m.w>x2 ?m.x+m.w: x2;
                y2=m.y+m.h>y2? m.y+m.h: y2;
            }
            w=(int)(x2-x1);
            h=(int)(y2-y1);
        }
        public void setHoleScales(float scale) {
            for(PM_Mole m: moles)
                m.setScale(scale);
            set(x, y);
            wh_update();
        }


        public static SoundPool pool;
        public static int bonk;
        public class PM_Mole extends Sensitive_object {

            public PM_Mole(){
                GO_Punch_mole_F.this.setFilm(inside?hole:mole);
                w=hole.getWidth();
                h=hole.getHeight();
                interactive=false;

                MD.active=true;
            }

            @Override
            public boolean onAction(Game_object target, Action act) {
                if(!inside)
                {
                    score+=1;
                    pool.play(bonk, 1, 1, 0, 0, 1);
                    inverse();
                }
                inside =true;
                return true;
            }

            public boolean inside = false;
            public void inverse(){
                inside =!inside;
                setFilm(inside?hole:mole);
            }
            public float chance = 0.005f;
            @Override
            public void update() {
                float r=random.nextFloat();
                if(r<=chance)
                    inverse();
                super.update();
            }
        }


        protected ArrayList<PM_Mole> moles=new ArrayList<>();


        @Override
        public void update() {
            if(disable)
                return;
            for(PM_Mole m: moles)
            {
                m.MD.update();
                m.update();
            }
            super.update();
        }

        @Override
        public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
            if(hide)
                return false;
            for(PM_Mole m: moles)
                m.render(canvas, paint, x0, y0);
            return super.render(canvas, paint, x0, y0);
        }
    }
    public static class GO_rat_racing extends Scene {
        public GO_rat_racing() {
            super("rat racing");
            Bitmap[] rats=Bitmap_modifier.split(load(R.drawable.rattatoile), 124, 37);
            add(r[0]=new Rat(rats[0], rats[2]));  r[0].name="<0xFFFF0000>rat 1";
            add(r[1]=new Rat(rats[1], rats[3]));  r[1].name="<0xFF00FF00>rat 5";
            add(r[2]=new Rat(rats[4], rats[6]));  r[2].name="<0xFFFF00FF>rat 2";
            add(r[3]=new Rat(rats[5], rats[7]));  r[3].name="<0xFF808080>rat 3";

            cur=r[0];

            r[0].y=y;
            for(int i=1; i<r.length; i++)
                r[i].y=r[i-1].y+rats[0].getHeight();
            h=(int)(r[r.length-1].y+r[r.length-1].h);

            for(Rat rat: r)
                rat_assorti.add(rat);
        }

        public float startx=0, finishx=500;


        public Event select=new Event(null){
            @Override
            public boolean post() {
                Graphic_system.camera.instantKillFocus(cur);
                Graphic_system.camera.instantSetFocus(cur=(Rat)target);
                return super.post();
            }
        };
        public Object_list.Object_list_TR rat_assorti=new Object_list.Object_list_TR(100);

        public float camera_speed;

        protected Rat winner=null, cur;
        protected boolean finished=true;
        public void start() {
            if(!finished)
                return;
            Random random=new Random();
            for(Rat rat: r)
            {
                rat.MD.active=true;
                rat.x=startx;
                rat.speed=random.nextFloat()*4;
            }
            finished=false;
            winner=null;

            camera_speed=Graphic_system.camera.speed;
            Graphic_system.camera.speed=cur.speed*2;
        }
        public void finish() {
            for(Rat rat: r)
                rat.MD.active=false;
            finished=true;
            Graphic_system.camera.speed=camera_speed;

            onFinish();
        }

        @Override
        public void update() {
            super.update();
            if(finished)
                return;
            if((winner=getLeader()).x>=finishx)
                finish();
        }
        public Rat getLeader(){
            Rat res=r[0];
            for(int i=1; i<r.length; i++)
                if(r[i].x>res.x)
                    res=r[i];
            return res;
        }

        @Override
        public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
            super.render(canvas, paint, x0, y0);
            canvas.drawLine(startx-x0, y-y0, startx-x0, y+h-y0, paint);
            canvas.drawLine(finishx+cur.w-x0, y-y0, finishx+cur.w-x0, y+h-y0, paint);
            return true;
        }

        public class Rat extends Game_object {
            private int index=0, timer=0, rate=16;
            protected Bitmap[] film=new Bitmap[2];


            public Rat(Bitmap stay, Bitmap jump){
                film[0]=stay;
                film[1]=jump;

                ANIM.push(new Animation() {
                    @Override
                    public void update() {
                        if(dx!=0)
                            index=(int)(timer++*speed/rate)%2;
                        else index=0;
                        setFilm(film[index]);
                    }
                });
                MD.add(new Motion_drive() {
                    @Override
                    public void update() {
                        dx+=speed;
                    }
                });
                MD.active=false;


                ACTS.push(new Event(null){
                    @Override
                    public boolean post() {
                        start();
                        return super.post();
                    }
                });
            }

            @Override
            public String toString() {
                return name+"("+speed+" "+(int)((x-startx)/(finishx-startx)*100)+"%)";
            }
        }
        public Rat[] r=new Rat[4];


        public Event onFinish=null;
        @Override
        public void onStart() {
            super.onStart();
            for(Rat rat: r)
                rat.x=startx;
            Graphic_system.camera.instantSetFocus(cur);
            rat_assorti.onSelect=select;
            rat_assorti.start();
        }
        @Override
        public void onFinish() {
            super.onFinish();
            Graphic_system.camera.instantKillFocus(cur);

            if(onFinish==null)
                return;
            onFinish.init=cur;
            onFinish.target=winner;
            onFinish.post();
            onFinish=null;
        }
    }


    public static class GO_lever extends Sensitive_object{
        public boolean state=false;
        protected Bitmap[] film=new Bitmap[2];
        public GO_lever(Bitmap on, Bitmap off){
            super();
            relative_touch=true;
            film[0]=off;
            film[1]=on;
            ANIM.push(a);
            a.update();
        }


        public Animation a=new Animation() {
            @Override
            public void update() {
                setFilm(state?film[1]:film[0]);
            }
        };

        public Event onSwitch;
        public void setState(boolean state) {
            if(this.state==state)
                return;
            this.state = state;
            if(onSwitch==null)
                return;
            onSwitch.init=this;
            onSwitch.post();
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            if(disable)
                return false;
            setState(!state);
            return true;
        }

        @Override
        public String toString() {
            return super.toString()+"("+state+")";
        }
    }
    public static abstract class GO_lever_pass {
        public ArrayList<GO_lever> levers=new ArrayList<>();
        public boolean add(GO_lever lever){
            if(!levers.add(lever))
                return false;
            lever.onSwitch=onSwitch;
            return true;
        }

        private Event onSwitch=new Event(null){
            @Override
            public boolean post() {
                if(checkPass((GO_lever)init))
                    onCorrectPass.post();
                return super.post();
            }
        };


        public Event onCorrectPass, onIncorrectPass=new Event(null){
            @Override
            public boolean post() {
                reset();
                return super.post();
            }
        };
        public abstract boolean checkPass(GO_lever next);
        public void reset(){
            for(GO_lever lever: levers)
                lever.state=false;
        }


        @NonNull
        @Override
        public String toString() {
            return levers.toString();
        }
    }

    public static class GO_Denchique_fight extends Scene {
        public GO_Denchique_fight() {
            super("dench dench");
            Graphic_system.player.menu.add("dench stop", new Event(null){
                @Override
                public boolean post() {
                    onFinish();
                    return super.post();
                }
            });
            Graphic_system.player.menu.add("dench start", new Event(null){
                @Override
                public boolean post() {
                    onStart();
                    return super.post();
                }
            });


            map=new Game_object();
            map.layer=-10;
            map.setFilm(load(R.drawable.scene_dench_dench));
            map.COLL.add(new Collider.Collider_bitmap(map.film));
            ((Collider.Collider_bitmap)map.COLL.colliders.get(0)).coll_color=0xFFFFFFFF;
            add(map);
            w=map.w;
            h=map.h;



            ball=new Game_object();
            ball.setFilm(load(R.drawable.denchique_fight_ball));
            ball.speed=0.1f;
            ball.rigid=false;

            //Graphic_system.player.speed=4;


            Random r=new Random();
            for(int i=0; i<ball2.length; i++)
            {
                add(ball2[i]=new Game_object_clone(ball));
                ball2[i].MD.add(new Motion_drive.MD_directive(Graphic_system.player));
                ball2[i].MD.add(new Motion_drive.MD_inerting(0.999f));
                ball2[i].set(-50+r.nextFloat()*100, -50+r.nextFloat()*100);
                ball2[i].COLL.add(new Collider.Collider_rect(){
                    @Override
                    public boolean collision(Collider other) {
                        if(!super.collision(other))
                            return false;
                        if(other.obj!=Graphic_system.player)
                            return true;
                        Graphic_system.player.addhp(-0.1f);
                        return true;
                    }
                });
                ((Collider.Collider_rect)ball2[i].COLL.colliders.get(0)).difficulty=-1;
            }
        }
        public Game_object map;

        public Game_object ball;
        public Game_object_clone[] ball2=new Game_object_clone[12];

        public MediaPlayer gas = MediaPlayer.create(context, R.raw.gasgasgas);


        public float tx, ty;
        protected Scene player_prev_scene;
        @Override
        public void onStart() {
            super.onStart();
            gas.start();
            tx=Graphic_system.player.x;
            ty=Graphic_system.player.y;
            Graphic_system.player.set(getCenter());
            player_prev_scene=Graphic_system.player.containing;
            replaceObject(Graphic_system.player);
            Graphic_system.camera.limiter.active=true;
        }

        @Override
        public void onFinish() {
            super.onFinish();
            gas.stop();
            Graphic_system.player.x=tx;
            Graphic_system.player.y=ty;
            player_prev_scene.replaceObject(Graphic_system.player);
            Graphic_system.camera.limiter.active=false;
        }
    }
}
