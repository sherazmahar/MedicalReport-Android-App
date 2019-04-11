package com.example.shiraz.medicalreport;

/**
 * Created by usman on 31-Mar-18.
 */

public class Doctor_Data  {


    String name,email,password;

    public Doctor_Data(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Doctor_Data() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
