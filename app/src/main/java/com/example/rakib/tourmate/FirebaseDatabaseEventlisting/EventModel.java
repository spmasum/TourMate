package com.example.rakib.tourmate.FirebaseDatabaseEventlisting;

import java.util.ArrayList;

/**
 * Created by Masum on 19-May-17.
 */

public class EventModel {
    public String eventPlacesName,customFromDate,customToDate,customBudget,key;


    public EventModel(){

    }

//String eventPlacesName, String customFromDate, String customToDate, String customBudget, String key

    public EventModel(ArrayList<String> a) {
        this.eventPlacesName = a.get(1);
        this.customFromDate = a.get(2);
        this.customToDate = a.get(3);
        this.customBudget = a.get(0);
        this.key = a.get(4);
    }


    //    public EventModel(String eventPlacesName, String customFromDate, String customToDate, String customBudget) {
//        this.eventPlacesName = eventPlacesName;
//        this.customFromDate = customFromDate;
//        this.customToDate = customToDate;
//        this.customBudget = customBudget;
//    }
}
