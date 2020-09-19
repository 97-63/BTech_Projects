package com.multiPingPong.pong;

class Ball {
    private int xPos;
    private int yPos;
    private static int size;
    private double xVel;
    private double yVel;
    private static final int NUM_SHADOWS = 10;
    public static final int NUM_BOUNCES_TIL_TWO_BALL = 5;
    private boolean isVisible;

    Ball(int x, int y){
        this.xPos = x;
        this.yPos = y;
        xVel = 0;
        yVel = 0;
        isVisible = false;
    }

    static void setSize(int size){
        Ball.size = size;
    }

    static int getSize(){
        return size;
    }

    int getX(){
        return xPos;
    }

    void addX(double x) {
        xPos += x;
    }

    void setX(int x){
        xPos = x;
    }

    int getY(){
        return yPos;
    }

    void addY(double y){
        yPos += y;
    }

    void setY(int y){
        yPos = y;
    }

    double getXVel(){
        return xVel;
    }

    void setXVel(double xVel){
        this.xVel = xVel;
    }

    double getYVel(){
        return yVel;
    }

    void setYVel(double yVel){
        this.yVel = yVel;
    }

    boolean hitSide(int left, int right){
        boolean hit = getX()+Ball.getSize() > right || getX() < left;
        if(hit) {
            if(getX() < left){
                setX(left);
            } else{
                setX(right-Ball.getSize());
            }
            setXVel(getXVel() * -1);
        }
        return hit;
    }

    boolean hitTop(int top1, int top2, double left, double right){
        return (getY() <= top1 && getY() >= top2 && getX() + Ball.getSize() >= left && getX() <= right);
    }

    boolean hitBot(int bot1, int bot2, double left, double right){
        return (getY()+Ball.getSize() >= bot1 && getY()+Ball.getSize() <= bot2 && getX()+Ball.getSize() >= left && getX() <= right);
    }

    void setVisible(boolean visible){
        isVisible = visible;
    }

    boolean isVisible(){
        return isVisible;
    }

}
