package com.example.f;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

public class NPC_Vigour extends NPC {
    public NPC_Vigour() {
        super("vigour");
        speed=4;
        COLL.active=false;


        Bitmap[] a=Bitmap_modifier.split(load(R.drawable.vigour_lurd_102), 48, 63);
        Bitmap[] l={a[0], a[1], a[2], a[1]};
        Bitmap[] u={a[3], a[4], a[5], a[4]};
        Bitmap[] r={a[6], a[7], a[8], a[7]};
        Bitmap[] d={a[9], a[10], a[11], a[10]};
        ANIM.push(a2_lurd = new Animation.Animation_LURD(l, u, r, d));
        controllers.active=false;
        MD.add(controllers);
    }
    Animation.Animation_LURD a2_lurd;
    Motion_drive.MD_controllers controllers=new Motion_drive.MD_controllers();
    MediaPlayer mp=MediaPlayer.create(context, R.raw.ocean_man);


    private boolean funny=false;
    protected Event e=new Event(null){
        @Override
        public boolean post() {
            funny=!funny;
            if(funny)
            {
                Graphic_system.camera.setFocus(NPC_Vigour.this);
                mp.start();
            }
            else
            {
                Graphic_system.camera.killFocus(NPC_Vigour.this);
                mp.pause();
            }
            controllers.active=funny;
            Graphic_system.player.MD.active=!funny;
            return super.post();
        }
    };
    @Override
    public boolean onAction(Game_object target, Action act) {
        if(!funny)
            Graphic_system.dialogue.start(this, target, "<0xFFFF8080>мама сказала моя очередь играться!", e);
        else Graphic_system.dialogue.start(this, target, "<0xFFFF0000><sc2>nadoelo", e);
        return true;
    }
}
