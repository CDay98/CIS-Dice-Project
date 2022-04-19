import java.net.SocketOption;
import java.util.Arrays;

public class PokerDiceManager {
    public static void main(String[] args){
            final int THREE_OF_A_KIND = 25;
            final int FOUR_OF_A_KIND = 40;
            final int FIVE_OF_A_KIND = 50;
            final int FULL_HOUSE = 35;
            final int SMALL_STRAIGHT = 30;
            final int LARGE_STRAIGHT = 45;
            int oldScore;
            System.out.println ("Testing begings");
            PokerDice game = new PokerDice();
            //********** phase 1 testing ************
            // testing the constructor
            assert game.getDice().size() == 5 : "dice should be an ArrayList of five";
            //********** phase 3 testing ************
            // testing fiveOfAKind - OK
            game.setDice (new int [] {2,2,2,2,2});
            game.checkFiveOfAKind();
            assert game.getScore() == FIVE_OF_A_KIND : "Score should increment by " +
                    FIVE_OF_A_KIND ;
            // testing fiveOfAKind - NOT OK
            oldScore = game.getScore();
            game.setDice (new int [] {2,6,2,2,2});
            game.checkFiveOfAKind();
            assert game.getScore() == oldScore : "Not fiveOfAKind "  ;
            System.out.println ("Testing ends");
        }

    }
