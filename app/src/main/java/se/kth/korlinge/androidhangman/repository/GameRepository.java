package se.kth.korlinge.androidhangman.repository;

import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.CompletableFuture;

import DTO.Guess;
import DTO.LetterPosition;
import DTO.StatusReport;
import se.kth.korlinge.androidhangman.net.Connection;

/**
 * Handles all data fetching calls, as well as threading them to make sure UI does not block.
 */
public class GameRepository  {
    private final MutableLiveData<Integer> score;
    private final MutableLiveData<Integer> remainingAttempts;
    private final MutableLiveData<String> word;
    private Connection conn;

    /**
     * Constructor. Needs to be passed observer objects from the ViewModel to update them on data fetch.
     * @param score
     * @param remainingAttempts
     * @param word
     */
    public GameRepository(MutableLiveData<Integer> score, MutableLiveData<Integer> remainingAttempts, MutableLiveData<String> word) {
        this.score = score;
        this.remainingAttempts = remainingAttempts;
        this.word = word;
    }

    /**
     * Send a Guess to the server and then update the view model with the new game status.
     * @param guess
     */
    public void makeGuess(Guess guess) {
        CompletableFuture.runAsync(() -> {
            StatusReport reply = conn.makeGuess(guess);
            updateViewModel(reply);
        });
    }

    /**
     * Start a new game and update the viewmodel with the game status.
     */
    public void start() {
        CompletableFuture.runAsync(() -> {
            if (conn == null) {
                conn = new Connection();
                StatusReport report = conn.start();
                updateViewModel(report);
            }
        });
    }
    private void updateViewModel(StatusReport statusReport) {
        score.postValue(statusReport.getScore());
        remainingAttempts.postValue(statusReport.getRemainingAttempts());
        formatAndSetWord(statusReport);
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
