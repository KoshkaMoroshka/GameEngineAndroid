package com.example.f;

public class Dialogue extends Label {
    protected Action action_capture=new Action(Action.Action_type.ACT_TALK, "talk", null, this);
    public Dialogue(int w, int h) {
        super(w, h);
    }


    public Event onFinish;
    Game_object init, target;


    private boolean started=false;
    public void start(Game_object init, Game_object target, String text, Event onFinish){
        finish();
        started=true;

        this.init=init;
        this.target=target;
        this.onFinish=onFinish;


        init.ACTS.push(action_capture);
        Graphic_system.camera.setFocus(init);

        color=0xFFFFFFFF;
        setTextSize(1);
        text+="<fin>";
        setText(text);

        turn(true);
    }
    public void finish(){
        if(!started)
            return;
        finished=true;
        started=false;
        init.ACTS.pop(action_capture);

        Graphic_system.camera.killFocus(init);
        turn(false);

        if(onFinish!=null)
            onFinish.post();
        onFinish=null;

    }

    private Controller_locker locker=new Controller_locker(true, false);
    @Override
    public void turn(boolean active) {
        super.turn(active);
        locker.turn(active);
        //Graphic_system.no_sex.setText(Graphic_system.no_sex.getText()+(active?"+":"-")+"d, ");
    }

    @Override
    public void setFormat(String format) {
        super.setFormat(format);
        int n=format.length();

        if(n<3) return;
        String s=format.substring(0, 3);
        if(s.equals("end"))
        {
            finished=true;
            next();
            return;
        }
        if(s.equals("fin"))
        {
            finished=true;
            return;
        }

        if(n<4) return;
        s+=format.charAt(3);
        if(s.equals("auto")){
            only_auto=true;
            return;
        }

        if(n<5) return;
        s+=format.charAt(4);
        if(s.equals("!auto")){
            only_auto=false;
            return;
        }
    }

    @Override
    public boolean onAction(Game_object target, Action act) {
        if(only_auto && (!waiting && !finished))
            return false;
        next();
        return true;
    }



    @Override
    public void next() {
        if(!finished)
            super.next();
        else finish();
    }
    @Override
    public void update() {
        set(-w/2, Graphic_system.h-h);
        super.update();
    }
}
