package com.example.f;


import android.graphics.Bitmap;

public abstract class Animation {
    public Game_object obj;

    public Event onFinish;
    public boolean finished=false, active=true;
    public boolean isFinished(){
        return false;
    }
    public abstract void update();
    public void restart(){
        finished=false;
    }
    public void finish(){
        finished=true;
    }


    public static class Animation_stack extends Animation {
        Animation_stack(Game_object obj){
            this.obj=obj;
        }

        protected Stack<Animation> animations=new Stack<>();
        public void push(Animation a){
            animations.push(a);
            a.obj=obj;
            a.restart();
        }
        public void remove(Animation a){
            animations.remove(a);
        }

        @Override
        public void update() {
            finished=animations.elems.size()==0;
            if(finished)
                return;

            Animation a=animations.peek();
            if(a.finished=a.isFinished())
            {
                animations.pop();
                if(a.onFinish!=null)
                    a.onFinish.post();
            }
            else a.update();
        }
    }
    public static class Animation_image extends Animation {
        protected Bitmap bmp;
        public Animation_image(Bitmap bmp){
            this.bmp=bmp;
        }

        @Override
        public void update() {
            obj.setFilm(bmp);
        }
    }
    public static class Animation_film extends Animation {
        public int film_index=0, timer=0, rate=10;
        protected Bitmap[] film;
        public Animation_film(Bitmap[] film) {
            setFilm(film);
        }

        public void setFilm(Bitmap[] film) {
            this.film = film;
            film_index=0;
        }

        @Override
        public void restart() {
            super.restart();
            timer=0;
            film_index=0;
        }

        @Override
        public void update() {
            obj.setFilm(film[film_index=(timer++/rate)%film.length]);
        }
    }
    public static class Animation_LURD extends Animation {
        protected Bitmap[] l, u, r, d;
        public Animation_LURD(Bitmap[] left, Bitmap[] up, Bitmap[] right, Bitmap[] down){
            l=left;
            u=up;
            r=right;
            d=down;
            cur=down;
        }

        public int index=0;
        protected Bitmap[] cur;
        public float timer=0, rate=12;

        public float e=0.2f;
        @Override
        public void update() {
            if(obj.dx<-e) cur=l;
            if(obj.dx>e) cur=r;
            if(obj.dy<-e) cur=u;
            if(obj.dy>e) cur=d;

            index=((int)((timer+=obj.speed)/rate))%cur.length;
            if(obj.dx>-e && obj.dx<e && obj.dy>-e && obj.dy<e)
                index=1;
            obj.setFilm(cur[index]);
        }
    }
    public static class Animation_single extends Animation_film {
        public Animation_single(Bitmap[] film) {
            super(film);
        }

        @Override
        public boolean isFinished() {
            return timer>=rate*film.length;
        }
    }
    public static class Animation_switch extends Animation_film {
        public Animation_switch(Bitmap[] film) {
            super(film);
        }


        @Override
        public void update() {
            if(film_index>=film.length)
            {
                obj.setFilm(film[film.length-1]);
                return;
            }
            obj.setFilm(film[film_index]);
            film_index=timer++/rate;
        }
    }
}
