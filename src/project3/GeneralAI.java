/*
    Object Orieted Programming, Section 01, Spring 2020
    Elijah Hand
    Project 3/4
    Team #6
 */
package project3;
import java.util.ArrayList;
import java.util.Random;


/* The goal of this class is to provide logic to the computer's decisions while
playing the game Bang!. This should include 3 basic aspects:
1.) What dice to roll (Based on expansions)
2.) What dice to reroll
3.) Who to shoot based on the ranges of dice given
4.) Keep track of enemies and allies
 */


/**
*
* @author Elijah
*/
public class GeneralAI {

    private ArrayList<Die> curDice;
    private CircularLinkedList curPlayers;
    private Character CharSelf;
    private CircularLinkedList suspicisonArray;
    private int arrowsToDraw = 0;
    private boolean activatedGatlingGun = false;
    private int selfIndex;
    private final int BASESUSVAL = 5;
    private final int REROLLMAX = 3;

    public GeneralAI(ArrayList<Die> curDice, ArrayList<Character> curPlayers, Character CharSelf, int index)
    {
        this.curDice = curDice;
        this.curPlayers = CircularLinkedList.createFromArrayList(curPlayers);
        this.CharSelf = CharSelf;
        this.selfIndex = index;
        this.suspicisonArray = new CircularLinkedList();
        for (int i=0;i<this.curPlayers.length();i++)
        {
            SuspicionCharacter temp = new SuspicionCharacter(curPlayers.get(i).getName(),BASESUSVAL,i);
            this.suspicisonArray.insert(temp);
        }
    }

    
    
    /**
     * AdjustSUS: Adjusts the suspicion value of the alive AI based on the role of the deadChar
     * When called, should be looped through the ai list with the dead char as a constant with the
     * aliveAI changing through the list.
     *      *
     * @param deadChar The character who has died
     * @param aliveAI The AI who's suspicion value you wish to change
     * 
     */
    public static void AdjustSus(Character deadChar,GeneralAI aliveAI)
    {
        if (aliveAI.CharSelf.getRole() == "Deputy")
        {
            if(deadChar.getRole() == "Deputy")
            {
                for(int i=0;i<aliveAI.curPlayers.length();i++)
                {
                    SuspicionCharacter curSusChar =(SuspicionCharacter) aliveAI.suspicisonArray.getHead().get();
                    int curSusVal = curSusChar.getSuspicionVal();
                    if (curSusVal < 5) // Good person died. That means there are less good people in the game. If we think someone is good, 
                    // they probably are not
                    {
                       curSusChar.addSuspicionVal(2); 
                    }
                    aliveAI.suspicisonArray.next(); // Advance head forward to get the next character to adjust
                }
            }
            else
            {
                for(int i=0;i<aliveAI.curPlayers.length();i++)
                {
                    SuspicionCharacter curSusChar =(SuspicionCharacter) aliveAI.suspicisonArray.getHead().get();
                    int curSusVal = curSusChar.getSuspicionVal();
                    if (curSusVal < 5) // Bad person died. That means there are less bad people in the game. If we think someone is good, 
                    // they probably are
                    {
                       curSusChar.subSuspicionVal(2); 
                    }
                    aliveAI.suspicisonArray.next(); // Advance head forward to get the next character to adjust
                }
            }
        }
        if (aliveAI.CharSelf.getRole() == "Outlaw")
        {
            if(deadChar.getRole() == "Deputy")
            {
                for(int i=0;i<aliveAI.curPlayers.length();i++)
                {
                    SuspicionCharacter curSusChar =(SuspicionCharacter) aliveAI.suspicisonArray.getHead().get();
                    int curSusVal = curSusChar.getSuspicionVal();
                    if (curSusVal < 5) // Bad person died. That means there are less Bad people in the game. If we think someone is good, 
                    // they probably are
                    {
                       curSusChar.subSuspicionVal(2); 
                    }
                    aliveAI.suspicisonArray.next(); // Advance head forward to get the next character to adjust
                }
            }
            else if (deadChar.getRole() == "Outlaw")
            {
                for(int i=0;i<aliveAI.curPlayers.length();i++)
                {
                    SuspicionCharacter curSusChar =(SuspicionCharacter) aliveAI.suspicisonArray.getHead().get();
                    int curSusVal = curSusChar.getSuspicionVal();
                    if (curSusVal < 5) // Good person died. That means there are less Good people in the game. If we think someone is good, 
                    // they probably are not
                    {
                       curSusChar.addSuspicionVal(2); 
                    }
                    aliveAI.suspicisonArray.next(); // Advance head forward to get the next character to adjust
                }
            }
        }
        if (aliveAI.CharSelf.getRole() == "Renegade") // Renegades want to kill everyone. When someone dies, everyone's values should increase
        {
            if(deadChar.getRole() == "Deputy")
            {
                for(int i=0;i<aliveAI.curPlayers.length();i++)
                {
                    SuspicionCharacter curSusChar =(SuspicionCharacter) aliveAI.suspicisonArray.getHead().get();
                    curSusChar.addSuspicionVal(4);
                    aliveAI.suspicisonArray.next(); // Advance head forward to get the next character to adjust
                }
            }
        }
    }




