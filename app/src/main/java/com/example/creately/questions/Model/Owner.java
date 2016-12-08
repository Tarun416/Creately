package com.example.creately.questions.Model;

/**
 * Created by rahul on 08/12/16.
 */

public class Owner
{
    private String display_name;

    private String user_type;

    private String profile_image;

    private String link;

    private String reputation;

    private String user_id;

    public String getDisplay_name ()
    {
        return display_name;
    }

    public void setDisplay_name (String display_name)
    {
        this.display_name = display_name;
    }

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getProfile_image ()
    {
        return profile_image;
    }

    public void setProfile_image (String profile_image)
    {
        this.profile_image = profile_image;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getReputation ()
    {
        return reputation;
    }

    public void setReputation (String reputation)
    {
        this.reputation = reputation;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [display_name = "+display_name+", user_type = "+user_type+", profile_image = "+profile_image+", link = "+link+", reputation = "+reputation+", user_id = "+user_id+"]";
    }
}