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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kirayepay.kirayepay101.MainActivity;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.EmailRegisterResponse;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rikki on 8/24/17.
 */

public class EmailSignUpFragment extends Fragment {
    EditText name, email_address, password, confirm_password, mobnum;
    Button register_user_bttn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_email_signup, container, false);
        name = (EditText) v.findViewById(R.id.signup_name);
        email_address = (EditText) v.findViewById(R.id.signup_emailaddress);
        password = (EditText) v.findViewById(R.id.signup_password);
        confirm_password = (EditText) v.findViewById(R.id.signup_confirmpassword);
        mobnum = (EditText) v.findViewById(R.id.signup_mobnum);
        register_user_bttn = (Button) v.findViewById(R.id.register_user_button);
        register_user_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name == null || name.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide a name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email_address == null || email_address.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide a email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mobnum == null || mobnum.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide your contact number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password == null || password.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide a password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (confirm_password == null || confirm_password.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please confirm your password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!confirm_password.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(getActivity(), "Confirm Password do not match !!!", Toast.LENGTH_LONG).show();
                    return;
                }
                emailLogin(name.getText().toString(), password.getText().toString(), mobnum.getText().toString(), email_address.getText().toString(), confirm_password.getText().toString());
            }
        });
        return v;
    }

    private void emailLogin(final String name, String password, String phone, final String email, String password_confirmation) {
        Log.e("EmailResponse", name + " " + password + " " + phone + " " + email + " " + password_confirmation);

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<EmailRegisterResponse> emailRegisterCall = apiInterface.userEmailRegister(name, password, phone, email, password_confirmation);
        emailRegisterCall.enqueue(new Callback<EmailRegisterResponse>() {
            @Override
            public void onResponse(Call<EmailRegisterResponse> call, Response<EmailRegisterResponse> response) {
                if (response.body().getError() != null) {
                    if (response.body().getError().getEmail() != null)
                        Toast.makeText(getActivity(), response.body().getError().getEmail().get(0), Toast.LENGTH_SHORT).show();
                    if (response.body().getError().getPhone() != null)
                        Toast.makeText(getActivity(), response.body().getError().getPhone().get(0), Toast.LENGTH_SHORT).show();
                    if (response.body().getError().getPassword() != null)
                        Toast.makeText(getActivity(), response.body().getError().getPassword().get(0), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    storeInfoInSharedPreference(email, "" + response.body().getId(), name, Acquire.EMAIL_AUTH);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<EmailRegisterResponse> call, Throwable t) {
                Log.e("EmailResponse", "Error " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void storeInfoInSharedPreference(String user_email, String user_id, String user_name, int Auth_Method) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Acquire.USER_DETAILS, MODE_PRIVATE).edit();
        editor.putString(Acquire.USER_EMAIL, user_email);
        editor.putString(Acquire.USER_ID, user_id);
        editor.putString(Acquire.USER_NAME, user_name);
        editor.putInt(Acquire.USER_AUTH_METHOD, Auth_Method);
        editor.apply();

    }
}
