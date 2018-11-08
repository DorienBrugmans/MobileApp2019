package mobdev.smartmenu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mobdev.smartmenu.R;

public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = (ImageView)findViewById(R.id.splash);

        anim = AnimationUtils.loadAnimation(this, R.anim.animsplashscreen);
        imageView.setAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent;


                        intent = new Intent(Splashscreen.this,
                                AccountSignUpActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };

        splashTread.start();
    }

}
