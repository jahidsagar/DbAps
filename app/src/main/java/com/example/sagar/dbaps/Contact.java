package com.example.sagar.dbaps;

public class Contact {
    int id;
    String name , phone;


    Contact(int id , String name , String phone){
       this.id = id;
       this.name = name;
       this.phone = phone;
    }

    Contact(String name , String phone){
        this.name = name;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }
}
