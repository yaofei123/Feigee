package com.feigee.feigee.app.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.entity.News;

import java.util.ArrayList;

/**
 * Created by fei.yao on 7/28/15.
 */
public class NewsAdapter implements ListAdapter {

    private ArrayList<News> mList;
    private LayoutInflater inflater;

    public NewsAdapter(ArrayList List,Context context) {
        mList = List;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_news,null);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title_text);
            viewHolder.mAuthor = (TextView) convertView.findViewById(R.id.author_text);
            viewHolder.mTheme = (TextView) convertView.findViewById(R.id.theme_text);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.date_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        News news = mList.get(position);
        viewHolder.mTitle.setText(news.getTitle());
        viewHolder.mAuthor.setText(news.getAuthor());
        viewHolder.mTheme.setText(news.getTheme());
        viewHolder.mDate.setText(news.getDate());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public class ViewHolder{
        TextView mTitle,mAuthor,mTheme,mDate;
    }
}
