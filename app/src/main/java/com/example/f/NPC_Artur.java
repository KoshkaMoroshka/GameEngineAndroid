package com.example.f;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;

import java.util.Random;

public class NPC_Artur extends NPC {
    public NPC_Artur(){
        super("artur", R.drawable.intelligentes);
    }


    protected static class NPC_Artur_menu extends Scene {
        public NPC_Artur_menu(){
            super("artur mordred lancelot");
            setFilm(load(R.drawable.artur));
            ACTS.push(defaultAction);
        }
        protected Action defaultAction=new Action(Action.Action_type.ACT_DEFAULT, "act", null, this);
        public static MediaPlayer mp=MediaPlayer.create(context, R.raw.bananarama);

        @Override
        public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
            return super.render(canvas, paint, x0, y0);
        }

        @Override
        public void update() {
            setScale(Graphic_system.w*2/film.getWidth());
            super.update();
        }

        Sensitive_object exit=new Sensitive_object(R.drawable.down_up){
            @Override
            public void onTouch() {
                farewell.post();
            }

            @Override
            public void update() {
                set(0, -Graphic_system.h);
                super.update();
            }
        };
        @Override
        public void onStart() {
            super.onStart();
            mp.start();
            Graphic_system.camera.instantSetFocus(this);
            Graphic_system.overlay.add(exit);
            turn(true);
            Graphic_system.dialogue.start(this, this, "<auto><d100><0xFFFF0000>коллегия крайне внимательно вас<d40> слушает", e1);
        }
        @Override
        public void onFinish() {
            super.onFinish();
            mp.pause();
            Graphic_system.overlay.del(exit);
            Graphic_system.camera.instantKillFocus(this);
            Graphic_system.player.inventory.turn(false);
            Graphic_system.player.inventory.onSelect=null;
            turn(false);
        }

        String[] tell={"<0xFFFF0000>да это же ",
                "умник со своим <0xFFFF0000> ",
                "<auto>Убери со стола <sc2><0xFFFF0000>дай отдохнуть!!!<fin>"};
        Event select=new Event(null){
            @Override
            public boolean post() {
                Random r=new Random();
                Graphic_system.player.inventory.turn(false);
                Graphic_system.dialogue.start(NPC_Artur_menu.this, NPC_Artur_menu.this, tell[r.nextInt(tell.length)]+target.name, e1);

                target.setCenter(getCenter());
                add(target);
                e1.target=target;
                return super.post();
            }
        };
        Event e1=new Event(null){
            @Override
            public boolean post() {
                if(target!=null)
                    del(target);
                Graphic_system.player.inventory.onSelect=select;
                Graphic_system.player.inventory.start();
                return super.post();
            }
        };


        public Event.Message farewell=new Event.Message(this,
                "<auto><0xFFFFFF00>коллегия агрессивно с вами прощается<d40><end>", new Event(null){
            @Override
            public boolean post() {
                onFinish();
                return super.post();
            }
        });
        @Override
        public boolean onAction(Game_object target, Action act) {
            farewell.post();
            return true;
        }
    }
    protected NPC_Artur_menu menu=new NPC_Artur_menu();

    @Override
    public boolean onAction(Game_object target, Action act) {
        menu.defaultAction.init=target;
        menu.onStart();
        return true;
    }
}
