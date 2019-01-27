package com.cloud.shangwu.businesscloud.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.im.models.Contact;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ContactComparator;
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.CreatGroupActivity;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ContactAdapter;
import com.cloud.shangwu.businesscloud.utils.Utils;
import com.inscripts.custom.RoundedImageView;
import com.inscripts.enums.SettingSubType;
import com.inscripts.enums.SettingType;
import com.inscripts.factories.LocalStorageFactory;
import com.inscripts.factories.RecyclerViewCursorAdapter;
import com.inscripts.keys.CometChatKeys;
import com.inscripts.pojos.CCSettingMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;



public class ContactListAdapter extends RecyclerViewCursorAdapter<ContactListAdapter.ContactItemHolder> {

    private static final String TAG = ContactListAdapter.class.getSimpleName();
    private Context context;
    int primaryColor;
    private CometChat cometChat;
    private LayoutInflater mLayoutInflater;

    private ArrayList<String> mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    private int mIndex=0;
    private int mHeaderCount = 1;

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
        ITEM_TYPE_HEADER
    }

    public ContactListAdapter(Context context , Cursor c) {
        super(c);
        this.context = context;
        cometChat = CometChat.getInstance(context);
        mLayoutInflater = LayoutInflater.from(context);

        mContactNames=new ArrayList();
        handleContact(c);
        primaryColor = (int) cometChat.getCCSetting(new CCSettingMapper(SettingType.UI_SETTINGS, SettingSubType.COLOR_PRIMARY));
    }

    private void handleContact(Cursor c) {

        if(c!=null&&c.moveToFirst()){
            do{
                mContactNames.add(c.getString(c.getColumnIndex(Contact.COLUMN_NAME)));
            }while(c.moveToNext());
        }
        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        for (String mContactName : mContactNames) {
            String pinyin = Utils.getPingYin(mContactName);
            map.put(pinyin, mContactName);
            mContactList.add(pinyin);
        }

        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new Contact());
//                    resultList.add(new Contact(new QBUser(character,""),ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));

                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact());
//                        resultList.add(new Contact(new QBUser("#",""),ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
//            contacts.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));

        }
    }

    @Override
    public void onBindViewHolder(ContactItemHolder contactItemHolder, Cursor cursor) {
        contactItemHolder.userName.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME))));
        contactItemHolder.userStatus.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STATUS_MESSAGE))));

        contactItemHolder.avatar.getBackground().setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
        String avatarURL = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_AVATAR_URL));
        LocalStorageFactory.loadImageUsingURL(context,avatarURL,contactItemHolder.avatar, R.drawable.cc_ic_default_avtar);


        contactItemHolder.view.setTag(R.string.contact_id,cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_CONTACT_ID)));
        contactItemHolder.view.setTag(R.string.contact_name,Html.fromHtml(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME))).toString());

        boolean recentChatEnabled = (boolean)cometChat.getCCSetting(new CCSettingMapper(SettingType.FEATURE, SettingSubType.RECENT_CHAT_ENABLED));
        if (recentChatEnabled || 0 == cursor.getInt(cursor.getColumnIndex(Contact.COLUMN_UNREAD_COUNT))){
            contactItemHolder.unreadCount.setVisibility(View.GONE);
        } else {
            GradientDrawable drawable = (GradientDrawable) contactItemHolder.unreadCount.getBackground();
            drawable.setColor(primaryColor);
            contactItemHolder.unreadCount.setVisibility(View.VISIBLE);
            contactItemHolder.unreadCount.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contact.COLUMN_UNREAD_COUNT))));
        }

        switch (cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STATUS)).toLowerCase()) {
            case CometChatKeys.StatusKeys.AVALIABLE:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_online);
                break;
            case CometChatKeys.StatusKeys.AWAY:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_available);
                break;
            case CometChatKeys.StatusKeys.BUSY:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_busy);
                break;
            case CometChatKeys.StatusKeys.OFFLINE:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_ofline);
                break;
            case CometChatKeys.StatusKeys.INVISIBLE:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_ofline);
                break;
            default:
                contactItemHolder.statusImage.setImageResource(R.drawable.cc_status_available);
                break;
        }


    }

    @Override
    public ContactItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactItemHolder(v);
    }

    static class ContactItemHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userStatus;
        public TextView unreadCount;
        public RoundedImageView avatar;
        public ImageView avtar_image;
        public ImageView statusImage;
        public View view;

        public ContactItemHolder(View view) {
            super(view);
            avatar = (RoundedImageView) view.findViewById(R.id.imageViewUserAvatar);
            userName = (TextView) view.findViewById(R.id.textviewUserName);
            userStatus = (TextView) view.findViewById(R.id.textviewUserStatus);
            statusImage = (ImageView) view.findViewById(R.id.imageViewStatusIcon);
            unreadCount = (TextView) view.findViewById(R.id.textviewSingleChatUnreadCount);
            this.view = view;
        }
    }

}
