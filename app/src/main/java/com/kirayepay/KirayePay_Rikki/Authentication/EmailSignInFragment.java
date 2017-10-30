package com.kirayepay.KirayePay_Rikki.Authentication;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.MainActivity;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.EmailLoginResponse;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by rikki on 8/23/17.
 */

public class EmailSignInFragment extends Fragment implements View.OnClickListener
{
    EditText user_email,user_password;
    Button signInButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_email_signin,container,false);
        user_email = (EditText) v.findViewById(R.id.user_email);
        user_password = (EditText) v.findViewById(R.id.user_password);
        signInButton = (Button) v.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in_button :
                if(user_email.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(user_password.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                emailLogin(user_email.getText().toString(),user_password.getText().toString());
                break;
        }
    }

    private void emailLogin(final String user_email, String user_password)
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<EmailLoginResponse>> emailLoginCall = apiInterface.userEmailLogin(user_email,user_password);
        emailLoginCall.enqueue(new Callback<ArrayList<EmailLoginResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<EmailLoginResponse>> call, Response<ArrayList<EmailLoginResponse>> response) {
                    EmailLoginResponse loginResponse = response.body().get(0);
                    storeInfoInSharedPreference(loginResponse.getEmail(), loginResponse.getUserid(), loginResponse.getName(), Acquire.EMAIL_AUTH);
            }
            @Override
            public void onFailure(Call<ArrayList<EmailLoginResponse>> call, Throwable t) {
                Toast.makeText(getActivity(),"Username or password incorrect",Toast.LENGTH_SHORT).show();

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
