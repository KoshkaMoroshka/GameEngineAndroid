package com.example.f;

import android.graphics.Bitmap;

import java.util.TreeSet;

public class NPC_Tulivuori extends NPC {

    public NPC_Tulivuori() {
        super("tulivuori", R.drawable.tulivuori);

        film_backilo[0]=load(R.drawable.bq1);
        film_backilo[1]=load(R.drawable.bq2);
        film_backilo[2]=load(R.drawable.bq3);

        an_backilo = new Animation.Animation_film(film_backilo);
        backilo.ANIM.push(an_backilo);
        an_backilo.rate = 35;

        Event e1 = new Event(new dia_yes_no_1()){
            @Override
            public boolean post() {
                list.turn(false);
                list.del("<0xFFFFFFFF>Кто вы такие?");
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Кто вы такие?", e1);
        Event e2 = new Event(new dia_yes_no_2()){
            @Override
            public boolean post() {
                list.turn(false);
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Не знаешь, как отсюда выбраться?", e2);
        Event e3 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Tulivuori.this, Graphic_system.player, "<0xFFFFFFFF>Ты ещё не достоин этого\t " +
                        "<0xFFFF0000>хам\t" +
                        "<0xFFFFFFFF>Сам ты клуб",null);
                list.turn(false);
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Как вступить в ваш клуб?", e3);
        Event e4 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Tulivuori.this, Graphic_system.player, "<0xFFFFFFFF>Откуда мне знать, пригрелась <0xFF777777>крыса.",null);
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Кто такой Chamoto?", e4);
        list.autoexit=false;
    }

    public Game_object backilo = new Game_object(R.drawable.bq1, R.drawable.bq_col);

    Bitmap[] film_backilo = new Bitmap[3];

    Animation.Animation_film an_backilo;

    Action_list list = new Action_list(100){
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

    public class dia_yes_no_1 extends Event {

        public dia_yes_no_1(){
            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    Graphic_system.dialogue.start(NPC_Tulivuori.this, Graphic_system.player, "<0xFFFFFFFF> Где-то здесь бродит знатный курильщик, " +
                                    "он надоел мне " +
                                    "своим неуважением к баку и его пламени. Он кидает туда бычки! Побей его", null);

                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Ладно", e1);

            Event e2 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    Graphic_system.dialogue.start(NPC_Tulivuori.this, Graphic_system.player, "<0xFFFFFFFF>(Вас кидает в озноб и вы получаете " +
                            "посохом по голове).\t" +
                            "Уважай старших, иди и побей знатного курильщика, он бродит где-то на этаже.", null);
                    Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                    Graphic_system.player.hp -=10;
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>У меня нет времени", e2);
            list.autoexit=false;
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

        Message m1=new Message(NPC_Tulivuori.this,
                "<0xFFFFFFFF>Мы 'Культ горящего бака', 30 лет назад я спустился сюда в поисках духовного просвящения и встретил его(её) -" +
                " этот мифический бак. Это пламя не гаснет уже 30 лет, и я, и мой культ не позволим ему затухнуть.\n" +
                "\tТы хорошо дерёшься, я вижу твоё духовное пламя, ты силён а моё тело меня уже подводит. " +
                "Не откажешь ли ты верховному жрецу культа горящего бака в одном поручении?", openList);

        @Override
        public boolean post() {
            m1.post();
            return super.post();
        }
    }
    public class dia_yes_no_2 extends Event {

        public dia_yes_no_2() {
            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    Graphic_system.dialogue.start(NPC_Tulivuori.this, Graphic_system.player, "<0xFFFFFFFF>(Прошло <0xFF9932CC>" + relax_time +
                            "<0xFFFFFFFF> минут, вы ничего не помните, зато выспались у теплого бака.)", null);
                    relax_time *= 2;
                    Graphic_system.player.hp = 100;
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Расскажи, дед", e1);

            Event e2 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    NPC_Tulivuori.this.list.start();
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Спасибо, дед, не надо", e2);
            list.autoexit=false;
        }

        int relax_time = 20;

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

        Message m1=new Message(NPC_Tulivuori.this,
                "<0xFFFFFFFF>Я спустился сюда 30 лет назад, я могу рассказать тебе, как горит огонь.", openList);

        @Override
        public boolean post() {
            m1.post();
            return super.post();
        }

    }

    public NPC_Bulkan bulkan = new NPC_Bulkan();
    public class NPC_Bulkan extends NPC{
        public NPC_Bulkan() {
            super("bulkan", R.drawable.bulkan);
            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.dialogue.start(NPC_Bulkan.this, Graphic_system.player, "<auto><0xFFFFFFFF>Ты не достоин <0xFFFF4500>теплоты и " +
                            "<0xFFB22222>огня", null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Здесь очень <0xFFFF4500>тепло", e1);
            Event e2 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.dialogue.start(NPC_Bulkan.this, Graphic_system.player, "<auto><0xFFFFFFFF>(Продолжает на вас презренно смотреть)",null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Как отсюда выбраться?", e2);
            Event e3 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    Graphic_system.dialogue.start(NPC_Bulkan.this, Graphic_system.player, "<auto><0xFFFFFFFF>Chamoto слабейший из нас, так что не доказал, " +
                            "а подраться с тобой мне запрещает Tulivuori",null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Я же уже доказал, что достоин быть возле <0xFFFF8С00>пламени", e3);
            list.autoexit=false;
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
            Graphic_system.dialogue.start(this, target, "<0xFFFFFFFF>(Смотрит на вас презренно и ничего не говорит)", openList);
            return true;
        }
    }

    public NPC_Llosgfynyd llosgfynyd = new NPC_Llosgfynyd();
    public class NPC_Llosgfynyd extends NPC{
        public NPC_Llosgfynyd() {
            super("llosgfynyd" , R.drawable.llosgfynyd);
            Event e1 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.dialogue.start(NPC_Llosgfynyd.this, Graphic_system.player,
                            "<auto><0xFFFFFFFF>Мне нравится твоё <0xFFFF4500>тепло", null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Почему ты так улыбаешься?", e1);
            Event e2 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.dialogue.start(NPC_Llosgfynyd.this, Graphic_system.player, "<0xFFFFFFFF>Мне и тебе это <0xFF00CFFF>не нужно...",null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Не знаешь, как отсюда выбраться??", e2);
            Event e3 = new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.dialogue.start(NPC_Llosgfynyd.this, Graphic_system.player,
                            "<auto><0xFFFFFFFF>Прости\t" +
                                 "не каждый день к нам приходят новички и побеждают Chamoto.",null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Ты меня пугаешь...", e3);
            list.autoexit=false;
            Event e4 = new Event(null){
                @Override
                public boolean post() {
                    list.turn(false);
                    Graphic_system.dialogue.start(NPC_Llosgfynyd.this, Graphic_system.player,
                            "<0xFFFFFFFF>Возвращайся <0xFFFFA07A>погреться",null);
                    return super.post();
                }
            };
            list.add("<0xFFFFFFFF>Я подойду позже", e4);


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
            Graphic_system.dialogue.start(this, target, "<0xFFFFFFFF>(Он улыбается и смотрит на вас,\tна тебя,\tна меня)", openList);
            return true;
        }
    }


    boolean f = false;
    @Override
    public boolean onAction(Game_object target, Action act){
        if (f == false) {
            f = true;
            Graphic_system.dialogue.start(this, target, "<0xFFFFFFFF>Прости за такой холодный приём, присядь на пол, погрейся.", openList);
        } else
            Graphic_system.dialogue.start(this, target, "<0xFFFFFFFF>О это опять ты, проходи, присаживайся.", openList);

        return true;
    }
}
