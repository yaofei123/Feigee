package com.feigee.feigee.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.feigee.feigee.app.R;

/**
 * Created by fei.yao on 7/29/15.
 */
public class ContactsFragment extends Fragment {
    private final String TAC = "com.feigee.feigee.app.fragment.ContactsFragment";
    private final int REQUEST_CONTACTS = 1;
    private OnResultToActivity onResultToActivity;
    private TextView mTextView;
    private String phoneNum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        mTextView = (TextView) v.findViewById(R.id.textView_contact);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNum));
                startActivity(i);
            }
        });
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CONTACTS);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CONTACTS) {
                Uri contactUri = data.getData();
//                String[] queryFields = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor c = getActivity().getContentResolver().query(contactUri, null, null, null, null);
                if (c.getCount() == 0) {
                    c.close();
                    return;
                }
                c.moveToFirst();
//                int indexPhoneNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int indexDisplayName = c.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                String contact = c.getString(indexDisplayName);
//                String number = c.getString(c.getColumnIndexOrThrow(Contacts.Phones.NUMBER));
                phoneNum = getContactPhone(c);
                mTextView.setText(contact+" : "+phoneNum);
                Log.i(TAC, contact);
                c.close();

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        onResultToActivity.onResultToActivity("123");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (onResultToActivity == null) {
            onResultToActivity = (OnResultToActivity) activity;
        }
    }

    public interface OnResultToActivity {
        public void onResultToActivity(String s);
    }

    private String getContactPhone(Cursor cursor) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;
//                  switch (phone_type) {//此处请看下方注释
//                  case 2:
//                      result = phoneNumber;
//                      break;
//
//                  default:
//                      break;
//                  }
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }
}
