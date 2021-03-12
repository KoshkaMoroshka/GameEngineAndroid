package com.example.f;


import java.util.ArrayList;

public class Object_list extends Action_list {
    public Object_list(int w) {
        super(w);
    }


    public Event onSelect;
    boolean autoexit=true;
    public void select(OL_Text_panel opt) {
        if(autoexit)
            turn(false);
        if(onSelect==null)
            return;
        onSelect.target=opt.obj;
        onSelect.post();
        onSelect=null;
    }


    public boolean add(Game_object object){
        if(!super.add(new OL_Text_panel(this, object, width)))
            return false;
        list.add(object);
        return true;
    }

    protected ArrayList<Game_object> list =new ArrayList<>();
    public boolean put(Game_object object) {
        if(!add(object))
            return false;

        Scene temp=object.containing;
        if(temp!=null)
            temp.del(object);
        object.containing=temp;
        reorderPanels();
        return true;
    }
    public boolean get(Game_object object){
        OL_Text_panel panel=findPanel(object);
        if(panel==null)
            return false;
        del(panel);
        list.remove(object);
        if(object.containing!=null)
            object.containing.add(object);
        reorderPanels();
        return true;
    }
    private OL_Text_panel findPanel(Game_object object){
        for(Game_object panel: objs)
            if(((OL_Text_panel)panel).obj==object)
                return (OL_Text_panel)panel;
        return null;
    }


    public static class OL_Text_panel extends Text_panel {
        public Game_object obj;
        protected Object_list list;
        public OL_Text_panel(Object_list list, Game_object obj, int w) {
            super(obj.name, w);
            this.obj=obj;
            this.list=list;
        }

        @Override
        public void onTouch() {
            list.select(this);
        }

        @Override
        public void update() {
            super.update();
            if(text!=obj.name)
                setText(obj.name, w);
        }
    }

    public static class Object_list_TR extends Object_list {
        public Object_list_TR(int w) {
            super(w);
        }

        @Override
        public void update() {
            set(Graphic_system.w-w, -Graphic_system.h);
            super.update();
        }
    }
}
