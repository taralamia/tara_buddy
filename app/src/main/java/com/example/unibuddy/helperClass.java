package com.example.unibuddy;

public class helperClass {

    String reg_username,reg_password,reg_stud_id;

    public helperClass()
    {

    }

    public helperClass(String reg_username,String reg_password,String reg_stud_id) {
        this.reg_username = reg_username;
        this.reg_password = reg_password;
        this.reg_stud_id = reg_stud_id;
    }

    public String getReg_username() {
        return reg_username;
    }

    public void setReg_username(String reg_username) {
        this.reg_username = reg_username;
    }

    public String getReg_password() {
        return reg_password;
    }

    public void setReg_password(String reg_password) {
        this.reg_password = reg_password;
    }

    public String getReg_stud_id() {
        return reg_stud_id;
    }

    public void setReg_stud_id(String reg_stud_id) {
        this.reg_stud_id = reg_stud_id;
    }
}
