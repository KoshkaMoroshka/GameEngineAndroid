package com.example.f;


import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.f.Event.Message;
import com.example.f.Event.Delayed_action;

import java.util.ArrayList;

public class Chapter_1_scenes extends Chapter_scenes {
    public Player player = Graphic_system.player;
    public Dialogue dialogue = Graphic_system.dialogue;
    public Keyboard keyboard = Graphic_system.keyboard;
    public Camera camera = Graphic_system.camera;


    public SoundPool soundPool=new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    public int punch_bucket=soundPool.load(Game_object.context, R.raw.falling_bucket_final_accord, 0);


    public class Scene_1 extends Scene {
        Scene_1(){
            super("scene 1");

            map.name="map";
            map.layer=-10;
            map.COLL.add(new Collider.Collider_bitmap(load(R.drawable.scene_1_collider)));
            add(map);


            trash.name="trash";
            trash.set(128, 116);
            trash.COLL.add(new Collider.Collider_bitmap(load(R.drawable.scene_1_trash_collider)));
            add(trash);


            artur.name="artur";
            artur.interactive=true;
            artur.COLL.add(new Collider.Collider_bitmap(load(R.drawable.intelligentes_collider)));
            artur.set(128, 116);
            artur.turn(false);
            add(artur);


            port_1to2.set(352, 101);
            add(port_1to2);

            w=map.w;
            h=map.h;
        }
        public Scene_port port_1to2 = new Scene_port(R.drawable.stairs, this);
        public Game_object map = new Game_object(R.drawable.scene_1);
        public Game_object trash = new Game_object(R.drawable.scene_1_trash);  //128 116
        public NPC_Artur artur = new NPC_Artur();


        public GO_Games.GO_Denchique_fight dench=new GO_Games.GO_Denchique_fight();
        @Override
        public void onStart() {

            Event i=new Event(null){
                @Override
                public boolean post() {
                    artur.turn(true);
                    return super.post();
                }
            };
            Predicate pp=new Predicate(i, true){
                @Override
                public boolean predicate() {
                    return player.containing!=Scene_1.this;
                }
            };
            super.onStart();

            player.ANIM.push(player.a4_relax);
            camera.set(player.x+player.w/2, player.y-200);


            Event e6=new Event(null){
                @Override
                public boolean post() {
                    player.a4_relax.finish();
                    return super.post();
                }
            };
            Message m5=new Message(player, "блин <0xFFFFFF00>жрать хочу", e6);

            Delayed_action e5=new Delayed_action(300, m5);
            Event e4=new Event(e5){
                @Override
                public boolean post() {
                    soundPool.play(punch_bucket, 1, 1, 0, 0, 1f);
                    return super.post();
                }
            };



            Message m4=new Message(player, "ладно сойдет", e4);
            Delayed_action a3=new Delayed_action(100, m4);

            Event e3=new Event(a3){
                @Override
                public boolean post() {
                    player.name=keyboard.input;
                    keyboard.input="";
                    return super.post();
                }
            };
            Keyboard.Event_start i2=keyboard.new Event_start(e3);

            Message m3=new Message(player, "а мне нравится bob...", i2);
            Delayed_action a2=new Delayed_action(100, m3);


            Keyboard.Event_start i1=keyboard.new Event_start(null);
            final Message m33=new Message(player, "<0xFFFFFF00>ничего <0xFFFFFFFF>не услышал", i1);
            Event e2=new Event(a2){
                @Override
                public boolean post() {
                    if(keyboard.input=="")
                        return m33.post();
                    player.name=keyboard.input;
                    keyboard.input="";
                    return super.post();
                }
            };
            i1.onFinish=e2;


            Message m2=new Message(player, "<0xFFFFFFFF>ничего не помню,\t" +
                    "<0xFFFFFF00>(здесь он разворачивается в сторону экрана)\t" +
                    "<0xFFFFFFFF>может ты напомнишь как меня зовут?", i1);
            Delayed_action a1=new Delayed_action(200, m2);

            Message m1=new Message(player, "судьба проказница-шалунья определила так сама\t" +
                    "всем глупым счастье от безумья,\t" +
                    "а умным - <0xFFFF0000>горе от ума", a1);
            Predicate.Delay d1=new Predicate.Delay(m1, true, 200);
        }
    }
    public class Scene_2 extends Scene {
        Scene_2(){
            super("scene 2");

            map.layer=-10;
            map.name="map";
            map.COLL.add(new Collider.Collider_bitmap(load(R.drawable.scene_2_collider)));
            add(map);


            arc.set(122, 134);
            add(arc);

            darts.set(273, 147);
            add(darts);

            ritsu.set(10, 134);
            add(ritsu);

            tulivuori.set(20, 245);
            add(tulivuori);

            tulivuori.backilo.set(tulivuori.x + 25, tulivuori.y + 5);
            add(tulivuori.backilo);

            tulivuori.bulkan.set(tulivuori.backilo.x + 20, tulivuori.backilo.y - 40);
            add(tulivuori.bulkan);

            tulivuori.llosgfynyd.set(tulivuori.backilo.x + 15, tulivuori.backilo.y + 25);
            add(tulivuori.llosgfynyd);


            chamoto = new NPC_Chamoto();
            chamoto.set(tulivuori.backilo.x + 40, tulivuori.backilo.y - 13);
            add(chamoto);


            denchique = new NPC_Denchique();
            denchique.set(205,42);
            add(denchique);

            port_2to3.set(534, 49);
            add(port_2to3);


            port_2to1.set(0, 359);
            add(port_2to1);
            Scene_port.join_scenes_RtoL(scene_1.port_1to2, port_2to1);


            w=map.w;
            h=map.h;
        }
        public Scene_port port_2to1 = new Scene_port(R.drawable.stairs, null);
        public Scene_port port_2to3 = new Scene_port(R.drawable.stairs, null);

