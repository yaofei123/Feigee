package com.feigee.feigee.app.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.feigee.feigee.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei.yao on 8/5/15.
 */
public class AppsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ResolveInfo> mList = new ArrayList<ResolveInfo>();
    private PackageManager packageManager;

    public AppsAdapter(Context mContext, List<ResolveInfo> list, PackageManager pm) {
        inflater = LayoutInflater.from(mContext);
        mList = list;
        packageManager = pm;
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
            convertView = inflater.inflate(R.layout.item_apps, null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView_icon);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ResolveInfo resolveInfo = mList.get(position);
        viewHolder.mImageView.setImageDrawable(resolveInfo.loadIcon(packageManager));
        viewHolder.mTextView.setText(resolveInfo.loadLabel(packageManager).toString());

        return convertView;
    }

    public class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
