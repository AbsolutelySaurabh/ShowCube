package com.appsomniac.showbox.model;

public class User {

    private String name;
    private String email;
    private String contactNo;
    private String avatarUrl;
    //private FoodPost posts;

    public User(String name, String email, String contact, String avatarUrl){

        this.name = name;
        this.email = email;
        this.contactNo = contact;
        this.avatarUrl = avatarUrl;
        //this.posts = posts;
    }

    public User(){
        //empty constructor
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getContactNo(){
        return contactNo;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }
//
//    public FoodPost getFoodPosts(){
//        return posts;
//    }

    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }
}