    /**
     * Shot : To be called when a character is shot. Will adjust the Suspicion value in this AI corresponding to 
     * player parameter accordingly
     *      *
     * @param player The Character who shot the character housed within this AI.
     * 
     */
    private void shot(Character player)
    {
        Node temp = this.suspicisonArray.getHead();
        for (int i=0;i<this.curPlayers.length();i++)
        {
            SuspicionCharacter curChar = (SuspicionCharacter) temp.get();
            if (curChar.getName() == player.getName())
            {
                curChar.setSuspicionVal(curChar.getSuspicionVal()+1);
                break;
            }
            else
            {
                temp = temp.nextNode;
            }
        }
    }

    /**
     * Shot : To be called when a character is healed. Will adjust the Suspicion value in this AI corresponding to 
     * player parameter accordingly
     *      *
     * @param player The Character who healed the character housed within this AI.
     * 
     */
    private void healed(Character player)
    {
        Node temp = this.suspicisonArray.getHead();
        for (int i=0;i<this.curPlayers.length();i++)
        {
            SuspicionCharacter curChar = (SuspicionCharacter) temp.get();
            if (curChar.getName() == player.getName())
            {
                curChar.setSuspicionVal(curChar.getSuspicionVal()-1);
                break;
            }
            else
            {
                temp = temp.nextNode;
            }
        }
    }
    
    /**
     * whichDice : If expansions are turned on, this method will select which die (loudmouth or coward)
     * the AI would like to roll.
     *      *
     * @param expansions This parameter determines if expansions are being used or not.
     * @return Returns an integer array of size 5 with values corresponding to the expansion value of the dice requested
     */
    public int[] whichDice(boolean expansions) {
        // Since the array is initialized to zero we don't need to take that 
        // step
        int[] returnArray = new int[5];
        // If the player's health is low then we want to roll the coward die
        if (expansions == true) {
            if (this.CharSelf.getHealth() <= 3) {
                returnArray[0] = 1;
            } else {
                returnArray[0] = 2;
            }
            // If we are playing with the zombie expansion, two dice are required
            // to be the unique zombie dice.
            returnArray[1] = 3;
            returnArray[2] = 3;
        }
        return returnArray;
    }
    
    
    /**
     * getRange : Returns an ArrayList of characters around the calling AI.
     *      *
     * @param range1 range1 {@literal<}range2 denotes the minimum range to start pulling characters
     * @param range2 range1{@literal<}range2 denotes the maximum range to stop pulling characters
     * @return Returns an ArrayList of characters within range1 and range2.
     */
    private ArrayList<Character> getRange(int range1,int range2)
    {
        ArrayList<Character> characterInRange = new ArrayList<Character>();
        Node curPlayer = this.curPlayers.get(this.CharSelf);
        Node nextPlayer = curPlayer;
        Node prevPlayer = curPlayer;
        if (range1 == range2)
        {
            for(int i=0;i<range1;i++)
            {
                nextPlayer = nextPlayer.nextNode;
                prevPlayer = prevPlayer.prevNode;
            }
            characterInRange.add((Character)prevPlayer.get());
            characterInRange.add((Character)nextPlayer.get());
        }
        else
        {
            for(int i=0;i<range1;i++)
            {
                nextPlayer = nextPlayer.nextNode;
                prevPlayer = prevPlayer.prevNode;
            }
            Character tempchar =(Character) nextPlayer.get();
            Character tempchar2 = (Character) prevPlayer.get();
            for(int j=range1;j<range2;j++)
            {
                nextPlayer = nextPlayer.nextNode;
                prevPlayer = prevPlayer.prevNode;
            }
            characterInRange.add((Character)prevPlayer.get());
            characterInRange.add(tempchar2);
            characterInRange.add(tempchar);
            characterInRange.add((Character)nextPlayer.get());
        }
        return characterInRange;
    }


    /**
     * findSelfSus : Finds a node Node from the suspicion circularlinkedList with a name matching
     * this.charSelf.getName()
     *
     * @return Returns a node Node from the suspicion circularlinkedList with a name matching
     * this.charSelf.getName()
     */
    public Node findSelfSus()
    {
        Node tempNode = this.suspicisonArray.getHead();
        // System.out.println(this.suspicisonArray.length());
        while(tempNode != null)
        {
            if (((SuspicionCharacter)tempNode.get()).getName() == this.CharSelf.getRealName())
            {
                return tempNode;
            }
            else
            {
                tempNode = tempNode.nextNode;
            }
        }
        return null;
    }

    /**
     * getRange : Finds an ArrayList of SuspicionCharacters around the calling AI.
     *      *
     * @param range1 range1{@literal<}range2 denotes the minimum range to start pulling characters
     * @return Returns an ArrayList of SuspicionCharacters within range1 and range2.
     */
    private ArrayList<SuspicionCharacter> getSusRange(int range1)
    {
    	SuspicionCharacter tempSusChar;
        ArrayList<SuspicionCharacter> characterInRange = new ArrayList<SuspicionCharacter>();
        Node curPlayer = this.findSelfSus();
        Node nextPlayer = curPlayer;
        Node prevPlayer = curPlayer;
        for(int i=0;i<range1;i++)
            {
                nextPlayer = nextPlayer.nextNode;
                tempSusChar = ((SuspicionCharacter)nextPlayer.get());
                if(Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getisDead() && (!Game.getZombieOutbreakBoolean()))
                {
                	i--;
                }
            }
        for (int i=0;i<range1;i++)
        {
        	prevPlayer = prevPlayer.prevNode;
            tempSusChar = ((SuspicionCharacter)nextPlayer.get());
            if(Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getisDead() && (!Game.getZombieOutbreakBoolean()))
            {
            	i--;
            }
        }
        characterInRange.add((SuspicionCharacter)nextPlayer.get());
        characterInRange.add((SuspicionCharacter)prevPlayer.get());
        return characterInRange;
    }


