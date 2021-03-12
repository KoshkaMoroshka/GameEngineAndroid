package com.example.f;


public class Action_list extends Object_group {

    public boolean autoexit=true;
    protected int width, h=0, border=6;
    public Action_list(int w){
        width=w;
    }

    public void reorderPanels(){
        int h=0;
        for(Game_object obj: objs)
        {
            obj.set(border+x, y+border+h);
            h+=obj.h;
        }
        int ww=width+border*2, hh=h+border*2;
        if(objs.size()==0)
            return;
        if(film==null)
            setFilm(Label.createTextbox(ww, hh));
        else if(film.getWidth()!=ww || film.getHeight()!=hh)
            setFilm(Label.createTextbox(ww, hh));
    }
    @Override
    public void sortObjects() {
        super.sortObjects();
        reorderPanels();
    }

    public AL_Text_panel add(AL_Text_panel panel){
        panel.layer=objs.size();
        super.add(panel);
        reorderPanels();
        return panel;
    }
    public AL_Text_panel createPanel(String title, Event event){
        return new AL_Text_panel(this, title, event, width);
    }
    public AL_Text_panel add(String title, Event event){
        return add(createPanel(title, event));
    }
    public boolean del(String title){
        AL_Text_panel panel=find(title);
        if(panel==null)
            return false;
        return del(panel);
    }

    public AL_Text_panel find(String title){
        for(Game_object obj: objs)
            if(((AL_Text_panel)obj).text.equals(title))
                return (AL_Text_panel)obj;
        return null;
    }

    public void start(){
        start(null);
    }
    public void start(Game_object target){
        this.target=target;
        if(containing==null)
        {
            update();
            Graphic_system.overlay.add(this);
        }
        if(objs.size()>0)
            turn(true);
        else Graphic_system.no_sex.setText("empty list");
    }

    public boolean disable_turning=false;
    private Controller_locker locker=new Controller_locker(true, true);
    @Override
    public void turn(boolean active) {
        if(disable_turning)
            return;
        super.turn(active);
        locker.turn(active);
        //Graphic_system.no_sex.setText(Graphic_system.no_sex.getText()+(active?"+":"-")+"l, ");
    }

    public Game_object target;
    public void select(AL_Text_panel opt){
        if(autoexit)
            turn(false);
        if(target!=null)
            opt.onTouch.target=target;
        opt.onTouch.post();
    }

    public static class AL_Text_panel extends Text_panel {
        protected Action_list list;
        public AL_Text_panel(Action_list list, String title, Event event, int w){
            super(title, w);
            onTouch=event;
            this.list=list;
        }

        public void setText(String text){
            super.setText(text, list.width);
        }

        @Override
        public boolean onAction(Game_object target, Action act) {
            list.select(this);
            return true;
        }
    }
    public static class Action_list_TR extends Action_list {
        public Action_list_TR(int w) {
            super(w);
        }

        @Override
        public void update() {
            set(Graphic_system.w-w, -Graphic_system.h);
            super.update();
        }
    }
}
