package com.example.f;

public class Controller_locker {
    public boolean lock_arrows=true, lock_act=true;
    public Controller_locker(boolean lock_arrows, boolean lock_act){
        this.lock_arrows=lock_arrows;
        this.lock_act=lock_act;
    }

    private boolean active=false;
    private boolean arrows_prev_state=true, act_prev_state=true;
    public void turn(boolean active){
        if(this.active==active)
            return;
        this.active=active;
        if(active)
        {
            if(lock_arrows) arrows_prev_state=Graphic_system.activateArrows(false);
            if(lock_act) act_prev_state=Graphic_system.activateActionButton(false);
        }
        else
        {
            if(lock_arrows) Graphic_system.activateArrows(arrows_prev_state);
            if(lock_act) Graphic_system.activateActionButton(act_prev_state);
        }
    }
}