    /**
     * numDice : Counts the number of dice of a specific number in this.curDice.
     *      *
     * @param diceNumber The "face" of the dice we wish to count
     * @return Returns an integer of the count of diceNumber in this.curDice
     */
    private int numDice(int diceNumber)
	{
		int returnNum = 0;
		for (int i=0;i<this.curDice.size();i++)
		{
			if (this.curDice.get(i).getNumber() == diceNumber)
			{
				returnNum++;
			}
		}
        return returnNum;
    }
    
    /**
     * numDice : Counts the number of dice of a specific number and expansion in this.curDice.
     *      *
     * @param diceNumber The "face" of the dice we wish to count
     * @param expansion The expansion the dice you are wishing to count
     * @return Returns an integer of the count of diceNumber in this.curDice
     */
    private int numDice(int diceNumber, int expansion)
    {
    	int returnNum =0;
    	for (int i=0;i<this.curDice.size();i++)
    	{
    		if ((this.curDice.get(i).getNumber() == diceNumber) && (this.curDice.get(i).getExpansionNumber() == expansion))
    		{
    			returnNum++;
    		}
    	}
    	return returnNum;
    }

    /*
        The goal for this function is to take in a dice number, a boolean, and the current boolean array corresponding to rerolls.
        This will set any dice in the die array matching diceNumber and set their coresponding values in the curReroll array equal to
        bool.
        
    private void setReroll(int diceNumber, boolean bool,boolean[] curReroll)
	{
		for (int i=0;i<5;i++)
		{
			if (this.curDice.get(i).getNumber() == diceNumber)
			{
				curReroll[i] = bool;
			}
		}
    }
    */

   
    
    /**
     * verifyRerollAllowed : Verifies if a specific die is allowed to be rerolled.
     *      *
     * @param rerollVal A boolean value associated with if the AI wants to reroll the die.
     * @param i The index of the die in this.curDice
     * @return Returns the new boolean value for rerollVal
     */
    private boolean verifyRerollAllowed(boolean rerollVal,int i)
	{
		if (rerollVal == true)
		{
			if (this.curDice.get(i).getCurrentRollCount() > 2)
			{
				rerollVal = false;
            }
			if (numDice(5) >= 3)
	        {
	            rerollVal = false; // If dynamite are 3 or more, then we need to not let the character reroll
	        }
			if (this.CharSelf.hasAnyDuelTokens())
			{
				if (this.CharSelf.getDuelWoundTokens()[3] == true)
				{
					if (numDice(5) >= 2)
					{
						rerollVal = false;
					}
				}
			}
		}
        if (rerollVal)
        {
        	this.curDice.get(i).addRollCount();
        }
        return rerollVal;
    }
    

    /* DICE NUMBERS:
        0 - Blank
        1 - Attack 1 away
        2 - Attack 2 away
        3 - Beer
        4 - Gattling Gun
        5 - Dynamite
        6 - Arrow */


    /**
     * calcReroll : Calculates if the AI wants to reroll certain dice 
     * 
     * @param curDie The current die under consideration for the AI
     * @return Returns a boolean value, true to reroll the die, false otherwise.
     */
    private boolean calcReroll(Die curDie)
	// returns false to keep dice, true to reroll them
	{
        boolean returnVal = true;
        int currentFace = curDie.getNumber();
        ArrayList<SuspicionCharacter> range1 = getSusRange(1);
        ArrayList<SuspicionCharacter> range2 = getSusRange(2);
        int[] range1Vals = {range1.get(0).getSuspicionVal(),range1.get(0).getSuspicionVal()};
        int[] range2Vals = {range2.get(0).getSuspicionVal(),range2.get(0).getSuspicionVal()};
        switch(currentFace)
        {
            case(1):
            {
                if ((range1Vals[0] > 7) || (range1Vals[1] > 7))
                {
                    returnVal = false;
                }
                break;
            }
            case(2):
            {
                if ((range2Vals[0] > 7) || (range2Vals[1] > 7))
                {
                    returnVal = false;
                }
                break;
            }
            case(3):
            {
                if(this.CharSelf.getHealth() <= 3)
                {
                    returnVal = true;
                }
                break;
            }
            case(4):
            {
                if (this.numDice(4) >= 2)
                {
                    returnVal = false;
                }
                break;
            }

            case(5):
            {
                if ((this.CharSelf.getName() == "BLACK JACK") && (this.numDice(5) < 3))
                {
                    returnVal = true;
                }
                else
                {
                    returnVal = false;
                }
                break;
            }
            case(6):
            {
                if (this.CharSelf.getArrows() > this.CharSelf.getHealth())
                {
                    returnVal = false;
                }
                break;
            }
        }
        return returnVal;
    }
    
