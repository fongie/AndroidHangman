package se.kth.korlinge.androidhangman.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import DTO.Guess;
import se.kth.korlinge.androidhangman.repository.GameRepository;

/**
 * Provides data for the UI component "CurrentGameFragment"
 * If the mutable live data objects are updated with postValue, UI will update
 */
public class CurrentGameViewModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingAttempts = new MutableLiveData<>();
    private final  MutableLiveData<String> word = new MutableLiveData<>();
    private GameRepository gameRepository;

    /**
     * Initiate the viewmodel
     */
    public void init() {
        gameRepository = new GameRepository(getScore(), getRemainingAttempts(), getWord());
        gameRepository.start();
    }
    /**
     * Make a Guess
     * @param guess
     */
    public void makeGuess(String guess) {
        if (guess.trim().length() == 1) {
            Guess g = new Guess(guess.charAt(0));
            gameRepository.makeGuess(g);
        } else {
            Guess g = new Guess(guess.trim());
            gameRepository.makeGuess(g);
        }
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

}
