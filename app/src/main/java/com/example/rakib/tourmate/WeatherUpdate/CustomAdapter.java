package com.example.rakib.tourmate.WeatherUpdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rakib.tourmate.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Masum on 19-May-17.
 */

public class CustomAdapter extends BaseAdapter {
    Context ctx1;
    List<WeatherData> listData;

    public CustomAdapter(Context ctx1, List<WeatherData> listData) {
        this.ctx1 = ctx1;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(ctx1);
        convertView=inflater.inflate(R.layout.list_weatheritem, parent, false);

        ImageView imageView=(ImageView) convertView.findViewById(R.id.weatherImage);

        TextView textView1=(TextView) convertView.findViewById(R.id.date);
        TextView textView2=(TextView) convertView.findViewById(R.id.max_temp);
        TextView textView3=(TextView) convertView.findViewById(R.id.min_temp);


        long sec = Long.parseLong(listData.get(position).getDt());
        Date date = new Date(sec*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String newDate = dateFormat.format(date);

        textView1.setText(newDate);
        textView2.setText("Max : "+listData.get(position).getMax()+ "C");
        textView3.setText("Min : "+listData.get(position).getMin()+ "C");

        String url = "http://openweathermap.org/img/w/"+listData.get(position).getIcon()+".png";
        Glide.with(ctx1).load(url).into(imageView);

        return convertView;
    }
}
