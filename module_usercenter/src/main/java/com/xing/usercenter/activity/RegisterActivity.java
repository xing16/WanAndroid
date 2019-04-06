package com.xing.usercenter.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.module.usercenter.R;
import com.xing.usercenter.bean.RegisterResult;
import com.xing.usercenter.contract.RegisterContract;
import com.xing.usercenter.presenter.RegisterPresenter;

@Route(path = "/user/RegisterActivity")
public class RegisterActivity extends BaseMVPActivity<RegisterPresenter>
        implements RegisterContract.View {
    EditText usernameEditText;
    EditText passwordEditText;
    EditText repasswordEditText;
    Button registerBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.register);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        usernameEditText = findViewById(R.id.et_register_username);
        passwordEditText = findViewById(R.id.et_register_password);
        repasswordEditText = findViewById(R.id.et_register_repassword);
        registerBtn = findViewById(R.id.btn_register);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
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
