/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templatesu
 * and open the template in the editor.
 */
package project3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Summer Warrican
 */
public class Game {

    /**
     * Arraylist of all characters
     */
    public static ArrayList<Character> playerList = new ArrayList<>();
    private static ArrayList<Character> trimList = new ArrayList<>();

    /**
     * A list of dead characters
     */
    public static ArrayList<Character> zombieList = new ArrayList<>();

    /**
     * Boneyard cards
     */
    public static ArrayList<Integer> boneyardList = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 0));

    /**
     * Circularlinkedlist of all characters
     */
    public static CircularLinkedList playerlist = new CircularLinkedList();

    /**
     * The playerlist before any mutations
     */
    public static CircularLinkedList zombieCircular = new CircularLinkedList();

    /**
     * The copy of the playelist
     */
    public static CircularLinkedList playerlistcopy;

    /**
     * The list of AI
     */
    public static ArrayList<GeneralAI> aiList = new ArrayList<>();
    private static ArrayList<String> aiLogs = new ArrayList<>();
    private static int currentPlayer = 0;
    private static int arrowsLeft = 9;
    private static int beerToken = 3;
    private static int shoot1Token = 5;
    private static int shoot2Token = 5;
    private static int dynamiteToken = 2;
    private static int shoot1DieSeen = 0;
    private static int shoot2DieSeen = 0;
    private static int beerTokenSeen = 0;
    private static int totalTokens = beerToken + shoot1Token + shoot2Token + dynamiteToken;
    private static int totalNumberOfPlayers = 0;
    private static Character chiefArrowHolder = null;
    private static int chiefArrowsLeft = 1;
    private static boolean turnOver = false;
    private boolean gameFinished = false;
    private static boolean zombieOutbreak = false;
    private static int boneyardCounter = 0;
    private static int boneyard1Count = 0;
    private static int boneyard2Count = 0;

    /**
     * if expansions are enabled
     */
    public static boolean expEnabled = false;
    private static boolean gatlingUseTurn = false;
    private static int roundRerollCount = 0;
    private static String activeAI = "";
    private static boolean isAIActive = false;
    // BART CASSIDY,EL GRINGO,JOURDONNAIS,PAUL REGRET,PEDRO RAMIREZ,VULTURE SAM
    // 8,7,7,9,8,9
    private static Integer[] charNums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static int gameOverNumber = 0;
    private final static String[] Roles3 = {"Sheriff", "Renegade", "Outlaw"};
    private final static String[] Roles4 = {"Sheriff", "Renegade", "Outlaw", "Outlaw"};
    private final static String[] Roles5 = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Deputy"};
    private final static String[] Roles6 = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy"};
    private final static String[] Roles7 = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy", "Deputy"};
    private final static String[] Roles8 = {"Sheriff", "Renegade", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy",
        "Deputy"};

    /**
     * makeAI: will make the AI
     */
    public static void makeAI() {
        for (int i = 0; i < playerList.size(); i++) {
            GeneralAI temp = new GeneralAI(dice, playerList, playerList.get(i), i);
            aiList.add(temp);
        }
    }

    /**
     * expansionChecker: Checks if the expansions are enabled
     *
     * @param expNo the boolean value if yes or no
     */
    public static void expansionChecker(int expNo) {
        // expEnabled = no==1;
        if (expNo == 1) {
            expEnabled = true;
        } else {
            expEnabled = false;
        }
    }

    /**
     * makeCharacters: will make all the characters
     *
     * @param NoOfPlayers a int value of number of players
     */
    public static void makeCharacters(int NoOfPlayers) {
        totalNumberOfPlayers = NoOfPlayers;
        Collections.shuffle(Arrays.asList(charNums));
        switch (NoOfPlayers) {
            case 3:
                Collections.shuffle(Arrays.asList(Roles3));
                for (int i = 0; i < 3; i++) {
                    Character charac = new Character(charNums[i], Roles3[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            case 4:
                Collections.shuffle(Arrays.asList(Roles4));
                for (int i = 0; i < 4; i++) {
                    Character charac = new Character(charNums[i], Roles4[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            case 5:
                Collections.shuffle(Arrays.asList(Roles5));
                for (int i = 0; i < 5; i++) {
                    Character charac = new Character(charNums[i], Roles5[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            case 6:
                Collections.shuffle(Arrays.asList(Roles6));
                for (int i = 0; i < 6; i++) {
                    Character charac = new Character(charNums[i], Roles6[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            case 7:
                Collections.shuffle(Arrays.asList(Roles7));
                for (int i = 0; i < 7; i++) {
                    Character charac = new Character(charNums[i], Roles7[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            case 8:
                Collections.shuffle(Arrays.asList(Roles8));
                for (int i = 0; i < 8; i++) {
                    Character charac = new Character(charNums[i], Roles8[i]);
                    playerList.add(charac);
                    playerlist.insert(charac);
                    zombieCircular.insert(charac);
                }
                break;
            default:
                break;
        }
        try {
            playerlistcopy = (CircularLinkedList) playerlist.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<Die> dice = new ArrayList<Die>() {
        {
            add(new Die(0));
            add(new Die(0));
            add(new Die(0));
            add(new Die(0));
            add(new Die(0));
        }
    };
    private static ArrayList<Die> zombieDice = new ArrayList<Die>() {
        {
            add(new Die(0));
            add(new Die(0));
            add(new Die(0));
        }
    };

    /**
     *
     * @param dice1 if the first die was rerolled
     * @param dice2 if the second die was rerolled
     * @param dice3 if the third die was rerolled
     * @param dice4 if the fourth die was rerolled
     * @param dice5 if the fifth die was rerolled
     * @param player a character
     */
    public static void rerollDice(boolean dice1, boolean dice2, boolean dice3, boolean dice4, boolean dice5, Character player) {
        // Note---
        // need to rework roll method seems to be not so random
        // -------
        if (dice1 || dice2 || dice3 || dice4 || dice5) {
            roundRerollCount++;
        }

        if (dice1) {
            dice.get(0).reroll();
            dice.get(0).addRollCount();
            checkTokenRemoval(0);
        }
        if (dice2) {
            dice.get(1).reroll();
            dice.get(1).addRollCount();
            checkTokenRemoval(1);
        }
        if (dice3) {
            dice.get(2).reroll();
            dice.get(2).addRollCount();
            checkTokenRemoval(2);
        }
        if (dice4) {
            dice.get(3).reroll();
            dice.get(3).addRollCount();
            checkTokenRemoval(3);
        }
        if (dice5) {
            dice.get(4).reroll();
            dice.get(4).addRollCount();
            checkTokenRemoval(4);
        }
        checkDynamite(player);
        checkGoldenBullet();
        if (!"NOFACE".equals(player.getName())) {
            checkArrow(player, dice1, dice2, dice3, dice4, dice5);
        }
    }

    /**
     * checkGoldenBullet: will check if the player has rolled a golden bullet or
     * not
     */
    public static void checkGoldenBullet() {
        if (dice.get(2).getNumber() == 3 && dice.get(2).getExpansionNumber() == 1) {
            goldenBullet(getCharacter(1));
        }
    }

    /**
     * checkArrow: check if the player has rolled an arrow or not
     *
     * @param player a character
     * @param dice1 if the first die was rerolled
     * @param dice2 if the second die was rerolled
     * @param dice3 if the third die was rerolled
     * @param dice4 if the fourth die was rerolled
     * @param dice5 if the fifth die was rerolled
     */
    public static void checkArrow(Character player, boolean dice1, boolean dice2, boolean dice3, boolean dice4, boolean dice5) {
        if (dice.get(0).getNumber() == 6 && dice1) {
            takeArrow(player);
        }
        if (dice.get(1).getNumber() == 6 && dice2) {
            takeArrow(player);
        }
        if (dice.get(2).getNumber() == 6 && dice3) {
            takeArrow(player);
        }
        if (dice.get(3).getNumber() == 6 && dice4) {
            takeArrow(player);
        }
        if (dice.get(4).getNumber() == 6 && dice5) {
            takeArrow(player);
        }
    }

    /**
     * checkDynamite: will check if the dynamite is active
     *
     * @param player a character
     */
    public static void checkDynamite(Character player) {
        int counter = 0;
        if (dice.get(0).getNumber() == 5) {
            counter++;
        }
        if (dice.get(1).getNumber() == 5) {
            counter++;
        }
        if (dice.get(2).getNumber() == 5) {
            counter++;
        }
        if (dice.get(3).getNumber() == 5) {
            counter++;
        }
        if (dice.get(4).getNumber() == 5) {
            counter++;
        }
        // If player has dynamite duel token then the dynamite count is incremented
        // then the duel token is discarded from the player
        if (player.getDuelWoundTokens()[3] == true) {
            counter++;
        }
        if (counter >= 3) {
            player.setHealth(player.getHealth() - 1);
            if (player.getHealth() <= 0) {
                killPlayer(player);
            }
            turnOver = true;
        }
    }
    // Add functionality for rest dice checks

    /**
     * checkGatling: will check if gatling is active
     *
     * @param player a character
     */
    public static void checkGatling(Character player) {
        int counter = 0;
        if (dice.get(0).getNumber() == 4) {
            counter++;
        }
        if (dice.get(1).getNumber() == 4) {
            counter++;
        }
        if (dice.get(2).getNumber() == 4) {
            counter++;
            if (dice.get(2).getExpansionNumber() == 1) {
                counter++;
            }
        }
        if (dice.get(3).getNumber() == 4) {
            counter++;
        }
        if (dice.get(4).getNumber() == 4) {
            counter++;
        }
        // Double gatling functionality
        if (expEnabled) {
            for (Die die : dice) {
                if (die.getExpansionNumber() == 1 && die.getNumber() == 4) {
                    counter = counter + 2;
                }
            }
        }
        // Willy the kid ability to use gatling gun by rolling it twice instead of 3
        // times
        if (counter >= 2 && player.getName().equals("WILLY THE KID") && !gatlingUseTurn) {
            gatlingGun(player);
            returnAllArrows(player);
            gatlingUseTurn = true;
        } else if (counter >= 3 && !gatlingUseTurn) {
            gatlingGun(player);
            returnAllArrows(player);
            gatlingUseTurn = true;
        }
    }
    // Searches through dice to see if Shoot1 or SHoot 2 were rolled and if not
    // Suzy Lafayette gets two HP
    // only at end of turn

    /**
     * checkShoot: Suzy's ability call
     *
     * @param player a character
     */
    public static void checkShoot(Character player) {
        int counter = 0;
        for (Die die : dice) {
            if (die.getExpansionNumber() == 0 && (die.getNumber() == 1 || die.getNumber() == 2)) {
                counter++;
            } else if (die.getExpansionNumber() == 2 && (die.getNumber() == 1)) {
                counter++;
            }
        }
        if (player.getName().equals("SUZY LAFAYETTE") && counter == 0) {
            heal(player);
            heal(player);
        }
    }

    /**
     * getAsset
     *
     * @param number the index for the die
     * @return will return the asset for the die
     */
    public static String getAsset(int number) {
        return dice.get(number).getAsset();
    }

    /**
     * getDie
     *
     * @param number the die index number
     * @return will reutn the die at the index
     */
    public static Die getDie(int number) {
        return dice.get(number);
    }

    /**
     * gatlingGun: will call the gatling gun
     *
     * @param shooter a character
     */
    public static void gatlingGun(Character shooter) {
        Node current = playerlist.getHead();
        for (int i = 0; i < playerlist.length(); i++) {// Character ability for Paul Regret(cannot be shot by gatling)
            Character player = (Character) current.get();
            if (!(player.getName()).equals("PAUL REGRET") || !(player.getName().equals(shooter.getName()))) {
                player.setHealth(player.getHealth() - 1);
                if (player.getHealth() <= 0) {
                    killPlayer(player);
                }
            }
            current = current.getNext();
        }
    }

    /**
     * takeArrow: will take an arrow
     *
     * @param player a character
     */
    public static void takeArrow(Character player) {
        player.setArrows(player.getArrows() + 1);
        arrowsLeft--;
        if (arrowsLeft == 0) {
            indiansAttack();
        }
    }

    /**
     * putArrowBack: will put all the arrows back to the stack
     *
     * @param player a character
     */
    public static void putArrowBack(Character player) {
        if (player.getArrows() > 0 && !player.getChiefArrow()) {
            player.setArrows(player.getArrows() - 1);
            arrowsLeft++;
        }
        if (player.getArrows() > 2 && player.getChiefArrow()) {
            player.setArrows(player.getArrows() - 1);
            arrowsLeft++;
        }
    }

    /**
     * returnAllArrows: Will return all arrows to the stack
     *
     * @param player a character
     */
    public static void returnAllArrows(Character player) {
        arrowsLeft += player.getArrows();
        player.setArrows(0);
    }

    /**
     * heal: will heal the character
     *
     * @param player a character
     */
    public static void heal(Character player) {
        if ((player.getMaxHealth() > player.getHealth()) && player.getIsZombie() != true) {
            player.setHealth(player.getHealth() + 1);
        }
    }

    /**
     * charListOneTrim: will return the trimmed list for 1 attack
     *
     * @param realplayer a character
     */
    public static void charListOneTrim(String realplayer) {
        Node temp, current;
        if (zombieOutbreak && !(playerList.get(0).getIsZombie() || playerList.get(0).getIsZombieMaster())) {
            current = zombieCircular.getHead();
        } else {
            current = playerlist.getHead();
        }
        Character player = (Character) current.get();
        trimList.clear();
        int counter = 0;
        while (!realplayer.equals(player.getName())) {
            current = current.getNext();
            player = (Character) current.get();
            counter++;
            if (counter == 100) {
                break;
            }
        }
        temp = current.getPrev();
        player = (Character) temp.get();
        trimList.add(player);
        temp = current.getNext();
        player = (Character) temp.get();
        trimList.add(player);
    }

    /**
     * charListTwoTrim: will return the trimmed list for 2 attack
     *
     * @param realplayer a character
     */
    public static void charListTwoTrim(String realplayer) {
        Node temp, current;
        current = playerlist.getHead();
        if (zombieOutbreak && !(playerList.get(0).getIsZombie() || playerList.get(0).getIsZombieMaster())) {
            current = zombieCircular.getHead();
        }

        Character player = (Character) current.get();
        trimList.clear();
        if (zombieOutbreak && !(playerList.get(0).getIsZombie() || playerList.get(0).getIsZombieMaster())) {
            if (zombieCircular.length() < 4) {
                charListOneTrim(realplayer);
            } else {
                int counter = 0;
                while (!realplayer.equals(player.getName())) {
                    current = current.getNext();
                    player = (Character) current.get();
                    counter++;
                    if (counter == 100) {
                        break;
                    }
                }
                temp = current.getPrev().getPrev();
                player = (Character) temp.get();
                trimList.add(0, player);
                if (zombieCircular.length() > 4) {
                    temp = current.getNext().getNext();
                    player = (Character) temp.get();
                    trimList.add(1, player);
                }

            }
        } else {
            if (playerlist.length() < 4) {
                charListOneTrim(realplayer);
            } else {
                int counter = 0;
                while (!realplayer.equals(player.getName())) {
                    current = current.getNext();
                    player = (Character) current.get();
                    counter++;
                    if (counter == 100) {
                        break;
                    }
                }
                temp = current.getPrev().getPrev();
                player = (Character) temp.get();
                trimList.add(0, player);
                if (playerlist.length() > 4) {
                    temp = current.getNext().getNext();
                    player = (Character) temp.get();
                    trimList.add(1, player);
                }

            }
        }

    }

    /**
     * shootOneAway
     *
     * @param choice the person you want to shoot
     */
    public static void shootOneAway(int choice) {
        Character playerhit;
        // int bartDecision=extraDecision;//player's choice for whether to lose life
        // point(0) or take arrow(1)with Bart Cassidy
        // int pedroDecision=extraDecision;//player's choice for whether they want to
        // discard an arrow for each hp loss or not

        if (choice == 0) {
            playerhit = trimList.get(0);
        } else {
            playerhit = trimList.get(0);
            if (trimList.size() > 1) {
                playerhit = trimList.get(1);
            }
        }
        /*
     * Character ability for EL gringo When a player makes you lose one or more life
     * points, he must take an arrow. Life points lost to Indians or Dynamite are
     * not affected.(Find out if damage from gatling gun counts as well)
         */
        if (playerhit.getName().equals("EL GRINGO")) {
            takeArrow(getCharacter(1));
        }
        playerhit.setHealth(playerhit.getHealth() - 1);
        if (playerhit.getHealth() <= 0) {
            killPlayer(playerhit);
        }

    }

    /**
     * shootTwoAway
     *
     * @param choice the person you want to shoot
     */
    public static void shootTwoAway(int choice) {
        Character playerhit;
        // int bartDecision=extraDecision; //player's choice for whether to lose life
        // point(0) or take arrow(1)with Bart Cassidy
        // int pedroDecision=extraDecision;
        if (playerList.size() < 4) {
            shootOneAway(choice);
        } else {
            if (choice == 0) {
                playerhit = trimList.get(0);
            } else {
                playerhit = trimList.get(0);
                if (trimList.size() > 1) {
                    playerhit = trimList.get(1);
                }
            }

            // Character ability for EL gringo
            if (playerhit.getName().equals("EL GRINGO")) {
                takeArrow(getCharacter(1));
            }
            playerhit.setHealth(playerhit.getHealth() - 1);
            if (playerhit.getHealth() <= 0) {
                killPlayer(playerhit);
            }
        }

    }

    /**
     * resetArrows: will reset the arrows
     */
    public static void resetArrows() {
        arrowsLeft = 9;
        Node current = playerlist.getHead();
        for (int i = 0; i < playerlist.length(); i++) {
            Character player = (Character) current.get();
            player.setArrows(0);
            current = current.getNext();
        }
    }

    /**
     * getArrowsLeft
     *
     * @return will get the amount of arrows left in the pile
     */
    public static int getArrowsLeft() {
        return arrowsLeft;
    }

    /**
     * getChiefArrowsAmount
     *
     * @return will get the chief's arrow amount
     */
    public static int getChiefArrowsAmount() {
        return chiefArrowsLeft;
    }

    /**
     * takeChiefsArrow: Will take the chief's arrow
     *
     * @param player a character
     */
    public static void takeChiefsArrow(Character player) {
        putArrowBack(player);
        player.setHasChiefArrow(true);
        player.setArrows(player.getArrows() + 2);
        chiefArrowHolder = player;
        chiefArrowsLeft = 0;
    }
    // Take chief Arrow From another player if you are Apache Kid

    /**
     * apacheKidAbitlity: Apache's ability
     */
    public static void apacheKidAbitlity() {
        Character player = getCharacter(1);
        // If chief arrow is taken, take arrow from player
        if (chiefArrowsLeft == 0) {
            chiefArrowHolder.setHasChiefArrow(false);
            chiefArrowHolder.setArrows(player.getArrows() - 2);
            chiefArrowHolder = player;
            player.setArrows(player.getArrows() + 2);
            player.setHasChiefArrow(true);
        }

    }

    /**
     * indiansAttack: The Indians attack
     */
    public static void indiansAttack() {
        Node current = playerlist.getHead();
        for (int i = 0; i < playerlist.length(); i++) {
            Character player = (Character) current.get();
            // Character ability for Jourdonnais
            // You never lose more than one life point to Indians.
            if ((player.getName()).equals("JOURDONNAIS")) {
                if (player.getArrows() == 0) {
                    player.setHealth(player.getHealth());
                } else {
                    player.setHealth(player.getHealth() - 1);
                }
                if (player.getChiefArrow()) {
                    chiefArrowsLeft = 1;
                    chiefArrowHolder = null;
                    player.setHasChiefArrow(false);
                }

            } else if (expEnabled) {
                Character playerWithMostArrows = getPlayerWithMostArrows();
                if ((player.getChiefArrow())) {
                    if (player == playerWithMostArrows) {
                        player.setHealth(player.getHealth());
                    }
                    player.setHasChiefArrow(false);
                    chiefArrowsLeft = 1;
                    chiefArrowHolder = null;
                } else {
                    player.setHealth(player.getHealth() - player.getArrows());
                }
            } else {
                player.setHealth(player.getHealth() - player.getArrows());
            }

            if (player.getHealth() <= 0) {
                killPlayer(player);
            }
            current = current.getNext();
        }

        resetArrows();
    }

    /**
     * killPlayer: will kill the player and reset all their values
     *
     * @param deadplayer a character
     */
    public static void killPlayer(Character deadplayer) {
        Node current = playerlist.getHead();
        boolean[] duelWoundToken = new boolean[4];
        deadplayer.setisDead(true);
        if (deadplayer == chiefArrowHolder) {
            arrowsLeft += (deadplayer.getArrows() - 2);
        } else {
            arrowsLeft += deadplayer.getArrows();
        }
        deadplayer.setArrows(0);
        deadplayer.setDuelWoundTokens(duelWoundToken);
        if (deadplayer == chiefArrowHolder) {
            chiefArrowHolder = null;
            chiefArrowsLeft = 1;
            deadplayer.setHasChiefArrow(false);
        }
        //only create new zombies and add them to zombie list if zombie outbreak hasnt began
        //if outbreak has already began no one else turns into zombies, everyone dies for good
        if (!zombieOutbreak) {
            if (expEnabled) {
                zombieList.add(deadplayer);
            }

            playerlist.remove(deadplayer);
        } else {
            zombieCircular.remove(deadplayer);
            deadplayer.setIsDeadForever(true);
        }
        // Character ability for Vulture Sam
        for (int i = 0; i < playerlist.length(); i++) {
            Character player = (Character) current.get();
            if (((player.getName()).equals("VULTURE SAM")) && (player.getHealth() < player.getMaxHealth())) {
                if (!player.getisDead()) {
                    heal(player);
                    heal(player);
                }
            }
            current = current.getNext();
        }

    }

    // Fixed reset
    /**
     * resetGame: Will reset all values
     */
    public static void resetGame() {
        arrowsLeft = 9;
        beerToken = 3;
        shoot1Token = 5;
        shoot2Token = 5;
        dynamiteToken = 2;
        roundRerollCount = 0;
        expEnabled = false;
        playerlist.deleteList();
        playerList.clear();
        aiLogs.clear();
        aiList.clear();
        refreshTotalTokens();
        gameOverNumber = 0;
        chiefArrowHolder = null;
        chiefArrowsLeft = 1;
        startNewTurn();
    }

    /**
     * getCharacter
     *
     * @param num the character number
     * @return will return a character at the given number
     */
    public static Character getCharacter(int num) {
        Node current = playerlist.getHead();
        Character player = null;

        for (int i = 0; i < num; i++) {
            player = (Character) current.get();
            current = current.getNext();
        }
        return player;

    }

    // doActionsToTargetedPlayer: take a parameter string name and die number; the
    // method will find that player in the ArrayList and reference that object. It
    // will apply actions depending on the current die number to that player.
    // find out if i can just pass the player in as a parameter rather than just
    // their name
    /**
     * doActionsToTargetedPlayer
     *
     * @param player1 the first targeted player
     * @param player2 the second targeted player
     * @param dieNo the number of the die
     */
    public static void doActionsToTargetedPlayer(String player1, String player2, int dieNo) {
        int size = playerlist.size;
        Node current = playerlist.getHead();
        if (zombieOutbreak) {
            current = zombieCircular.getHead();
            size = zombieCircular.size;
        }
        Character firstPlayer = (Character) current.get();
        Character secondPlayer = null;
        int expansion = (dice.get(dieNo)).getExpansionNumber();
        int dieCurrentRollNumber = dice.get(dieNo).getNumber();
        if (player1 == null || player1.length() <= 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            if (player1.equals(firstPlayer.getName())) {
                break;
            } else {
                current = current.getNext();
                firstPlayer = (Character) current.get();
            }
        }
        current = playerlist.getHead();
        if (zombieOutbreak) {
            current = zombieCircular.getHead();
        }
        if (player2 != null) {
            secondPlayer = (Character) current.get();

            for (int i = 0; i < size; i++) {
                if (player1.equals(firstPlayer.getName())) {
                    break;
                } else {
                    current = current.getNext();
                    firstPlayer = (Character) current.get();
                }
            }
        }
        // DIE BASE
        if (expansion == 0) {
            switch (dieCurrentRollNumber) {
                case 1:
                    if (getCharacter(1).getDuelWoundTokens()[1] == true && expEnabled) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[1] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        shoot1Token++;
                    } else {
                        charListOneTrim(getCharacter(1).getName());
                        if (trimList.get(0).getName().equals(player1)) {
                            shootOneAway(0);
                        } else {
                            shootOneAway(1);
                        }
                    }
                    break;
                case 2:
                    if (getCharacter(1).getDuelWoundTokens()[2] == true) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[2] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        shoot2Token++;
                    } else {
                        charListTwoTrim(getCharacter(1).getName());
                        if (trimList.get(0).getName().equals(player1)) {
                            shootTwoAway(0);
                        } else {
                            shootTwoAway(1);
                        }
                    }
                    break;
                case 3:
                    if (getCharacter(1).getDuelWoundTokens()[0] == true && expEnabled) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[0] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        beerToken++;
                    } else if (!getCharacter(1).getIsZombie()) {
                        heal(firstPlayer);
                    }
                    break;
                case 4:
                    checkGatling(getCharacter(1));
                    break;
                default:
                    break;
            }
        }
        // DIE EXPANSION 1
        if (expansion == 1) {
            switch (dieCurrentRollNumber) {
                case 1:
                    if (getCharacter(1).getDuelWoundTokens()[1] == true) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[1] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        shoot1Token++;
                    } else {
                        charListOneTrim(playerList.get(1).getName());
                        if (trimList.get(0).getName().equals(player1)) {
                            shootOneAway(0);
                        } else {
                            shootOneAway(1);
                        }
                        if (trimList.get(0).getName().equals(player2)) {
                            shootOneAway(0);
                        } else {
                            shootOneAway(1);
                        }
                    }
                    break;
                case 2:
                    if (getCharacter(1).getDuelWoundTokens()[2] == true) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[2] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        shoot2Token++;
                    } else {
                        charListTwoTrim(playerList.get(1).getName());
                        if (trimList.get(0).getName().equals(player1)) {
                            shootTwoAway(0);
                        } else {
                            shootTwoAway(1);
                        }
                        if (trimList.get(0).getName().equals(player2)) {
                            shootTwoAway(0);
                        } else {
                            shootTwoAway(1);
                        }
                    }
                    break;
                case 4:
                    checkGatling(getCharacter(1));
                    break;
                default:
                    break;
            }
        }

        // DIE EXPANSION 2
        if (expansion == 2) {
            switch (dieCurrentRollNumber) {
                case 1:
                    charListOneTrim(getCharacter(1).getName());
                    if (trimList.get(0).getName().equals(player1)) {
                        shootOneAway(0);
                    } else {
                        shootOneAway(1);
                    }
                    break;
                case 2:
                    // If the person only has the chief arrow then they cannot break/put back arrow
                    if (!(firstPlayer.getChiefArrow()) && (firstPlayer.getArrows() == 2)) {
                        putArrowBack(firstPlayer);
                    }
                    break;
                case 3:
                    if (getCharacter(1).getDuelWoundTokens()[0] == true) {
                        boolean[] playerTokens = getCharacter(1).getDuelWoundTokens();
                        playerTokens[0] = false;
                        getCharacter(1).setDuelWoundTokens(playerTokens);
                        beerToken++;
                    } else {
                        heal(firstPlayer);
                    }
                    break;
                case 4:
                    heal(firstPlayer);
                    heal(secondPlayer);
                    break;
                default:
                    break;
            }

        }

        // DIE EXPANSION 3
        if (expansion == 3) {

            switch (dieCurrentRollNumber) {
                case 1:
                    duelPlayer(firstPlayer);
                    break;
                case 2:
                    duelPlayer(firstPlayer);
                    break;
                case 3:
                    if (getCharacter(1).equals("GREG DIGGER")) {
                        heal(getCharacter(1));
                        removeDuelToken(getCharacter(1));
                        heal(getCharacter(1));
                        removeDuelToken(getCharacter(1));
                    } else {
                        heal(getCharacter(1));
                        removeDuelToken(getCharacter(1));
                    }
                    break;
                case 4:
                    checkGatling(getCharacter(1));
                    break;
                default:
                    break;
            }
        }

        if (dieCurrentRollNumber == 6) {
            if ("NOFACE".equals(getCharacter(1).getName())) {
                takeArrow(getCharacter(1));
            }
        }

    }

    /**
     * getRoundRerollCount
     *
     * @return will get the total reroll count
     */
    public static int getRoundRerollCount() {
        return roundRerollCount;
    }

    /**
     * getTurnOver
     *
     * @return will get if the turn is over or not
     */
    public static boolean getTurnOver() {
        return turnOver;
    }

    /**
     * getGatlingGunUsed
     *
     * @return Will return if the gatling gun used or not
     */
    public static boolean getGatlingGunUsed() {
        return gatlingUseTurn;
    }

    /**
     * getTrimList
     *
     * @return Will return a trimmed list of characters
     */
    public static ArrayList<Character> getTrimList() {
        return trimList;
    }

    /**
     * getPlayerWithMostArrows
     *
     * @return Will return the character with the most arrows
     */
    public static Character getPlayerWithMostArrows() {
        Node current = playerlist.getHead();
        Character player = (Character) current.get();
        Character playerWithTheMostArrows = player;
        boolean tieFlag = false;
        for (int i = 0; i < playerlist.length(); i++) {
            if (player.getArrows() > playerWithTheMostArrows.getArrows()) {
                playerWithTheMostArrows = player;
                tieFlag = false;
            } else if (player.getArrows() == playerWithTheMostArrows.getArrows()) {
                tieFlag = true;
            }
            current = current.getNext();
            player = (Character) current.get();
        }
        if (tieFlag) {
            return null;
        } else {
            return playerWithTheMostArrows;
        }

    }

    /**
     * goldenBullet: Will do the golden bullet die action
     *
     * @param playerHit a character
     */
    public static void goldenBullet(Character playerHit) {
        playerHit.setHealth(playerHit.getHealth() - 1);
        if (playerHit.getHealth() <= 0) {
            killPlayer(playerHit);
        }
    }

    /**
     * duelPlayer: will challenge a character to a duel
     *
     * @param player the character being challenged
     */
    public static void duelPlayer(Character player) { //player denotes the character dueling the User.
        Die duelDie = new Die(3);
        int whosTurn = 1; // 0 denotes player turn, 1 denotes user turn
        do {
            whosTurn++;
            whosTurn = whosTurn % 2;
            duelDie.reroll();
        } while ((duelDie.getNumber() == 1) || (duelDie.getNumber() == 2));
        if (whosTurn == 0) {
            Game.loseDuel(player);
        } else {
            Game.loseDuel(Game.playerList.get(0));
        }
    }

    /**
     * removeDuelToken: will remove a duel wound token
     *
     * @param player a character
     */
    public static void removeDuelToken(Character player) {
        int i = 0;
        if (player.hasAnyDuelTokens()) {
            boolean playerTokens[] = player.getDuelWoundTokens();
            for (i = 0; i < 4; i++) {
                if (playerTokens[i] == true) {
                    playerTokens[i] = false;
                    player.setDuelWoundTokens(playerTokens);
                    if (i == 0) {
                        beerToken++;
                    }
                    if (i == 1) {
                        shoot1Token++;
                    }
                    if (i == 2) {
                        shoot2Token++;
                    }
                    if (i == 3) {
                        dynamiteToken++;
                    }
                }
            }
        }
        refreshTotalTokens();
    }

    /**
     * loseDuel: will do certain actions to the loser of the duel
     *
     * @param playerLost a character
     */
    public static void loseDuel(Character playerLost) {
        // boolean[] duelWoundToken = new boolean[3];
        boolean[] playerTokenList = new boolean[4];
        int num;
        Random generator = new Random();
        playerLost.setHealth(playerLost.getHealth() - 1);
        if (playerLost.getHealth() <= 0) {
            killPlayer(playerLost);
        }
        //Zombies dont collect duel tokens
        if (playerLost.getIsZombie()) {
            return;
        }
        num = generator.nextInt(4);
        while ((num == 0 && beerToken == 0) || (num == 1 && shoot1Token == 0) || (num == 2 && shoot2Token == 0)
                || (num == 3 && dynamiteToken == 0)) {
            num = generator.nextInt(4);
        }
        playerTokenList = playerLost.getDuelWoundTokens();
        if (playerTokenList[num] == true) {
            return;
        }
        playerTokenList[num] = true;
        playerLost.setDuelWoundTokens(playerTokenList);
        if (num == 0) {
            beerToken--;
        }
        if (num == 1) {
            shoot1Token--;
        }
        if (num == 2) {
            shoot2Token--;
        }
        if (num == 3) {
            dynamiteToken--;
        }
        if (playerLost.getName() == playerList.get(0).getName()) {
            dice.forEach((die) -> {
                if (die.getExpansionNumber() == 0) {
                    if (die.getNumber() == 1) {
                        shoot1DieSeen++;
                    }
                    if (die.getNumber() == 2) {
                        shoot2DieSeen++;
                    }
                    if (die.getNumber() == 3) {
                        beerTokenSeen++;
                    }
                }
                if (die.getExpansionNumber() == 1) {
                    if (die.getNumber() == 1) {
                        shoot1DieSeen++;
                    }
                    if (die.getNumber() == 2) {
                        shoot2DieSeen++;
                    }
                }

                if (die.getExpansionNumber() == 2) {
                    if (die.getNumber() == 1) {
                        shoot1DieSeen++;
                    }
                    if (die.getNumber() == 3) {
                        beerTokenSeen++;
                    }
                    if (die.getNumber() == 4) {
                        beerTokenSeen++;
                    }
                }
            });
        }
        refreshTotalTokens();
    }

    /**
     * returnAllTokens: will return all tokens from character
     *
     * @param player a character
     */
    public static void returnAllTokens(Character player) {

        boolean playerTokens[] = player.getDuelWoundTokens();
        for (int i = 0; i < 4; i++) {
            if (playerTokens[i] == true) {
                if (i == 0) {
                    beerToken++;
                } else if (i == 1) {
                    shoot1Token++;
                } else if (i == 2) {
                    shoot2Token++;
                } else if (i == 3) {
                    dynamiteToken++;
                }
            }
        }
        boolean[] duelWoundToken = new boolean[4];
        player.setDuelWoundTokens(duelWoundToken);
        refreshTotalTokens();

    }

    /**
     * startNewTurn: Will reset the values to start a new turn
     */
    public static void startNewTurn() {
        getDie(0).resetDie();
        getDie(1).resetDie();
        getDie(2).resetDie();
        getDie(3).resetDie();
        getDie(4).resetDie();
        roundRerollCount = 0;
        turnOver = false;
        gatlingUseTurn = false;
        if (playerList.size() > 0) {
            removeDuelToken(playerList.get(0));
        }
    }

    /**
     * callAITurn: will call the AI to do it's turn
     */
    public static void callAITurn() {
        ArrayList<String> logList = new ArrayList<String>();
        isAIActive = true;
        aiLogs.clear();
        boolean zombieOutbreakFlag = false;
        for (int i = 1; i < Game.aiList.size(); i++) {
            GeneralAI tempAI = Game.aiList.get(i);
            if (!tempAI.getCharSelf().getisDead()) {
                Game.activeAI = tempAI.getCharSelf().getName();
                logList = tempAI.doTurnExpan();
                tempAI.resetRerollCount();
            } else if ((Game.zombieOutbreak) && (tempAI.getCharSelf().getIsZombie())) {
                Game.activeAI = tempAI.getCharSelf().getName();
                logList = tempAI.doTurnExpan();
                tempAI.resetRerollCount();
            } else if ((Game.expEnabled) && (Game.totalNumberOfPlayers >= 4) && (!Game.zombieOutbreak)) {
                Game.drawBoneyardCard();
                if (Game.zombieOutbreak) {
                    if (!zombieOutbreakFlag) {
                        zombieOutbreakFlag = true;
                        for (GeneralAI zombieAI : Game.aiList) {
                            zombieAI.updateZombieAI(); // This will adjust the suspicion values and health values of the AI who are zombies.
                        }
                    }
                }
            }
            for (String tempStr : logList) {
                aiLogs.add(tempStr);
            }
        }
        logList.clear();
        isAIActive = false;
    }

    /**
     * getAiLogs
     *
     * @return will return the arraylist of actions the AI has taken
     */
    public static ArrayList getAiLogs() {
        return aiLogs;
    }

    /**
     * checkIfGameOver: will check if the game is over or not
     */
    public static void checkIfGameOver() {
        //GAME OVER CODES
        //1 - Sheriff died , outlaws win
        //2 - All outlaws and renegades died, sheriff and deputy win
        //3 - All Outlaws and Deputies and Sheriff has died ,renegade win
        //4 - All zombies and their master died , survivors win
        //5 - All survivors die, Zombies win
        //5 - All players die - so zombies win *special case*
        int evilDooerCounter = 0;
        int renegadeCheckerToWin = 0;
        int zombieCounter = 0;
        int survivorCounter = 0;
        for (Character n : playerList) {
            if ("Sheriff".equals(n.getRole())) {
                if (n.getisDead() && !zombieOutbreak) {
                    gameOverNumber = 1;
                }
            }
            if ("Outlaw".equals(n.getRole()) || "Renegade".equals(n.getRole())) {
                if (n.getisDead()) {
                    evilDooerCounter++;
                }
            }
            if ("Outlaw".equals(n.getRole()) || "Sheriff".equals(n.getRole()) || "Deputy".equals(n.getRole())) {
                if (n.getisDead()) {
                    renegadeCheckerToWin++;
                }
            }
            //If player is a zombie or master and theyre alive then increment counter, if counter remains at 0 then zombie team is dead
            if (zombieOutbreak && ("Zombie".equals(n.getRole()) || "Master".equals(n.getRole()))) {
                if (!n.getIsDeadForever()) {
                    zombieCounter++;
                }
            }
            // If player is not a zombie or master and they are alive, increment counter, if counter remains at 0 then survivor team is dead
            if (zombieOutbreak && (!"Zombie".equals(n.getRole())) && (!"Master".equals(n.getRole()))) {
                if (!n.getIsDeadForever()) {
                    survivorCounter++;
                }
            }
        }
        switch (totalNumberOfPlayers) {
            case 3:
                if (evilDooerCounter == 2) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 2) {
                    gameOverNumber = 3;
                }
                break;
            case 4:
                if (evilDooerCounter == 3 && !zombieOutbreak) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 3 && !zombieOutbreak) {
                    gameOverNumber = 3;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter > 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter > 0)) {
                    gameOverNumber = 4;
                }
                break;
            case 5:
                if (evilDooerCounter == 3 && !zombieOutbreak) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 4 && !zombieOutbreak) {
                    gameOverNumber = 3;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter > 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter > 0)) {
                    gameOverNumber = 4;
                }
                break;
            case 6:
                if (evilDooerCounter == 4 && !zombieOutbreak) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 5 && !zombieOutbreak) {
                    gameOverNumber = 3;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter > 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter > 0)) {
                    gameOverNumber = 4;
                }
                break;
            case 7:
                if (evilDooerCounter == 4 && !zombieOutbreak) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 6 && !zombieOutbreak) {
                    gameOverNumber = 3;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter > 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter > 0)) {
                    gameOverNumber = 4;
                }
                break;
            case 8:
                if (evilDooerCounter == 5 && !zombieOutbreak) {
                    gameOverNumber = 2;
                }
                if (renegadeCheckerToWin == 7 && !zombieOutbreak) {
                    gameOverNumber = 3;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter > 0 && survivorCounter == 0)) {
                    gameOverNumber = 5;
                }
                if (zombieOutbreak && (zombieCounter == 0 && survivorCounter > 0)) {
                    gameOverNumber = 4;
                }
                break;
            default:
                break;
        }
    }

    /**
     * getGameOverArray
     *
     * @return will return a number code depending on which game over it is
     */
    public static int getGameOverArray() {
        return gameOverNumber;
    }

    /**
     * refreshTotalTokens: refreshes the total number of tokens
     */
    public static void refreshTotalTokens() {
        totalTokens = beerToken + shoot1Token + shoot2Token + dynamiteToken;
        if (playerList.size() > 0) {
            checkDynamite(playerList.get(0));
        }
    }

    /**
     * getTotalTokens
     *
     * @return will get the total number of tokens left
     */
    public static int getTotalTokens() {
        return totalTokens;
    }

    /**
     * checkTokenRemoval: will remove a random token
     *
     * @param dieNo the dice number
     */
    public static void checkTokenRemoval(int dieNo) {
        boolean playerTokens[] = getCharacter(1).getDuelWoundTokens();
        if (getDie(dieNo).getExpansionNumber() == 0) {
            if (getCharacter(1).getDuelWoundTokens()[0]) {
                if (getDie(dieNo).getNumber() == 3 && beerToken == 2) {
                    playerTokens[0] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    beerToken++;
                    beerTokenSeen = 0;
                } else {
                    beerTokenSeen++;
                }
            }
            if (getCharacter(1).getDuelWoundTokens()[1]) {
                if (getDie(dieNo).getNumber() == 1 && shoot1DieSeen == 2) {
                    playerTokens[1] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    shoot1Token++;
                    shoot1DieSeen = 0;
                } else {
                    shoot1DieSeen++;
                }
            }
            if (getCharacter(1).getDuelWoundTokens()[2]) {
                if (getDie(dieNo).getNumber() == 2 && shoot2DieSeen == 2) {
                    playerTokens[2] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    shoot2Token++;
                    shoot2DieSeen = 0;
                } else {
                    shoot2DieSeen++;
                }
            }
        }

        if (getDie(dieNo).getExpansionNumber() == 1) {
            if (getCharacter(1).getDuelWoundTokens()[1]) {
                if (getDie(dieNo).getNumber() == 1 && shoot1DieSeen == 2) {
                    playerTokens[1] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    shoot1Token++;
                    shoot1DieSeen = 0;
                } else {
                    shoot1DieSeen++;
                }
            }
            if (getCharacter(1).getDuelWoundTokens()[2]) {
                if (getDie(dieNo).getNumber() == 2 && shoot2DieSeen == 2) {
                    playerTokens[2] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    shoot2Token++;
                    shoot2DieSeen = 0;
                } else {
                    shoot2DieSeen++;
                }
            }
        }

        if (getDie(dieNo).getExpansionNumber() == 2) {
            if (getCharacter(1).getDuelWoundTokens()[1]) {
                if (getDie(dieNo).getNumber() == 1 && shoot1DieSeen == 2) {
                    playerTokens[1] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    shoot1Token++;
                    shoot1DieSeen = 0;
                } else {
                    shoot1DieSeen++;
                }
            }
            if (getCharacter(1).getDuelWoundTokens()[0]) {
                if (getDie(dieNo).getNumber() == 3 && beerTokenSeen == 2) {
                    playerTokens[0] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    beerToken++;
                    beerTokenSeen = 0;
                } else {
                    beerTokenSeen++;
                }
            }
            if (getCharacter(1).getDuelWoundTokens()[0]) {
                if (getDie(dieNo).getNumber() == 4 && beerTokenSeen == 2) {
                    playerTokens[0] = false;
                    getCharacter(1).setDuelWoundTokens(playerTokens);
                    beerToken++;
                    beerTokenSeen = 0;
                } else {
                    beerTokenSeen++;
                }
            }
        }
    }

    /**
     * drawBoneyardCard: will draw a boneyard card
     */
    public static void drawBoneyardCard() {
        Collections.shuffle(boneyardList);
        int value = boneyardList.get(0);
        if (value == 1) {
            boneyard1Count += 1;
        }
        if (value == 2) {
            boneyard2Count += 1;
        }
        boneyardCounter += value;
        boneyardList.remove(0);
        //return 0 cards to boneyard card pile
        if (value == 0) {
            boneyardList.add(0);
        }
        //Zombie outbreak only occurs if there are 4 or more players and if boneyard card values
        //are more than the number of remaining players(alive)
        if (boneyardCounter > playerlist.length() && totalNumberOfPlayers >= 4 && !zombieOutbreak) {
            zombieOutbreak = true;
            zombieOutbreak();
        }
    }

    /**
     * zombieOutbreak: starts zombie outbreak
     */
    public static void zombieOutbreak() {
        currentPlayer = getCurrentPlayerNum();
        ArrayList<Character> renegadeList = new ArrayList<>();
        if (totalNumberOfPlayers == 8) {
            for (int i = 0; i < playerlist.length(); i++) {
                Character player = (Character) playerlist.getHead().get();
                if (player.getRole().equals("Renegade") && !player.getisDead()) {
                    renegadeList.add(player);
                }
                playerlist.next();
            }
            //If only one renegade is alive assign Zombie master to them  
            if (renegadeList.size() == 1) {
                renegadeList.get(0).setIsZombieMaster(true);
                renegadeList.get(0).setRole("Master");
            } else //if two renegades are alive
            {
                getZombieMaster(playerList.get(currentPlayer)).setIsZombieMaster(true);
                getZombieMaster(playerList.get(currentPlayer)).setRole("Master");
            }
        } else {
            for (int i = 0; i < playerlist.length(); i++) {
                Character player = (Character) playerlist.getHead().get();
                if (player.getRole().equals("Renegade") && !player.getisDead()) {
                    player.setIsZombieMaster(true);
                    player.setRole("Master");
                }
                playerlist.next();
            }
        }
        int counter = 1;
        for (Character zombie : zombieList) {
            zombie.setHealth(playerlist.length());
            zombie.setMaxHealth(playerlist.length());
            zombie.setName("Zombie " + counter);
            zombie.setIsZombie(true);
            zombie.setRole("Zombie");
            counter++;
        }
    }

    /**
     * getZombieOutbreakBoolean
     *
     * @return will get if the zombie outbreak has happened or not
     */
    public static boolean getZombieOutbreakBoolean() {
        return zombieOutbreak;
    }

    /**
     * getBoneyard1Count
     *
     * @return Will return the count for 1 boneyard cards active
     */
    public static int getBoneyard1Count() {
        return boneyard1Count;
    }

    /**
     * getBoneyard2Count
     *
     * @return Will return the count for 2 boneyard cards active
     */
    public static int getBoneyard2Count() {
        return boneyard2Count;
    }

    /**
     * getBoneYardTotal
     *
     * @return Will return the total number of boneyard cards left
     */
    public static int getBoneyardTotal() {
        return boneyardList.size();
    }

    /**
     * getZombieMaster
     *
     * @param currentPlayer the current player being affected
     * @return will return a character of who the zombie master is
     */
    public static Character getZombieMaster(Character currentPlayer) {
        Node current = playerlist.getHead();
        Character player = (Character) current.get();
        boolean foundRenegade = false;
        Character renegade = null;

        while (!currentPlayer.getName().equals(player.getName())) {
            current = current.getNext();
            player = (Character) current.get();
        }
        Node temp = current;

        while (foundRenegade != true) {
            current = current.getPrev();
            player = (Character) current.get();
            if (player.getRole().equals("Renegade")) {
                renegade = player;
                foundRenegade = true;
            }
        }

        return renegade;
    }

    /**
     * getCurrentPlayerNum
     *
     * @return will retun the number of the current player
     */
    public static int getCurrentPlayerNum() {
        int num = 0;
        for (Character player : playerList) {
            num++;
            if (player.getName().equals(activeAI)) {
                break;
            }
        }
        if (isAIActive) {
            return num;
        } else {
            return 0;
        }
    }
}