    /**
     * calcRerollExpansion : Calculates if the AI wants to reroll certain dice 
     * 
     * @param curDie The current die under consideration for the AI
     * @return Returns a boolean value, true to reroll the die, false otherwise.
     */
    private boolean calcRerollExpansion(Die curDie)
	// returns false to keep dice, true to reroll them
	{
        boolean returnVal = true;
        int currentFace = curDie.getNumber();
        int expansion = curDie.getExpansionNumber();
        ArrayList<SuspicionCharacter> range1 = getSusRange(1);
        ArrayList<SuspicionCharacter> range2 = getSusRange(2);
        int[] range1Vals = {range1.get(0).getSuspicionVal(),range1.get(0).getSuspicionVal()};
        int[] range2Vals = {range2.get(0).getSuspicionVal(),range2.get(0).getSuspicionVal()};
        switch(currentFace)
        {
            case(1):
            {
                if (expansion == 1)
                {
                    if ((range1Vals[0] > 7) || (range1Vals[1] > 7))
                    {
                        returnVal = false;
                    }
                }
                if (expansion == 2)
                {
                    if ((range1Vals[0] > 7) || (range1Vals[1] > 7))
                    {
                        returnVal = false;
                    }
                }
                if (expansion == 3)
                {
                    for (int i=0;i<this.suspicisonArray.size;i++)
                    {
                        Node tempNode = this.suspicisonArray.getHead();
                        if (((SuspicionCharacter)(tempNode.o)).getSuspicionVal() > 9) // More likely to lose this one so gotta be more careful
                        {
                            returnVal = false;
                        }
                    }
                }
                break;
            }
            case(2):
            {
                if (expansion == 1)
                {
                    if ((range2Vals[0] > 7) || (range2Vals[1] > 7))
                    {
                        returnVal = false;
                    }
                }
                if (expansion == 2)
                {
                    ; // We want to reroll it since it is identical to the regular arrow but with upsides
                }
                if (expansion == 3)
                {
                    for (int i=0;i<this.suspicisonArray.size;i++)
                    {
                        Node tempNode = this.suspicisonArray.getHead();
                        if (((SuspicionCharacter)(tempNode.o)).getSuspicionVal() > 9) // More likely to lose this one so gotta be more careful
                        {
                            returnVal = false;
                        }
                    }
                }
                break;
            }
            case(3):
            {
                if (expansion == 1)
                {
                   ;
                }
                if (expansion == 2)
                {
                    if (this.CharSelf.getHealth() <= this.CharSelf.getMaxHealth()-1)
                    {
                        returnVal = false;
                    }
                }
                if (expansion == 3)
                {
                    if (this.CharSelf.getHealth() <= this.CharSelf.getMaxHealth()-1)
                    {
                        returnVal = false;
                    }
                }
                break;
            }
            case(4):
            {
                if (expansion == 1)
                {
                   returnVal = false; // Its a double gatling gun. Of course we are going to keep it.
                }
                if (expansion == 2)
                {
                    if (this.CharSelf.getHealth() <= this.CharSelf.getMaxHealth()-2) // Double beer
                    {
                        returnVal = false;
                    }
                }
                if (expansion == 3)
                {
                    if (this.numDice(4) == 2)
                    {
                        returnVal = false;
                    }
                }
                break;
            }

            case(5):
            {
                if ((this.CharSelf.getName() == "BLACK JACK") && (this.numDice(5) < 3))
                {
                    returnVal = true;
                }
                else
                {
                    returnVal = false;
                }
                break;
            }
            case(6):
            {
                if (this.CharSelf.getArrows() > this.CharSelf.getHealth())
                {
                    returnVal = false;
                }
                break;
            }
        }
        return returnVal;
    }

    /**
     * takeArrow : To be called if expansions are being used and when an arrow is rolled. Cycles through the playerlist
     * looking to see if any character currently has the chiefArrow. If someone does, then the calling character takes an arrow.
     * Otherwise the calling character takes the chiefs arrow.
     * 
     * 
     */
    private void takeArrow()
    {
    	boolean chiefFlag = true;
    	if (Game.expEnabled)
    	{
    		for(Character tempChar:Game.playerList)
    		{
    			if (tempChar.getChiefArrow())
    			{
    				Game.takeArrow(this.CharSelf);
    				chiefFlag = false;
    				break;
    			}
    		}
    		if (chiefFlag)
    		{
    			Game.takeChiefsArrow(this.CharSelf);
    		}
    	}
    	else
    	{
    		Game.takeArrow(this.CharSelf);
    	}
    }

    
    /**
     * whoToShoot : Determines the players in range of being shot, and of those players who to shoot. 
     * 
     * @return Returns an ArrayList of SuspicionCharacters who the AI has determined to shoot.
     */
    private ArrayList<SuspicionCharacter> whoToShoot()
	{
		int numGun1 = this.numDice(1,0);
		int numGun2 = this.numDice(2,0);
		numGun1 += this.numDice(1,1);
		numGun1 += this.numDice(1,2);
		numGun2 += this.numDice(2,1);		
		ArrayList<SuspicionCharacter> susRange1 = this.getSusRange(1);
		ArrayList<SuspicionCharacter> susRange2 = this.getSusRange(2);
        ArrayList<SuspicionCharacter> playersToShoot = new ArrayList<SuspicionCharacter>();
        for (SuspicionCharacter thing:susRange1)
        {
            thing.setRange(1);
        }
        for(SuspicionCharacter thing:susRange2)
        {
            thing.setRange(2);
        }
		if (numGun1 > 0)
		{
			if (susRange1.get(0).getSuspicionVal() < susRange1.get(1).getSuspicionVal())
			{
                playersToShoot.add(susRange1.get(1));
			}
			else
			{
                playersToShoot.add(susRange1.get(0));
			}
		}
		if (numGun2 > 0)
		{
			if (susRange2.get(0).getSuspicionVal() < susRange2.get(1).getSuspicionVal())
			{
                playersToShoot.add(susRange2.get(1));
			}
			else
			{
                playersToShoot.add(susRange2.get(0));
			}
		}
		playersToShoot.trimToSize();
		return playersToShoot;
    }

