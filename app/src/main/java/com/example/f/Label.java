package com.example.f;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Label extends Sensitive_object {
    public static String rus_alpha="абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static String rus_ALPHA="АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static String eng_alpha="abcdefghijklmnopqrstuvwxyz";
    public static String eng_ALPHA="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String num_alpha="0123456789!?.,:'-() ";

    public static Bitmap[] rus = loadRusFont();
    public static Bitmap[] eng = loadEngFont();
    public static Bitmap[] num = loadNumFont();


    public static Bitmap[] loadRusFont(){
        rus=Bitmap_modifier.split(load(R.drawable.rus_alpha), 6, 6);
        Bitmap_modifier.cut_space(rus, false, false, true, false);
        return rus;
    }
    public static Bitmap[] loadEngFont(){
        eng=Bitmap_modifier.split(load(R.drawable.eng_alpha), 6, 6);
        Bitmap_modifier.cut_space(eng, false, false, true, false);
        return eng;
    }
    public static Bitmap[] loadNumFont(){
        num=Bitmap_modifier.split(load(R.drawable.num_alpha), 6, 6);
        Bitmap_modifier.cut_space(num, false, false, true, false);
        return num;
    }


    public static Bitmap corners;
    public static Bitmap createTextbox(int w, int h){
        if(corners==null)
            corners=load(R.drawable.textbox_corners);
        if(w<corners.getWidth() || h<corners.getHeight())
            return null;
        return createTextbox(w, h, corners);
    }
    public static Bitmap createTextbox(int w, int h, Bitmap corners){
        Bitmap res=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
        int ww=corners.getWidth()/2, hh=corners.getHeight()/2;

        for(int x=0; x<ww; x++)
            for(int y=0; y<hh; y++)
                res.setPixel(x, y, corners.getPixel(x, y));

        for(int x=ww; x<ww*2; x++)
            for(int y=0; y<hh; y++)
                res.setPixel(w-ww+x-ww, y, corners.getPixel(x, y));

        for(int x=0; x<ww; x++)
            for(int y=hh; y<hh*2; y++)
                res.setPixel(x, h-hh+y-hh, corners.getPixel(x, y));

        for(int x=ww; x<ww*2; x++)
            for(int y=hh; y<hh*2; y++)
                res.setPixel(w-ww+x-ww, h-hh+y-hh, corners.getPixel(x, y));


        for(int x=ww; x<w-ww; x++)
            for(int y=0; y<hh; y++)
                res.setPixel(x, y, corners.getPixel(ww, y));
        for(int x=0; x<w; x++)
            for(int y=hh; y<h-hh; y++)
                res.setPixel(x, y, res.getPixel(x, hh-1));
        for(int x=ww; x<w-ww; x++)
            for(int y=hh; y<hh*2; y++)
                res.setPixel(x, h-hh+y-hh, corners.getPixel(ww, y));

        return res;
    }


    public static Bitmap symbol(char ch){
        int index;
        if((index=rus_alpha.indexOf(ch))!=-1)
            return rus[index];
        if((index=rus_ALPHA.indexOf(ch))!=-1)
            return rus[index];

        if((index=eng_alpha.indexOf(ch))!=-1)
            return eng[index];
        if((index=eng_ALPHA.indexOf(ch))!=-1)
            return eng[index];

        if((index=num_alpha.indexOf(ch))!=-1)
            return num[index];
        return num[num_alpha.indexOf('?')];
    }


    public static int getColor(String src){
        if(src.charAt(1)=='x')
        {
            int res=0;
            for(int i=2; i<src.length(); i++)
            {
                res*=16;
                switch (src.charAt(i))
                {
                    case '0': res+=0; break;    case '1': res+=1; break;
                    case '2': res+=2; break;    case '3': res+=3; break;
                    case '4': res+=4; break;    case '5': res+=5; break;
                    case '6': res+=6; break;    case '7': res+=7; break;

                    case '8': res+=8; break;    case '9': res+=9; break;
                    case 'A': res+=10; break;   case 'B': res+=11; break;
                    case 'C': res+=12; break;   case 'D': res+=13; break;
                    case 'E': res+=14; break;   case 'F': res+=15; break;
                }
            }
            return res;
        }
        return Integer.parseInt(src);
    }
    public void setFormat(String format){
        String s="";

        s+=format.charAt(0);
        if(s.equals("x"))
        {
            xpos=Integer.valueOf(format.substring(1));
            return;
        }
        if(s.equals("y"))
        {
            ypos=Integer.valueOf(format.substring(1));
            return;
        }
        if(s.equals("d"))
        {
            delay=Integer.valueOf(format.substring(1));
            return;
        }

        s+=format.charAt(1);
        if(s.equals("sc"))
        {
            setTextSize(Float.valueOf(format.substring(2)));
            return;
        }
        if(s.equals("0x"))
        {
            color=getColor(format);
            return;
        }

        s+=format.charAt(2);
        if(s.equals("clr"))
        {
            erase();
            waiting=false;
            return;
        }
    }


    public Label(int w, int h){
        COLL.active=false;
        setFilm(Label.createTextbox(w, h));
    }


    protected int cur=0, color=0xFFFFFFFF;
    protected String text="null";
    public void setText(String text){
        this.text=text;
        erase();
        cur=0;
        finished=false;
        waiting=false;
    }


    protected boolean finished=true, waiting=false;
    public void next(){
        if(!waiting)
            skip();
        else
        {
            erase();
            waiting=false;
        }
    }
    public void skip(){
        for(int i=cur; i<text.length(); i++)
            if(!waiting)
                writeNext();
            else break;
        waiting=true;
    }


    protected int nextWordLength(){
        char ch;
        int res=0;
        for(int i=cur-1; i<text.length(); i++)
        {
            ch=text.charAt(i);
            if(ch=='<')
            {
                for(i=i; i<text.length(); i++)
                    if(text.charAt(i)=='>')
                        break;
                continue;
            }
            if(ch==' '||ch=='\n'||ch=='\t')
                break;
            res+=Label.symbol(ch).getWidth();
        }
        return res;
    }


    boolean only_auto=false;
    public int dh=10, dw=4;
    public int delay=0;
    protected Bitmap result;
    protected void write(Bitmap bmp){
        int w=bmp.getWidth();
        int h=bmp.getHeight();
        w=xpos+w>=result.getWidth()?result.getWidth()-xpos:w;
        h=ypos+h>=result.getHeight()?result.getHeight()-ypos:h;
        for(int x=0; x<w; x++)
            for(int y=0; y<h; y++)
                if(bmp.getPixel(x, y)==0xFFFFFFFF)
                    result.setPixel(xpos+x, ypos+y, color);
        xpos+=bmp.getWidth();
    }
    protected void writeNext(){
        if((finished=cur>=text.length()) || waiting || delay-->0)
            return;
        char ch=text.charAt(cur++);
        if(ch=='<') {
            int i=text.indexOf('>', cur);
            if(i!=-1)
            {
                int t=cur;
                cur=i+1;
                setFormat(text.substring(t, i));
                return;
            }
        }
        if(ch==' ') {
            xpos+=dw;
            return;
        }
        if(ch=='\n') {
            xpos=0;
            ypos+=dh;
            return;
        }
        if(ch=='\t'){
            waiting=true;
            return;
        }

        int len=nextWordLength();
        if(len>xmax)
            color=0xFFFF0000;
        else if(xpos+len>=xmax)
        {
            xpos=0;
            ypos+=dh;
        }

        Bitmap bmp=Label.symbol(ch);
        if(ypos+bmp.getHeight()>=ymax) {
            waiting=true;
            cur--;
            return;
        }
        write(bmp);
    }


    protected int xpos=0, ypos=0, xmax, ymax, border=4;
    public void update_box(){
        xmax=film.getWidth();
        ymax=film.getHeight();

        xmax*=textSizeRev*scale;    xmax-=border*2;
        ymax*=textSizeRev*scale;    ymax-=border*2;

        if(xpos>=xmax)
            xpos=0;
        if(ypos>=ymax)
            ypos=0;
    }
    public void erase(){
        update_box();
        xpos=0;
        ypos=0;
        result=Bitmap.createBitmap(xmax+border, ymax+border, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
    }
    @Override
    public void setFilm(Bitmap film0) {
        super.setFilm(film0);
        erase();
    }


    protected float textSize=1, textSizeRev=1;
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textSizeRev=1/textSize;

        update_box();
    }
    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        update_box();
    }


    public int timer=0, rate=2;
    @Override
    public boolean render(Canvas canvas, Paint paint, float x0, float y0) {
        if(hide)
            return false;
        if(timer++%rate==0) writeNext();

        super.render(canvas, paint, x0, y0);
        canvas.scale(textSize, textSize);
        canvas.drawBitmap(result, border+(x-x0)*textSizeRev, border+(y-y0)*textSizeRev, paint);
        canvas.scale(textSizeRev, textSizeRev);
        return true;
    }


    @Override
    public boolean onAction(Game_object target, Action act) {
        if(only_auto && !waiting)
            return false;
        next();
        return true;
    }
}
