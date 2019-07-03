package com.bahricorp.bahrimedia.models;

public class BlogPost extends BlogPostId
{
    public String name;
    public String email;
    public String title;
    public String image;
    public String image2;
    public String image3;
    public String desc;
    public String userId;
    public String price;
    public String category;

    public BlogPost() {}

    public BlogPost(String name, String email, String title, String desc, String userId, String price, String category)
    {
        this.name = name;
        this.email = email;
        this.title = title;
        this.desc = desc;
        this.userId = userId;
        this.price = price;
        this.category = category;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getImage2()
    {
        return image2;
    }

    public void setImage2(String image2)
    {
        this.image2 = image2;
    }

    public String getImage3()
    {
        return image3;
    }

    public void setImage3(String image3)
    {
        this.image3 = image3;
    }

    public String getUid()
    {
        return userId;
    }

    public void setUid(String userId)
    {
        this.userId = userId;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
