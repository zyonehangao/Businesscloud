package com.cloud.shangwu.businesscloud.im.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.im.activity.GroupListActivity;
import com.cloud.shangwu.businesscloud.im.models.Contact;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ContactComparator;
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.CreatGroupActivity;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ChooseContactAdapter;
import com.cloud.shangwu.businesscloud.utils.Utils;
import com.inscripts.custom.RoundedImageView;
import com.inscripts.enums.SettingSubType;
import com.inscripts.enums.SettingType;
import com.inscripts.factories.LocalStorageFactory;
import com.inscripts.keys.CometChatKeys;
import com.inscripts.pojos.CCSettingMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

/**
 * Created by Administrator on 2019/1/27.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ContactListAdapter.class.getSimpleName();
    private Context context;
    int primaryColor;
    private CometChat cometChat;
    private LayoutInflater mLayoutInflater;

    private ArrayList<String> mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    private List<Contact> mContacts;  //选中的
    private Map<Integer,Boolean> map=new HashMap<>();// 存放已被选中的CheckBox

    private CheckItemListener mCheckListener;
    public void setmCheckListener(CheckItemListener listener){
        this.mCheckListener=listener;
    }
    public interface CheckItemListener {

        void itemChecked(Contact checkBean, boolean isChecked);
    }

    public List<Contact> getCheckedContacts(){
        return mContacts;
    }

    private int mIndex=0;
    private int mHeaderCount = 1;

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
        ITEM_TYPE_HEADER
    }

    public ContactAdapter(Context context , List data) {
        super();
        this.context = context;
        cometChat = CometChat.getInstance(context);
        mLayoutInflater = LayoutInflater.from(context);
        mContacts=new ArrayList<>();
        mContactNames=new ArrayList();
        handleContact(data);
        primaryColor = (int) cometChat.getCCSetting(new CCSettingMapper(SettingType.UI_SETTINGS, SettingSubType.COLOR_PRIMARY));
    }

    private void handleContact(List<Contact> data) {

        mContactList = new ArrayList<>();
        Map<String, Contact> map = new HashMap<>();

        for (Contact contact : data) {
            String pinyin = Utils.getPingYin(contact.name);
            map.put(pinyin, contact);
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
                    Contact contact=new Contact();
                    contact.name=character;
                    contact.type=ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal();
                    resultList.add(contact);
//                    resultList.add(new Contact(new QBUser(character,""),ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));

                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        Contact contact=new Contact();
                        contact.name="#";
                        contact.type=ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal();
                        resultList.add(contact);
//                        resultList.add(new Contact(new QBUser("#",""),ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            resultList.add(map.get(name).setType(ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
            Log.i("contactadapter","hello");

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.contactmessage_heard, viewGroup, false));
        } else {
            if (viewType == com.cloud.shangwu.businesscloud.mvp.ui.adapter.ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
                return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, viewGroup, false));
            } else {
                return new ContactItemHolder(mLayoutInflater.inflate(R.layout.contact_list_item_check, viewGroup, false));
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder contactItemHolder, int i) {
        if ( i==0){
            context.startActivity(new Intent(context,GroupListActivity.class));
            ((Activity)context).finish();
        }

            if (contactItemHolder instanceof CharacterHolder) {
                ((CharacterHolder) contactItemHolder).mTextView.setText(resultList.get(i).name);
//                ((CharacterHolder) contactItemHolder).mTextView.setClickable(false);
            } else if (contactItemHolder instanceof ContactItemHolder) {
                Contact contact = resultList.get(i);


                ((ContactItemHolder) contactItemHolder).userName.setText(contact.name);
                ((ContactItemHolder) contactItemHolder).userStatus.setText(contact.statusMessage);

                ((ContactItemHolder) contactItemHolder).avatar.getBackground().setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);

                LocalStorageFactory.loadImageUsingURL(context,contact.avatarURL,((ContactItemHolder) contactItemHolder).avatar, R.drawable.cc_ic_default_avtar);


                ((ContactItemHolder) contactItemHolder).view.setTag(R.string.contact_id,contact.contactId);
                ((ContactItemHolder) contactItemHolder).view.setTag(R.string.contact_name,contact.name);

                boolean recentChatEnabled = (boolean)cometChat.getCCSetting(new CCSettingMapper(SettingType.FEATURE, SettingSubType.RECENT_CHAT_ENABLED));
                if (recentChatEnabled || 0 == contact.unreadCount){
                    ((ContactItemHolder) contactItemHolder).unreadCount.setVisibility(View.GONE);
                } else {
                    GradientDrawable drawable = (GradientDrawable) ((ContactItemHolder) contactItemHolder).unreadCount.getBackground();
                    drawable.setColor(primaryColor);
                    ((ContactItemHolder) contactItemHolder).unreadCount.setVisibility(View.VISIBLE);
                    ((ContactItemHolder) contactItemHolder).unreadCount.setText(String.valueOf(contact.unreadCount));
                }

                switch (contact.status.toLowerCase()) {
                    case CometChatKeys.StatusKeys.AVALIABLE:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_online);
                        break;
                    case CometChatKeys.StatusKeys.AWAY:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_available);
                        break;
                    case CometChatKeys.StatusKeys.BUSY:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_busy);
                        break;
                    case CometChatKeys.StatusKeys.OFFLINE:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_ofline);
                        break;
                    case CometChatKeys.StatusKeys.INVISIBLE:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_ofline);
                        break;
                    default:
                        ((ContactItemHolder) contactItemHolder).statusImage.setImageResource(R.drawable.cc_status_available);
                        break;
                }

                ((ContactItemHolder) contactItemHolder).view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        contact.setIsSelect(!contact.isSelect);
//                        ((ContactItemHolder) contactItemHolder).isSelect.setChecked(contact.isSelect);
                        if (null != mCheckListener) {
                            mCheckListener.itemChecked(contact, ((ContactItemHolder) contactItemHolder).isSelect.isChecked());
                        }
                        notifyDataSetChanged();
                    }
                });

                ((ContactItemHolder) contactItemHolder).isSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked==true){
                            map.put(i,true);
                            mContacts.add(resultList.get(i));
                        }else {
                            map.remove(i);
                            mContacts.remove(mContacts.size()-1);
                        }
                    }
                });

                if(map!=null&&map.containsKey(i)){
                    ((ContactItemHolder) contactItemHolder).isSelect.setChecked(true);
                }else {
                    ((ContactItemHolder) contactItemHolder).isSelect.setChecked(false);
                }

            }

    }



    @Override
    public int getItemViewType(int position) {
//        return resultList.get(position).getmType();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else {
            //内容View
            return resultList.get(position).getType();
//            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        }

    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }


    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
//                if (resultList.get(i).getName().equals(character)) {
//                    return i;
//                }
            }
        }

        return -1; // -1不会滑动
    }

    static class ContactItemHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userStatus;
        public TextView unreadCount;
        public RoundedImageView avatar;
        public ImageView avtar_image;
        public ImageView statusImage;
        public View view;
        public CheckBox isSelect;

        public ContactItemHolder(View view) {
            super(view);
            avatar = (RoundedImageView) view.findViewById(R.id.imageViewUserAvatar);
            userName = (TextView) view.findViewById(R.id.textviewUserName);
            userStatus = (TextView) view.findViewById(R.id.textviewUserStatus);
            statusImage = (ImageView) view.findViewById(R.id.imageViewStatusIcon);
            unreadCount = (TextView) view.findViewById(R.id.textviewSingleChatUnreadCount);
            isSelect=view.findViewById(R.id.choose);
            this.view = view;
        }
    }
}
