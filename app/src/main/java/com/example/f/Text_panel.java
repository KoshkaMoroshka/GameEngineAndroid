package com.example.f;


import android.graphics.Bitmap;

public class Text_panel extends Sensitive_object {
    protected String text;
    public void setText(String text, int w) {
        this.text = text;
        setFilm(createLabel(text, w));
    }
    public Text_panel(String text, int w){
        setText(text, w);
    }



    protected int cur;
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


    public int color=0xFFFFFFFF;
    public void setFormat(String format){
        String s="";

        s+=format.charAt(0);
        if(s.equals("x")) {
            xpos=Integer.valueOf(format.substring(1));
            return;
        }
        if(s.equals("y")) {
            ypos=Integer.valueOf(format.substring(1));
            return;
        }

        s+=format.charAt(1);
        if(s.equals("0x")) {
            color=Label.getColor(format);
            return;
        }
    }


    public int dh=10, dw=4, border=4;
    protected int xpos, ypos, xmax, ymax;


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
        xpos+=w;
    }
    protected void writeNext(boolean measuring_mode){
        char ch=text.charAt(cur++);
        if(ch=='<') {
            int i=text.indexOf('>', cur);
            if(i!=-1)
            {
                int t=cur;
                cur=i+1;
                if(!measuring_mode)
                    setFormat(text.substring(t, i));
                return;
            }
        }
        if(ch==' ') {
            xpos+=dw;
            return;
        }
        if(ch=='\n') {
            xpos=border;
            ypos+=dh;
            return;
        }
        if(ch=='\t'){
            cur=text.length();
            return;
        }

        int len=nextWordLength();
        if(len>xmax)
            color=0xFFFF0000;
        else if(xpos+len>=xmax) {
            xpos=border;
            ypos+=dh;
        }

        Bitmap bmp=Label.symbol(ch);
        if(measuring_mode) {
            xpos+=bmp.getWidth();
            return;
        }

        if(ypos+bmp.getHeight()>=ymax) {
            cur=text.length();
            return;
        }
        write(bmp);
    }


    public Bitmap createLabel(String text, int w){
        xmax=w-border;


        xpos=border;
        ypos=border;
        for(cur=0; cur<text.length();)
            writeNext(true);


        int h=ypos+border+dh;
        result=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
        ymax=h-border;


        xpos=border;
        ypos=border;
        for(cur=0; cur<text.length();)
            writeNext(false);

        return result;
    }


    public Event onTouch;
    @Override
    public boolean onAction(Game_object target, Action act) {
        if(onTouch==null)
            return false;
        onTouch.post();
        return true;
    }
}
