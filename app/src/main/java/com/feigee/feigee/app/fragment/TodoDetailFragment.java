package com.feigee.feigee.app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.feigee.feigee.app.R;
import com.feigee.feigee.app.db.DBOperation;
import com.feigee.feigee.app.entity.TodoList;
import com.feigee.feigee.app.util.Constant;

import java.sql.SQLException;

/**
 * Created by fei.yao on 7/29/15.
 */
public class TodoDetailFragment extends DialogFragment {
    private EditText mEditText;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private TodoList mTodoList;
    public static final String CALLBACK_TODO_LIST = "com.feigee.criminalintent.app.fragment.OthersFragment";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.DATABASE_OPERATION_OK:
                    sendResult(Activity.RESULT_OK);
                    break;
            }

        }
    };


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_todo_detail, null);
        mEditText = (EditText) view.findViewById(R.id.editText_todo);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker_todo);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker_todo);
        return new AlertDialog.Builder(getActivity()).setTitle("title").setView(view).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                TODO 取消操作
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//              TODO 保存操作
                        mTodoList = new TodoList();
                        mTodoList.setContext(mEditText.getText().toString());
                        mTodoList.setDeadline(mDatePicker.getYear() + "-" + mDatePicker.getMonth() + "-" + mDatePicker.getDayOfMonth() + " " + mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute());
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    DBOperation.getInstance(getActivity()).saveTodoMsg(mTodoList);
                                    mHandler.sendEmptyMessage(Constant.DATABASE_OPERATION_OK);
                                } catch (SQLException e) {
                                    mHandler.sendEmptyMessage(Constant.DATABASE_OPERATION_FAILURE);
                                }
                            }
                        }.start();
                    }
                }

        ).create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    private void sendResult(int resultCode){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CALLBACK_TODO_LIST,mTodoList);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
