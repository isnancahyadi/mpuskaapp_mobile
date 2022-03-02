package com.arcmedtek.mpuskaapp_mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.arcmedtek.mpuskaapp_mobile.R;

public class Login extends AppCompatActivity {

    ImageView _web, _telegram, _instagram, _twitter, _youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _web = findViewById(R.id.btn_web);
        _telegram = findViewById(R.id.btn_telegram);
        _instagram = findViewById(R.id.btn_instagram);
        _twitter = findViewById(R.id.btn_twitter);
        _youtube = findViewById(R.id.btn_youtube);

        _socmed();
    }

    private void _socmed() {
        _web.setOnClickListener(v -> {
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tif.uad.ac.id"));
            startActivity(web);
        });

        _telegram.setOnClickListener(v -> {
            Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/infotifuad"));
            startActivity(telegram);
        });

        _instagram.setOnClickListener(v -> {
            Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/informatika.uad/"));
            startActivity(instagram);
        });

        _twitter.setOnClickListener(v -> {
            Intent twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ProdiTIFUAD"));
            startActivity(twitter);
        });

        _youtube.setOnClickListener(v -> {
            Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCoE-ydbWBsV0OU-OXpocQIA/"));
            startActivity(youtube);
        });
    }
}