    /**
     * grabIndianChiefArrow : Takes the indian chief arrow from the player who has it in Game.playerList and gives it to this.charSelf.
     * 
     */
    private void grabIndianChiefArrow()
    {
    	for(Character a:Game.playerList)
    	{
    		if(a.getChiefArrow())
    		{
    			a.setHasChiefArrow(false);
    			this.CharSelf.setHasChiefArrow(true);
    		}
    	}
    }
    
    private void shoot(Character person)
    {
    	person.setHealth(person.getHealth()-1);
    	if (person.getHealth() <= 0)
    	{
    		Game.killPlayer(person);
    	}
    	if (person.getName() == "EL GRINGO")
    	{
    		Game.takeArrow(this.CharSelf);
    	}
    }
    
    
    
    /**
     * reRoll : Rerolls this.curDice based on this.calcReroll. Also grabs arrows if any arrows are rolled.
     * 
     * @return Returns an ArrayList of Strings showing what was done while rerolling
     */
    private ArrayList<String> reRoll()
	{
        ArrayList<String> returnStringList = new ArrayList<>();
        String tempStr,tempStr2;
		boolean rerollVal;
		for (int i=0;i<this.curDice.size();i++)
		{
            Die curDie = this.curDice.get(i);
            rerollVal = this.calcReroll(curDie);
            rerollVal = this.verifyRerollAllowed(rerollVal,i);
            if (rerollVal)
            {
                curDie.reroll();
                curDie.addRollCount();
            }
            if (curDie.getNumber() == 6)
            {
                Game.takeArrow(this.CharSelf);
            }
        }
        return returnStringList;
    }
    
    /**
     * reRollExpan : Rerolls this.curDice based on this.calcReroll. Also grabs arrows if any arrows are rolled.
     * Takes care of any expansion character's abilities.
     * 
     * @return Returns an ArrayList of Strings showing what was done while rerolling
     */
    private ArrayList<String> reRollExpan()
	{
        ArrayList<String> returnStringList = new ArrayList<>();
        String tempStr,tempStr2;
		boolean rerollVal;
		int arrowNum = 0;
		for (int i=0;i<this.curDice.size();i++)
		{
            //tempStr = ("Round "+ i);
            //returnStringList.add(tempStr);
            Die curDie = this.curDice.get(i);
            if (curDie.getExpansionNumber() > 0)
            {
            	rerollVal = this.calcRerollExpansion(curDie);
            }
            else
            {
            	rerollVal = this.calcReroll(curDie);
            }
            switch(curDie.getExpansionNumber())
            {
                case(0):
                {
                    if (curDie.getNumber() == 6)
                    {
                    	if (this.CharSelf.getName() == "BILL NOFACE")
                    	{
                    		arrowNum++;
                    	}
                    	else
                    	{
                    		this.takeArrow();
                    	}
                    }
                break;
                }
                case(1):
                {
                    if (curDie.getNumber() == 6)
                    {
                    	if (this.CharSelf.getName() == "BILL NOFACE")
                    	{
                    		arrowNum++;
                    	}
                    	else if (this.CharSelf.getName() == "APACHE KID")
                    	{
                    		if (this.CharSelf.getChiefArrow() == false)
                    		{
                    			this.grabIndianChiefArrow();
                    		}
                    		else
                    		{
                    			this.takeArrow();
                    		}
                    	}
                    	else
                    	{
                    		this.takeArrow();
                    	}
                    }
                    if (curDie.getNumber() == 3)
                    {
                        Game.goldenBullet(this.CharSelf);
                    }
                    break;
                }
                case(2):
                {
                    if (curDie.getNumber() == 6)
                    {
                    	if (this.CharSelf.getName() == "BILL NOFACE")
                    	{
                    		arrowNum++;
                    	}
                    	else if (this.CharSelf.getName() == "APACHE KID")
                    	{
                    		if (this.CharSelf.getChiefArrow() == false)
                    		{
                    			this.grabIndianChiefArrow();
                    		}
                    		else
                    		{
                    			this.takeArrow();
                    		}
                    	}
                    	else
                    	{
                    		this.takeArrow();
                    	}
                    }
                    if (curDie.getNumber() == 2)
                    {
                        Game.putArrowBack(this.CharSelf);
                    }
                    break;
                }
                case(3):
                {
                    if (curDie.getNumber() == 6)
                    {
                    	if (this.CharSelf.getName() == "BILL NOFACE")
                    	{
                    		arrowNum++;
                    	}
                    	else if (this.CharSelf.getName() == "APACHE KID")
                    	{
                    		if (this.CharSelf.getChiefArrow() == false)
                    		{
                    			this.grabIndianChiefArrow();
                    		}
                    		else
                    		{
                    			this.takeArrow();
                    		}
                    	}
                    	else
                    	{
                    		this.takeArrow();
                    	}
                    }
                    break;
                }
            }
            rerollVal = this.verifyRerollAllowed(rerollVal,i);
            if (rerollVal)
            {
            //    tempStr = (this.CharSelf.getName() + " Rerolled " + curDie.getNumber());
                curDie.reroll();
            //    tempStr2 = ("for" + curDie.getNumber()+'\n');
            //    tempStr = tempStr.concat(tempStr2);
            //    returnStringList.add(tempStr);
            }
        }
		if (this.numDice(5) < 3)
		{
			if (this.CharSelf.getName() == "BELLE STAR")
			{
				for (Die dice:this.curDice)
				{
					if (dice.getNumber() == 5)
					{
						if (dice.getExpansionNumber() != 2)
						{
							dice.setNumber(4);
							break;
						}
					}
				}
			}
		}
		this.arrowsToDraw = arrowNum;
        return returnStringList;
	}

