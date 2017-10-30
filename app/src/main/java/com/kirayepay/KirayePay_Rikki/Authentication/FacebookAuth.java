package com.kirayepay.KirayePay_Rikki.Authentication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kirayepay.KirayePay_Rikki.MainActivity;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.SocialLoginResponse;
import com.kirayepay.KirayePay_Rikki.R;
import org.json.JSONObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by rikki on 8/23/17.
 */

public class FacebookAuth extends Fragment
{
    LoginButton loginButton;
    Context mContext;
    private CallbackManager mCallbackManager;
    private String callingKey;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_facebook,container,false);
        mContext = getActivity();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) v.findViewById(R.id.fb_signin_button);
        loginButton.setFragment(this);

        float fbIconScale = 1.45F;
        Drawable drawable = getActivity().getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*fbIconScale),
                (int)(drawable.getIntrinsicHeight()*fbIconScale));
        loginButton.setCompoundDrawables(drawable, null, null, null);
        loginButton.setCompoundDrawablePadding(getActivity().getResources().
                getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        loginButton.setPadding(
                loginButton.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_lr),
                loginButton.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_top),
                loginButton.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_lr),
                loginButton.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_bottom));

        loginButton.setReadPermissions(Collections.singletonList("email"));

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() == null) {
                                    loginWithCredentials(me.optString("email"),loginResult.getAccessToken().getUserId(),me.optString("name"));
                                }
                            }
                        });
                Bundle bundle=new Bundle();
                bundle.putString("fields","name, email");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
        return v;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithCredentials(final String email, final String id , final String name)
    {
        Toast.makeText(mContext,"please wait ... ",Toast.LENGTH_LONG).show();
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<SocialLoginResponse> loginCall = apiInterface.userSocialLogin(name,email,id,"facebook");
        loginCall.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                storeInfoInSharedPreference(email,id,name,Acquire.FACEBOOK_AUTH);

            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                Toast.makeText(mContext,"Connection Error !!!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void  storeInfoInSharedPreference(String user_email,String user_id, String user_name, int Auth_Method) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE).edit();
        editor.putString(Acquire.USER_EMAIL,user_email);
        editor.putString(Acquire.USER_ID,user_id);
        editor.putString(Acquire.USER_NAME,user_name);
        editor.putInt(Acquire.USER_AUTH_METHOD,Auth_Method);
        editor.apply();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
