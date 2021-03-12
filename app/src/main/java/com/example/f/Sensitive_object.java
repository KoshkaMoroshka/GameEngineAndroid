package com.example.f;

public class Sensitive_object extends Game_object {
    public Sensitive_object(int id, int coll_id){
        super(id, coll_id);
        interactive=true;
    }
    public Sensitive_object(int id){
        super(id);
        interactive=true;
    }
    public Sensitive_object(){
        interactive=true;
    }
    public void onTouch(){
        onAction(this, null);
    }

    public boolean disable_sensor=false, block=true, relative_touch =false;
    @Override
    public void update() {
        if(disable)
            return;
        super.update();
        if(disable_sensor)
            return;
        float tx=Graphic_system.tx, ty=Graphic_system.ty;
        if(relative_touch) {
            tx+=Graphic_system.camera.x;
            ty+=+Graphic_system.camera.y;
        }
        if(Graphic_system.touch && tx>=x&&tx<=x+w&&ty>=y&&ty<=y+h)
        {
            onTouch();
            Graphic_system.touch=!block;
        }
    }
}
