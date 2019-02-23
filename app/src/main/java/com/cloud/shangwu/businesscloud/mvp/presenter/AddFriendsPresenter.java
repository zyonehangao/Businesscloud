package com.cloud.shangwu.businesscloud.mvp.presenter;

import com.cloud.shangwu.businesscloud.base.BasePresenter;
import com.cloud.shangwu.businesscloud.http.RetrofitHelper;
import com.cloud.shangwu.businesscloud.mvp.contract.AddFriendsContract;
import com.cloud.shangwu.businesscloud.mvp.model.bean.Friend;
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddFriendsPresenter extends BasePresenter<AddFriendsContract.View> implements AddFriendsContract.Presenter {
    private final AddFriendsContract.View view;

    @Override
    public void sendFriendsMessage(@NotNull String type, @NotNull String fuid, @NotNull String message, @NotNull String uid) {
        RetrofitHelper.INSTANCE.getService().sendFriendsMessage(uid, type, fuid, message)
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .subscribe(new Observer<HttpResult<Friend>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<Friend> friendHttpResult) {
                        if (friendHttpResult.getCode() == 200) {
                            view.sendFriendMessage(friendHttpResult.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void searchFriends(int page, @NotNull String message, int count, boolean isrefresh) {
        RetrofitHelper.INSTANCE.getService().searchUser(page, count, message)
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .subscribe(new Observer<ToDoListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ToDoListBean bean) {
                        view.searchFriends(bean,isrefresh);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public AddFriendsPresenter(AddFriendsContract.View view) {
        this.view = view;
    }


}
