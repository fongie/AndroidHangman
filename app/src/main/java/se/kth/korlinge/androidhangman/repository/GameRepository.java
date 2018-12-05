package se.kth.korlinge.androidhangman.repository;

import java.util.ArrayList;

import se.kth.korlinge.androidhangman.DTO.LetterPosition;
import se.kth.korlinge.androidhangman.DTO.StatusReport;

public class GameRepository {

    public StatusReport startGame() {
        return new StatusReport(5,5,0,new ArrayList<LetterPosition>());
    }
    public StatusReport makeGuess() {
        return new StatusReport(5,5,0,new ArrayList<LetterPosition>());
    }
}
