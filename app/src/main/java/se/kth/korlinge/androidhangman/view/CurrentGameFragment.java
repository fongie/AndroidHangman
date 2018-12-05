package se.kth.korlinge.androidhangman.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import se.kth.korlinge.androidhangman.R;
import se.kth.korlinge.androidhangman.viewmodel.CurrentGameViewModel;

/**
 * Main game UI fragment.
 */
public class CurrentGameFragment extends Fragment implements View.OnClickListener {

    private CurrentGameViewModel mViewModel;
    private Button guessButton;
    private EditText guessInput;

    //when creating the fragment. initialize essential components of the fragment that are retained when paused or stopped
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called on clicking the "Guess" button. The reason this class implements onclicklistener.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (guessInput.getText().toString().length() < 1) {
            return;
        }
        mViewModel.makeGuess(guessInput.getText().toString());
    }

    /**
     * On creating the fragment. Set listeners and save components present in the view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    //first time fragment is called to draw its ui
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_game_fragment, container, false);
        guessButton = (Button) view.findViewById(R.id.guessButton);
        guessButton.setOnClickListener(this);
        guessInput = view.findViewById(R.id.guess_input);
        return view;
    }

    /**
     * When parent activity is created, initiate the viewmodel and set this class to listen to the viewmodel for changes, and on changes happening, update the UI
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CurrentGameViewModel.class);
        mViewModel.init();

        //observers that update UI automatically when viewmodel is updated
        mViewModel.getWord().observe(this, word -> {
            //update UI
            TextView wordView = getView().findViewById(R.id.word_hint);
            wordView.setText(word);
        });

        mViewModel.getRemainingAttempts().observe(this, attempts -> {
            //update UI
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.remaining_attempts_string_start));
            sb.append(" ");
            sb.append(attempts);
            sb.append(" ");
            sb.append(getResources().getString(R.string.remaining_attempts_string_end));

            TextView attemptsView = getView().findViewById(R.id.remaining_attempts);
            attemptsView.setText(sb.toString());
        });

        mViewModel.getScore().observe(this, score -> {
            //update UI
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.score_string));
            sb.append(" ");
            sb.append(score);

            TextView scoreView = getView().findViewById(R.id.score);
            scoreView.setText(sb.toString());
        });
    }
}
