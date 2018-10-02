package mobdev.smartmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: started.");
        ImageView firstImage=(ImageView) findViewById(R.id.image);

        int imageResource=getResources().getIdentifier("@drawable/foto",null,this.getPackageName());
        firstImage.setImageResource(imageResource);
    }
}
