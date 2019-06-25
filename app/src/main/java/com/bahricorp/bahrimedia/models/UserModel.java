package com.bahricorp.bahrimedia.models;

public class UserModel
{
    public String id;
    public String email;
    public String password;
    public String imageURL;


    public UserModel() {}

    public UserModel(String id, String email, String password, String imageURL)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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