        public Game_object map = new Game_object(R.drawable.scene_2);
        public Game_object arc = new Game_object(R.drawable.scene_2_arc);     // 122  134
        public NPC_Ritsu ritsu = new NPC_Ritsu();    //  10  152
        public NPC_Tulivuori tulivuori = new NPC_Tulivuori(); // 16  250

        public NPC_Chamoto chamoto;

        public NPC_Denchique denchique;

        public Game_object darts = new Darts();
        public class Darts extends Game_object {
            public Darts(){
                super(R.drawable.scene_2_darts);
                interactive=true;

                game.apple=new Event(null){
                    @Override
                    public boolean post() {
                        Graphic_system.camera.killFocus(game);
                        Graphic_system.dialogue.start(pew.init, pew.init, "score: "+game.score, null);
                        return super.post();
                    }
                };
                fight.add(game);
                game.ACTS.push(pew);
            }
            public GO_Games.GO_Darts_F game=new GO_Games.GO_Darts_F();
            public Scene fight=new Scene("darts");

            public Action pew=new Action(Action.Action_type.ACT_DEFAULT, "pew", player, game);

            @Override
            public boolean onAction(Game_object target, Action act) {
                game.setCenter(camera.focus.peek().getCenter());
                Graphic_system.camera.setFocus(game);
                pew.init=target;
                return true;
            }
        };

