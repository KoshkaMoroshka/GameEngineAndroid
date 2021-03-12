package com.example.f;

import android.graphics.Bitmap;

public class NPC_Berdo extends NPC {
    NPC_Berdo(){
        super("berdo");

        final Event e2 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Berdo.this, Graphic_system.player, "<auto><0xFFFFFFFF>(Сквозь ещё более громкие рыдания вы слышите:)\n" +
                        "\tСпасибо", null);
                return super.post();
            }
        };

        Event e1 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Berdo.this, Graphic_system.player, "<auto><0xFFFFFFFF>(Сковзь рыдания вы разбираете несколько слов)\n" +
                        "\t<auto>Брат... <d10>убежал... <d15>где...", null);
                if (!help_brother) {
                    list.add("Давай я попробую его найти.", e2);
                    help_brother = true;
                }
                return super.post();
            }
        };
        list.add("Что случилось?", e1);

        Event e3 = new Event(null){
            @Override
            public boolean post() {
                Graphic_system.dialogue.start(NPC_Berdo.this, Graphic_system.player, "<auto><0xFFFFFFFF>(Вы не понимаете ни одного слова," +
                        " так как больше он плачет, чем говорит.)", null);
                return super.post();
            }
        };
        list.add("Ты не знаешь как отсюда выбраться?", e3);

        Event e4 = new Event(null){
            @Override
            public boolean post() {
                list.turn(false);
                return super.post();
            }
        };
        list.add("(Отойти от него)", e4);

        ANIM.push(stand);
        cry.rate = 16;

        Graphic_system.overlay.add(list);
        list.autoexit= false;
        list.turn(false);
    }

    Bitmap[] t = Bitmap_modifier.split(load(R.drawable.bedro), 26,29);
    public Animation.Animation_image stand = new Animation.Animation_image(t[0]);
    public Animation.Animation_single cry = new Animation.Animation_single(t);

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
    public boolean help_brother = false;

    @Override
    public boolean onAction(Game_object target, Action act) {
        ANIM.push(cry);
        Graphic_system.dialogue.start(NPC_Berdo.this, Graphic_system.player, "<0xFFFFFFFF>(Вы видите, что мужчина горестно рыдает.)", openList);
        return true;
    }
}
