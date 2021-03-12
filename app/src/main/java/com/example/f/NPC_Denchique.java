package com.example.f;

public class NPC_Denchique extends NPC {
    NPC_Denchique(){
        super("denchique", R.drawable.denchique);

        Event message = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player,
                        "<0xFFFFFFFF>Друг, иди сюда.", null);
                return super.post();
            }
        };
        Predicate p1 = new Predicate(message, true){
            @Override
            public boolean predicate() {
                return Graphic_system.player.distance(NPC_Denchique.this) <= 100;
            }
        };


        Event relax = new Event(null){
            @Override
            public boolean post() {
                flex=!flex;
                if(!flex){
                    Graphic_system.player.a4_relax.finish();
                    Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player, "<0xFFFFFFFF>Удачной дороги, друг. :)", null);
                    temp.setText("лечь");
                    return super.post();
                }
                Graphic_system.player.a4_relax.finished = false;
                Graphic_system.player.ANIM.push(Graphic_system.player.a4_relax);
                Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player, "<0xFFFFFFFF>Правильно, на ногах правды нет. :)", openList);
                temp.setText("встать");
                return super.post();
            }
        };
        temp=list.add("Лечь", relax);

        Event e1 = new Event(null){
            @Override
            public boolean post() {
                if(flex)
                    Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player,
                        "<0xFFFFFFFF>Такой вопрос путешественникам не задают. :)", openList);
                else
                    Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player,
                            "<0xFFFFFFFF>(Видимо он хочет, чтобы вы легли рядом)", openList);
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Давно ты тут лежишь?",e1);

        Event e2 = new Event(null){
            @Override
            public boolean post() {
                if(flex)
                    Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player,
                            "<0xFFFFFFFF>Я так долго путешествую, что уже не знаю, но отсюда выход только направо. :)", openList);
                else
                    Graphic_system.dialogue.start(NPC_Denchique.this, Graphic_system.player,
                            "<0xFFFFFFFF>(Видимо он хочет, чтобы вы легли рядом)", openList);
                return super.post();
            }
        };
        list.add("<0xFFFFFFFF>Не знаешь как выбраться отсюда?",e2);
    }
    Action_list.AL_Text_panel temp;

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

    boolean flex=false;

    @Override
    public boolean onAction(Game_object target, Action act) {
        Graphic_system.dialogue.start(NPC_Denchique.this, target,
                "<0xFFFFFFFF>Ты здесь новенький? Приляг, отдохни. :)", openList);
        return true;
    }
}
