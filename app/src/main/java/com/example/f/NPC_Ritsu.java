package com.example.f;


import java.util.Random;

public class NPC_Ritsu extends NPC {
    public NPC_Ritsu(){
        super("ritsu", R.drawable.scene_2_ritsu);
        name="ritsu";

        Event peek=new Event(null){
            @Override
            public boolean post() {
                Graphic_system.player.inventory.put(NPC_Ritsu.this);
                return super.post();
            }
        };
        Event talk=new Event(null){
            @Override
            public boolean post() {
                Random r=new Random();
                Graphic_system.dialogue.start(NPC_Ritsu.this, target, "<0xFFFFFFFF>"+phrases[r.nextInt(phrases.length)], null);
                return super.post();
            }
        };
        Event fight=new Event(null){
            @Override
            public boolean post() {
                battle.onStart();
                return super.post();
            }
        };
        Event rats=new Event(null){
            @Override
            public boolean post() {
                racing.onFinish=racing_finish;
                racing.onStart();
                return super.post();
            }
        };


        actions.add("поговорить", talk);
        actions.add("<0xFFFFFFFF>хочу себе такую <0xFFFFFF00>дакимакуру", peek);
        actions.add("<0xFFFF0000>я хочу побить эту безобидную трубу!", fight);
        actions.add("<0xFF80FFFF>крыски крыски крыыыыски!!!!", rats);

    }
    protected Action_list.Action_list_TR actions =new Action_list.Action_list_TR(100);

    public String[] phrases={"аххах труба мужику",
            "не хотел бы я оказаться в одной трубе с ним",
            "я бы забрал эту модную повязку но она туго завязана",
            "почему он молчит",
            "<0xFF00FFFF>*тук тук* \t<0xFFFFFFFF>...\tнаверное никого нет",
            "он <0xFFFF00FF>очень<0xFFFFFFFF> стильно одет",
            //"как же хочется потрогать твои сисяндры",
            "<auto>как дела мужик?\t...\tладно не буду мешать",
            "это я с ним говорю\t<0xFFFF0000>\nили он со мной?",
            //"все не так уж и плохо\tон мог бы быть <0xFF804000>черным",
            "ну как оно?<d30><clr>...<d30><clr><0xFFFF0000>...<0xFFFFFFFF><d30><clr>хахахах<clr><0xFF0000FF>ох уж\n<0xFFFFFF00>эти хохлы!)",
            "бейджик не видно\t буду звать тебя <0xFFFF00FF>ritsu",

            "Там точно <0xFFFF00FF>кто-то есть.",
            "Похоже он не может выбраться.",
            "Вы там точно не поместитесь.",
            "Ему и без вас хорошо.",
            "Какое рельефное тело.",
            "На его трубе видны <0xFF4040FF>слёзы.",
            "<0xFFFF00FF>Он вас внимательно слушает.",
            "Почему его никто не видит?",
            "Он буквально <0xFF404040>слился с окружением.",
            "Настоящий пример для подражания.",
            "Статуя или человек?",
            "Почему он молчит?",
            "<auto>Интересно, он хочет есть?<d100> Я вот хочу.",
            "Слышно его дыхание.",
            "Я бы хотел такую <0xFFFFA020>дакимакуру.",
            "Он залез сверху или снизу?",
            "Было бы хорошо его <0xFFFF0000>вы<0xFFFF4040>дав<0xFFFF8080>ить.",
            "Может он спит?",
            "Вот это я понимаю, искусство.",
            "Хочу такое надгробие."};

    public Ritsu_battle battle=new Ritsu_battle();
    public class Ritsu_battle extends Scene {
        public Ritsu_battle() {
            super("ritsu battle");
            ACTS.push(new Event(null){
                @Override
                public boolean post() {
                    Graphic_system.player.a5_boxing.kick();
                    NPC_Ritsu.this.hp-=4;
                    if(NPC_Ritsu.this.hp<0)
                        onFinish();
                    Graphic_system.player.pool.play(Graphic_system.player.bucket, 1, 1, 0, 0, 1);
                    return super.post();
                }
            });
            ritsu_sprite.setFilm(NPC_Ritsu.this.film);
            ritsu_sprite.setScale(158/ritsu_sprite.film.getHeight());
            add(ritsu_sprite);
        }
        Overlay.HP_Bar ritsu_hp=new Overlay.HP_Bar(NPC_Ritsu.this){
            @Override
            public void update() {
                set(Graphic_system.w-w, -Graphic_system.h);
                super.update();
            }
        };
        Game_object ritsu_sprite=new Game_object();

        @Override
        public void onStart() {
            super.onStart();
            set(Graphic_system.player.x+100, Graphic_system.player.y+80);
            ritsu_sprite.set(Graphic_system.player.x+100, Graphic_system.player.y);
            NPC_Ritsu.this.hp=NPC_Ritsu.this.maxhp;


            ritsu_hp.turn(true);
            Graphic_system.overlay.add(ritsu_hp);
            Graphic_system.camera.instantSetFocus(this);
            replaceObject(Graphic_system.player);

            Graphic_system.player.ANIM.push(Graphic_system.player.a5_boxing);
        }
        @Override
        public void onFinish() {
            super.onFinish();


            ritsu_hp.turn(false);
            Graphic_system.overlay.del(ritsu_hp);
            Graphic_system.camera.killFocus(this);
            NPC_Ritsu.this.containing.replaceObject(Graphic_system.player);

            Graphic_system.player.ANIM.remove(Graphic_system.player.a5_boxing);
            Graphic_system.dialogue.start(NPC_Ritsu.this, Graphic_system.player,
                    "<0xFFFF0000>вы от души наваляли безобидному <0xFFFF00FF>Ritsu<0xFFFF0000>!", null);
        }
    }


    public Event racing_finish=new Event(null){
        @Override
        public boolean post() {
            Graphic_system.dialogue.start(NPC_Ritsu.this, Graphic_system.player, (init==target?"победка":"хехе, мимо"), null);
            return super.post();
        }
    };
    public GO_Games.GO_rat_racing racing=new GO_Games.GO_rat_racing();


    @Override
    public boolean onAction(Game_object target, Action act) {
        actions.start(target);
        return true;
    }
}
