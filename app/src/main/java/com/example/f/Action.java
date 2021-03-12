package com.example.f;

public class Action {
    public String title;
    public Game_object init, target;
    public Action(Action_type type, String title, Game_object init, Game_object target) {
        this.type=type;
        this.title=title;
        this.init=init;
        this.target=target;
        return;
    }
    public Action(Action_type type, String title, Game_object init, Game_object target, int param) {
        this.type=type;
        this.title=title;
        this.init=init;
        this.target=target;
        this.param=param;
        return;
    }
    public boolean post(){
        if(target==null)
            return false;
        target.onAction(init, this);
        return true;
    }


    enum Action_type {
        ACT_TALK,
        ACT_FIGHT,
        ACT_PICK,
        ACT_DROP,
        ACT_USE,
        ACT_DEFAULT,
        ACT_EVENT
    };
    public Action_type type;
    public int param = 0;


    public String toString(){
        return title+"  "+init+"  "+target+"  "+param;
    }


    public static class Action_stack {
        protected Stack<Action> actions=new Stack<>();
        protected Game_object obj;
        public Action_stack(Game_object obj){
            this.obj=obj;
        }


        public void push(Action action){
            action.init=obj;
            actions.push(action);
        }
        public Action peek(){
            return actions.peek();
        }
        public Action pop(){
            return actions.pop();
        }
        public Action pop(Action action){
            return actions.pop(action);
        }


        public void post(){
            Action a=actions.peek();
            if(a==null)
                return;
            a.post();
        }
    }
}
