package com.cloud.shangwu.businesscloud.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.im.utils.TimeUtils;
import com.cloud.shangwu.businesscloud.im.utils.chat.ChatHelper;
import com.cloud.shangwu.businesscloud.im.utils.qb.PaginationHistoryListener;
import com.cloud.shangwu.businesscloud.im.utils.qb.QbUsersHolder;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.helper.CollectionsUtil;

import com.quickblox.sample.core.utils.ResourceUtils;
import com.quickblox.sample.core.utils.UiUtils;
import com.quickblox.ui.kit.chatmessage.adapter.QBMessagesAdapter;
import com.quickblox.ui.kit.chatmessage.adapter.listeners.QBChatAttachClickListener;
import com.quickblox.ui.kit.chatmessage.adapter.listeners.QBLinkPreviewClickListener;
import com.quickblox.users.model.QBUser;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.util.List;


public class ChatAdapter extends QBMessagesAdapter<QBChatMessage> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private static final String TAG = ChatAdapter.class.getSimpleName();
    private final QBChatDialog chatDialog;
    private PaginationHistoryListener paginationListener;
    private int previousGetCount = 0;

    private QBChatAttachClickListener attachImageClickListener;
    private QBChatAttachClickListener attachLocationClickListener;
    private QBChatAttachClickListener attachAudioClickListener;
    private QBChatAttachClickListener attachVideoClickListener;
    private QBLinkPreviewClickListener linkPreviewClickListener;

    public ChatAdapter(Context context, QBChatDialog chatDialog, List<QBChatMessage> chatMessages) {
        super(context, chatMessages);
        this.chatDialog = chatDialog;
    }

    public void addToList(List<QBChatMessage> items) {
        chatMessages.addAll(0, items);
        notifyItemRangeInserted(0, items.size());
    }

    @Override
    public void add(QBChatMessage item) {
        this.chatMessages.add(item);
        this.notifyItemInserted(chatMessages.size() - 1);
    }

    @Override
    public void onBindViewHolder(QBMessageViewHolder holder, int position) {
        downloadMore(position);
        QBChatMessage chatMessage = getItem(position);
        if (isIncoming(chatMessage) && !isRead(chatMessage)) {
            readMessage(chatMessage);
        }
//        super.onBindViewHolder(holder, position);
        onBindViewCustomHolder(holder,chatMessage,position);
    }

    @Override
    public String getImageUrl(int position) {
        QBAttachment attachment = getQBAttach(position);
        return attachment.getUrl();
    }

    @Override
    protected void onBindViewMsgLeftHolder(TextMessageHolder holder, QBChatMessage chatMessage, int position) {
        holder.timeTextMessageTextView.setVisibility(View.GONE);

        TextView opponentNameTextView = holder.itemView.findViewById(R.id.opponent_name_text_view);
        opponentNameTextView.setTextColor(UiUtils.getRandomTextColorById(chatMessage.getSenderId()));
        opponentNameTextView.setText(getSenderName(chatMessage));

        TextView customMessageTimeTextView = holder.itemView.findViewById(R.id.custom_msg_text_time_message);
        customMessageTimeTextView.setText(getDate(chatMessage.getDateSent()));

        super.onBindViewMsgLeftHolder(holder, chatMessage, position);
    }

    @Override
    protected void onBindViewAttachLeftHolder(ImageAttachHolder holder, QBChatMessage chatMessage, int position) {
        TextView opponentNameTextView = holder.itemView.findViewById(R.id.opponent_name_attach_view);
        opponentNameTextView.setTextColor(UiUtils.getRandomTextColorById(chatMessage.getSenderId()));
        opponentNameTextView.setText(getSenderName(chatMessage));

        super.onBindViewAttachLeftHolder(holder, chatMessage, position);
    }

    private String getSenderName(QBChatMessage chatMessage) {
        QBUser sender = QbUsersHolder.getInstance().getUserById(chatMessage.getSenderId());
        return sender.getFullName();
    }

    private void readMessage(QBChatMessage chatMessage) {
        try {
            chatDialog.readMessage(chatMessage);
        } catch (XMPPException | SmackException.NotConnectedException e) {
            Log.w(TAG, e);
        }
    }

    private boolean isRead(QBChatMessage chatMessage) {
        Integer currentUserId = ChatHelper.getCurrentUser().getId();
        return !CollectionsUtil.isEmpty(chatMessage.getReadIds()) && chatMessage.getReadIds().contains(currentUserId);
    }

    public void setPaginationHistoryListener(PaginationHistoryListener paginationListener) {
        this.paginationListener = paginationListener;
    }

    private void downloadMore(int position) {
        if (position == 0) {
            if (getItemCount() != previousGetCount) {
                paginationListener.downloadMore();
                previousGetCount = getItemCount();
            }
        }
    }

    @Override
    public long getHeaderId(int position) {
        QBChatMessage chatMessage = getItem(position);
        return TimeUtils.getDateAsHeaderId(chatMessage.getDateSent() * 1000);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_chat_message_header_bc, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        TextView dateTextView = view.findViewById(R.id.header_date_textview);

        QBChatMessage chatMessage = getItem(position);
        dateTextView.setText(TimeUtils.getDate(chatMessage.getDateSent() * 1000));

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dateTextView.getLayoutParams();
        if (position == 0) {
            lp.topMargin = ResourceUtils.getDimen(R.dimen.chat_date_header_top_margin);
        } else {
            lp.topMargin = 0;
        }
        dateTextView.setLayoutParams(lp);
    }

    @Override
    protected QBMessageViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateCustomViewHolder viewType:"+viewType);
        switch(viewType) {
            case 1:
                this.qbViewHolder = new QBMessagesAdapter.TextMessageHolder(this.inflater.inflate(R.layout.list_item_text_right_bc, parent, false), R.id.text, R.id.text_time, R.id.icon);
                return this.qbViewHolder;
            case 2:
                this.qbViewHolder = new QBMessagesAdapter.TextMessageHolder(this.inflater.inflate(R.layout.list_item_text_left_bc, parent, false), R.id.text, R.id.text_time, R.id.icon);
                return this.qbViewHolder;
            case 3:
                this.qbViewHolder = new QBMessagesAdapter.ImageAttachHolder(this.inflater.inflate(R.layout.list_item_attach_right, parent, false), R.id.show_image, R.id.pb, R.id.text_time, R.id.msg_image_avatar);
                return this.qbViewHolder;
            case 4:
                this.qbViewHolder = new QBMessagesAdapter.ImageAttachHolder(this.inflater.inflate(R.layout.list_item_attach_left, parent, false), R.id.show_image, R.id.pb, R.id.text_time, R.id.msg_image_avatar);
                return this.qbViewHolder;
            default:
                Log.d(TAG, "onCreateViewHolder case default");
                return this.onCreateCustomViewHolder(parent, viewType);
        }

    }

    @Override
    protected void onBindViewCustomHolder(QBMessageViewHolder holder, QBChatMessage chatMessage, int position) {
        int valueType = this.getItemViewType(position);
        switch(valueType) {
            case 1:
                this.onBindViewMsgRightHolderBc((QBMessagesAdapter.TextMessageHolder) holder, chatMessage, position);
                break;
            case 2:
                this.onBindViewMsgLeftHolderBc((QBMessagesAdapter.TextMessageHolder) holder, chatMessage, position);
                break;
            case 3:
                Log.i(TAG, "onBindViewHolder TYPE_ATTACH_RIGHT");
                this.onBindViewAttachRightHolderBc((QBMessagesAdapter.ImageAttachHolder) holder, chatMessage, position);
                break;
            case 4:
                Log.i(TAG, "onBindViewHolder TYPE_ATTACH_LEFT");
                this.onBindViewAttachLeftHolderBc((QBMessagesAdapter.ImageAttachHolder) holder, chatMessage, position);
                break;
        }
    }

    private void onBindViewAttachLeftHolderBc(ImageAttachHolder holder, QBChatMessage chatMessage, int position) {

    }

    private void onBindViewAttachRightHolderBc(ImageAttachHolder holder, QBChatMessage chatMessage, int position) {
        this.setDateSentAttach(holder, chatMessage);
        this.displayAttachment(holder, position);
        int valueType = this.getItemViewType(position);
        String avatarUrl = this.obtainAvatarUrl(valueType, chatMessage);
        if(avatarUrl != null) {
            this.displayAvatarImage(avatarUrl, holder.avatar);
        }
        this.setItemAttachClickListener(this.getAttachListenerByType(position), holder, this.getQBAttach(position), position);
    }

    private void onBindViewMsgRightHolderBc(TextMessageHolder holder, QBChatMessage chatMessage, int position) {
        holder.messageTextView.setText(chatMessage.getBody());
        String avatarUrl = this.obtainAvatarUrl(getItemViewType(position), chatMessage);
        if(avatarUrl != null) {
            this.displayAvatarImage(avatarUrl, (ImageView) holder.linkPreviewLayout);
        }
//        holder.messageTextView.setText(getSenderName(chatMessage));
//        Glide.with(this.context).load(url).placeholder(com.quickblox.ui.kit.chatmessage.adapter.R.drawable.placeholder_user).dontAnimate().into(imageView);
    }

    private void onBindViewMsgLeftHolderBc(TextMessageHolder holder, QBChatMessage chatMessage, int position) {
        holder.messageTextView.setText(chatMessage.getBody());
        String avatarUrl = this.obtainAvatarUrl(getItemViewType(position), chatMessage);
        if(avatarUrl != null) {
            this.displayAvatarImage(avatarUrl, (ImageView) holder.linkPreviewLayout);
        }
//        holder.messageTextView.setText(getSenderName(chatMessage));
    }

    public QBMessagesAdapter.QBMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder viewType:" + viewType);

        switch (viewType) {
            case 1:
//                this.qbViewHolder = new QBMessagesAdapter.TextMessageHolder(this.inflater.inflate(R.layout.list_item_text_right, parent, false), com.quickblox.ui.kit.chatmessage.adapter.R.id.msg_text_message, com.quickblox.ui.kit.chatmessage.adapter.R.id.msg_text_time_message, com.quickblox.ui.kit.chatmessage.adapter.R.id.msg_link_preview);
//                return this.qbViewHolder;
                this.qbViewHolder = new QBMessagesAdapter.TextMessageHolder(this.inflater.inflate(R.layout.list_item_text_right_bc, parent, false), R.id.text, R.id.text_time, R.id.icon);
                return this.qbViewHolder;
            case 2:
                this.qbViewHolder = new QBMessagesAdapter.TextMessageHolder(this.inflater.inflate(R.layout.list_item_text_left_bc, parent, false),R.id.text, R.id.text_time, R.id.icon);
                return this.qbViewHolder;
            case 3:
                this.qbViewHolder = new QBMessagesAdapter.ImageAttachHolder(this.inflater.inflate(R.layout.list_item_attach_right_bc, parent, false), R.id.show_image, R.id.pb, R.id.text_time, R.id.msg_image_avatar);
                return this.qbViewHolder;
            case 4:
                this.qbViewHolder = new QBMessagesAdapter.ImageAttachHolder(this.inflater.inflate(R.layout.list_item_attach_left_bc, parent, false), R.id.show_image, R.id.pb, R.id.text_time, R.id.msg_image_avatar);
                return this.qbViewHolder;

            default:
                Log.d(TAG, "onCreateViewHolder case default");
                return this.onCreateCustomViewHolder(parent, viewType);
        }

    }

    public void onViewRecycled(QBMessagesAdapter.QBMessageViewHolder holder) {
        if(holder.getItemViewType() == 2 || holder.getItemViewType() == 1) {
            QBMessagesAdapter.TextMessageHolder textMessageHolder = (QBMessagesAdapter.TextMessageHolder)holder;
            if(textMessageHolder.linkPreviewLayout.getTag() != null) {
                textMessageHolder.linkPreviewLayout.setTag((Object)null);
            }
        }

//        if(this.containerLayoutRes.get(holder.getItemViewType()) != 0) {
//            Glide.clear(holder.avatar);
//        }

//        super.onViewRecycled(holder);
    }

    private QBChatAttachClickListener getAttachListenerByType(int position) {
        QBAttachment attachment = this.getQBAttach(position);
        return !"photo".equalsIgnoreCase(attachment.getType()) && !"image".equalsIgnoreCase(attachment.getType())?("location".equalsIgnoreCase(attachment.getType())?this.attachLocationClickListener:("audio".equalsIgnoreCase(attachment.getType())?this.attachAudioClickListener:("video".equalsIgnoreCase(attachment.getType())?this.attachVideoClickListener:null))):this.attachImageClickListener;
    }

}