    /**
     * challengeDuel : Used when a duel dice is rolled.
     * 
     * @param challengedAi The AI that is currently having to roll the die. If the AI rolls a
     * duel die, then the function is called again from challengedAI and is passed the initial AI as challengedAi.
     * When the challengedAi does not roll a duel die, then the Game.loseDuel method is called with challengedAi.CharSelf 
     * passed as the parameter.
     * 
     * 
     */
    private void challengeDuel(GeneralAI challengedAi)
    {
        int rollNum;
        rollNum = challengedAi.aiDuel();
        if ((rollNum == 1) || (rollNum == 2))
        {
            challengedAi.challengeDuel(this);
        }
        else
        {
            Game.loseDuel(challengedAi.CharSelf);
            System.out.println(challengedAi.CharSelf.getName() + " lost a duel.");
        }
    }

    /**
     * aiDuel : This function rolls a die from expansion 3 and returns its result.
     * 
     * @return Returns the integer value corresponding to the face of a die from expansion 3.
     */
    public int aiDuel()
    {
        Die tempDie = new Die(3);
        tempDie.reroll();
        return tempDie.getNumber();
    }
    
    /**
     * getCurDice : This function returns an arrayList containing the curDice of the AI.
     * 
     * @return Returns an ArrayList of Die Objects.
     */
	public ArrayList<Die> getCurDice() {
		return curDice;
	}


	/**
     * getCharSelf : This function returns the Character contained inside the AI.
     * 
     * @return Returns the Character object inside the AI.
     */
	public Character getCharSelf() {
		return CharSelf;
	}


	/**
     * getSuspicionArray : This function returns the Suspicion CLL contained inside the AI.
     * 
     * @return Returns a CircularLinkedList with SuspicionCharacters populating it.
     */
	public CircularLinkedList getSuspicisonArray() {
		return suspicisonArray;
	}


