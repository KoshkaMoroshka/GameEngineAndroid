package com.example.f;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Camera extends Game_object {
    public Point cur=new Point();
    public Stack<Game_object> focus = new Stack<>();
    public Camera(){
        MD.add(directive);
        MD.add(limiter);
        limiter.active=false;
        layer=-100;
        speed=5;
        name="camera";

    }
    protected Motion_drive.MD_directive directive=new Motion_drive.MD_directive(cur);
    protected Motion_drive.MD_camera_limiter limiter=new Motion_drive.MD_camera_limiter();

    public void setFocus(Game_object target){
        focus.push(target);
        return;
    }
    public boolean killFocus(Game_object target){
        focus.pop(target);
        return true;
    }

    public void instantSetFocus(Game_object target){
        setFocus(target);
        cur.set(target.getCenter());
        set(cur);
    }
    public void instantKillFocus(Game_object target){
        killFocus(target);
        if(focus.peek()==null)
            return;
        cur.set(focus.peek().getCenter());
        set(cur);
    }

    public int timer=0, rate=1;
    public void update() {
        if(disable)
            return;

        Game_object obj=focus.peek();
        if(obj==null)
            return;
        cur.set(obj.getCenter());

        if(Graphic_system.action_button_click)
            obj.ACTS.post();

        if(timer++%rate==0)
            if(obj.containing!=null)
                obj.containing.update();
            else obj.update();

        super.update();
    }

    public int bgColor = 0xFF000000;
    @Override
    public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
        canvas.drawColor(bgColor);
        if(hide)
            return false;
        if(focus.peek()==null)
            return false;
        if(film == null && Graphic_system.w > 0 && Graphic_system.h > 0)
            setFilm(Bitmap_modifier.createFocusMask(Graphic_system.w*2+1, Graphic_system.h*2+1));
        Game_object obj=focus.peek();
        if(obj.containing!=null)
            obj.containing.render(canvas, paint, x0+x, y0+y);
        else obj.render(canvas, paint, x0+x, y0+y);

        super.render(canvas, paint, x+w/2, y+h/2);
        //canvas.drawLine(-Graphic_system.w, 0, Graphic_system.w, 0, paint);
        //canvas.drawLine(0, -Graphic_system.h, 0, Graphic_system.h, paint);
        return true;
    }
}
