package com.example.f;

public class NPC extends Game_object
{
    public NPC(String name){
        COLL.add(new Collider.Collider_bottom());
        this.name=name;
        interactive=true;
        ACTS.push(defaultAction);
    }
    public NPC(String name, int id) {
        super(id);
        COLL.add(new Collider.Collider_bottom());
        this.name=name;
        interactive=true;
        ACTS.push(defaultAction);
    }

    public Action defaultAction=new Action(Action.Action_type.ACT_DEFAULT, "act", this, null){
        @Override
        public boolean post() {
            containing.onAction(NPC.this, this);
            return true;
        }
    };
    protected Inventory inventory = new Inventory(this);

    public boolean onAction(Game_object target, Action act){
        Graphic_system.dialogue.start(this, target, "звуки <0xFFFFFF00>"+name, null);
        return false;
    }
}
