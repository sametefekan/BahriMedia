package com.bahricorp.bahrimedia.models;

public class UserModel
{
    public String uid;
    public String username;
    public String email;
    public String password;
    public String imageURL;


    public UserModel() {}

    public UserModel(String username, String email, String password, String imageURL, String uid)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
        this.uid = uid;
    }

    public String getId()
    {
        return uid;
    }

    public void setId(String id)
    {
        this.uid = uid;
    }

    public String getName()
    {
        return username;
    }

    public void setName(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }
}
