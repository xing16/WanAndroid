package com.xing.usercenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.commonbase.util.SoftKeyboardUtil;
import com.xing.module.usercenter.R;
import com.xing.module.usercenter.R2;
import com.xing.usercenter.bean.LoginResult;
import com.xing.usercenter.contract.LoginContract;
import com.xing.usercenter.presenter.LoginPresenter;

import butterknife.BindView;

public class LoginActivity extends BaseMVPActivity<LoginPresenter>
        implements LoginContract.View, View.OnClickListener {

    @BindView(R2.id.btn_login)
    Button loginBtn;
    @BindView(R2.id.tv_register)
    TextView registerTxtView;
    @BindView(R2.id.cb_login_pwd_visible)
    CheckBox pwdVisibleCheckBox;
    @BindView(R2.id.et_login_username)
    EditText usernameEditText;
    @BindView(R2.id.et_login_password)
    EditText passwordEditText;

    @Override
    protected int getLayoutResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        }
//        UETool.showUETMenu();
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    protected void init() {
        super.init();
        // 添加下划线
        registerTxtView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        String cacheUsername = presenter.readUsernamePassword(Constants.USERNAME);
//        String cachePassword = presenter.readUsernamePassword(Constants.PASSWORD);
        String cacheUsername = "xing123456";
        String cachePassword = "123456";
        usernameEditText.setText(cacheUsername);
        usernameEditText.setSelection(cacheUsername.length());
        passwordEditText.setText(cachePassword);
        passwordEditText.setSelection(cachePassword.length());

        loginBtn.setOnClickListener(this);
        registerTxtView.setOnClickListener(this);
        pwdVisibleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEditText.setSelection(passwordEditText.getText().toString().length());
                } else {
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEditText.setSelection(passwordEditText.getText().toString().length());
                }
            }
        });
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    SoftKeyboardUtil.hideSoftKeyboard(passwordEditText);
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            login();
        } else if (i == R.id.tv_register) {
            gotoRegisterActivity();
        }
    }

    /**
     * 开始登陆
     */
    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, R.string.please_input_username, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, R.string.please_input_password, Toast.LENGTH_LONG).show();
            return;
        }
        presenter.login(username, password);
    }


    @Override
    public void loginSuccess(LoginResult result) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        presenter.saveUsernamePassword(username, password);
        gotoMainActivity();
    }


    /**
     * 跳转注册页面
     */
    private void gotoRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转主界面
     */
    private void gotoMainActivity() {
        ARouter.getInstance().build("/main/activity").navigation();
        finish();
    }

    @Override
    public void showLoading() {
//        dialog = new FlagDialog.Builder()
//                .layoutResId(R.layout.dialog_login)
//                .width(DensityUtils.dp2px(mContext, 150))
//                .height(DensityUtils.dp2px(mContext, 120))
//                .setOutsideCancel(false)
//                .dimAmount(0f)
//                .create()
//                .show(((AppCompatActivity) mContext).getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        passwordEditText = null;
        pwdVisibleCheckBox = null;
    }
}
