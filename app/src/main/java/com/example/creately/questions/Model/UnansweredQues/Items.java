package com.example.creately.questions.Model.UnansweredQues;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rahul on 08/12/16.
 */
public class Items implements Serializable
{
    private String creation_date;

    private ArrayList<String> tags;

    private String title;

    private String link;

    private String score;

    private String answer_count;

    private Owner owner;

    private String last_activity_date;

    private String question_id;

    private String view_count;

    private String is_answered;

    public String getCreation_date ()
    {
        return creation_date;
    }

    public void setCreation_date (String creation_date)
    {
        this.creation_date = creation_date;
    }

    public ArrayList<String> getTags ()
    {
        return tags;
    }

    public void setTags (ArrayList<String> tags)
    {
        this.tags = tags;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    public String getAnswer_count ()
    {
        return answer_count;
    }

    public void setAnswer_count (String answer_count)
    {
        this.answer_count = answer_count;
    }

    public Owner getOwner ()
    {
        return owner;
    }

    public void setOwner (Owner owner)
    {
        this.owner = owner;
    }

    public String getLast_activity_date ()
    {
        return last_activity_date;
    }

    public void setLast_activity_date (String last_activity_date)
    {
        this.last_activity_date = last_activity_date;
    }

    public String getQuestion_id ()
    {
        return question_id;
    }

    public void setQuestion_id (String question_id)
    {
        this.question_id = question_id;
    }

    public String getView_count ()
    {
        return view_count;
    }

    public void setView_count (String view_count)
    {
        this.view_count = view_count;
    }

    public String getIs_answered ()
    {
        return is_answered;
    }

    public void setIs_answered (String is_answered)
    {
        this.is_answered = is_answered;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [creation_date = "+creation_date+", tags = "+tags+", title = "+title+", link = "+link+", score = "+score+", answer_count = "+answer_count+", owner = "+owner+", last_activity_date = "+last_activity_date+", question_id = "+question_id+", view_count = "+view_count+", is_answered = "+is_answered+"]";
    }
}