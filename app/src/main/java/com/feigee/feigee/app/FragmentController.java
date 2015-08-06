package com.feigee.feigee.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.feigee.feigee.app.fragment.*;

/**
 * Created by fei.yao on 7/28/15.
 */
public class FragmentController {
    private static final int REQUEST_DATE = 0;

    public static void fragmentController(Class classes, FragmentManager fm) {
        Fragment fragment = null;
        Bundle mBundle = new Bundle();
        if (NewsFragment.class.getName().equals(classes.getName())) {
            fragment = new NewsFragment();
        } else if (AppsFragment.class.getName().equals(classes.getName())) {
            fragment = new AppsFragment();
        } else if (TodoFragment.class.getName().equals(classes.getName())) {
            fragment = new TodoFragment();
        } else if (ContactsFragment.class.getName().equals(classes.getName())) {
            fragment = new ContactsFragment();
//            ContactsFragment dialogFragment = new ContactsFragment();
//            dialogFragment.show(fm, "datePiker");
        } else if (TodoDetailFragment.class.getName().equals(classes.getName())) {
//            TodoDetailFragment todoDetailFragment = new TodoDetailFragment();
//            todoDetailFragment.setTargetFragment(OthersFragment.this,REQUEST_DATE);
//            todoDetailFragment.show(fm,"todoDetail");
            return;
        }
        fragment.setArguments(mBundle);
        fm.beginTransaction().replace(R.id.fragment_contains, fragment).commit();
    }
}
