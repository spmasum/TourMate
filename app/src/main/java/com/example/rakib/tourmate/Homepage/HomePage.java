package com.example.rakib.tourmate.Homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rakib.tourmate.FirebaseDatabaseEventlisting.EventAdapter;
import com.example.rakib.tourmate.FirebaseDatabaseEventlisting.EventModel;
import com.example.rakib.tourmate.MainPage.MainActivity;
import com.example.rakib.tourmate.R;
import com.example.rakib.tourmate.Add_Travel_Event;
import com.example.rakib.tourmate.Update_Travel_Event;
import com.example.rakib.tourmate.WeatherUpdate.Weather;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.rakib.tourmate.R.id.logout;

public class HomePage extends AppCompatActivity {
//    ListView travelListLV;
//    DatabaseReference mDatabaseReference;
//    FirebaseAuth firebaseAuth;


    private RecyclerView recyclerView;
    private ArrayList<EventModel> result;
    private EventAdapter adapter;
    private FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
//    private ArrayList<String> mkeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        setTitle("Travel Event List");

        firebaseAuth=FirebaseAuth.getInstance();
        String uid=firebaseAuth.getCurrentUser().getUid().toString();
        database=FirebaseDatabase.getInstance();
        reference=database.getReferenceFromUrl("https://tourmate-da70f.firebaseio.com/"+uid).child("Travel Event List");

        recyclerView= (RecyclerView) findViewById(R.id.travelList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lin=new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(lin);
        result=new ArrayList<>();
        init();
        adapter=new EventAdapter(result);
        recyclerView.setAdapter(adapter);
        updateList();



    }

    //menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.traveleventmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                break;
            case R.id.Home:
                startActivity(new Intent(HomePage.this,LoginVerifiedPage.class));
                break;
            case R.id.Weather:
                startActivity(new Intent(HomePage.this, Weather.class));
                break;


        }



        return super.onOptionsItemSelected(item);
    }

    ArrayList<String> eventList=new ArrayList<>();
     public void init() {
        // result.clear();
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
               // Toast.makeText(HomePage.this,""+dataSnapshot.getChildrenCount(),Toast.LENGTH_SHORT).show();
                    eventList.clear();

                    for(DataSnapshot dsp1:dsp.getChildren()){

                            eventList.add(String.valueOf(dsp1.getValue()));

                    }
                   // Toast.makeText(HomePage.this,arr[0]+arr[1]+arr[2]+arr[3],Toast.LENGTH_LONG).show();
                    EventModel e=new EventModel(eventList);
                    result.add(e);
                   // Toast.makeText(HomePage.this, ""+result.size(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    EventModel event;
    void updateEvent(int postion){

         event=result.get(postion);


    }
    void deleteEvent(int position){
        reference.child(result.get(position).key).removeValue();
        result.remove(position);
        adapter=new EventAdapter(result);

        //onRestart();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId()==0){
                updateEvent(item.getGroupId());
            Intent t=new Intent(this, Update_Travel_Event.class);
            t.putExtra("Budget",event.customBudget);
            t.putExtra("EventName",event.eventPlacesName);
            t.putExtra("FromDate",event.customFromDate);
            t.putExtra("ToDate",event.customToDate);
            t.putExtra("Key",event.key);
            startActivity(t);

        }else if(item.getItemId()==1){
                deleteEvent(item.getGroupId());
        }


        return super.onContextItemSelected(item);
    }

//    private void createResult(){
//        for (int i=0;i<10;i++){
//            result.add(new EventModel("eventPlacesName","customFromDate","customToDate","customBudget"));
//        }
//
//    }


    private void updateList(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(EventModel.class));
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                EventModel model=dataSnapshot.getValue(EventModel.class);
                int index=getItemIndex(model);
                result.set(index,model);
                adapter.notifyItemChanged(index);

/*//
                String changedKey=dataSnapshot.getKey();
                Log.d("ckey",""+changedKey);
                int index=mkeys.indexOf(changedKey);
                Log.d("index",""+index);

//                int index=getItemIndex(model);
                result.set(index,model);
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


                EventModel model=dataSnapshot.getValue(EventModel.class);
                int index=getItemIndex(model);
                result.remove(index);
                adapter.notifyItemRemoved(index);

  /*              EventModel model=dataSnapshot.getValue(EventModel.class);
                String changedKey=dataSnapshot.getKey();
                int index=mkeys.indexOf(changedKey);
                result.remove(index);
                adapter.notifyItemRemoved(index);*/
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(EventModel event){
        int index=-1;
        for (int i=0;i<result.size();i++){
            if (result.get(i).key.equals(event.key)){
                index=1;
                break;
            }
        }
        return  index;
    }

    public void addTravelEvent(View view) {
        startActivity(new Intent(this, Add_Travel_Event.class));

    }


}
