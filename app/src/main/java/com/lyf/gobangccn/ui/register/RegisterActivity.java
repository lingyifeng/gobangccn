package com.lyf.gobangccn.ui.register;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jiang.common.base.CommonActivity;
import com.jiang.common.widget.EditTextWithDelete;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.easemob.DemoHelper;
import com.lyf.gobangccn.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class RegisterActivity extends MVPBaseActivity {

    //
    @BindView(R.id.edit_account)
    EditTextWithDelete mEditAccount;
    @BindView(R.id.edit_password)
    EditTextWithDelete mEditPassword;
    @BindView(R.id.edit_password_again)
    EditTextWithDelete mEditPasswordAgain;
    @BindView(R.id.btn_register)
    Button mBtnRigister;

    public static void startAction(CommonActivity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        new ToolBarBuilder(this).setTitle(getString(R.string.regist))
                .setNavigationIcon(R.drawable.ic_back)
                .setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initInjector() {

    }


    @OnClick(R.id.btn_register)
    public void register() {
        String account = mEditAccount.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        String passwordAgain = mEditPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShort("帐号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("密码不能为空");
            return;
        }
        if (!password.equals(passwordAgain)) {
            ToastUtil.showShort("两次密码输入不一致");
            return;
        }
        Observable<String> accountObservable = Observable.just(account);
        Observable<String> passwordObservable = Observable.just(password);
        startProgressDialog();
        Observable.zip(accountObservable, passwordObservable, new Func2<String, String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String s, String s2) {
                try {
                    EMClient.getInstance().createAccount(account, password);
                } catch (HyphenateException e) {
                    return Observable.error(e);

                }
                return Observable.just(true);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booleanObservable -> booleanObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            stopProgressDialog();
                            if (aBoolean) {
                                ToastUtil.showShort("注册成功");
                                DemoHelper.getInstance().setCurrentUserName(account);
                            }
                        }, throwable -> {
                            stopProgressDialog();
                            if (!(throwable instanceof HyphenateException)) {
                                ToastUtil.showShort("注册失败");
                                return;
                            }
                            int errorCode = ((HyphenateException) throwable).getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                ToastUtil.showShort(R.string.network_anomalies);
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                ToastUtil.showShort(R.string.User_already_exists);
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                ToastUtil.showShort(R.string.registration_failed_without_permission);
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                ToastUtil.showShort(R.string.illegal_user_name);
                            } else {
                                ToastUtil.showShort(R.string.Registration_failed);
                            }
                        }));
    }
}
