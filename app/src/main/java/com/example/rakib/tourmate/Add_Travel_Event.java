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
public class Add_Travel_Event extends AppCompatActivity {

    private ActivityAddTravelEventBinding addEventBinding;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    private Calendar calendar;
    private int fromYear,fromMonth,fromDay;
    private int toYear,toMonth,toDay;

//    SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");

    String fromDate,toDate;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel__event);
        addEventBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_travel__event);
        firebaseAuth=FirebaseAuth.getInstance();
        calendar=Calendar.getInstance(Locale.getDefault());




        //from Date
        fromDay=calendar.get(Calendar.DAY_OF_MONTH);
        fromMonth=calendar.get(Calendar.MONTH)+1;
        fromYear=calendar.get(Calendar.YEAR);

        addEventBinding.eventFromDate.setText(""+fromDay+"/"+fromMonth+"/"+fromYear);

        //to date
        toDay=calendar.get(Calendar.DAY_OF_MONTH)+1;
        toMonth=calendar.get(Calendar.MONTH)+1;
        toYear=calendar.get(Calendar.YEAR);

        addEventBinding.eventToDate.setText(""+toDay+"/"+toMonth+"/"+toYear);

    }

    //EditText Date
    public void eventFromDate(View view) {
        DatePickerDialog fromDpd=new DatePickerDialog(this,fromDateListner,fromYear,fromMonth,fromDay);
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
        DatePickerDialog toDpd=new DatePickerDialog(this,toDateListner,toYear,toMonth,toDay);
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
    public void createTravelEvent(View view) {




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
                Toast.makeText(Add_Travel_Event.this,"Invalid Date",Toast.LENGTH_LONG).show();
                return;
            }
//                String eventBudget=addEventBinding.eventBudget.getText().toString();

//                if (!eventDestinationName.isEmpty()&&!fromDate.isEmpty()&&!toDate.isEmpty()&&!eventBudget.isEmpty()){



            eventID=""+eventDestinationName+"_"+calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"_"+calendar.get(Calendar.HOUR)+""+calendar.get(Calendar.MINUTE)+""+calendar.get(Calendar.SECOND);
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

                                startActivity(new Intent(Add_Travel_Event.this, HomePage.class));

                                Toast.makeText(Add_Travel_Event.this,"Event Created",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(Add_Travel_Event.this,"Not Successful",Toast.LENGTH_SHORT).show();
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
