package com.feigee.feigee.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.entity.TodoList;

import java.util.List;

/**
 * Created by fei.yao on 7/29/15.
 */
public class TodoAdapter extends BaseAdapter {
    private List<TodoList> mList;
    private LayoutInflater inflater;

    public TodoAdapter(Context c, List list) {
        inflater = LayoutInflater.from(c);
        mList = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_todo,null);
            viewHolder.mContext = (TextView)convertView.findViewById(R.id.textView_context);
            viewHolder.mDeadline = (TextView)convertView.findViewById(R.id.textView_deadline);
            convertView.setTag(viewHolder);
        }else{
           viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mContext.setText(mList.get(position).getContext());
        viewHolder.mDeadline.setText(mList.get(position).getDeadline());
        return convertView;
    }

    private class ViewHolder {
        TextView mContext,mDeadline;
    }
}
