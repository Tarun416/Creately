package com.example.creately.questions.ApiInterface;

import com.example.creately.questions.Model.UnansweredQues.Questions;
import com.example.creately.questions.Model.Tag.Tags;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rahul on 08/12/16.
 */

public interface StackExchange {

    @GET("/questions/unanswered")
    void getAndroidUnansweredQueastion(@Query("sort") String sort,@Query("order") String order,@Query("tagged") String tagged, @Query("site") String site, Callback<Questions> questionsCallback);


    @GET("/tags")
    void getTags( @Query("site") String site, Callback<Tags> questionsCallback);


}
