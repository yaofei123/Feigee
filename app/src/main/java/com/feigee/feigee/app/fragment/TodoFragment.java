package com.feigee.feigee.app.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.adapter.TodoAdapter;
import com.feigee.feigee.app.db.DatabaseHelper;
import com.feigee.feigee.app.entity.TodoList;
import com.feigee.feigee.app.util.Constant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei.yao on 7/28/15.
 */
public class TodoFragment extends Fragment {
    private final String TAG = TodoFragment.class.toString();
    private final int REQUEST_DATE = 0;

    private ListView mTodoList;
    private ImageButton mAddButton;
    private TodoAdapter mTodoAdapter;
    private List<TodoList> todoListList = new ArrayList<TodoList>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.DATABASE_OPERATION_OK:
                    Object obj = msg.obj;
                    if (obj != null && obj instanceof List) {
                        todoListList = (List<TodoList>) msg.obj;
                    }
                    createUI();
                    break;
                case Constant.DATABASE_OPERATION_FAILURE:
//                    TODO 操作数据库失败
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_todo, container, false);
        mTodoList = (ListView) view.findViewById(R.id.list_todo);
        mAddButton = (ImageButton) view.findViewById(R.id.button_add);

        new Thread() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    todoListList = DatabaseHelper.getInstance(getActivity()).getTodoListDao().queryForAll();
                    message.obj = todoListList;
                    message.what = Constant.DATABASE_OPERATION_OK;
                } catch (SQLException e) {
                    message.what = Constant.DATABASE_OPERATION_OK;
                    message.obj = e.getMessage();
                    Log.e(TAG, "select table todoList error: " + e.getMessage());
                }
                mHandler.sendMessage(message);
            }
        }.start();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(mTodoList);
        } else {
            mTodoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mTodoList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_crime:
                            for (int i = 0; i < mTodoAdapter.getCount(); i++) {
                                if (mTodoList.isItemChecked(i)) {
                                    delete(i);
                                    Log.d(TAG, "触发");
                                }
                            }
                            break;
                    }
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                     新增详细待办信息
                TodoDetailFragment todoDetailFragment = new TodoDetailFragment();
                todoDetailFragment.setTargetFragment(TodoFragment.this, REQUEST_DATE);
                todoDetailFragment.show(getFragmentManager(), "todoDetail");
//                    FragmentController.fragmentController(Constant.fragmentMap.get("todoDetail"), getFragmentManager());
            }
        });
        mTodoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    TODO 显示详细待办信息
            }
        });

    }

    private void createUI() {
        if (todoListList == null || todoListList.size() == 0) {
            mTodoList.setVisibility(View.GONE);
            mAddButton.setVisibility(View.VISIBLE);

        } else {
            mAddButton.setVisibility(View.GONE);
            mTodoList.setVisibility(View.VISIBLE);
            if (mTodoAdapter != null) {
                mTodoAdapter.notifyDataSetChanged();
                return;
            }
            mTodoAdapter = new TodoAdapter(getActivity(), todoListList);
            mTodoList.setAdapter(mTodoAdapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_DATE) {
                if (todoListList == null) {
                    todoListList = new ArrayList<TodoList>();
                }
                mAddButton.setVisibility(View.GONE);
                mTodoList.setVisibility(View.VISIBLE);
                todoListList.add((TodoList) (data.getSerializableExtra(TodoDetailFragment.CALLBACK_TODO_LIST)));
//                mTodoAdapter = new TodoAdapter(getActivity(), todoListList);
//                mTodoList.setAdapter(mTodoAdapter);
                mTodoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                delete(position);
                Log.d(TAG, "触发");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    /**
     * 删除指定position的待办
     *
     * @param p
     */
    private void delete(int p) {
        final int position = p;
        final TodoList todoList = todoListList.get(position);
        new Thread() {
            @Override
            public void run() {
                try {
                    int delete = DatabaseHelper.getInstance(getActivity()).getTodoListDao().delete(todoList);
                    if (delete == 1) {
                        todoListList.remove(position);
                        mHandler.sendEmptyMessage(Constant.DATABASE_OPERATION_OK);
                    } else {
                        mHandler.sendEmptyMessage(Constant.DATABASE_OPERATION_FAILURE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(Constant.DATABASE_OPERATION_FAILURE);
                }
            }
        }.start();
    }
}
