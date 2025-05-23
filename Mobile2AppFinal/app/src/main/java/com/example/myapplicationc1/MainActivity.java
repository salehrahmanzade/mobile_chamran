package com.example.myapplicationc1;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.net.Uri;
import android.content.IntentFilter;
import android.os.Build;


public class MainActivity extends AppCompatActivity {

    TextView text1;
    TextView tvWelcome;
    Button button1;
    MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tvWelcome = findViewById(R.id.TextWelcome);
        String _username = getIntent().getStringExtra("USERNAME");
        tvWelcome.setText("خوش آمدید، " + _username + "!");

        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText("Hello Chamran");
                Toast.makeText(MainActivity.this,"Thanks for your click",Toast.LENGTH_SHORT).show();
            }
        });

        // دکمه مراجعه به وب‌سایت
        findViewById(R.id.buttonWebsite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://salehrahmanzade.com"));
                startActivity(intent);
            }
        });

        // دکمه تماس تلفنی
        findViewById(R.id.buttonCallUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+09139437802"));
                startActivity(intent);
            }
        });

        // دکمه ارسال ایمیل
        findViewById(R.id.buttonEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:salehrahmanzade@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                startActivity(intent);
            }
        });


        Button btnLogin = findViewById(R.id.btnPermition);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PermitionActivity.class);
                    startActivity(intent);
                    finish();
            }
        });
    }

    @Override
    protected void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(myReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }
}



