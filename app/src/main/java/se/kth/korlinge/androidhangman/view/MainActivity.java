package se.kth.korlinge.androidhangman.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import se.kth.korlinge.androidhangman.R;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "se.kth.korlinge.androidhangman.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        TextView editText = (TextView) findViewById(R.id.textView4);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
