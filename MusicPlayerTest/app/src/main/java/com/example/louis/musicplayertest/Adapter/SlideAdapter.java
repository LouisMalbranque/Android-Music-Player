package com.example.louis.musicplayertest.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.louis.musicplayertest.Model.Slide;
import com.example.louis.musicplayertest.R;

import java.util.List;

public class SlideAdapter extends BaseAdapter {
    private Context context;
    private List<Slide> lstItem;

    public SlideAdapter(Context context, List<Slide> lstItem) {
        this.context = context;
        this.lstItem = lstItem;
    }

    @Override
    public int getCount() {
        return lstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_slide,null);
        TextView tv=(TextView) view.findViewById(R.id.item_title);
        Slide item=lstItem.get(position);
        tv.setText(item.getTitle());
        return view;
    }
}
