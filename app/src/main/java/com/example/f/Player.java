package com.example.f;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;

public class Player extends NPC {
    public Player() {
        super("pluer");
        MD.add(new Motion_drive.MD_controllers());
        MD.add(gravity);
        gravity.active=false;
        speed=2f;

        scream =pool.load(context, R.raw.scream, 0);
        chinatown=pool.load(context, R.raw.xue_hue_piao_piao, 0);
        bonk=pool.load(context, R.raw.bonk, 0);
        bucket=pool.load(context, R.raw.falling_bucket, 0);


        menu.add("chinatown", new Event(null){
            @Override
            public boolean post() {
                pool.play(chinatown, 1, 1, 0, 0, 0.6f);
                return super.post();
            }
        });
        menu.add("<0xFFFF0000>AAAAAAA", new Event(null){
            @Override
            public boolean post() {
                pool.play(scream, 1, 1, 0, 0, 0.4f);
                return super.post();
            }
        });
        menu.add("<sc2>привет", new Event.Message(this, "<0xFF00FFFF>лолъ", null));
        menu.turn(false);

        menu.add("inventory", new Event(null){
            @Override
            public boolean post() {
                inventory.onSelect=onInventorySelect;
                inventory.start();
                return super.post();
            }
        });

        inventory.put(new NPC("enchelatti", R.drawable.enchelatti));


        Bitmap[] a=Bitmap_modifier.split(load(R.drawable.chad_lurd_102), 20, 30);
        Bitmap[] l={a[0], a[1], a[2], a[1]};
        Bitmap[] u={a[3], a[4], a[5], a[4]};
        Bitmap[] r={a[6], a[7], a[8], a[7]};
        Bitmap[] d={a[9], a[10], a[11], a[10]};
        a2_walk =new Animation.Animation_LURD(l, u, r, d);
        ANIM.push(a2_walk);

        Bitmap[] a2=Bitmap_modifier.split(load(R.drawable.chud_butiful_lurd_102), 20, 30);
        Bitmap[] l2={a2[0], a2[1], a2[2], a2[1]};
        Bitmap[] u2={a2[3], a2[4], a2[5], a2[4]};
        Bitmap[] r2={a2[6], a2[7], a2[8], a2[7]};
        Bitmap[] d2={a2[9], a2[10], a2[11], a2[10]};
        a2_walk2 =new Animation.Animation_LURD(l2, u2, r2, d2);

        Bitmap[] t={l[1], u[1], r[1], d[1]};
        a3_roll =new Animation.Animation_single(t){
            @Override
            public void restart() {
                pool.play(chinatown, 1, 1, 0, 0, 0.6f);
                super.restart();
            }
        };
        a3_roll.rate=16;


        final Bitmap[] f=Bitmap_modifier.split(load(R.drawable.chad_boxing_012), 93, 156);
        a5_boxing=new Animation_boxing(f);


        a4_relax=new Animation.Animation_image(load(R.drawable.chad_relax)){
            Controller_locker locker=new Controller_locker(true, false);
            @Override
            public void restart() {
                super.restart();
                locker.turn(true);
                //Graphic_system.no_sex.setText(Graphic_system.no_sex.getText()+"+a, ");
                menu.disable_turning=true;
            }

            public void finish(){
                super.finish();
                locker.turn(false);
                //Graphic_system.no_sex.setText(Graphic_system.no_sex.getText()+"-a, ");
            }
            @Override
            public boolean isFinished() {
                menu.disable_turning=!finished;
                return finished;
            }
        };
        return;
    }

    public Animation.Animation_LURD a2_walk, a2_walk2;
    public Animation.Animation_single a3_roll;
    public Animation.Animation_image a4_relax;
    public Animation_boxing a5_boxing;
    public class Animation_boxing extends Animation{
        public int film_index=0, timer=0, rate=48;
        protected Bitmap[] film;
        public Animation_boxing(Bitmap[] film){
            this.film=film;
        }

        public void kick(){
            setFilm(film[1+film_index++%2]);
        }
        @Override
        public void update() {
            if(timer++%rate!=0)
                return;
            setFilm(film[film_index=0]);
        }
    }


    public Motion_drive.MD_gravity gravity=new Motion_drive.MD_gravity();


    public SoundPool pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    public int scream, chinatown, bonk, bucket;


    protected Action_list.Action_list_TR menu=new Action_list.Action_list_TR(100);
    public Event onInventorySelect=new Event(null){
        @Override
        public boolean post() {
            inventory.get(target);
            return super.post();
        }
    };


    @Override
    public void update() {
        if(disable)
            return;
        super.update();

        if(hp<0)
            onDeath();
    }

    public boolean dead=false;
    public void onDeath(){
        if(dead)
            return;
        dead=true;
        Graphic_system.no_sex.setText("вечная памядь");
        Graphic_system.camera.turn(false);
        MainActivity.activateControllers(false);
        //pool.play(scream, 1, 1, 0, 0, 0.2f);
        //Graphic_system.dialogue.start(this, this, "потр<0xFFFF0000>а<0xFFFFFFFF>чено", null);
    }
    public void addhp(float hp){
        this.hp+=hp;
        if(hp>=0)
            return;
        pool.play(bonk, 1, 1, 0, 0, 1);
    }
    @Override
    public boolean onAction(Game_object target, Action act) {
        if(target!=this)
            return target.onAction(this, act);
        menu.start();
        return true;
    }
}
