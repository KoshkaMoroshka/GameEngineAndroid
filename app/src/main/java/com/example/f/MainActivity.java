package com.example.f;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends Activity {
    public static Graphic_system graphic_sys;
    public static TextView no_sex;

    public static boolean arrows=true, act=true;
    public static ImageButton left, up, right, down, action;
    public static void activateControllers(boolean active){
        activateActionButton(active);
        activateArrows(active);
    }
    public static void activateArrows(boolean active){
        if(arrows==active)
            return;
        int flag=active? View.VISIBLE:View.INVISIBLE;

        MainActivity.left.setVisibility(flag);
        MainActivity.up.setVisibility(flag);
        MainActivity.right.setVisibility(flag);
        MainActivity.down.setVisibility(flag);
        arrows=active;
    }
    public static void activateActionButton(boolean active){
        if(act==active)
            return;
        int flag=active? View.VISIBLE:View.INVISIBLE;
        MainActivity.action.setVisibility(flag);
        act=active;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        action = new ImageButton(this);
        action.setLayoutParams(findViewById(R.id.action_button).getLayoutParams());
        left = new ImageButton(this);
        left.setLayoutParams(findViewById(R.id.left_button).getLayoutParams());
        up = new ImageButton(this);
        up.setLayoutParams(findViewById(R.id.up_button).getLayoutParams());
        right = new ImageButton(this);
        right.setLayoutParams(findViewById(R.id.right_button).getLayoutParams());
        down = new ImageButton(this);
        down.setLayoutParams(findViewById(R.id.down_button).getLayoutParams());


        final View area = findViewById(R.id.main_area);
        graphic_sys=new Graphic_system(this);
        area.post(new Runnable() {
            @Override
            public void run() {
                Graphic_system.width =area.getWidth();
                Graphic_system.height =area.getHeight();
            }
        });
        ((ViewGroup)findViewById(R.id.main_area)).addView(graphic_sys);
        graphic_sys.setOnTouchListener(main_listener);


        no_sex=new TextView(this);
        no_sex.setLayoutParams(findViewById(R.id.t).getLayoutParams());
        no_sex.setText("");
        no_sex.setTextColor(Color.rgb(255,255,255));
        no_sex.setGravity(Gravity.CENTER);
        no_sex.setTextSize(20f);
        ((ViewGroup)findViewById(R.id.main_area)).addView(no_sex);
        graphic_sys.no_sex=no_sex;


        action.setAlpha(0.4f);
        action.setImageResource(R.drawable.action_up);
        action.setOnTouchListener(action_listener);
        ((ViewGroup)findViewById(R.id.main_area)).addView(action);
        action.setScaleType(ImageView.ScaleType.CENTER_CROP);
        action.setBackgroundColor(0x00000000);

        left.setAlpha(0.4f);
        left.setImageResource(R.drawable.left_up);
        left.setOnTouchListener(left_listener);
        ((ViewGroup)findViewById(R.id.main_area)).addView(left);
        left.setScaleType(ImageView.ScaleType.CENTER_CROP);
        left.setBackgroundColor(0x00000000);

        up.setAlpha(0.4f);
        up.setImageResource(R.drawable.up_up);
        up.setOnTouchListener(up_listener);
        ((ViewGroup)findViewById(R.id.main_area)).addView(up);
        up.setScaleType(ImageView.ScaleType.CENTER_CROP);
        up.setBackgroundColor(0x00000000);

        right.setAlpha(0.4f);
        right.setImageResource(R.drawable.right_up);
        right.setOnTouchListener(right_listener);
        ((ViewGroup)findViewById(R.id.main_area)).addView(right);
        right.setScaleType(ImageView.ScaleType.CENTER_CROP);
        right.setBackgroundColor(0x00000000);

        down.setAlpha(0.4f);
        down.setImageResource(R.drawable.down_up);
        down.setOnTouchListener(down_listener);
        ((ViewGroup)findViewById(R.id.main_area)).addView(down);
        down.setScaleType(ImageView.ScaleType.CENTER_CROP);
        down.setBackgroundColor(0x00000000);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    protected void onStart() {
        super.onStart();
        return;
    }
    @Override
    protected void onDestroy() {
        //graphic_sys.player.save("save.txt", graphic_sys.player.x, graphic_sys.player.y);
        super.onDestroy();
    }

    public View.OnTouchListener main_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Graphic_system.tx=graphic_sys.x+(event.getX()-graphic_sys.getX()-graphic_sys.width /2)/graphic_sys.scale;
                    Graphic_system.ty=graphic_sys.y+(event.getY()-graphic_sys.getY()-graphic_sys.height /2)/graphic_sys.scale;
                    Graphic_system.touch=true;
                    break;
                case MotionEvent.ACTION_UP:
                    Graphic_system.tx=graphic_sys.x+(event.getX()-graphic_sys.getX()-graphic_sys.width /2)/graphic_sys.scale;
                    Graphic_system.ty=graphic_sys.y+(event.getY()-graphic_sys.getY()-graphic_sys.height /2)/graphic_sys.scale;
                    Graphic_system.touch=false;
                    break;
            }
            return false;
        }
    };

    public View.OnTouchListener left_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Graphic_system.dx=-1;
                    left.setImageResource(R.drawable.left_down);
                    break;
                case MotionEvent.ACTION_UP:
                    Graphic_system.dx=0;
                    left.setImageResource(R.drawable.left_up);
                    break;
            }
            return false;
        }
    };
    public View.OnTouchListener up_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Graphic_system.dy=-1;
                    up.setImageResource(R.drawable.up_down);
                    break;
                case MotionEvent.ACTION_UP:
                    Graphic_system.dy=0;
                    up.setImageResource(R.drawable.up_up);
                    break;
            }
            return false;
        }
    };
    public View.OnTouchListener right_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Graphic_system.dx=1;
                    right.setImageResource(R.drawable.right_down);
                    break;
                case MotionEvent.ACTION_UP:
                    Graphic_system.dx=0;
                    right.setImageResource(R.drawable.right_up);
                    break;
            }
            return false;
        }
    };
    public View.OnTouchListener down_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Graphic_system.dy=1;
                    down.setImageResource(R.drawable.down_down);
                    break;
                case MotionEvent.ACTION_UP:
                    Graphic_system.dy=0;
                    down.setImageResource(R.drawable.down_up);
                    break;
            }
            return false;
        }
    };
    public View.OnTouchListener action_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    graphic_sys.act=1;
                    action.setImageResource(R.drawable.action_down);
                    break;
                case MotionEvent.ACTION_UP:
                    graphic_sys.act=0;
                    action.setImageResource(R.drawable.action_up);
                    break;
            }
            return false;
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
