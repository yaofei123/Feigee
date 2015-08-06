package com.feigee.feigee.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.adapter.NewsAdapter;
import com.feigee.feigee.app.entity.News;

import java.util.ArrayList;

/**
 * Created by fei.yao on 7/28/15.
 */
public class NewsFragment extends Fragment {
    private ListView mContextList;
    private ArrayList<News> sNewsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        mContextList = (ListView) view.findViewById(R.id.context_list);
        sNewsList = News.get(getActivity()).getNewsList();
        mContextList.setAdapter(new NewsAdapter(sNewsList,getActivity()));
        mContextList.setOnItemClickListener(onItemClickListener);
        return view;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
}
