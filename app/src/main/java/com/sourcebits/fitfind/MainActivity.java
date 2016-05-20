package com.sourcebits.fitfind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIConstants;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIPreference;

import java.util.Arrays;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText mUsername, mPassword;
    private CallbackManager callbackManager;
    public static AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    public static ProfileTracker profileTracker;
    private LoginButton mFacebook;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref = UIPreference.getSharedPref(MainActivity.this);
        if(loginCheck()){
            startActivity(new Intent(MainActivity.this, HomeAcivity.class));
            this.finish();
        }
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        mUsername = (EditText)findViewById(R.id.user_name);
        mPassword = (EditText)findViewById(R.id.password);

        TextView signin = (TextView)findViewById(R.id.sign_in);
        mFacebook = (LoginButton)findViewById(R.id.facebook);
        Button linkedIn = (Button)findViewById(R.id.linkedin);

        signin.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        linkedIn.setOnClickListener(this);

        TextView register = (TextView)findViewById(R.id.register);
        String text = (getString(R.string.donot_have_account));

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, text.indexOf("Register"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        register.setHighlightColor(Color.BLACK);
        register.setText(ss);
        register.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean loginCheck(){
        String res = mPref.getString(UIConstants.USER_NAME, "");
        return (!res.equals(""));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.sign_in:
                break;
            case R.id.facebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                mFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeAcivity.class));
                        MainActivity.this.finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

                  accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if(currentAccessToken != null) {
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString(UIConstants.FB_ACCESS_TOKEN, currentAccessToken.getToken());
                    editor.commit();
                }
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();

                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(
                            Profile oldProfile,
                            Profile currentProfile) {
                        if(currentProfile != null) {
                            SharedPreferences.Editor editor = mPref.edit();
                            String name = currentProfile.getName();
                            editor.putString(UIConstants.USER_NAME, name);
                            editor.commit();
                        }
                    }
                };

                break;
            case R.id.linkedin:
                break;


        }
    }

    private void validation_check(){
        int user_name_length = mUsername.getText().length();
        int user_password = mPassword.getText().length();

        if(user_name_length<=0 || user_password <=0 ){
            Toast.makeText(this,getString(R.string.field_empty),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
