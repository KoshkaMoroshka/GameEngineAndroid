package com.example.f;

import android.graphics.Bitmap;
import android.graphics.Color;


public class Chapter_0_scenes extends Chapter_scenes {
    public Player player=Graphic_system.player;
    public Camera camera=Graphic_system.camera;


    public class Scene_1 extends Scene {

        public GO_Games.GO_Darts_F darts=new GO_Games.GO_Darts_F(){
            @Override
            public void update() {
                if(phase==0 && yshot==h/2)
                    phase=1;
                if(phase==1 && xshot==w/2)
                    phase=2;
                super.update();
            }
        };
        public GO_Games.GO_Punch_mole_F punch=new GO_Games.GO_Punch_mole_F();
        public Dialogue dialogue=new Dialogue(600, 60);


        public Scene_1(){
            super("scene 1");
            punch.set(200, 0);
            punch.fixed=true;
            punch.COLL.active=false;
            add(punch);


            darts.set(-200, 0);
            darts.setScale(1.5f);
            darts.fixed=true;
            darts.COLL.active=false;
            add(darts);




            dialogue.setTextSize(2);
            dialogue.set(darts.x+darts.w/2-138, darts.y+darts.h);
            dialogue.layer=10;
            dialogue.turn(false);
            add(dialogue);

        }

        @Override
        public void onStart() {

            final Point[] pts=new Point[9];
            for(int i=0; i<9; i++)
                pts[i]=new Point();


            final Motion_drive.MD_complex md=new Motion_drive.MD_complex(punch){
                public float x0=punch.x+punch.w/2, y0=punch.y+punch.h/2, r=90, ph=0, dph=(float)Math.PI*2/9;


                int timer=0;
                @Override
                public void update() {
                    super.update();
                    x0=player.x-10;
                    y0=player.y;

                    ph+=0.01;
                    for(Point pt: pts)
                    {
                        pt.set(x0-(float)Math.cos(ph)*r, y0-(float)Math.sin(ph)*r);
                        ph+=dph;
                    }
                }
            };
            for(int i=0; i<9; i++)
                punch.moles.get(i).speed=1;


            Event event=new Event(null){
                @Override
                public boolean post() {
                    drives.add(md);
                    return super.post();
                }
            };
            Predicate predicate=new Predicate(event, true){
                @Override
                public boolean predicate() {
                    for(int i=0; i<9; i++)
                        if(punch.moles.get(i).distance(pts[i])>10)
                            return false;
                    return true;
                }
            };


            Event e2=new Event(null){
                @Override
                public boolean post() {
                    for(int i=0; i<9; i++)
                        drives.add(new Motion_drive.MD_directive(pts[i]));
                    player.centalize(punch);
                    return super.post();
                }
            };
            Event.Delayed_action d2=new Event.Delayed_action(200, e2);
            Event f=new Event(d2){
                @Override
                public boolean post() {
                    float x=punch.x, y=punch.y;
                    punch.moles.get(0).set(x, y);
                    punch.moles.get(1).set(x+50, y+0);
                    punch.moles.get(2).set(x+100, y+0);
                    punch.moles.get(3).set(x+0, y+30);
                    punch.moles.get(4).set(x+0, y+60);
                    punch.moles.get(5).set(x+50, y+60);
                    punch.moles.get(6).set(x+100, y+60);
                    punch.moles.get(7).set(x+0, y+90);
                    punch.moles.get(8).set(x+0, y+120);



                    punch.wh_update();
                    player.centalize(punch);


                    float x0=punch.x+punch.w/2, y0=punch.y+punch.h/2, r=90, ph=0, dph=(float)Math.PI*2/9;
                    x0=player.x-10;
                    y0=player.y;
                    for(Point pt: pts)
                    {
                        pt.set(x0-(float)Math.cos(ph)*r, y0-(float)Math.sin(ph)*r);
                        ph+=dph;
                    }
                    return super.post();
                }
            };


            Event.Delayed_action d=new Event.Delayed_action(200, f);
            final Event.Delayed_action a=new Event.Delayed_action(200, d);


            Event.Message m=new Event.Message(player, "untitled sergey and eugene games", null){
                @Override
                public boolean post() {
                    a.post();
                    return super.post();
                }
            };
            m.color=Color.rgb(255, 255, 0);
            m.dialogue=dialogue;
            darts.apple=m;


            Event e=new Event(null){
                @Override
                public boolean post() {
                    scene_1.darts.onAction(null, null);
                    return super.post();
                }
            };
            Event.Delayed_action dd=new Event.Delayed_action(200, e);
            dd.post();
        }
    }
    public Scene_1 scene_1=new Scene_1();


    public class Scene_2 extends Scene {
        public class Lava extends Game_object {
            public Lava(){
                super(-1, -1);
                Bitmap[] a=Bitmap_modifier.split(load(R.drawable.lava_123), 117, 23);
                ANIM.push(new Animation.Animation_film(a));

                COLL.active=false;
                COLL.add(new Collider.Collider_rect());
            }
        }
        public Lava lava=new Lava();
        public Game_object appl=new Game_object(R.drawable.aple, -1);


        public Scene_2(){
            super("scene 2");
            lava.set(0, -100);
            lava.layer=10;
            add(lava);

            appl.layer=1;
            appl.COLL.active=false;
            appl.fixed=true;
            add(appl);
        }


        @Override
        public void onStart() {
            player.set(lava.x-100, lava.y+lava.h-player.h);



            appl.speed=player.speed;
            appl.set(player.x+player.w*2, lava.y+lava.h-player.h);


            Event e2=new Event(null){
                @Override
                public boolean post() {
                    player.MD.add(new Motion_drive.MD_directive(appl));
                    appl.MD.add(new Motion_drive.MD_directive(new Point(lava.x+lava.w, lava.y+lava.h-player.h)));
                    return super.post();
                }
            };
            Event.Delayed_action a=new Event.Delayed_action(100, e2);
            a.post();


            Event e=new Event(null){
                @Override
                public boolean post() {
                    player.pool.play(player.scream, 1, 1, 0, 0, 0.3f);
                    return super.post();
                }
            };
            Predicate p=new Predicate(e, true){
                @Override
                public boolean predicate() {
                    return lava.COLL.collision(player);
                }


                int timer=0;
                @Override
                public void finish() {
                    player.speed=appl.speed/2;
                    if(timer++%12==0)
                        player.onAction(lava, null);
                }
            };

        }

    }
    public Scene_2 scene_2=new Scene_2();


    public class Scene_3 extends Scene {
        public NPC enchelatti = new NPC("enchelatti", R.drawable.enchelatti);
        public NPC denchique = new NPC("denchique", R.drawable.denchique);


        public Scene_3() {
            super("scene 3");
            denchique.set(0, 0);
            denchique.COLL.active=false;
            add(denchique);


            enchelatti.layer=1;
            enchelatti.set(0, -40);
            enchelatti.COLL.active=false;
            add(enchelatti);


            enchelatti.MD.add(new Motion_drive.MD_patrol(denchique, new Point(denchique.x, denchique.y-40)));
        }

        @Override
        public void onStart() {
            player.hide=true;
            Graphic_system.hp_bar.hide=true;
        }
    }
    public Scene_3 scene_3=new Scene_3();


    public void start(){
        MainActivity.activateControllers(false);
        camera.hide=true;


        scene_3.add(player);
        scene_3.onStart();
    }
}
