package com.kirayepay.kirayepay101.Authentication;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.EmailRegisterResponse;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rikki on 8/24/17.
 */

public class EmailSignUpFragment extends Fragment
{
    EditText name,email_address,password,confirm_password,mobnum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_email_signup,container,false);
        name = (EditText) v.findViewById(R.id.signup_name);
        email_address = (EditText) v.findViewById(R.id.signup_emailaddress);
        password = (EditText) v.findViewById(R.id.signup_password);
        confirm_password = (EditText) v.findViewById(R.id.signup_confirmpassword);
        mobnum = (EditText) v.findViewById(R.id.signup_mobnum);
        return v;
    }
    private void emailLogin(final String name,String password,String phone,String email,String password_confirmation)
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<EmailRegisterResponse>> emailRegisterCall = apiInterface.userEmailRegister(name,password,phone,email,password_confirmation);
        emailRegisterCall.enqueue(new Callback<ArrayList<EmailRegisterResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<EmailRegisterResponse>> call, Response<ArrayList<EmailRegisterResponse>> response) {
//                Log.e("EmailResponse","Logged In");
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                storeInfoInSharedPreference(loginResponse.getEmail(),loginResponse.getUserid(),loginResponse.getName(),Acquire.EMAIL_AUTH);
//                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ArrayList<EmailRegisterResponse>> call, Throwable t) {
                Log.e("EmailResponse","Error "+t.getCause());
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
