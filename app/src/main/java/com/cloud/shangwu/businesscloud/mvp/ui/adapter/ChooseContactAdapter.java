package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.mvp.model.bean.Contact;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ContactComparator;
import com.cloud.shangwu.businesscloud.utils.Utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChooseContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    private Map<Integer,Boolean> map=new HashMap<>();// 存放已被选中的CheckBox

    private List<Contact> mContacts=new ArrayList<>();

    private CheckItemListener mCheckListener;

    public interface CheckItemListener {

        void itemChecked(Contact checkBean, boolean isChecked);
    }

    public Map<Integer,Boolean> getCheckedContact(){
        return map;
    }

    public List<Contact> getCheckedContacts(){
        return mContacts;
    }

    public void setmCheckListener(CheckItemListener listener){
        this.mCheckListener=listener;
    }

    private int mHeaderCount = 1;

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
        ITEM_TYPE_HEADER
    }


    public ChooseContactAdapter(Context context,  ArrayList<String> list) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
//        mContactNames = contactNames;
//        mContactNames=new ArrayList();
        Log.i("test","size"+list.size());
        handleContact(list);
    }

    private void handleContact(ArrayList<String> list) {

        mContactList = new ArrayList<>();
        Map<String, Contact> map = new HashMap<>();

        for (int i=0;i<list.size();i++){
//            QBUser qbUser = list.get(i);
//            String pinyin = Utils.getPingYin(qbUser.getFullName()==null?qbUser.getLogin():qbUser.getFullName());
//            map.put(pinyin, qbUser);
//            mContactList.add(pinyin);
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
//                    resultList.add(new Contact(new QBUser(character,""), ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));

                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
//                        resultList.add(new Contact(new QBUser("#",""), ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

//            resultList.add(new Contact(map.get(name), ContactAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType== ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()){
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.contactmessage_heard, parent, false));
        }else {
            if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
                return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
            } else {
                return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contactmessage_chooselist, parent, false));
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){

        }else if(holder instanceof CharacterHolder) {
//            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getName());
        } else if (holder instanceof ContactHolder) {
                 Contact contact = resultList.get(position);
//                ((ContactHolder) holder).mTextView.setText(contact.getName());
                ((ContactHolder) holder).mTextView.setOnClickListener(view -> {
                    contact.setIsChecked(!contact.getIsChecked());
                    ((ContactHolder) holder).mCheckbox.setChecked(contact.getIsChecked());
                    if (null != mCheckListener) {
                        mCheckListener.itemChecked(contact, ((ContactHolder) holder).mCheckbox.isChecked());
                    }
                    notifyDataSetChanged();
                });
                ((ContactHolder) holder).mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked==true){
                            map.put(position,true);
                            mContacts.add(resultList.get(position));
                        }else {
                            map.remove(position);
                            mContacts.remove(mContacts.size()-1);
                        }
                    }
                });

                if(map!=null&&map.containsKey(position)){
                    ((ContactHolder) holder).mCheckbox.setChecked(true);
                }else {
                    ((ContactHolder) holder).mCheckbox.setChecked(false);
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

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);

        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CheckBox mCheckbox;
        ContactHolder(View view) {
            super(view);
            mCheckbox = (CheckBox) view.findViewById(R.id.choose);
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
//                if (resultList.get(i).getName().equals(character)) {
//                    return i;
//                }
            }
        }

        return -1; // -1不会滑动
    }


}
