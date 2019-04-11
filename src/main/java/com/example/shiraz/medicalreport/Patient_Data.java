package com.example.shiraz.medicalreport;

/**
 * Created by usman on 31-Mar-18.
 */

public class Patient_Data  {

    String name,email,password;
    int age,sugar;


    public Patient_Data(String name, String email, String password, int age, int sugar) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sugar = sugar;
    }

    public Patient_Data() {

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }
}
