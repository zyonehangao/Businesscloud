package com.cloud.shangwu.businesscloud.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.cloud.shangwu.businesscloud.base.BasePresenter;
import com.cloud.shangwu.businesscloud.http.RetrofitHelper;
import com.cloud.shangwu.businesscloud.mvp.contract.ToDoListContract;
import com.cloud.shangwu.businesscloud.mvp.model.bean.Friend;
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ToDoListPresenter extends BasePresenter<ToDoListContract.View> implements ToDoListContract.Presenter {
    private final ToDoListContract.View view;

    @Override
    public void getToDoList(@NotNull  int page, @NotNull int size, @NotNull String uid,@NotNull boolean refresh) {
        RetrofitHelper.INSTANCE.getService().toDoList(page, size, uid)
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .subscribe(new Observer<ToDoListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ToDoListBean bean) {
                        view.getList(bean,refresh);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public ToDoListPresenter(ToDoListContract.View view) {
        this.view = view;
    }


    @Override
    public void addFriends(String uid, int fuid, int status) {
        RetrofitHelper.INSTANCE.getService().addFriends(uid,fuid,status)
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .subscribe(new Observer<HttpResult<Friend>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<Friend> friendHttpResult) {
                        int code = friendHttpResult.getCode();
                        view.addFriends(friendHttpResult.getCode());
                        Log.i("text","code===="+code);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
