import java.util.ArrayList;
import java.util.HashMap;

public class PokerDice {
    private int rolls;
    private int score;
    private int rounds;
    private ArrayList<GVdie> dice;
    private HashMap<Integer, Integer> tally;

    //constants
    private final static int FIVE_OF_A_KIND = 50;
    private final static int FOUR_OF_A_KIND = 40;
    private final static int THREE_OF_A_KIND = 25;
    private final static int FULL_HOUSE = 35;
    private final static int SMALL_STRAIGHT = 30;
    private final static int LARGE_STRAIGHT = 45;
    private final static int NUM_DICE = 5;

    public PokerDice(){
        dice = new ArrayList<GVdie>();
        for(int i = 0; i < NUM_DICE; i++){
            dice.add(new GVdie());
        }
        tally = new HashMap<Integer, Integer>();
        resetGame();
    }

    public void resetGame() {
        rolls = 0;
        rounds = 0;
        score = 0;

        //set dice to blank
        for(GVdie d : dice){
            d.setBlank();
            d.setHeld(false);
        }

        //set tally to zeros
        for(int i = 0; i < 6; i++){
            tally.put(i + 1, 0);
        }
    }

    public int getNumRolls() {
        return rolls;
    }

    public int getNumRounds() {
        return rounds;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<GVdie> getDice() {
        return dice;
    }

    public boolean okToRoll(){
        return rolls < 3;
    }

    public boolean gameOver(){
        return rounds == 7;
    }

    public String diceToString (){
        String dice_string = "[";

        for(int i = 0; i < dice.size(); i++){
            if(i == dice.size() - 1)
                dice_string += dice.get(i).getValue();
            else{
                dice_string += dice.get(i).getValue() + ",";
            }
        }
        dice_string += "]";
        return dice_string;
    }

    public void setDice(int[] values){
        for(int i = 0; i < values.length; i++){
           do {
               dice.get(i).roll();
           }while(dice.get(i).getValue() != values[i]);

        }
    }
    private void tallyDice(){
        int total = 0;

        for(Integer s : tally.keySet()){
            for (GVdie die : dice) {
                if (die.getValue() == s) {
                    total += 1;

                }
            }
            tally.put(s, total);
            total = 0;
        }
    }
    private boolean hasStraight(int length){
        int consecutive = 0;
        boolean straight = false;

        tallyDice();
        for(Integer value : tally.values()){
            if(value > 0){
                consecutive += 1;
                if(consecutive == length){
                    straight = true;
                    break;
                }
            }
            else{
                consecutive = 0;
            }
        }
        return straight;
    }

    private boolean hasMultiples(int count){
        boolean multiples = false;

        tallyDice();
        for(Integer value : tally.values()){
            if (value >= count) {
                multiples = true;
                break;
            }
            }
        return multiples;
        }
    private boolean hasStrictPair() {
        int pairs = 0;

        tallyDice();
        for (Integer value : tally.values()) {
            if(value == 2){
                pairs += 1;
            }
        }
        return pairs == 1;

    }

    private void nextRound(){
        rounds += 1;
        rolls = 0;

        for(GVdie d : dice){
            d.setBlank();
            d.setHeld(false);
        }

        for(int i = 0; i < 6; i++){
            tally.put(i + 1, 0);
        }

    }
    public void rollDice(){
        for(GVdie die : dice){
            if(!die.isHeld()){
                die.roll();
            }
        }
        rolls += 1;
    }
    public void checkThreeOfAKind(){
        if(hasMultiples(3)){score += THREE_OF_A_KIND;}
        nextRound();
    }
    public void checkFullHouse(){
        if(hasMultiples(3) && hasStrictPair() || hasMultiples(5)){
            score += FULL_HOUSE;
        }
        nextRound();
    }
    public void checkSmallStraight(){
        if(hasStraight(4)){
            score += SMALL_STRAIGHT;
        }
        nextRound();
    }
    public void checkLargeStraight(){
        if(hasStraight(5)){
            score += LARGE_STRAIGHT;
        }
        nextRound();
    }
    public void checkFiveOfAKind(){
        if(hasMultiples(5)){
            score += FIVE_OF_A_KIND;
        }
        nextRound();
    }
    public void checkFourOfAKind(){
        if(hasMultiples(4)){
            score += FOUR_OF_A_KIND;
        }
        nextRound();
    }
    public void checkChance(){
        for(GVdie die : dice){
            score += die.getValue();
        }
        nextRound();
    }
}