        @Override
        public void onStart() {
            super.onStart();
            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    chamoto.onAction(player, null);
                    return super.post();
                }
            };
            Predicate p1 = new Predicate(e1, true){
                @Override
                public boolean predicate() {
                    return player.distance(tulivuori.backilo) <= 70;
                }
            };
        }
    }
    public class Scene_3 extends Scene {
        Scene_3(){
            super("scene 3");

            map.name="map";
            map.layer=-10;
            map.COLL.add(new Collider.Collider_bitmap(load(R.drawable.scene_3_collider)));
            add(map);

            port_3to2.set(0,80);
            add(port_3to2);

            port_3to4.set(513,215);
            add(port_3to4);

            posters = new Posters();
            posters.set(298,25);
            add(posters);

            cute.interactive=true;
            cute.name = "cute";

            bone.interactive=true;
            bone.name = "bone";

            skeleton = new Skeleton();
            skeleton.set(472,70);
            add(skeleton);

            vigour.set(380, 150);
            add(vigour);

            berdo = new NPC_Berdo();
            berdo.set(460,305);
            add(berdo);

            Bitmap[] color_lewer = Bitmap_modifier.split(load(R.drawable.color_lewer), 6,4);
            Game_object[] color_lewer_object = new Game_object[4];
            for (int i = 0; i<color_lewer_object.length; i++){
                color_lewer_object[i] = new Game_object();
                color_lewer_object[i].setFilm(color_lewer[i]);
            }

            Bitmap[] t = Bitmap_modifier.split(load(R.drawable.lever_anim), 10, 9);
            int hh1 = 72;
            GO_Games.GO_lever lever;
            for (int i=0; i<lever_pack_1.length; i++){
                lever_pack_1[i]=(lever = new GO_Games.GO_lever(t[2], t[0]));
                lever.COLL.add(new Collider.Collider_rect());
                lever.setScale(2);
                lever.set(470, hh1+=30);
                color_lewer_object[i].set(lever.x - 8, lever.y+10);
                add(lever);
                add(color_lewer_object[i]);
            }

            int hh2 = 185;
            for (int i=0; i<lever_pack_2.length; i++){
                lever_pack_2[i]=(lever = new GO_Games.GO_lever(t[2], t[0]));
                lever.COLL.add(new Collider.Collider_rect());
                lever.setScale(2);
                lever.set(202, hh2+=30);
                add(lever);
            }

            pass=new GO_Games.GO_lever_pass(){
                public GO_Games.GO_lever[] password = {lever_pack_1[1],lever_pack_1[3],lever_pack_1[0],lever_pack_1[2]};
                ArrayList<GO_Games.GO_lever> temp_pass = new ArrayList<>();
                @Override
                public boolean checkPass(GO_Games.GO_lever next) {
                    temp_pass.add(next);
                    next.ANIM.update();
                    next.disable = true;
                    if (temp_pass.size() != password.length)
                        return false;
                    for(int i = 0; i<password.length;i++)
                        if (temp_pass.get(i) != password[i])
                            return onIncorrectPass.post();

                    return true;
                }

                @Override
                public void reset() {
                    temp_pass.clear();
                    for(GO_Games.GO_lever lever: levers)
                        lever.disable=false;
                    super.reset();
                }
            };
            pass2=new GO_Games.GO_lever_pass(){
                public GO_Games.GO_lever[] password = {lever_pack_2[3],lever_pack_2[0],lever_pack_2[2],lever_pack_2[1]};
                ArrayList<GO_Games.GO_lever> temp_pass = new ArrayList<>();
                @Override
                public boolean checkPass(GO_Games.GO_lever next) {
                    temp_pass.add(next);
                    next.ANIM.update();
                    next.disable = true;
                    if (temp_pass.size() != password.length)
                        return false;
                    for(int i = 0; i<password.length;i++)
                        if (temp_pass.get(i) != password[i])
                            return onIncorrectPass.post();

                    return true;
                }

                @Override
                public void reset() {
                    temp_pass.clear();
                    for(GO_Games.GO_lever lever: levers)
                        lever.disable=false;
                    super.reset();
                }
            };


            for (GO_Games.GO_lever l: lever_pack_1)
                pass.add(l);
            for (GO_Games.GO_lever l: lever_pack_2)
                pass2.add(l);


            pass.onIncorrectPass=new Event(null){
                @Override
                public boolean post() {
                    //Graphic_system.no_sex.setText(pass+" ");
                    pass.reset();
                    Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "(Ничего не произошло)", null);
                    return false;
                }
            };
            pass.onCorrectPass=new Event(null){
                @Override
                public boolean post() {
                    //pass.reset();
                    if(correct2 == true){
                        Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                        door.setFilm(door_sprite);
                        return super.post();
                    }
                    Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                    Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "(Вы слышите щелчок)", null);
                    correct1 = true;
                    return super.post();
                }
            };


            pass2.onIncorrectPass=new Event(null){
                @Override
                public boolean post() {
                    //Graphic_system.no_sex.setText(pass+" ");
                    pass2.reset();
                    Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "(Ничего не произошло)", null);
                    return false;
                }
            };
            pass2.onCorrectPass=new Event(null){
                @Override
                public boolean post() {
                    if(correct1 == true){
                        Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                        door.setFilm(door_sprite);
                        return super.post();
                    }
                    //pass2.reset();
                    Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                    Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "(Вы слышите щелчок)", null);
                    correct2 = true;
                    return super.post();
                }
            };


            door = new Game_object();
            door.setFilm(load(R.drawable.door_close));
            door.set(495,175);
            door.COLL.add(new Collider.Collider_rect());
            add(door);


            Scene_port.join_scenes_RtoL(scene_2.port_2to3, port_3to2);
            w=map.w;
            h=map.h;
        }
        public GO_Games.GO_lever_pass pass;
        public GO_Games.GO_lever_pass pass2;

        public Game_object map = new Game_object(R.drawable.scene_3);
        public NPC_Vigour vigour=new NPC_Vigour();
        public Scene_port port_3to2 = new Scene_port(R.drawable.stairs, this);
        public Scene_port port_3to4 = new Scene_port(R.drawable.stairs, this);

        @Override
        public void update() {
            Graphic_system.no_sex.setText(door + "  " + door.hide + "  " + door.disable + "  " + scene_3.find(door));
            super.update();
        }

        public Game_object cute = new Game_object(R.drawable.cute){

            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.player.inventory.put(cute);
                    list.del("Подобрать");
                    return super.post();
                }
            };

            public Action_list list=new Action_list(100){

                @Override
                public void update() {
                    set(Graphic_system.w-width-border*2, -Graphic_system.h);
                    super.update();
                }
            };
            public Event openList=new Event(null){
                @Override
                public boolean post() {
                    list.add("Подобрать", e1);
                    list.start(null);
                    return super.post();
                }
            };
            @Override
            public boolean onAction(Game_object target, Action act) {
                Graphic_system.dialogue.start(target, target,
                        "(<0xFFFFFFFF>Вы смотрите на <0xFFFF8080>кра<0xFFFFD700>сот<0xFFC0C0C0>уль<0xFFCD853F>ку<0xFFFFFFFF>)", openList);
                return true;
            }
        };

        public Posters posters;
        Bitmap door_sprite = load(R.drawable.door_open);
        public class Posters extends Game_object{
            public Posters(){
                super(R.drawable.posters);
                interactive=true;

                Event e1 = new Event(null){
                    @Override
                    public boolean post() {
                        list.turn(false);
                        Posters.this.turn(false);
                        player.ANIM.push(player.a2_walk2);
                        return super.post();
                    }
                };
                list.add("(Сделать из них одежду)", e1);
                Event e2 = new Event(null){
                    @Override
                    public boolean post() {
                        Graphic_system.player.inventory.put(cute);
                        Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "<0xFFFFFFFF>Ого, какая за ними красивая стена, возьму один кирпич. " +
                                "\t (Вы получаете <0xFFFF8080>кра<0xFFFFD700>сот<0xFFC0C0C0>уль<0xFFFFFFFF>ку)", null);
                        list.del("Посмотреть что за ними");
                        return super.post();
                    }
                };
                list.add("Посмотреть что за ними",e2);
                Event e3 = new Event(null){
                    @Override
                    public boolean post() {
                        list.turn(false);
                        return super.post();
                    }
                };
                list.add("Отойти",e3);
            }
            public Action_list list=new Action_list(100){
                @Override
                public void update() {
                    set(Graphic_system.w-width-border*2, -Graphic_system.h);
                    super.update();
                }
            };
            public Event openList=new Event(null){
                @Override
                public boolean post() {
                    list.start(null);
                    return super.post();
                }
            };
            @Override
            public boolean onAction(Game_object target, Action act) {
                Graphic_system.dialogue.start(target, target, "<0xFFFFFFFF>Какие красивые картины, я бы ими укрылся", openList);
                return true;
            }
        }

        Skeleton skeleton;
        public class Skeleton extends Game_object{
            Skeleton(){
                super(R.drawable.skeleton);
                interactive=true;

                Event e1 = new Event(null){
                    @Override
                    public boolean post() {
                        Graphic_system.dialogue.start(Skeleton.this, target, "Цвета идут слева на право\t" +
                                "Слова идут с права на лево\t" +
                                "Слова рычагов сверху вниз идут образом странным\t" +
                                "Спокойствие-Радость-Сила-Осторожность\t" +
                                "Догадайся как правильно включить рычаги", null);
                        return super.post();
                    }
                };
                list.add("(Прочесть записку)", e1);
                Event e2 = new Event(null){
                    @Override
                    public boolean post() {
                        Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "<0xFFFFFFFF>Блин, слишком мёртвая хватка, могу только прочитать", null);
                        return super.post();
                    }
                };
                list.add("(Взять записку)",e2);
                Event e3 = new Event(null){
                    @Override
                    public boolean post() {
                        list.turn(false);
                        return super.post();
                    }
                };
                list.add("(Оставить парня в покое)",e3);
                Event e4 = new Event(null){
                    @Override
                    public boolean post() {
                        Graphic_system.player.inventory.put(bone);
                        Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "<0xFFFFFFFF>Вы подобрали косточку на память", null);
                        bone_pick = true;
                        list.del("Взять кость на память");
                        return super.post();
                    }
                };
                list.add("Взять кость на память", e4);
            }

            public Action_list list=new Action_list(100){
                @Override
                public void update() {
                    set(Graphic_system.w-width-border*2, -Graphic_system.h);
                    super.update();
                }
            };
            public Event openList=new Event(null){
                @Override
                public boolean post() {
                    list.start(null);
                    return super.post();
                }
            };

            public boolean bone_pick = false;

            public boolean onAction(Game_object target, Action act) {
                Graphic_system.dialogue.start(target, target, "<0xFFFFFFFF>Видимо этот парень тут давно, нет пары рёбер...", openList);
                return true;
            }
        }
        public Game_object bone = new Game_object(R.drawable.bone){


            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.player.inventory.put(bone);
                    list.del("Подобрать");
                    list.del("Проверить остроту");
                    return super.post();
                }
            };
            Event e2 = new Event(null){
                @Override
                public boolean post() {
                    list.del("Подобрать");
                    list.del("Проверить остроту");
                    Graphic_system.player.hp += -5;
                    Graphic_system.dialogue.start(Graphic_system.player, Graphic_system.player, "<0xFFFFFFFF>Вы порезали себе палец, кость явно острая", null);
                    return super.post();
                }
            };

            public Action_list list=new Action_list(100){

                @Override
                public void update() {
                    set(Graphic_system.w-width-border*2, -Graphic_system.h);
                    super.update();
                }
            };
            public Event openList=new Event(null){
                @Override
                public boolean post() {
                    list.add("Проверить остроту", e2);
                    list.add("Подобрать", e1);
                    list.start(null);
                    return super.post();
                }
            };
            @Override
            public boolean onAction(Game_object target, Action act) {
                Graphic_system.dialogue.start(target, target,
                        "(<0xFFFFFFFF>Кусок кости, острый)", openList);
                return true;
            }
        };

        public Game_object door;

        public GO_Games.GO_lever[] lever_pack_1 = new GO_Games.GO_lever[4];
        public GO_Games.GO_lever[] lever_pack_2 = new GO_Games.GO_lever[4];
        public boolean correct1 = false;
        public boolean correct2 = false;

        public NPC_Berdo berdo;
    }
    public class Scene_4 extends Scene {
        Scene_4(){
            super("scene 4");

            map.name="map";
            map.layer=-10;
            map.COLL.add(new Collider.Collider_bitmap(load(R.drawable.scene_4_collider)));
            add(map);


            port_4to3.set(0,144);
            add(port_4to3);


            Scene_port.join_scenes_RtoL(scene_3.port_3to4, port_4to3);
            w=map.w;
            h=map.h;
        }


        public Game_object map = new Game_object(R.drawable.scene_4);
        public Scene_port port_4to3 = new Scene_port(R.drawable.stairs, this);
    }


    public Scene_1 scene_1 = new Scene_1();
    public Scene_2 scene_2 = new Scene_2();
    public Scene_3 scene_3 = new Scene_3();
    public Scene_4 scene_4 = new Scene_4();


    public void start(){
        player.set(155, 104);
        scene_1.add(player);

        scene_1.onStart();
        return;
    }
}
