package com.example.f;

public class NPC_Chamoto extends NPC {
    public NPC_Chamoto() {
        super("chamoto", R.drawable.chamoto);

        Event e1 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Chamoto.this, Graphic_system.player, "<auto><0xFFFFFFFF>Тебя волновать не должно, отойди от <0xFFFF8C00>тепла<0xFFFFFFFF>!", null);
                return super.post();
            }
        };
        list.add("Кто вы такие?", e1);
        Event e2 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Chamoto.this, Graphic_system.player, "<auto><0xFFFFFFFF>Мне не нужно, у меня есть <0xFFB22222>пламя!", null);
                return super.post();
            }
        };
        list.add("Не знаешь как отсюда выбраться?", e2);

        Event tuli=new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(containing.findObject("tulivuori"), Graphic_system.player,
                        "Хватит! Chamoto, ты всё так же слаб. С момента посвящения ты не приобрёл и капли силы пламени бака.\n" +
                                "(Chamoto немедленно расстроился.)", null);
                return super.post();
            }
        };
        Event.Delayed_action delay=new Event.Delayed_action(50, tuli);
        Event e3 = new Event(delay){
            @Override
            public boolean post() {
                Graphic_system.player.pool.play(Graphic_system.player.bonk, 1, 1,1  , 0, 1);
                Graphic_system.player.hp-=34;
                NPC_Chamoto.this.turn(false);
                list.turn(false);
                return super.post();
            }
        };
        list.add("Можно погреться?", e3);


        Graphic_system.overlay.add(list);
        list.autoexit= false;
        list.turn(false);
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
            list.turn(true);
            return super.post();
        }
    };

    @Override
    public boolean onAction(Game_object target, Action act) {

        Graphic_system.dialogue.start(this, target,
                "<0xFFFFFFFF>Отойди \tты не имеешь права греться возле нашего <0xFFFF8C00>пламени<0xFFFFFFFF>!!!", openList);
        return true;
    }
}
