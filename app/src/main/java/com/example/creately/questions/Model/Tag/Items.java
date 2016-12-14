package com.example.creately.questions.Model.Tag;

import java.io.Serializable;

/**
 * Created by rahul on 13/12/16.
 */

public class Items implements Serializable
{
    private String has_synonyms;

    private String is_required;

    private String count;

    private String name;

    private String is_moderator_only;

    public String getHas_synonyms ()
    {
        return has_synonyms;
    }

    public void setHas_synonyms (String has_synonyms)
    {
        this.has_synonyms = has_synonyms;
    }

    public String getIs_required ()
    {
        return is_required;
    }

    public void setIs_required (String is_required)
    {
        this.is_required = is_required;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getIs_moderator_only ()
    {
        return is_moderator_only;
    }

    public void setIs_moderator_only (String is_moderator_only)
    {
        this.is_moderator_only = is_moderator_only;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [has_synonyms = "+has_synonyms+", is_required = "+is_required+", count = "+count+", name = "+name+", is_moderator_only = "+is_moderator_only+"]";
    }
}

