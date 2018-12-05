package se.kth.korlinge.androidhangman.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import se.kth.korlinge.androidhangman.R;

/**
 * Main game activity. Parent to the Game UI fragment (CurrentGameFragment)
 */
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
