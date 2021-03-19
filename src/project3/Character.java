/*
    Object Oriented Programming, Section 01, Spring 2020
    Anna Bartley and Isaak Chinault
    Project 3
    Team #6
 */

package project3;
//import java.util.Scanner; 
//import java.util.InputMismatchException;

/**
 *
 * @author Isaak
 * @author Anna
 */
public class Character {
    
    private boolean isDead, isDeadForever, hasChiefArrow, isZombieMaster, isZombie;
    private boolean duelWoundTokens[] = {false,false,false,false} ;
    private String role, name, realName, description, picture;
    private int health,arrows,maxHealth;
    private final int initialHealth[] = {7,7,9,8,9,8,8,9,9,9};
    private final String pictureFileArray[] = {
        "/assets/elGringo.png",
        "/assets/jourdonnais.png",
        "/assets/paulRegret.png",
        "/assets/suzyLafayette.png",
        "/assets/vultureSam.png",
        "/assets/willyTheKid.png",
        "/assets/belleStar.png",
        "/assets/gregDigger.png",
        "/assets/apacheKid.png",
        "/assets/noface.png"
    };
    private final String initialName[] = {"EL GRINGO", "JOURDONNAIS",
        "PAUL REGRET","SUZY LAFAYETTE", "VULTURE SAM","WILLY THE KID", 
        "BELLE STAR", "GREG DIGGER", "APACHE KID","NOFACE"};
    private final String initialDescription[]={
        "When a player makes you lose one or more life points, he must take"
        + " an arrow. Life points lost to Indians or Dynamite are not affected.",
        "You never lose more than one life point to Indians.",
        "You never lose life points to the Gatling Gun",
        "If you didn't roll any ONE or TWO, you gain two life points. This only"
        + "applies at the end of your turn, not during your re-rolls.",
        "Each time another player is eliminated, you gain two life points.",
        "You only need two GATLING to use the Gatling Gun. You can activate"
        + "the Gatling Gun only once per turn, even if you roll more than "
        + "two GATLING results.",
        "After each of your dice rolls, you can change one DYNAMITE to GATLING "
        + "(Not if you roll three or more DYNMAMITE!).",
        "You may use each WHISKEY you roll twice",
        "If you roll ARROW, you may take the Indian Chief's Arrow"
        + " from another player",
        "Apply ARROW results only after your last roll. Your last roll isn't"
        + " necesarily the third one, you may stop earlier, as normal"};   
    
    // an int is used to fill the name, HP, description, picture link, and arrows of a character
    // using the arrays in the character class

    /**
     * Character
     * This constructs a character with a name, health points, text description, arrow count, picture
     * @param userinput integer selecting which character accessed out of an array with all the character options inside
     * @param role the string containing the role of the character (EX: Sheriff)
     */
    
    public Character(int userinput, String role) {
        this.name = initialName[userinput];
		this.realName = initialName[userinput];
        this.health = initialHealth[userinput];
        this.description = initialDescription[userinput];
        this.arrows = 0;
        this.picture=pictureFileArray[userinput];
        this.isDead = false;
        this.setRole(role);
        //this.maxHealth=initialHealth[userinput];
        if (role.equals("Sheriff")) {
            this.health = this.health + 2;
            this.maxHealth = this.health;
        } else {
            this.maxHealth = this.health;
        }
        this.isZombie = false;
        this.isZombieMaster = false;
        this.isDeadForever = false;
        this.hasChiefArrow = false;
    }

    /**
     * setIsDeadForever
     * @param isDeadForever Sets the dead forever param.
     */
    public void setIsDeadForever(Boolean isDeadForever)
    {
        this.isDeadForever=isDeadForever;
    }
    /**
     * setDuelWoundTokens
     * Sets the array containing whether or not a character has a certain duel token
     * @param duelTokens determines if the character has a certain duel token
     */
    public void setDuelWoundTokens(boolean[] duelTokens)
    {
        this.duelWoundTokens=duelTokens.clone();   
    }
    
    /**
     * setHasChiefArrow
     * Sets whether or not the character is holding the Chief's arrow token
     * @param hasChiefArrow determines whether or not they have the Chief's arrow
     */
    public void setHasChiefArrow(Boolean hasChiefArrow)
    {
        this.hasChiefArrow=hasChiefArrow;
    }
    
    /**
     * setPicture
     * Sets a portrait of the selected character to the current character
     * @param picture determines which picture to select
     */
    public void setPicture(String picture)
    {
        this.picture=picture;
    }
    
