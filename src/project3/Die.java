/*
    Object Orieted Programming, Section 01, Spring 2020
    Arthur Lee Jones
    Project 3
    Team #6
 */

/*
   Expansion Number: 0 -  DICE NUMBERS BASE GAME:
        0 - Blank
        1 - Attack 1 away
        2 - Attack 2 away
        3 - Beer
        4 - Gattling Gun
        5 - Dynamite
        6 - Arrow
*/

/*
    DICE NUMBERS OLD SALOON EXPANSION:
        Expansion Number 1 - LOUDMOUTH DIE:
            0 - Blank
            1 - Attack 1 away (Two targets with expansion)
            2 - Attack 2 away (Two targets with expansion)
            3 - Golden Bullet
            4 - Gattling Gun (Double Gatling with expansion)
            5 - Dynamite
            6 - Arrow
        Expansion Number 2 - COWARD DIE:
            0 - Blank
            1 - Attack 1 away
            2 - Broken Arrow
            3 - Beer
            4 - Double Beer
            5 - Dynamite
            6 - Arrow
    
*/

/*
    Expansion Number 3 - DICE NUMBERS UNDEAD OR ALIVE EXPANSION:
        0 - Blank
        1 - Duel
        2 - Duel
        3 - Whiskey Bottle
        4 - Gattling Gun
        5 - Dynamite
        6 - Arrow
*/

package project3;
import java.util.Random;

/**
 *
 * @author Arthr
 */
public class Die {

    private int number;
    private String asset;
    private int currentRollCount;
    private int expansionNumber;
    
    /**
     *Die
     * @param expansionNumber the expansion's number
     */
    public Die(int expansionNumber) {
        this.number = 0;
        this.currentRollCount = 0;
        this.setAsset(0);
        this.setExpansionNumber(expansionNumber);
    }
    
    /**
     * Will re-roll the die single die
     */
    public void reroll() {
        Random randomGenerator = new Random();
        int randomInt = 0   ;
        for(int i = 0; i < 6; i++)
            randomInt = randomGenerator.nextInt(6) + 1;
        
        this.number = randomInt;
        this.setAsset(this.number);
    }
    
    /**
     * Will set the asset according to the number rolled.
     * @param number The number the die rolled on
     */
    private void setAsset(int number) {
        //BASE DIE
        if(this.expansionNumber == 0) {
            if(number == 0) this.asset = "/assets/0.jpg";
            if(number == 1) this.asset = "/assets/1.jpg";
            if(number == 2) this.asset = "/assets/2.jpg";
            if(number == 3) this.asset = "/assets/3.jpg";
            if(number == 4) this.asset = "/assets/4.jpg";
            if(number == 5) this.asset = "/assets/5.jpg";
            if(number == 6) this.asset = "/assets/6.jpg";
        }
        //LOUDMOUTH DIE
        if(this.expansionNumber == 1) {
            if(number == 0) this.asset = "/assets/0.jpg";
            if(number == 1) this.asset = "/assets/1-2.jpg";
            if(number == 2) this.asset = "/assets/2-2.jpg";
            if(number == 3) this.asset = "/assets/5-2.jpg";
            if(number == 4) this.asset = "/assets/4-2.jpg";
            if(number == 5) this.asset = "/assets/5.jpg";
            if(number == 6) this.asset = "/assets/6.jpg";
        }
        //Coward DIE
        if(this.expansionNumber == 2) {
            if(number == 0) this.asset = "/assets/0.jpg";
            if(number == 1) this.asset = "/assets/1.jpg";
            if(number == 2) this.asset = "/assets/6-2.jpg";
            if(number == 3) this.asset = "/assets/3.jpg";
            if(number == 4) this.asset = "/assets/3-2.jpg";
            if(number == 5) this.asset = "/assets/5.jpg";
            if(number == 6) this.asset = "/assets/6.jpg";
        }
        //UNDEAD OR ALIVE DIE
        if(this.expansionNumber == 3) {
            if(number == 0) this.asset = "/assets/0.jpg";
            if(number == 1) this.asset = "/assets/4-3.jpg";
            if(number == 2) this.asset = "/assets/4-3.jpg";
            if(number == 3) this.asset = "/assets/3-3.jpg";
            if(number == 4) this.asset = "/assets/4.jpg";
            if(number == 5) this.asset = "/assets/5.jpg";
            if(number == 6) this.asset = "/assets/6.jpg";
        }
    }
    
    /**
     * Gives a URL string that contains the image to a dice asset
     * @return Returns the url to the asset
     */
    public String getAsset() {
        return this.asset;
    }
    
    /**
     * Gives a number
     * @return Returns the number the die landed on
     */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Will set the die's number
     * @param number A random number between 1-6
     */
    public void setNumber(int number) {
        this.number = number;
        this.setAsset(this.number);
    }
    
    /**
     * Will +1 to the roll count when called
     */
    public void addRollCount() {
        this.currentRollCount++;
    }
    
    /**
     * Provides the amount of times this die has been rolled.
     * @return Returns the current roll count number
     */
    public int getCurrentRollCount() {
        return this.currentRollCount;
    }
    
    /**
     * This is mainly used to reset the roll count.
     */
    public void resetRollCount() {
        this.currentRollCount = 0;
    }
    
    /**
     * Sets the expansion number
     * @param expansionNumber The number of the expansion
     */
    public void setExpansionNumber(int expansionNumber) {
        this.expansionNumber = expansionNumber;
    }
    
    /**
     * Gets the expansion number
     * @return Returns the expansion number
     */
    public int getExpansionNumber() {
        return this.expansionNumber;
    }
    
    /**
     *resetDie: Will reset the die back to nothing 
     */
    public void resetDie(){
        this.resetRollCount();
        this.setNumber(0);
        this.setExpansionNumber(0);
    }
}