	/**
	 * resetRerollCount : This function resets the rerollCount of every die in the Current AI
	 */
    public void resetRerollCount()
    {
    	for(Die dice:this.curDice)
    	{
    		dice.resetRollCount();
    	}
    }
    
    
   /* public static void main(String[] args)
    {
    	Character testChar = new Character(0,"Outlaw");
    	Character testChar2 = new Character(1,"Sheriff");
    	int [] diceval;
    	ArrayList<Character> tempCharArray = new ArrayList<Character>();
    	tempCharArray.add(testChar);
    	tempCharArray.add(testChar2);
    	Die a,b,c,d,e;
    	a = new Die(0);
    	b = new Die(0);
    	c = new Die(0);
    	d = new Die(0);
    	e = new Die(0);
    	ArrayList<Die> dice = new ArrayList<Die>();
    	dice.add(a);
    	dice.add(b);
    	dice.add(c);
    	dice.add(d);
    	dice.add(e);
    	GeneralAI testAI1 = new GeneralAI(dice,tempCharArray,testChar,0);
    	System.out.println(testAI1.getCharSelf().getName());
    	diceval = testAI1.whichDice(true);
    	for(int i=0;i<diceval.length;i++)
    	{
    		System.out.println(diceval[i]);
    	}
    } */
    
    
    /**
     * doTurnExpan: Does the turn for this AI. Takes care of all abilities and shooting.
     * @return Returns an ArrayList of strings showing the logs of what the AI did.
     */
    public ArrayList<String> doTurnExpan()
    {
    	ArrayList <String> log = new ArrayList<>();
    	this.activatedGatlingGun = false;
    	int shootingIndex,playerListIndex;
    	int dieExpansionArray[] = {0,0,0,0,0};
        int beer,shot1,shot2,gatling,shot1dbl,shot2dbl,gatlingdbl,beerdbl,duel,whiskey;
        ArrayList<SuspicionCharacter> shootingList = new ArrayList<SuspicionCharacter>();
        if (!this.getCharSelf().getIsZombie())
        {
        	dieExpansionArray = this.whichDice(Game.expEnabled);
        }
        for (int i=0;i<this.curDice.size();i++)
        {
        	Die tempDie = this.curDice.get(i);
        	tempDie.setExpansionNumber(dieExpansionArray[i]);
        }
        for (Die dice:this.curDice) // Reset the dice numbers before the AI get them
        {
        	dice.setNumber(0);
        	dice.resetRollCount();
        }

        this.reRollExpan();


        for (int i=0;i<this.arrowsToDraw;i++)
        {
        	Game.takeArrow(this.CharSelf); // This is for Bill Noface, for everyone else this value will be 0.
        }
        shot1 = this.numDice(1,0);
        shot1 += this.numDice(1,2);
        shot2 = this.numDice(2,0);
        beer = this.numDice(3,0);
        gatling = this.numDice(4,0);
        gatling += this.numDice(4,3);
        shot1dbl = this.numDice(1, 1);
        shot2dbl = this.numDice(2, 1);
        beerdbl = this.numDice(3,2);
        gatlingdbl = this.numDice(4, 1);
        duel = this.numDice(1, 3);
        duel += this.numDice(2,3);
        whiskey = this.numDice(3,3);
        if (this.CharSelf.hasAnyDuelTokens())
        {
        	if(this.CharSelf.getDuelWoundTokens()[0])
        	{
        		beer--;
        	}
        	if(this.CharSelf.getDuelWoundTokens()[1])
        	{
        		shot1--;
        	}
        	if(this.CharSelf.getDuelWoundTokens()[2])
        	{
        		shot2--;
        	}
        	Game.removeDuelToken(this.CharSelf);
        }
        if (this.CharSelf.getName() == "SUZY LAFAYETTE")
        {
        	if ((shot1 == 0) && (shot2 == 0))
        	{
        		Game.heal(this.CharSelf);
        		Game.heal(this.CharSelf);
        		log.add(this.CharSelf.getName() + " just activated her special ability!");
        	}
        }
        if (this.CharSelf.getName() == "GREG DIGGER")
        {
        	whiskey*=2;
        	log.add(this.CharSelf.getName() + " just activated his special ability!");
        }
        for (int i=0;i<(beer+(beerdbl*2));i++) // Simply setting it to heal itself every turn. 
        {
            Game.heal(this.CharSelf);
        }
        if ((beer+(beerdbl*2) > 0))
        	{
        		log.add(this.CharSelf.getName() + " healed for "+ (beer+(beerdbl*2))+ " using beer!");
        	}
        for (int i=0;i<whiskey;i++) // Simply setting it to heal itself every turn. 
        {
            Game.heal(this.CharSelf);
            if (this.CharSelf.hasAnyDuelTokens())
            {
            	for(int j=0;j<this.CharSelf.getDuelWoundTokens().length;j++)
            	{
            		if (this.CharSelf.getDuelWoundTokens()[j] == true)
            		{
            			this.CharSelf.getDuelWoundTokens()[j] = false;
            			break;
            		}
            	}
            }
        }
        if (whiskey > 0)
        {
        	log.add(this.CharSelf.getName() + " healed for "+ whiskey+ " using whiskey!");
        }
        if (gatling+(gatlingdbl*2) >= 3)
        {
            Game.gatlingGun(this.CharSelf);
            this.activatedGatlingGun = true;
            log.add(this.CharSelf.getName() + " activated the Gatling Gun!");
        }
        if ((this.CharSelf.getName() == "WILLIE THE KID") && (this.activatedGatlingGun == false))
        {
        	if (gatling == 2)
        	{
        		Game.gatlingGun(this.CharSelf);
        		this.activatedGatlingGun = true;
        		log.add(this.CharSelf.getName() + " activated the Gatling Gun!");
        	}
        }
        shootingList = this.whoToShoot();
        for (SuspicionCharacter temp:shootingList)
        {
        	shootingIndex = this.findIndex(temp);
        	playerListIndex = this.findPlayerListIndex(temp);
            for (int i=0;i<shot1;i++)
            {
                if (temp.getRange() == 1)
                {
                	Game.aiList.get(shootingIndex).shot(this.CharSelf); // Adjust player hit's sus values
                    this.shoot(Game.playerList.get(playerListIndex));// Adjust player hit's health values
                    shot1--;
                	log.add(this.CharSelf.getName() + " just shot "+ Game.aiList.get(shootingIndex).getCharSelf().getName());
                }
            }
            for (int i=0;i<shot2;i++)
            {
            	if (temp.getRange() == 2)
                {
            		Game.aiList.get(shootingIndex).shot(this.CharSelf); // Adjust player hit's sus values
                    this.shoot(Game.playerList.get(playerListIndex));
                    shot2--;
                    log.add(this.CharSelf.getName() + " just shot "+ Game.aiList.get(shootingIndex).getCharSelf().getName());
                }
            }
            for (int i=0;i<shot1dbl;i++)
            {
            		if(temp.getRange() == 1)
            		{
            			Game.aiList.get(shootingIndex).shot(this.CharSelf);  // Adjust player hit's sus values
            			Game.aiList.get(shootingIndex).shot(this.CharSelf); 
            			this.shoot(Game.playerList.get(playerListIndex)); // Adjust player hit's health values
            			this.shoot(Game.playerList.get(playerListIndex));
            			shot1dbl--;
            			log.add(this.CharSelf.getName() + " just shot "+ Game.aiList.get(shootingIndex).getCharSelf().getName() + " twice!");
            		}
            }
            for (int i=0;i<shot2dbl;i++)
            {
            		if (temp.getRange() == 2)
            		{
            			Game.aiList.get(shootingIndex).shot(this.CharSelf);  // Adjust player hit's sus values
            			Game.aiList.get(shootingIndex).shot(this.CharSelf); 
            			this.shoot(Game.playerList.get(playerListIndex)); // Adjust player hit's health values
            			this.shoot(Game.playerList.get(playerListIndex));
            			shot2dbl--;
            			log.add(this.CharSelf.getName() + " just shot "+ Game.aiList.get(shootingIndex).getCharSelf().getName() + " twice!");
            		}
            }
        }
        for (int i=0;i<duel;i++)
        {
        	SuspicionCharacter greatestSus = findGreatestSus();
        	int indexGreatestSus = this.findIndex(greatestSus); // Will have the index of the highest suspicion's index in aiList.
        	this.challengeDuel(Game.aiList.get(indexGreatestSus));
        	log.add(this.CharSelf.getName() + " challenged "+ Game.aiList.get(indexGreatestSus).CharSelf.getName()
            		+ " to a duel!");
        }
        return log;
    }
    
