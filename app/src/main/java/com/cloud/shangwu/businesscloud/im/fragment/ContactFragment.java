package com.cloud.shangwu.businesscloud.im.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.im.Keys.BroadCastReceiverKeys;
import com.cloud.shangwu.businesscloud.im.activity.CCSingleChatActivity;
import com.cloud.shangwu.businesscloud.im.adapter.ContactAdapter;
import com.cloud.shangwu.businesscloud.im.adapter.ContactListAdapter;
import com.cloud.shangwu.businesscloud.im.models.Contact;
import com.cloud.shangwu.businesscloud.im.models.Conversation;
import com.cloud.shangwu.businesscloud.mvp.contract.ContactsFragmentContract;
import com.cloud.shangwu.businesscloud.mvp.model.ContactsFragmentPresenter;
import com.cloud.shangwu.businesscloud.mvp.model.bean.Friend;
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData;
import com.cloud.shangwu.businesscloud.widget.LetterView;
import com.inscripts.custom.RecyclerTouchListener;
import com.inscripts.enums.SettingSubType;
import com.inscripts.enums.SettingType;
import com.inscripts.factories.DataCursorLoader;
import com.inscripts.helpers.PreferenceHelper;
import com.inscripts.interfaces.ClickListener;
import com.inscripts.keys.PreferenceKeys;
import com.inscripts.orm.SugarDb;
import com.inscripts.pojos.CCSettingMapper;
import com.inscripts.utils.CommonUtils;
import com.inscripts.utils.LocalConfig;
import com.inscripts.utils.Logger;
import com.inscripts.utils.SessionData;
import com.inscripts.utils.StaticMembers;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;



public class ContactFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener,
        LetterView.CharacterClickListener, ContactsFragmentContract.View {
    private static final String TAG = ContactFragment.class.getSimpleName();
    private final int CONTACTS_LOADER = 1,CONTACTS_SEARCH_LOADER = 2;
    private ContactListAdapter contactListAdapter;
    private RecyclerView contactRecyclerView;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout grpNoContacts;
    private TextView tvNoContacts;
    private SearchView searchView;
    private boolean isSearchStart = true, lastSearchisZero = false, isSearching = false;
    private static String onoSearchText = "";
    private CometChat cometChat;
    private LinearLayoutManager layoutManager;
    private SQLiteDatabase db;
    private ArrayList<Contact> contacts;
    private final int FINISH=3;
    private ContactAdapter mAdapter;
    private LetterView mLetterView;
    private Context mContext;
    private RelativeLayout layout;
    private ContactsFragmentPresenter mPresenter;
    private LoginData data;


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FINISH:
                    contacts.clear();
                    contacts= (ArrayList<Contact>) msg.obj;
                    mAdapter=new ContactAdapter(mContext,contacts);
                    contactRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if(contacts.size() == 0){
                        grpNoContacts.setVisibility(View.VISIBLE);
                    }else {
                        grpNoContacts.setVisibility(View.GONE);

                    }
                    layout.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public ContactFragment() {
        // Required empty public constructor
    }




    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    public static ContactFragment newInstance(LoginData data) {
        ContactFragment fragment = new ContactFragment();

        Bundle args = new Bundle();
        args.putSerializable("data",data);
        fragment.setArguments(args);

    return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Logger.error(TAG,"OnReceive Called");
                Bundle extras = intent.getExtras();
                if (extras.containsKey(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_CONTACT_LIST_KEY)) {
                    if (contactListAdapter !=null && !isSearching) {
                        getLoaderManager().restartLoader(CONTACTS_LOADER, null, ContactFragment.this);
                    }
                }
            }
        };

         data = (LoginData) getArguments().getSerializable("data");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        mContext=getContext();
        contacts=new ArrayList<>();
//        db= SugarDb.getInstance(mContext).getWritableDatabase();
//        loadUsersFromQb();
        cometChat = CometChat.getInstance(getContext());
        grpNoContacts = view.findViewById(R.id.grpNoContacts);
        tvNoContacts = view.findViewById(R.id.tvNoContacts);
