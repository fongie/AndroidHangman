package se.kth.korlinge.androidhangman.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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

    /*
    public StatusReport makeGuess() {
        return new StatusReport(5,5,0,new ArrayList<LetterPosition>());
    }
    */

    public void run() {
        Log.e("set", "HELLO FROM GAMEREPO RUN THREAD ");
        int i = 0;
        while (running) {
            if (i == 0) {
                conn = new Connection();
                StatusReport report = conn.start();
                score.postValue(report.getScore());
                remainingAttempts.postValue(report.getRemainingAttempts());
                formatAndSetWord(report);
                i++;
            }
        }
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