    private SuspicionCharacter findGreatestSus()
    {
    	SuspicionCharacter tempSusChar;
    	Node tempNode = this.suspicisonArray.getHead();
    	SuspicionCharacter greatest = (SuspicionCharacter)tempNode.get();
    	for (int i=0;i<this.curPlayers.size;i++)
    	{
    		tempSusChar = (SuspicionCharacter)tempNode.get();
    		if (tempSusChar.getSuspicionVal() > greatest.getSuspicionVal())
    		{
    			if (tempSusChar.getName() != this.CharSelf.getRealName()) // Get the greatest sus character that is not myself
    			{
    				if (!Game.aiList.get(findIndex(tempSusChar)).getCharSelf().getisDead()) // If tempSusChar is alive
    				{
    					greatest = tempSusChar;
    				}
    				else if ((Game.getZombieOutbreakBoolean()) && (!Game.aiList.get(findIndex(tempSusChar)).getCharSelf().getIsDeadForever())) // Sus character is dead, but if Zombie outbreak has occured then this is fine
    				{
    					greatest = tempSusChar;
    				}
    			}
    		}
    		tempNode = tempNode.nextNode;
    	}
    	return greatest;
    }
    
    /**
     * findIndex: Finds the index of needle in Game.aiList
     * @param needle: The suspicion Character whos corresponding AI you wish to find
     * @return Returns an integer index corresponding to the AI you wished to find.
     */
    private int findIndex(SuspicionCharacter needle)
    {
    	int returnNum = 0;
    	GeneralAI haystack;
    	for(int i=0;i<Game.aiList.size();i++)
    	{
    		haystack = Game.aiList.get(i);
    		if (haystack.getCharSelf().getRealName() == needle.getName())
    		{
    			returnNum = i;
    		}
    	}
    	return returnNum;
    }
    
    /**
     * findPlayerListIndex: Finds the index of needle in Game.playerList
     * @param needle: The suspicion Character whos corresponding Character you wish to find
     * @return returns an integer index corresponding to the Character you wished to find.
     */
    private int findPlayerListIndex(SuspicionCharacter needle)
    // Finds index of needle in playerList
    {
    	int returnNum = 0;
    	Character haystack;
    	for(int i=0;i<Game.aiList.size();i++)
    	{
    		haystack = Game.playerList.get(i);
    		if (haystack.getRealName() == needle.getName())
    		{
    			returnNum = i;
    		}
    	}
    	return returnNum;
    }
    
    /**
     * zombieAdjustSus : Adjusts the suspicion values of this AI corresponding to if
     * it is "dead" or "alive".
     */
    private void zombieAdjustSus()
    {
    	Node tempNode;
    	SuspicionCharacter tempSusChar;
    	if(this.CharSelf.getIsZombie() || this.CharSelf.getIsZombieMaster())
    	{
    		tempNode = this.suspicisonArray.getHead();
    		for(int i=0;i<this.suspicisonArray.size;i++)
    		{
    			tempSusChar = (SuspicionCharacter) tempNode.get();
    			if((Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getIsZombie())
    				|| (Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getIsZombieMaster()))
    			{
    				tempSusChar.setSuspicionVal(-999);
    			}
    			else
    			{
    				tempSusChar.setSuspicionVal(999);
    			}
    			tempNode = tempNode.nextNode;
    		}
    	}
    	else
    	{
    		tempNode = this.suspicisonArray.getHead();
    		for(int i=0;i<this.suspicisonArray.size;i++)
    		{
    			tempSusChar = (SuspicionCharacter) tempNode.get();
    			if((Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getIsZombie())
        				|| (Game.aiList.get(this.findIndex(tempSusChar)).getCharSelf().getIsZombieMaster()))
    			{
    				tempSusChar.setSuspicionVal(999);
    			}
    			else
    			{
    				tempSusChar.setSuspicionVal(-999);
    			}
    			tempNode = tempNode.nextNode;
    		}
    	}
    }
    
    /**
     * updateZombieAI : Makes necessary changes to AI so that it can act as a zombie.
     * Replaces this.curDice with 3 new original Die, and then calls zombieAdjustSus
     * to adjust the suspicion values accordingly.
     */
    public void updateZombieAI()
    {
    	Die a = new Die(0);
    	Die b = new Die(0);
    	Die c = new Die(0);
    	ArrayList<Die> ZombieDie = new ArrayList<>();
    	ZombieDie.add(a);
    	ZombieDie.add(b);
    	ZombieDie.add(c);
    	if (this.CharSelf.getIsZombie())
    	{
    		this.setCurDice(ZombieDie);
    	}
    	this.zombieAdjustSus();
    }
    
    /**
     * setcurDice : Sets the current dice to a.
     * @param a: An arraylist of Die objects.
     */
    public void setCurDice(ArrayList<Die> a)
    {
    	this.curDice = a;
    }
}
