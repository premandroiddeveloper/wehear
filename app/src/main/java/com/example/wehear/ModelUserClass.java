package com.example.wehear;

public class ModelUserClass {
    String name;
    String phoneno;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    String userid;

    public ModelUserClass(String name, String phoneno) {
        this.name = name;
        this.phoneno = phoneno;
    }

    public ModelUserClass(String name, String phoneno,String Userid) {
        this.name = name;
        this.phoneno = phoneno;
        this.userid=Userid;
    }
public ModelUserClass(){

}


    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public String getPhoneno() {
        return phoneno;
    }
}
