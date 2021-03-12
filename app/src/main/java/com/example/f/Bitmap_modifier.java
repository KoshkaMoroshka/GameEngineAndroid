package com.example.f;

import android.graphics.Bitmap;

public class Bitmap_modifier {
    public static int add(int c1, int c2){
        int a, r, g, b;
        b=c1&0xFF + c2&0xFF;    b=b>0xFF?0xFF:b;
        c1=c1>>8;   c2=c2>>8;
        g=c1&0xFF + c2&0xFF;    g=g>0xFF?0xFF:g;
        c1=c1>>8;   c2=c2>>8;
        r=c1&0xFF + c2&0xFF;    r=r>0xFF?0xFF:r;
        c1=c1>>8;   c2=c2>>8;
        a=c1&0xFF + c2&0xFF;    a=a>0xFF?0xFF:a;
        return a+r+g+b;
    }

    public static Bitmap[] split(Bitmap bmp, int w0, int h0){
        int cx=(bmp.getWidth()-1)/(1+w0);
        int cy=(bmp.getHeight()-1)/(1+h0);

        Bitmap[] res=new Bitmap[cx*cy];
        for(int i=0; i<res.length; i++)
            res[i]=Bitmap.createBitmap(w0, h0, Bitmap.Config.ARGB_8888);

        int x0, y0;
        for(int i=0; i<res.length; i++){
            x0=1+(1+w0)*(i%cx);
            y0=1+(1+h0)*(i/cx);
            for(int x=0; x<w0; x++)
                for(int y=0; y<h0; y++)
                    res[i].setPixel(x, y, bmp.getPixel(x0+x, y0+y));
        }
        return res;
    }
    public static Bitmap cut_space(Bitmap bmp, boolean l, boolean u, boolean r, boolean d){
        int x1=0, y1=0, x2=bmp.getWidth(), y2=bmp.getHeight();
        boolean s;

        if(l){
            s=false;
            for(x1=x1; x1<x2 && !s; x1++)
                for(int y=y1; y<y2; y++)
                    if(s=(bmp.getPixel(x1, y)!=0xFF000000))
                        break;
            x1--;
        }
        if(u){
            s=false;
            for(y1=y1; y1<y2 && !s; y1++)
                for(int x=x1; x<x2; x++)
                    if(s=(bmp.getPixel(x, y1)!=0xFF000000))
                        break;
            y1--;
        }
        if(r){
            s=false;
            for(x2=x2; x2>x1 && !s; x2--)
                for(int y=y1; y<y2; y++)
                    if(s=(bmp.getPixel(x2-1, y)!=0xFF000000))
                        break;
            x2++;
        }
        if(d){
            s=false;
            for(y2=y2; y2>y1 && !s; y2--)
                for(int x=x1; x<x2; x++)
                    if(s=(bmp.getPixel(x, y2-1)!=0xFF000000))
                        break;
            y2++;
        }

        Bitmap res=Bitmap.createBitmap(x2-x1, y2-y1, Bitmap.Config.ARGB_8888);
        for(int x=0; x<x2-x1; x++)
            for(int y=0; y<y2-y1; y++)
                res.setPixel(x, y, bmp.getPixel(x1+x, y1+y));
        return res;
    }
    public static void cut_space(Bitmap[] bmps, boolean l, boolean u, boolean r, boolean d){
        for(int i=0; i<bmps.length; i++)
            bmps[i]=cut_space(bmps[i], l, u, r, d);
    }


    public static Bitmap alphaGradient(Bitmap src){
        int w=src.getWidth(), h=src.getHeight();
        Bitmap res=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);


        for(int x=0; x<w; x++)
            for(int y=0; y<h; y++)
                res.setPixel(x, y, src.getPixel(x, y)&0x00FFFFFF | (int)(255.0f*y/h)<<24);
        return res;
    }

    public static Bitmap addLayer(Bitmap bmp, int color){
        Bitmap res=Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);

        for(int x=0; x<res.getWidth(); x++)
            for(int y=0; y<res.getHeight(); y++)
                res.setPixel(x, y, add(color, bmp.getPixel(x, y)));
        return res;
    }

    public static Bitmap createFocusMask(int w, int h){
        Bitmap res = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);

        int color = 0xFF000000;
        for(int xx=0; xx<w; xx++)
            for(int yy=0; yy<h; yy++)
                res.setPixel(xx, yy, color);

        int range=10;
        int x0=w/2, y0=h/2, r=w-x0, dr=r/range;
        for(int i=0; i<range; i++)
        {
            color=(255*(range-i)/range)<<24;
            for(int xx=x0-r; xx<x0+r; xx++)
                for(int yy=y0-r; yy<y0+r; yy++)
                {
                    if(xx<0 || xx>=w || yy<0 || yy>=h)
                        continue;
                    if((xx-x0)*(xx-x0)+(yy-y0)*(yy-y0)>r*r)
                        continue;
                    res.setPixel(xx, yy, color);
                }
            r-=dr;
        }
        return res;
    }
}
