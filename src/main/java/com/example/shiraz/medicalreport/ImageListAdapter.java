package com.example.shiraz.medicalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by usman on 02-Jul-18.
 */

public class ImageListAdapter extends BaseAdapter {


    Context c;
    ArrayList<Link_Suggestion> link_suggestions;
    LayoutInflater inflater;


    public ImageListAdapter(Context c, ArrayList<Link_Suggestion> link_suggestions) {
        this.c = c;
        this.link_suggestions = link_suggestions;
    }


    @Override
    public int getCount() {
        return link_suggestions.size();
    }

    @Override
    public Object getItem(int i) {
        return link_suggestions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

if(inflater==null)
{
    inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

}
    if(view==null)
    {
        view=inflater.inflate(R.layout.model,viewGroup,false);
    }



    Holder h=new Holder(view);
    Picasso.get().load(link_suggestions.get(i).getLink()).into(h.img);




        return view;
    }
}