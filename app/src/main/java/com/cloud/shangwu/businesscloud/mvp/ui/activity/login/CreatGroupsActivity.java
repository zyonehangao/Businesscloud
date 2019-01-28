package com.cloud.shangwu.businesscloud.mvp.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.base.BaseActivity;
import com.cloud.shangwu.businesscloud.im.activity.CCGroupChatActivity;
import com.cloud.shangwu.businesscloud.im.adapter.ContactAdapter;
import com.cloud.shangwu.businesscloud.im.models.Contact;
import com.cloud.shangwu.businesscloud.im.models.Groups;
import com.cloud.shangwu.businesscloud.widget.LetterView;
import com.inscripts.enums.GroupType;
import com.inscripts.interfaces.Callbacks;
import com.inscripts.orm.SugarDb;
import com.inscripts.utils.Logger;
import com.inscripts.utils.SessionData;
import com.inscripts.utils.StaticMembers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

/**
 * Created by Administrator on 2019/1/27.
 */

public class CreatGroupsActivity extends BaseActivity implements LetterView.CharacterClickListener, View.OnClickListener {
    private static final String TAG = CreatGroupsActivity.class.getSimpleName();
    private RecyclerView mRecycle;
    private LinearLayoutManager layoutManager;
    private SQLiteDatabase db;
    private ArrayList<Contact> contacts;
    private final int FINISH=0;
    private ContactAdapter mAdapter;
    private LetterView mLetterView;
    private AppBarLayout toorbar;
    private ImageView back;
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FINISH:
                    List<Contact> data= (List<Contact>) msg.obj;
                        mAdapter=new ContactAdapter(CreatGroupsActivity.this,data);
                        mRecycle.setAdapter(mAdapter);
                    Toast.makeText(CreatGroupsActivity.this,"finish",Toast.LENGTH_SHORT).show();
                        mLetterView.setCharacterListener(CreatGroupsActivity.this);

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_creatgroup;
    }

    @Override
    public void initData() {
        contacts=new ArrayList<>();
        db= SugarDb.getInstance(this).getWritableDatabase();
    }

    @Override
    public void initView() {
        mRecycle=findViewById(R.id.contact_list);
        layoutManager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(layoutManager);
        mLetterView=findViewById(R.id.letter_view);
        back=findViewById(R.id.iv_black);
        back.setOnClickListener(this);

    }

    @Override
    public void start() {
        loadUsersFromQb();
    }

    private void loadUsersFromQb() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                contacts.clear();

                Cursor cursor = db.query("CONTACT", null, Contact.SHOW_USER+"=?",
                        new String[]{"1"}, null, null, Contact.COLUMN_LAST_UPDATED
                                + " desc");

                if(cursor.moveToFirst()){
                    do{
                        Long id = cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_CONTACT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME));
                        String statsMes = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STATUS_MESSAGE));
                        String avatarUrl = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_AVATAR_URL));
                        int unReadCount = cursor.getInt(cursor.getColumnIndex(Contact.COLUMN_UNREAD_COUNT));
                        String status = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STATUS));
                        Contact contact=new Contact();
                        contact.contactId=id;
                        contact.name=name;
                        contact.statusMessage=statsMes;
                        contact.avatarURL=avatarUrl;
                        contact.unreadCount=unReadCount;
                        contact.status=status;
                        contacts.add(contact);
                    }while(cursor.moveToNext());
                }
                cursor.close();
//                db.close();
                Message msg=new Message();
                msg.what=FINISH;
                msg.obj=contacts;
                handler.sendMessage(msg);
            }
        }.start();


    }

    @Override
    public void clickCharacter(String character) {
        layoutManager.scrollToPositionWithOffset(mAdapter.getScrollPosition(character), 0);
    }

    @Override
    public void clickArrow() {
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onClick(View v) {
        List<Contact> checkedContacts = mAdapter.getCheckedContacts();
        StringBuffer name=new StringBuffer();
        for (Contact contact:
        checkedContacts) {
            name.append(contact.name).append(",");
        }
        String chatRoomName = name.deleteCharAt(name.length() - 1).toString();
        CometChat.getInstance(this).createGroup(chatRoomName, "", GroupType.PRIVATE, new Callbacks() {
            @Override
            public void successCallback(JSONObject jsonObject) {
                Logger.error(TAG, "successCallback: createGroup: "+jsonObject);

                try {
                    if(!jsonObject.has("type")){
                        jsonObject.put("type", 4);
                    }
                    if(!jsonObject.has("createdby")){
                        jsonObject.put("createdby", SessionData.getInstance().getId());
                    }
                    Groups.insertNewGroup(jsonObject);
                    Intent intent = new Intent(CreatGroupsActivity.this, CCGroupChatActivity.class);
                    intent.putExtra(StaticMembers.INTENT_CHATROOM_ID, jsonObject.getString("group_id"));
                    intent.putExtra(StaticMembers.INTENT_CHATROOM_NAME, chatRoomName);
                    intent.putExtra(StaticMembers.INTENT_CHATROOM_ISOWNER, true);
                    intent.putExtra("GROUP_TYPE", 4);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failCallback(JSONObject jsonObject) {

            }
        });
    }
}
