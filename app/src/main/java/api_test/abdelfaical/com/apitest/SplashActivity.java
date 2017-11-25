package api_test.abdelfaical.com.apitest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_DELAY = 1500;
    private TextView textView;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Regular.ttf");

        textView = (TextView)findViewById(R.id.tvAppName);
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AuthentificationActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DELAY);
    }
}
