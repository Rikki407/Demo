package com.kirayepay.kirayepay101.Authentication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kirayepay.kirayepay101.MainActivity;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.SocialLoginResponse;
import com.kirayepay.kirayepay101.R;
import org.json.JSONObject;
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
//        callingKey = getArguments().getString(MyConstants.CALLING_KEY);
        mContext = getActivity();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) v.findViewById(R.id.fb_signin_button);
        //if using a fragment
        loginButton.setFragment(this);

        /*
            changing facebook login  button attributes below
         */
//        ////////////////////////////////////////////////
//        loginButton.setText("Sign in");
//        loginButton.setBackgroundResource(R.drawable.bottom_nav_home);
//        ////////////////////////////////////////////////

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
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.e("FB_user_id",""+loginResult.getAccessToken().getUserId());
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    Log.e("Error","!!!!");
                                } else {
                                    Log.e("user_detais",""+me.optString("email")+""+me.optString("name"));
                                    loginWithCredentials(me.optString("email"),loginResult.getAccessToken().getUserId(),me.optString("name"));
                                }
                            }
                        });
                Bundle bundle=new Bundle();
                bundle.putString("fields","email,name");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e("FB", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("FB", "facebook:onError", error);
                // ...
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
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<SocialLoginResponse> loginCall = apiInterface.userSocialLogin(name,email,id,"facebook");
        loginCall.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                Log.e("FacebookResponse","Logged In");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                storeInfoInSharedPreference(email,id,name,Acquire.FACEBOOK_AUTH);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                Log.e("FacebookResponse","Error "+t.getCause());
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

    }
}
