package com.example.rakib.tourmate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.rakib.tourmate.Homepage.HomePage;
import com.example.rakib.tourmate.databinding.ActivityAddTravelEventBinding;
import com.example.rakib.tourmate.databinding.ActivityUpdateTravelEventBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Update_Travel_Event extends AppCompatActivity {

    private ActivityUpdateTravelEventBinding addEventBinding;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    private Calendar calendar;
    private int fromYear,fromMonth,fromDay;
    private int toYear,toMonth,toDay;

//    SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");

    //String fromDate,toDate;


    String budget,eventName,fromDate,toDate,key;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addEventBinding= DataBindingUtil.setContentView(this,R.layout.activity_update__travel__event);
        firebaseAuth=FirebaseAuth.getInstance();
        calendar=Calendar.getInstance(Locale.getDefault());


        budget=getIntent().getStringExtra("Budget");
        eventName=getIntent().getStringExtra("EventName");
        fromDate=getIntent().getStringExtra("FromDate");
        toDate=getIntent().getStringExtra("ToDate");
        key=getIntent().getStringExtra("Key");



        addEventBinding.eventBudget.setText(budget);
        addEventBinding.eventDestination.setText(eventName);



        addEventBinding.eventFromDate.setText(fromDate);

        addEventBinding.eventToDate.setText(toDate);

    }

    //EditText Date
    public void eventFromDate(View view) {
        String[] s=fromDate.split("/");
        DatePickerDialog fromDpd=new DatePickerDialog(this,fromDateListner,Integer.parseInt(s[2]),Integer.parseInt(s[1]),Integer.parseInt(s[0]));
        fromDpd.show();
    }
    DatePickerDialog.OnDateSetListener fromDateListner=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            fromDate=dayOfMonth+"/"+(month+1)+"/"+year;
            addEventBinding.eventFromDate.setText(fromDate);

        }
    };


    public void eventToDate(View view) {
        String[] s=toDate.split("/");
        DatePickerDialog toDpd=new DatePickerDialog(this,toDateListner,Integer.parseInt(s[2]),Integer.parseInt(s[1]),Integer.parseInt(s[0]));
        toDpd.show();
    }

    DatePickerDialog.OnDateSetListener toDateListner=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            toDate=dayOfMonth+"/"+(month+1)+"/"+year;
            addEventBinding.eventToDate.setText(toDate);
        }
    };










    //Button event


    //@RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTravelEvent(View view) {




//        try {
//            Date dateFrom=dateFormatter.parse(fromDate);
//            Date dateTo=dateFormatter.parse(toDate);
//            boolean t=dateFrom.before(dateTo);
//            if (t){
        // Log.d("date",""+dateFrom.compareTo(dateTo));
        String eventDestinationName=addEventBinding.eventDestination.getText().toString();
        String eventBudget=addEventBinding.eventBudget.getText().toString();

        String eventID ;

        if (!eventDestinationName.isEmpty()&&!eventBudget.isEmpty()){
                /*startActivity(new Intent(this, HomePage.class));*/

            String uid=firebaseAuth.getCurrentUser().getUid().toString();
            Log.d("uid",uid);

            mDatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourmate-da70f.firebaseio.com/"+uid).child("Travel Event List");



//                String eventDestinationName=addEventBinding.eventDestination.getText().toString();
            String eventFromDate=addEventBinding.eventFromDate.getText().toString();
            String eventToDate=addEventBinding.eventToDate.getText().toString();
            if(!checkValidDate(eventFromDate,eventToDate)){
                Toast.makeText(Update_Travel_Event.this,"Invalid Date",Toast.LENGTH_LONG).show();
                return;
            }
//                String eventBudget=addEventBinding.eventBudget.getText().toString();

//                if (!eventDestinationName.isEmpty()&&!fromDate.isEmpty()&&!toDate.isEmpty()&&!eventBudget.isEmpty()){



            eventID=key;
            Log.d("eventID",eventID);
            HashMap<String,String> datamap=new HashMap<String, String>();
            datamap.put("Event Destination Name",eventDestinationName);
            datamap.put("From Date",eventFromDate);
            datamap.put("To Date",eventToDate);
            datamap.put("Budget",eventBudget);
            datamap.put("key",eventID);







            mDatabaseReference.child(eventID).setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(Update_Travel_Event.this, HomePage.class));
                        Toast.makeText(Update_Travel_Event.this,"Event Updated",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(Update_Travel_Event.this,"Not Successful",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        else {
            if (eventDestinationName.isEmpty()){
                addEventBinding.eventDestination.setError("This field can't be empty");
            }
                   /* if (eventFromDate.isEmpty()){

                    }
                    if (eventToDate.isEmpty()){

                    }*/
            if (eventBudget.isEmpty()){
                addEventBinding.eventBudget.setError("This field can't be empty");
            }
        }




//            }
//            else {
//                Toast.makeText(this,"Select Actual Date",Toast.LENGTH_SHORT).show();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }








    }

    public boolean checkValidDate(String startDate,String endDate){
        String[] dateStart = startDate.split("/");
        String[] dateEnd = endDate.split("/");
        if (Integer.parseInt(dateStart[2].trim()) > Integer.parseInt(dateEnd[2].trim())){
            return false;
        }else if (Integer.parseInt(dateStart[1].trim()) > Integer.parseInt(dateEnd[1].trim())){
            return false;
        }else if (Integer.parseInt(dateStart[0].trim()) > Integer.parseInt(dateEnd[0].trim())){
            return false;
        }else return true;
    }



}
