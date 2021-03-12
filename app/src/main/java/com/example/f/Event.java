package com.example.f;

public class Event extends Action {

    public Event(String title, Game_object init) {
        super(Action_type.ACT_EVENT, title, init, null);
    }
    public Event(){
        super(Action_type.ACT_EVENT, "null", null, null);
    }
    public Event(Event onFinish){
        super(Action_type.ACT_EVENT, "null", null, null);
        this.onFinish=onFinish;
    }


    public Event onFinish;

    @Override
    public boolean post() {
        if(onFinish!=null)
            onFinish.post();
        return false;
    }


    public static class Message extends Event {
        public Message(String title, Game_object target, String text, Event onFinish) {
            super(title, target);
            this.target=target;
            this.onFinish=onFinish;
            this.text=text;
        }
        public Message(Game_object target, String text, Event onFinish){
            this("", target, text, onFinish);
        }

        public int color=0xFFFFFFFF;
        public String text;
        public Dialogue dialogue;

        @Override
        public boolean post() {
            if(dialogue==null)
                dialogue=Graphic_system.dialogue;
            dialogue.start(init, target, text, onFinish);
            return true;
        }
    }
    public static class Delayed_action extends Event {
        public Delayed_action(int time, Event action){
            super(action);
            timer=new Predicate.Delay(action, false, time);
        }


        public Predicate.Delay timer;
        @Override
        public boolean post() {
            timer.start();
            return true;
        }
    }
}
