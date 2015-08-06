package com.feigee.feigee.app.util;

import com.feigee.feigee.app.fragment.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fei.yao on 7/28/15.
 */
public class Constant {
    public static void addData(ArrayList mList){
        mList.add("news");
        mList.add("启动");
        mList.add("待办");
        mList.add("setting");
    }

    public  static HashMap<String,Class> fragmentMap = new HashMap();
    static {
        fragmentMap.put("news", NewsFragment.class);
        fragmentMap.put("启动", AppsFragment.class);
        fragmentMap.put("待办", TodoFragment.class);
        fragmentMap.put("setting", ContactsFragment.class);
        fragmentMap.put("todoDetail", TodoDetailFragment.class);
    };

    public final static int DATABASE_OPERATION_OK = 200;
    public final static int INTENT_REQUEST = 1;
    public final static int DATABASE_OPERATION_FAILURE = 100;
}
