package com.example.creately.questions.Model.UnansweredQues;

import java.util.ArrayList;

/**
 * Created by rahul on 08/12/16.
 */

public class Questions
{
    private String quota_max;

    private ArrayList<Items> items;

    private String has_more;

    private String quota_remaining;

    public String getQuota_max ()
    {
        return quota_max;
    }

    public void setQuota_max (String quota_max)
    {
        this.quota_max = quota_max;
    }

    public ArrayList<Items> getItems ()
    {
        return items;
    }

    public void setItems (ArrayList<Items> items)
    {
        this.items = items;
    }

    public String getHas_more ()
    {
        return has_more;
    }

    public void setHas_more (String has_more)
    {
        this.has_more = has_more;
    }

    public String getQuota_remaining ()
    {
        return quota_remaining;
    }

    public void setQuota_remaining (String quota_remaining)
    {
        this.quota_remaining = quota_remaining;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quota_max = "+quota_max+", items = "+items+", has_more = "+has_more+", quota_remaining = "+quota_remaining+"]";
    }
}
