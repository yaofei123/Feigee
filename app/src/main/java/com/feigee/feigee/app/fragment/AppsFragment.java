package com.feigee.feigee.app.fragment;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.adapter.AppsAdapter;
import com.feigee.feigee.app.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fei.yao on 7/28/15.
 */
public class AppsFragment extends Fragment {
    private GridView mGridView;
    private AppsAdapter appsAdapter;
    private List<ResolveInfo> mList = new ArrayList<ResolveInfo>();

    private final String TAG = "com.feigee.feigee.app.fragment.AppsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        findApps();
        appsAdapter = new AppsAdapter(getActivity(), mList, getActivity().getPackageManager());
        mGridView.setAdapter(appsAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo resolveInfo = mList.get(position);
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                Log.i(TAG, "packageName:" + resolveInfo.activityInfo.packageName + ";activityName:" + resolveInfo.activityInfo.name);
                startActivityForResult(intent, Constant.INTENT_REQUEST);
            }
        });
        return view;
    }

    /**
     * 利用PackageManager 遍历出所有的第三方应用
     */
    private void findApps() {

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> aAllApps = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
        //按包名排序
        Collections.sort(aAllApps, new ResolveInfo.DisplayNameComparator(getActivity().getPackageManager()));

//        遍历list的同时不能remove或者add，否则会出现exception
        for (ResolveInfo resolveInfo : aAllApps) {
//            String pkgName = resolveInfo.activityInfo.packageName;//获取包名
            //根据包名获取PackageInfo mPackageInfo;（需要处理异常）
//            PackageInfo packageInfo;
//            try {
//                packageInfo = getActivity().getPackageManager().getPackageInfo(pkgName, 0);
            if ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                //第三方应用
                mList.add(resolveInfo);
            }
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//                Log.e(TAG, e.getMessage());
//            }
        }

//        List<ApplicationInfo> mAllApps = getActivity().getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//        for (ApplicationInfo applicationInfo : mAllApps) {
//            if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM) <= 0) {
//                apps.add(applicationInfo);
//            }
//        }
    }
}
