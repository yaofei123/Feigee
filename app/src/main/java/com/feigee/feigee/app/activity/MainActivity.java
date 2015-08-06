package com.feigee.feigee.app.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.feigee.feigee.app.FragmentController;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.adapter.TitleAdapter;
import com.feigee.feigee.app.fragment.ContactsFragment;
import com.feigee.feigee.app.fragment.MainFragment;
import com.feigee.feigee.app.util.Constant;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ContactsFragment.OnResultToActivity {
    private ListView mTitleList;
    private TitleAdapter titleAdapter;
    private ArrayList mList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
//        翻转屏幕不会导致fragment置空
        Fragment fragment = fm.findFragmentById(R.id.fragment_contains);
        if (fragment == null) {
            fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key", "value");
            fragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.fragment_contains, fragment).commit();
        }
        Constant.addData(mList);
        mTitleList = (ListView) findViewById(R.id.title_list);
        titleAdapter = new TitleAdapter(mList, this);
        mTitleList.setAdapter(titleAdapter);
        mTitleList.setOnItemClickListener(onItemClickListener);
//        mTitleList.setSelector(R.drawable.listview_bg);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        //        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            titleAdapter.setSelectItem(position);
            String title = ((TitleAdapter.ViewHolder) view.getTag()).mTextView.getText().toString();
//            if("setting".equals(title)){
//                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
//                startActivity(intent);
//                return;
//            }
            FragmentController.fragmentController(Constant.fragmentMap.get(title), getFragmentManager());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onResultToActivity(String s) {
        Log.i("DatePickerDate", s);
    }
}
