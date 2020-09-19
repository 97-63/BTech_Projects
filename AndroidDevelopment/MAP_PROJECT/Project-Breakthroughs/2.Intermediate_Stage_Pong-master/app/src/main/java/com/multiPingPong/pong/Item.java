package com.multiPingPong.pong;

public class Item
{
    String upname,upemail,uppass;
    Item()
    {

    }

    public Item(String upname,String upemail,String uppass) {
        this.upname = upname;
        this.upemail = upemail;
        this.uppass = uppass;
    }

    public String getUpname() {
        return upname;
    }

    public String getUpemail() {
        return upemail;
    }

    public String getUppass() {
        return uppass;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public void setUpemail(String upemail) {
        this.upemail = upemail;
    }

    public void setUppass(String uppass) {
        this.uppass = uppass;
    }
}
