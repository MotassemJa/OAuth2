package com.github.motassemja.exampleapp;

import android.os.Bundle;
import android.util.Log;

import com.github.motassemja.moauth.Authenticator;
import com.github.motassemja.moauth.MoAuthConfig;
import com.tsystems.key4iot.exampleapp.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MoAuthConfig moAuthConfig = new MoAuthConfig("http://brentertainment.com/oauth2/lockdin/token"
                , "demouser", "testpass");
        Authenticator authenticator = new Authenticator(moAuthConfig);

        final Disposable disposable = authenticator.authenticate()
                .subscribe(moAuthTokenResult -> {
                    Log.d("Token", moAuthTokenResult.getAccessToken());
                });
    }

}
