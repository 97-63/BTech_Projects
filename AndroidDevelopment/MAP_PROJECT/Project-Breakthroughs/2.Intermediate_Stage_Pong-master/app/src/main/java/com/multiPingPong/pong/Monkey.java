package com.multiPingPong.pong;

class Monkey extends Paddle{

    private int length;
    private int velocity;

    Monkey(int x, int y){
        super(x,y);
        velocity = 0;
    }

    void setMonkeyLength(int length){
        this.length = length;
    }

    int getMonkeyLength(){
        return length;
    }

    void setVelocity(int velocity){
        this.velocity = velocity;
    }

    int getVelocity(){
        return velocity;
    }

    void bounce(){
        setVelocity(-1*getVelocity());
    }

}
