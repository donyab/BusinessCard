package com.example.syarusov.businesscard;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String mailTextString;
    private static final int LAYOUT = R.layout.activity_main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Button button = findViewById(R.id.buttonSendMessage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });

        ImageButton buttonTelegram = findViewById(R.id.buttonTelegram);
        buttonTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mailAdres = findViewById(R.id.mailAdres);
                mailTextString = mailAdres.getText().toString();
                intentMessageTelegram(mailTextString);
            }
        });

        ImageButton buttonInstagram = findViewById(R.id.buttonInstagram);
        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenInstagram();
            }
        });
    }

    private void OpenInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.instagram.com"));
        startActivity(intent);
    }


    private void openMail() {
        EditText mailAdres = findViewById(R.id.mailAdres);
        mailTextString = mailAdres.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.MyMailAdres});
        intent.putExtra(Intent.EXTRA_SUBJECT, "from app");
        intent.putExtra(Intent.EXTRA_TEXT, mailTextString);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_LONG);
        } else {
            startActivity(intent);
        }


    }

    private void intentMessageTelegram(String msg) {
        final String appName = "org.telegram.messenger";

        final boolean isAppInstalled = isAppAvailable(this.getApplicationContext(), appName);
        if (isAppInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
            this.startActivity(Intent.createChooser(myIntent, "Share with"));
        } else {
            Toast.makeText(this, "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
