package com.tsystems.key4iot.exampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.motassemja.moauth.Authenticator;
import com.github.motassemja.moauth.MoAuthConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            MoAuthConfig config = new MoAuthConfig(new ArrayList<String>()
                    , new URI("http://brentertainment.com/oauth2/lockdin/token"), null, "demoapp", "demopass");
            Authenticator authenticator = new Authenticator(config, this, "Test");
            authenticator.authenticateWithUsername("demouser", "testpass", new Authenticator.AuthenticationCallback() {
                @Override
                public void onAuthenticationCompleted(boolean flag, Exception e) {
                    if (flag) Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
