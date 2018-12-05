package se.kth.korlinge.androidhangman.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.function.Consumer;

import DTO.Guess;
import DTO.LetterPosition;
import DTO.StatusReport;
import se.kth.korlinge.androidhangman.net.Connection;
import se.kth.korlinge.androidhangman.repository.GameRepository;
import se.kth.korlinge.androidhangman.repository.InfoSetter;

/**
 * Provides data for the UI component "CurrentGameFragment"
 */
public class CurrentGameViewModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingAttempts = new MutableLiveData<>();
    private final  MutableLiveData<String> word = new MutableLiveData<>();
    private GameRepository gameRepository;
    private Connection conn;

    public void init() {
        Log.e("set", "HELLO FROM VIEWMODEL INIT");
        gameRepository = new GameRepository(getScore(), getRemainingAttempts(), getWord());
        Thread thread = new Thread(gameRepository);
        thread.start();

    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public MutableLiveData<Integer> getRemainingAttempts() {
        return remainingAttempts;
    }

    public MutableLiveData<String> getWord() {
        return word;
    }

    private void formatAndSetWord(StatusReport statusReport) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < statusReport.getWordLength(); i++) {
            word.append("_");
        }
        for (LetterPosition lp : statusReport.getCorrectLetters()) {
            word.setCharAt(lp.getPosition(), lp.getLetter());
        }

        for (int i = 1; i < statusReport.getWordLength() * 2; i += 2) {
            word.insert(i, " ");
        }
        this.word.setValue(word.toString());
    }


}
