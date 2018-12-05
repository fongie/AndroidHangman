package se.kth.korlinge.androidhangman.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import se.kth.korlinge.androidhangman.DTO.LetterPosition;
import se.kth.korlinge.androidhangman.DTO.StatusReport;
import se.kth.korlinge.androidhangman.repository.GameRepository;

/**
 * Provides data for the UI component "CurrentGameFragment"
 */
public class CurrentGameViewModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingAttempts = new MutableLiveData<>();
    private final MutableLiveData<String> word = new MutableLiveData<>();
    private GameRepository gameRepository;

    public void init() {
        System.out.println("INITIATING VIEWMODEL");


        gameRepository = new GameRepository();
        StatusReport statusReport = gameRepository.startGame();

        score.setValue(statusReport.getScore());
        remainingAttempts.setValue(statusReport.getRemainingAttempts());


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

    public LiveData<Integer> getScore() {
        return score;
    }

    public LiveData<Integer> getRemainingAttempts() {
        return remainingAttempts;
    }

    public LiveData<String> getWord() {
        return word;
    }
}