    /**
     * setRole
     * Sets the role (Sheriff, Deputy, Outlaw, Renegade) of the current character to the selected value
     * @param role determines which role to select
     */
    public void setRole(String role)
    {
        this.role=role;
    }

	/**
     * setName
     * Sets the name of the current character to the selected value
     * @param name determines the name of the character
     */
    public void setName(String name)
    {
        this.name=name;
    }

	/**
     * getRealName
     * Returns the RealName value of the character
     * @return Returns the RealName String of the character
     */
	public String getRealName()
    {
    	return this.realName;
    }
    
    /**
     * setisDead
     * Sets whether the player is alive or dead
     * @param isdead boolean determining if the player is currently active or has died
     */
    public void setisDead(boolean isdead)
    {
        this.isDead = isdead;
    }
    
    /**
     * setDescription
     * Sets the text description for each character that the GUI displays for the user
     * @param description the contents of each character description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * setHealth
     * Sets the number of hit points (health) each character has
     * @param health - number of hit points
     */
    public void setHealth(int health)
    {
        if(health >= 0)
        this.health = health;
    }
    
    /**
     * setMaxHealthHealth
     * Sets the number of maximum hit points (health) each character has initially
     * @param maxHealth - number of hit points
     */
	public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }
	
    /**
     * setArrows
     * Sets the number of arrows each character has initially 
     * @param arrows number of arrows each player has
     */
    public void setArrows(int arrows)
    {
        this.arrows = arrows;
    }
    
     /**
     * setIsZombie
     * Sets if the player is a zombie 
     * @param isZombie T/F whether player is a zombie 
     */
    public void setIsZombie(Boolean isZombie)
    {
        this.isZombie = isZombie;
    }
    
    /**
     * setIsZombieMaster
     * Sets if the player is the zombie master 
     * @param isZombieMaster T/F whether player is zombie master
     */
    public void setIsZombieMaster(Boolean isZombieMaster)
    {
        this.isZombieMaster = isZombieMaster;
    }
    
    /**
     * getRole
     * Returns which role the character has (Sheriff, Deputy, Renegade, Outlaw)
     * @return Will get the role of the character
     */
    public String getRole()
    {
        return role;
    }
    
     /**
     * getName
     * Returns the name of the character
     * @return Will get the name of the character
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * getisDead
     * Returns whether the selected character status is active or dead
     * @return will get if the character is dead or not
     */
    public Boolean getisDead()
    {
        return isDead;
    }
    
    /**
     * getDescription
     * returns the description of the selected character
     * @return will get the character description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * getHealth
     * gets the number of hit points the character has currently
     * @return will get the health of the character
     */
    public int getHealth()
    {
        return health;
    }
    
    /**
     * getArrows
     * Returns the number of arrows the character has currently
     * @return will get the arrows of the character
     */
    public int getArrows()
    {
        return arrows;
    }
    
    /**
     * getPicture
     * Returns the selected picture of the character
     * @return will get the picture of the character
     */
    public String getPicture()
    {
        return picture;
    }
    
    /**
     * getMaxHealth
     * Returns the max health of the character
     * @return will get the max health of the character
     */
    public int getMaxHealth()
    {
        return maxHealth;
    }
        
    /**
     * getChiefArrow
     * gets whether or not the character is holding the Chief's arrow token
     * @return will get check if the character has chiefs arrow
     */
    public Boolean getChiefArrow()
    {
        return hasChiefArrow;
    }
    
    /**
     * getIsZombieMaster
     * gets whether or not the character is the zombie master
     * @return will get the check if the character is the zombie master
     */
    public Boolean getIsZombieMaster()
    {
        return isZombieMaster;
    }
    
    /**
     * getIsZombieMaster
     * gets whether or not the character is a zombie
     * @return will get the check if the character is a zombie
     */
    public Boolean getIsZombie()
    {
        return isZombie;
    }
    
    /**
     * getDuelWoundTokens
     * Gets the array containing whether or not a character has a certain duel token
     * @return will get the array of boolean values of the character's duel wound tokens
     */
    public boolean[] getDuelWoundTokens()
    {
        return duelWoundTokens.clone();   
    }
    
    /**
     * hasAnyDuelTokens
     * Determines if the character has ANY duel wound tokens or not
     * @return will get a checker if the character has any duel wound tokens
     */
    public Boolean hasAnyDuelTokens()
    {
        for (boolean temp: this.duelWoundTokens)
            if (temp)
                return true;
        return false;
    }
    
    /**
     * getIsDeadForever
     * @return will get the check if the character is dead forever
     */
    public Boolean getIsDeadForever()
    {
        return isDeadForever;
    }
    
}