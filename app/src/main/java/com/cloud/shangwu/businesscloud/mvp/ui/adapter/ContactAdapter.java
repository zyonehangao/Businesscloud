package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.im.managers.DialogsManager;
import com.cloud.shangwu.businesscloud.im.ui.activity.ChatActivity;
import com.cloud.shangwu.businesscloud.im.ui.activity.DialogActivity;
import com.cloud.shangwu.businesscloud.im.ui.activity.DialogsActivity;
import com.cloud.shangwu.businesscloud.im.ui.activity.SelectUsersActivity;
import com.cloud.shangwu.businesscloud.im.utils.chat.ChatHelper;
import com.cloud.shangwu.businesscloud.mvp.model.bean.Contact;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ContactComparator;
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.CreatGroupActivity;
import com.cloud.shangwu.businesscloud.utils.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DialogsManager.ManagingDialogsCallbacks {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    private int mHeaderCount = 1;


    @Override
    public void onDialogCreated(QBChatDialog chatDialog) {

    }

    @Override
    public void onDialogUpdated(String chatDialog) {

    }

    @Override
    public void onNewDialogLoaded(QBChatDialog chatDialog) {

    }

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
        ITEM_TYPE_HEADER
    }


    public ContactAdapter(Context context, String[] contactNames) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;


        handleContact();
    }

    private void handleContact() {
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
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.contactmessage_heard, parent, false));
        } else {
            if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
                return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
            } else {
                return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contactmessage_list, parent, false));
            }

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else {
            if (holder instanceof CharacterHolder) {
                ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
            } else if (holder instanceof ContactHolder) {
                ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
                holder.itemView.setOnClickListener(v -> {
//                    createChatThenStart();
                    mContext.startActivity(new Intent(mContext,CreatGroupActivity.class));
                });
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
            return resultList.get(position).getmType();
        }

    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    /**
     * create chat
     */
    private void createChatThenStart() {


//        Integer id =72298876;
//        List<QBUser> list = new ArrayList<>();
//        QBUser user = new QBUser(id);
//        QBUser user1 = new QBUser(71870789);
//        list.add(user);
//        list.add(user1);
//      //  list.add(ChatHelper.getCurrentUser());
//        ChatHelper.getInstance().createDialogWithSelectedUsers(list, new QBEntityCallback<QBChatDialog>() {
//            @Override
//            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
//                Log.d("CREATE-CHAT", "onSuccess");
//
//                Intent intent = new Intent(mContext, ChatActivity.class);
//                intent.putExtra(ChatActivity.EXTRA_DIALOG_ID, qbChatDialog);
//                mContext.startActivity(intent);
//
//            }
//
//            @Override
//            public void onError(QBResponseException e) {
//
//            }
//        });
        Intent intent=new Intent(mContext, DialogActivity.class);
        mContext.startActivity(intent);


    }


    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ContactHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.tv_name);
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
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }

        return -1; // -1不会滑动
    }
}
