package se.kth.korlinge.androidhangman.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import java.util.concurrent.CompletableFuture;

import DTO.Guess;
import DTO.LetterPosition;
import DTO.StatusReport;
import se.kth.korlinge.androidhangman.net.Connection;

/**
 * Handles all data fetching calls, as well as threading them to make sure UI does not block.
 */
public class GameRepository  extends Thread {
    private final MutableLiveData<Integer> score;
    private final MutableLiveData<Integer> remainingAttempts;
    private final MutableLiveData<String> word;
    private Connection conn;

    private boolean running = true;

    public GameRepository(MutableLiveData<Integer> score, MutableLiveData<Integer> remainingAttempts, MutableLiveData<String> word) {
        this.score = score;
        this.remainingAttempts = remainingAttempts;
        this.word = word;
    }

    public void makeGuess(Guess guess) {
        CompletableFuture.runAsync(() -> {
            StatusReport reply = conn.makeGuess(guess);
            updateViewModel(reply);
        });
    }

    private void updateViewModel(StatusReport statusReport) {
        score.postValue(statusReport.getScore());
        remainingAttempts.postValue(statusReport.getRemainingAttempts());
        formatAndSetWord(statusReport);
    }

    public void start() {
        /*
        Log.e("set", "HELLO FROM GAMEREPO RUN THREAD ");
        while (running) {
            if (conn == null) {
                conn = new Connection();
                StatusReport report = conn.start();
                updateViewModel(report);
            }
        }
        */
        CompletableFuture.runAsync(() -> {
            if (conn == null) {
                conn = new Connection();
                StatusReport report = conn.start();
                updateViewModel(report);
            }
        });
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
        this.word.postValue(word.toString());
    }
}
