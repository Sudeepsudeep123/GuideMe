package com.example.aadmin.guidemethree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TakeTour extends ArrayAdapter <String> {

    private String[] values;
    private Integer[] imgid;
    private Integer[] color;
    private Activity context;
    public TakeTour(Activity context, String[] values, Integer[] imgid, Integer[] color) {
        super(context, R.layout.take_tour_list, values);
        this.context = context;
        this.values = values;
        this.imgid = imgid;
        this.color = color;

    }

        public View getView( int position, View convertView, ViewGroup parent)
        {
            View r = convertView;
            ViewHolder viewHolder = null;
            if (r==null)
            {
                LayoutInflater layoutInflater = context.getLayoutInflater();
                r = layoutInflater.inflate(R.layout.take_tour_list, parent, false);

                r.setBackgroundColor(color[position]);

                viewHolder = new ViewHolder(r);
                r.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) r.getTag();
            }

            if (position==0)
            {
                r.setBackgroundColor(Color.rgb(255,138,103));

            }
            if (position==1)
            {
                r.setBackgroundColor(Color.rgb(75,182,172));

            }
            if (position==2)
            {
                r.setBackgroundColor(Color.rgb(255,183,76));

            }
            if (position==3)
            {
                r.setBackgroundColor(Color.rgb(29,154,174));

            }
            if (position==4)
            {
                r.setBackgroundColor(Color.rgb(108,122,71));

            }
            if (position==5)
            {
                r.setBackgroundColor(Color.rgb(212,235,171));

            }
            if (position==6)
            {
                r.setBackgroundColor(Color.rgb(106,3,4));

            }
            if (position==7)
            {
                r.setBackgroundColor(Color.rgb(78,208,226));

            }
            if (position==8)
            {
                r.setBackgroundColor(Color.rgb(203,44,40));

            }
            if (position==9)
            {
                r.setBackgroundColor(Color.rgb(54,52,53));

            }
            viewHolder.imgView.setImageResource(imgid [position]);
            viewHolder.textView.setText(values [position]);
            return r;
        }

        class ViewHolder
        {
            TextView textView;
            ImageView imgView;

            ViewHolder (View v)
            {
                textView = (TextView) v.findViewById(R.id.txtCategory);
                Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/gill-sans-ultra-bold-condensed.TTF");
                textView.setTypeface(custom_font);
                imgView = (ImageView) v.findViewById (R.id.imgCategory);



            }
        }

    }




