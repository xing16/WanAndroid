package com.xing.usercenter.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.module.usercenter.R;
import com.xing.module.usercenter.R2;
import com.xing.usercenter.bean.RegisterResult;
import com.xing.usercenter.contract.RegisterContract;
import com.xing.usercenter.presenter.RegisterPresenter;

import butterknife.BindView;

public class RegisterActivity extends BaseMVPActivity<RegisterPresenter>
        implements RegisterContract.View {
    @BindView(R2.id.et_register_username)
    EditText usernameEditText;
    @BindView(R2.id.et_register_password)
    EditText passwordEditText;
    @BindView(R2.id.et_register_repassword)
    EditText repasswordEditText;
    @BindView(R2.id.btn_register)
    Button registerBtn;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void init() {
        super.init();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String repassword = repasswordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(mContext, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password, repassword)) {
                    Toast.makeText(mContext, "两次密码不相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.register(username, password, repassword);
            }
        });
    }

    @Override
    public void registerSuccess(RegisterResult result) {
        if (result == null) {
            return;
        }
        Toast.makeText(mContext, "register success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
