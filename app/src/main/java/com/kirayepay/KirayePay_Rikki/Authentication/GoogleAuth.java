package com.kirayepay.KirayePay_Rikki.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kirayepay.KirayePay_Rikki.MainActivity;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.SocialLoginResponse;
import com.kirayepay.KirayePay_Rikki.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rikki on 8/23/17.
 */

public class GoogleAuth extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient mGoogleApiClient;
    private SignInButton googleSignInButton;
    private String googleIdToken;
    private static final int RC_SIGN_IN = 9001;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_google,container,false);
        googleSignInButton =(SignInButton) v.findViewById(R.id.google_signin_button);
        googleSignInButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage((FragmentActivity) getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(), "Unable To Google Sign in", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage((FragmentActivity) getActivity());
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.google_signin_button :
                signIn();
                break;
        }
    }

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                loginWithCredentials(acct.getEmail(),acct.getId(),acct.getDisplayName());
            }
        }
    }

    private void loginWithCredentials(final String email, final String id, final String name)
    {
        Toast.makeText(getActivity(),"Please Wait .....",Toast.LENGTH_LONG).show();

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<SocialLoginResponse> loginCall = apiInterface.userSocialLogin(name,email,id,"google");
        loginCall.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {

                storeInfoInSharedPreference(email,id,name,Acquire.GOOGLE_AUTH);

            }
            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Connection Error !!!",Toast.LENGTH_LONG).show();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
