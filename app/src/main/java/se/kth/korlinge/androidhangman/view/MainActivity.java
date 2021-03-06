package se.kth.korlinge.androidhangman.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import se.kth.korlinge.androidhangman.R;

/**
 * Starting activity for the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Start the Game activity.
     * @param view
     */
    public void goToGame(View view) {

        //get token from firebase to send notifications from firebase console
        Log.e("token",FirebaseInstanceId.getInstance().getToken());

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}