//        layout=view.findViewById(R.id.progress_bar);
        contactRecyclerView = view.findViewById(R.id.contacts_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPresenter = new ContactsFragmentPresenter();
        mPresenter.attachView(this);

        if (getLoaderManager().getLoader(CONTACTS_LOADER) == null) {
            getLoaderManager().initLoader(CONTACTS_LOADER, null, this);
        }else{
            getLoaderManager().restartLoader(CONTACTS_LOADER, null, this);
        }


        layoutManager = new LinearLayoutManager(mContext);
        contactRecyclerView.setLayoutManager(layoutManager);

        mLetterView=view.findViewById(R.id.letter_view);
        mLetterView.setCharacterListener(ContactFragment.this);

        tvNoContacts.setText(cometChat.getCCSetting(new CCSettingMapper(SettingType.LANGUAGE, SettingSubType.LANG_NO_CONTACTS_FOUND)).toString());

        contactRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), contactRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                long contactID = (long) view.getTag(R.string.contact_id);
                String contactname = (String) view.getTag(R.string.contact_name);

                Conversation conversation = Conversation.getConversationByBuddyID(String.valueOf(contactID));
                if(conversation != null) {
                    conversation.unreadCount = 0;
                    conversation.save();
                }

                Contact contact = Contact.getContactDetails(contactID);
                if(contact!=null) {
                    contact.unreadCount = 0;
                    contact.save();
                }

                Intent intent = new Intent(getActivity(), CCSingleChatActivity.class);
                intent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_ID,contactID);
                if (PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_IMAGE_URL) != null && !PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_IMAGE_URL).isEmpty()) {
                    intent.putExtra("ImageUri", PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_IMAGE_URL));
                }
                if (PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_VIDEO_URL) != null && !PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_VIDEO_URL).isEmpty()) {
                    intent.putExtra("VideoUri", PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_VIDEO_URL));
                }
                if (PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_AUDIO_URL) != null && !PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_AUDIO_URL).isEmpty()) {
                    intent.putExtra("AudioUri", PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_AUDIO_URL));
                }
                if (PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_FILE_URL) != null && !PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_FILE_URL).isEmpty()) {
                    intent.putExtra("FileUri", PreferenceHelper.get(PreferenceKeys.DataKeys.SHARE_FILE_URL));
                }
                intent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_NAME, contactname);
                SessionData.getInstance().setTopFragment(StaticMembers.TOP_FRAGMENT_ONE_ON_ONE);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        if(LocalConfig.isApp && !TextUtils.isEmpty((String)cometChat.getCCSetting(new CCSettingMapper(SettingType.FEATURE, SettingSubType.AD_UNIT_ID)))){
            CommonUtils.setBottomMarginToRecyclerView(contactRecyclerView);
        }

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // menu.findItem(R.id.custom_action_create_chatroom).setVisible(false);
//        try {
//            MenuItem searchMenuItem = menu.findItem(R.id.custom_action_search);
//            searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
//            searchView.setOnQueryTextListener(this);
//
//            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>Search</font>"));
//
//
//            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//
//                @SuppressLint("NewApi")
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        if (TextUtils.isEmpty(searchView.getQuery())) {
//                            searchView.setIconified(true);
//                            isSearching = false;
//                        }
//                    } else {
//                        if (!searchView.isIconified()) {
//                            searchView.setIconified(false);
//                        }
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            Logger.error("onCreateOptionsMenu in oneononefragment.java Exception = " + e.getLocalizedMessage());
//            e.printStackTrace();
//        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(broadcastReceiver!= null){
            getActivity().registerReceiver(broadcastReceiver,
                    new IntentFilter(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST));
        }
        mPresenter.getFriends(data.getUid());
    }

    @Override
    public void onStop() {
        super.onStop();

        if (null != broadcastReceiver) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection;

        switch (id){

            case CONTACTS_LOADER:
                Logger.error(TAG,"CONTACTS_LOADER ");
                selection = Contact.getAllContactsQuery();
//                return db.query(Contact.TABLE_NAME, new String[]{Contact.SHOW_USER},"=?",new String[]{"1"},null,null,Contact.COLUMN_LAST_UPDATED + " desc");
                return new DataCursorLoader(getContext(), selection, null);

            case CONTACTS_SEARCH_LOADER:
                Logger.error(TAG,"Search key = "+args.getString("search_key"));
                selection = Contact.searchContactsQuery(args.getString("search_key"));
                return new DataCursorLoader(getContext(), selection, null);


            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Logger.error(TAG,"on load data = "+ data.getCount());
        if(contactListAdapter == null){
            contactListAdapter = new ContactListAdapter(getContext(),data);
            contactRecyclerView.setAdapter(contactListAdapter);
        }

        if(data.getCount() == 0){
            grpNoContacts.setVisibility(View.VISIBLE);
        }else {
            grpNoContacts.setVisibility(View.GONE);

        }
        contactListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*switch (loader.getId()) {
            case CONTACTS_LOADER:
                if (contactListAdapter != null) {
                    contactListAdapter.swapCursor(null);
                }
                break;
            default:
                break;
        }*/
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        if (contactRecyclerView != null && contactListAdapter != null) {
            searchText = searchText.replaceAll("^\\s+", "");
            if (!searchView.isIconified() && !TextUtils.isEmpty(searchText)) {
                onoSearchText = searchText;
            }
            if (!TextUtils.isEmpty(searchText)) {
                searchUser(searchText, true);
                isSearchStart = true;
                lastSearchisZero = false;
            } else {
                if (isSearchStart) {
                    if (!lastSearchisZero) {
                        lastSearchisZero = true;
                        onoSearchText = searchText;
                        searchUser(searchText, false);
                    }
                }
            }
        }
        return true;
    }


    private void searchUser(String searchText, boolean search) {
        Logger.error(TAG,"Search user called with key "+searchText);
        if (search) {
            isSearching = true;
            Bundle bundle = new Bundle();
            bundle.putString("search_key",searchText);
            if (getLoaderManager().getLoader(CONTACTS_SEARCH_LOADER) == null) {
                getLoaderManager().initLoader(CONTACTS_SEARCH_LOADER, bundle, this);
            }else {
                getLoaderManager().restartLoader(CONTACTS_SEARCH_LOADER, bundle, this);
            }
        } else {
            getLoaderManager().restartLoader(CONTACTS_LOADER, null, this);
        }
    }

    public void refreshFragment(){
        Logger.error("Refresh Fragment called");
        try {
            if (getLoaderManager().getLoader(CONTACTS_LOADER) != null) {
                getLoaderManager().restartLoader(CONTACTS_LOADER, null, this);
            } else {
                getLoaderManager().initLoader(CONTACTS_LOADER, null, this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
                db.close();
                Message msg=new Message();
                msg.what=FINISH;
                msg.obj=contacts;
                handler.sendMessage(msg);
            }
        }.start();


    }

    @Override
    public void clickCharacter(String character) {

    }

    @Override
    public void clickArrow() {

    }

    @Override
    public void onGetFriends(@NotNull List<Friend> list) {
        Log.e(TAG,"onGetFriends:"+list.get(0));
    }

    @Override
    public void onError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String errorMsg) {

    }
}
