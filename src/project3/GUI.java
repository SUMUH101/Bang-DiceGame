package project3;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Arthr
 */
public class GUI extends Application {

    private Stage window;
    private Scene mainMenu, gameView;
    private final GridPane centerPane = new GridPane();
    BorderPane borderPrimary = new BorderPane();
    private int amountOfPlayers = 2;
    private int hasExpansions = 0;
    private ComboBox amountOfPlayersBox;
    private ComboBox withOrWithoutExpansionsBox;
    private boolean rerollChecker = false;
    private boolean inGame = false;
    private boolean belleAbilityUsedThisTurn = false;
    private boolean printedGatling = false;
    private String duel1Selection = "";
    private String duel2Selection = "";
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D bounds = screen.getVisualBounds();
    private VBox actionTextList = new VBox();
    private Text gameover = new Text();
    private boolean gameoverBool = false;
    private boolean[] dueltokens = {false, false, false, false};
    private boolean duel1 = false;
    private boolean duel2 = false;
    private ArrayList<String> actionPanelChoices = new ArrayList<String>() {
        {
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
        }
    };
    private String realPlayerCharacter = "";

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Bang! The Dice Game");
        this.buildMainMenu();
        window.setMaximized(true);
        window.show();
    }

    /**
     * BuildMainMenu: This method will init the first screen
     */
    private void buildMainMenu() {
        StackPane root = new StackPane();
        mainMenu = new Scene(root, bounds.getWidth(), bounds.getHeight() - 20);
        Button play = newGameButton();
        play.setTranslateX(-150);
        play.setMaxWidth(200);
        play.setMaxHeight(50);

        Button quit = quitButton();
        quit.setTranslateX(150);
        quit.setMaxWidth(200);
        quit.setMaxHeight(50);

        Text aiText = new Text("Number of AI players:");
        aiText.setTranslateY(75);
        aiText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        amountOfPlayersBox = new ComboBox();
        amountOfPlayersBox.getItems().addAll("2", "3", "4", "5", "6", "7");
        amountOfPlayersBox.setTranslateY(110);
        amountOfPlayersBox.getSelectionModel().selectFirst();

        Text expansionText = new Text("Play with or without Expansions:");
        expansionText.setTranslateY(160);
        expansionText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        withOrWithoutExpansionsBox = new ComboBox();
        withOrWithoutExpansionsBox.getItems().addAll("Without", "With");
        withOrWithoutExpansionsBox.setTranslateY(195);
        withOrWithoutExpansionsBox.getSelectionModel().selectFirst();

        this.printBangName(root);
        root.getChildren().addAll(play, quit, amountOfPlayersBox, aiText, expansionText, withOrWithoutExpansionsBox);
        window.setScene(mainMenu);
    }

    /**
     * printBangName: Creates and builds the Bang! The Dice Game name.
     *
     * @param root The pane that it will be placed on.
     */
    private void printBangName(StackPane root) {
        //Creating a Text object 
        Text primaryText = new Text();
        Text secondaryText = new Text();
        //Setting font to the text 
        primaryText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

        secondaryText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        //setting the position of the text  
        primaryText.setTranslateX(0);
        primaryText.setTranslateY(-150);

        secondaryText.setTranslateX(0);
        secondaryText.setTranslateY(-100);
        //Setting the color 
        primaryText.setFill(Color.RED);

        secondaryText.setFill(Color.WHITE);
        //Setting the Stroke  
        primaryText.setStrokeWidth(2);

        secondaryText.setStrokeWidth(2);
        // Setting the stroke color
        primaryText.setStroke(Color.BLACK);

        secondaryText.setStroke(Color.BLACK);

        //Setting the text to be added. 
        primaryText.setText("BANG!");
        secondaryText.setText("THE DICE GAME");
        root.getChildren().add(primaryText);
        root.getChildren().add(secondaryText);
    }

    /**
     * startGame: It will build the second window of the game.
     */
    private void startGame() {
        borderPrimary = new BorderPane();
        StackPane temp = new StackPane();
        actionTextList = new VBox();
        borderPrimary.setPadding(new Insets(10, 20, 10, 20));
        realPlayerCharacter = Game.getCharacter(1).getName();
        borderPrimary.setLeft(combineLeft());

        borderPrimary.setCenter(center());
        borderPrimary.setTop(temp);
        borderPrimary.setBottom(addBottomButtons());
        borderPrimary.setRight(actionPanel(Game.getDie(0).getNumber(), Game.getDie(1).getNumber(), Game.getDie(2).getNumber(), Game.getDie(3).getNumber(), Game.getDie(4).getNumber()));
        gameView = new Scene(borderPrimary, bounds.getWidth(), bounds.getHeight() - 20);

        window.setScene(gameView);
        //window.setFullScreen(true);
    }

    /**
     * center: builds the center of the startGame() method
     *
     * @return returns the everything that is in the center. IE player cards and
     * arrows left.
     */
    private GridPane center() {
        centerPane.getChildren().clear();
        centerPane.setAlignment(Pos.CENTER);
        VBox arrows = arrowCounter(Game.getArrowsLeft(), Game.getChiefArrowsAmount());
        StackPane card = new StackPane(), card2 = new StackPane(), card3 = new StackPane(), card4 = new StackPane(), card5 = new StackPane(), card6 = new StackPane(), card7 = new StackPane(), card8 = new StackPane();
        //Note------------------
        //Need to add a for each loop
        //Center should take an arraylist of all the current players or whatever
        //It will populate this
        switch (amountOfPlayers) {
            case 7:
                card8 = createCharacterCard(Game.playerList.get(7).getHealth(), Game.playerList.get(7).getName(), Game.playerList.get(7).getDescription(), Game.playerList.get(7).getRole(), "AI", Game.playerList.get(7).getPicture(), Game.playerList.get(7).getArrows(), Game.playerList.get(7).getChiefArrow(), Game.playerList.get(7).getisDead(), Game.playerList.get(7).getIsZombie(), Game.playerList.get(7).getIsDeadForever());
            case 6:
                card7 = createCharacterCard(Game.playerList.get(6).getHealth(), Game.playerList.get(6).getName(), Game.playerList.get(6).getDescription(), Game.playerList.get(6).getRole(), "AI", Game.playerList.get(6).getPicture(), Game.playerList.get(6).getArrows(), Game.playerList.get(6).getChiefArrow(), Game.playerList.get(6).getisDead(), Game.playerList.get(6).getIsZombie(), Game.playerList.get(6).getIsDeadForever());
            case 5:
                card6 = createCharacterCard(Game.playerList.get(5).getHealth(), Game.playerList.get(5).getName(), Game.playerList.get(5).getDescription(), Game.playerList.get(5).getRole(), "AI", Game.playerList.get(5).getPicture(), Game.playerList.get(5).getArrows(), Game.playerList.get(5).getChiefArrow(), Game.playerList.get(5).getisDead(), Game.playerList.get(5).getIsZombie(), Game.playerList.get(5).getIsDeadForever());
            case 4:
                card5 = createCharacterCard(Game.playerList.get(4).getHealth(), Game.playerList.get(4).getName(), Game.playerList.get(4).getDescription(), Game.playerList.get(4).getRole(), "AI", Game.playerList.get(4).getPicture(), Game.playerList.get(4).getArrows(), Game.playerList.get(4).getChiefArrow(), Game.playerList.get(4).getisDead(), Game.playerList.get(4).getIsZombie(), Game.playerList.get(4).getIsDeadForever());
            case 3:
                card4 = createCharacterCard(Game.playerList.get(3).getHealth(), Game.playerList.get(3).getName(), Game.playerList.get(3).getDescription(), Game.playerList.get(3).getRole(), "AI", Game.playerList.get(3).getPicture(), Game.playerList.get(3).getArrows(), Game.playerList.get(3).getChiefArrow(), Game.playerList.get(3).getisDead(), Game.playerList.get(3).getIsZombie(), Game.playerList.get(3).getIsDeadForever());
            case 2:
                card3 = createCharacterCard(Game.playerList.get(2).getHealth(), Game.playerList.get(2).getName(), Game.playerList.get(2).getDescription(), Game.playerList.get(2).getRole(), "AI", Game.playerList.get(2).getPicture(), Game.playerList.get(2).getArrows(), Game.playerList.get(2).getChiefArrow(), Game.playerList.get(2).getisDead(), Game.playerList.get(2).getIsZombie(), Game.playerList.get(2).getIsDeadForever());
                card2 = createCharacterCard(Game.playerList.get(1).getHealth(), Game.playerList.get(1).getName(), Game.playerList.get(1).getDescription(), Game.playerList.get(1).getRole(), "AI", Game.playerList.get(1).getPicture(), Game.playerList.get(1).getArrows(), Game.playerList.get(1).getChiefArrow(), Game.playerList.get(1).getisDead(), Game.playerList.get(1).getIsZombie(), Game.playerList.get(1).getIsDeadForever());
                card = createCharacterCard(Game.playerList.get(0).getHealth(), Game.playerList.get(0).getName(), Game.playerList.get(0).getDescription(), Game.playerList.get(0).getRole(), "Player", Game.playerList.get(0).getPicture(), Game.playerList.get(0).getArrows(), Game.playerList.get(0).getChiefArrow(), Game.playerList.get(0).getisDead(), Game.playerList.get(0).getIsZombie(), Game.playerList.get(0).getIsDeadForever());
        }
        if (amountOfPlayers == 2) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 1);
            GridPane.setConstraints(card3, 0, 1);
        }
        if (amountOfPlayers == 3) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 1);
            GridPane.setConstraints(card3, 1, 0);
            GridPane.setConstraints(card4, 0, 1);
        }
        if (amountOfPlayers == 4) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 1);
            GridPane.setConstraints(card3, 1, 0);
            GridPane.setConstraints(card4, 0, 0);
            GridPane.setConstraints(card5, 0, 1);
        }
        if (amountOfPlayers == 5) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 1);
            GridPane.setConstraints(card3, 2, 0);
            GridPane.setConstraints(card4, 1, 0);
            GridPane.setConstraints(card5, 0, 0);
            GridPane.setConstraints(card6, 0, 1);
        }
        if (amountOfPlayers == 6) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 1);
            GridPane.setConstraints(card3, 2, 0);
            GridPane.setConstraints(card4, 1, 0);
            GridPane.setConstraints(card5, 0, 0);
            GridPane.setConstraints(card6, 0, 1);
            GridPane.setConstraints(card7, 0, 2);
        }
        if (amountOfPlayers == 7) {
            GridPane.setConstraints(card, 1, 2);
            GridPane.setConstraints(card2, 2, 2);
            GridPane.setConstraints(card3, 2, 1);
            GridPane.setConstraints(card4, 2, 0);
            GridPane.setConstraints(card5, 1, 0);
            GridPane.setConstraints(card6, 0, 0);
            GridPane.setConstraints(card7, 0, 1);
            GridPane.setConstraints(card8, 0, 2);
        }
        GridPane.setConstraints(arrows, 1, 1);

        centerPane.getChildren().addAll(card, card2, card3, card4, card5, card6, card7, card8, arrows);
        return centerPane;
    }

    /**
     * Dices: Will build the group of dices on the left side.
     *
     * @param dice1 The asset URL of dice1
     * @param dice2 The asset URL of dice2
     * @param dice3 The asset URL of dice3
     * @param dice4 The asset URL of dice4
     * @param dice5 The asset URL of dice5
     * @param border The borderpane from startGame(). The reason why I'm passing
     * this is because I need to refresh it when reroll is called.
     * @return
     */
    private VBox Dices(String dice1, String dice2, String dice3, String dice4, String dice5) {
        VBox vbox = new VBox();
        Button reroll = new Button();
        Button takeChief = new Button("Take Chief's Arrow");
        if (this.rerollChecker) {
            reroll.setText("Re-roll Selected");
        } else {
            reroll.setText("Roll Dice");
        }
        if (Game.getTurnOver() || gameoverBool || (Game.playerList.get(0).getisDead() && !Game.playerList.get(0).getIsZombie())) {
            reroll.setDisable(true);
        }
        reroll.setMaxWidth(200);
        reroll.setMaxHeight(50);
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10));

        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        CheckBox checkBox4 = new CheckBox();
        CheckBox checkBox5 = new CheckBox();

        checkBox1.setPadding(new Insets(5));
        checkBox2.setPadding(new Insets(5));
        checkBox3.setPadding(new Insets(5));
        checkBox4.setPadding(new Insets(5));
        checkBox5.setPadding(new Insets(5));
        if (!rerollChecker) {
            checkBox1.setDisable(true);
            checkBox2.setDisable(true);
            checkBox3.setDisable(true);
            checkBox4.setDisable(true);
            checkBox5.setDisable(true);
            checkBox1.setSelected(true);
            checkBox2.setSelected(true);
            checkBox3.setSelected(true);
            checkBox4.setSelected(true);
            checkBox5.setSelected(true);
        }
        HBox hbox1 = dieHBox(dice1, checkBox1, takeChief, 0);
        if (checkDisabled(0)) {
            checkBox1.setDisable(true);
        }

        HBox hbox2 = dieHBox(dice2, checkBox2, takeChief, 1);
        if (checkDisabled(1)) {
            checkBox2.setDisable(true);
        }

        HBox hbox3 = dieHBox(dice3, checkBox3, takeChief, 2);
        if (checkDisabled(2)) {
            checkBox3.setDisable(true);
        }

        HBox hbox4 = dieHBox(dice4, checkBox4, takeChief, 3);
        if (checkDisabled(3)) {
            checkBox4.setDisable(true);
        }

        HBox hbox5 = dieHBox(dice5, checkBox5, takeChief, 4);
        if (checkDisabled(4)) {
            checkBox5.setDisable(true);
        }

        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, reroll);

        reroll.setOnAction((ActionEvent event) -> {
            if (!Game.playerList.get(0).getisDead() || Game.playerList.get(0).getIsZombie()) {
                this.rerollChecker = true;
                belleAbilityUsedThisTurn = false;
                Game.rerollDice(checkBox1.isSelected(), checkBox2.isSelected(), checkBox3.isSelected(), checkBox4.isSelected(), checkBox5.isSelected(), Game.playerList.get(0));
                refreshEverything();
            }

        });

        return vbox;
    }

    /**
     * addDice: Will add a singular die to whatever calls it
     *
     * @param url The url to the asset for the die
     * @return Will return a die to the location calling it
     */
    private StackPane addDice(String url) {
        StackPane dice = new StackPane();
        Rectangle diceIcon = new Rectangle(52.0, 52.0);
        Image img = new Image("File:." + url + "");
        ImageView iv = new ImageView();
        iv.setImage(img);

        dice.getChildren().add(diceIcon);
        dice.getChildren().add(iv);
        return dice;
    }

    private boolean checkDisabled(int number) {
        if ((Game.getDie(number).getNumber() == 5 && realPlayerCharacter != "Black Jack") || (Game.getDie(number).getCurrentRollCount() == 3) || (Game.getRoundRerollCount() == 4) || Game.getTurnOver()) {
            return true;
        }
        if (number == 3 && duel1) {
            return true;
        }
        if (number == 4 && duel2) {
            return true;
        }
        return false;
    }

    /**
     * addBottomButtons: Contains the New Game and Quit buttons at the bottom of
     * the game.
     *
     * @return Will return a HBox containing New Game and Quit buttons
     */
    private HBox addBottomButtons() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonNewGame = newGameButton();
        buttonNewGame.setPrefSize(100, 20);

        Button buttonExit = quitButton();
        buttonExit.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonNewGame, buttonExit);
        return hbox;
    }

    /**
     * quitButton: Creates a quit button with the on click action call to
     * prevent repetitive code.
     *
     * @return Returns a Quit Button
     */
    private Button quitButton() {
        Button buttonQuit = new Button("Quit");

        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        return buttonQuit;
    }

    /**
     * newGameButton: Creates a new game button with the on click action call to
     * prevent repetitive code.
     *
     * @return Returns a new game button
     */
    private Button newGameButton() {
        Button buttonNewGame = new Button("New Game");
        buttonNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                amountOfPlayers = Integer.parseInt((String) amountOfPlayersBox.getValue());

                if (inGame) {
                    buildMainMenu();
                    inGame = false;
                    rerollChecker = false;
                    gameoverBool = false;
                    gameover.setText("");
                    Game.resetGame();
                    if (withOrWithoutExpansionsBox.getValue() == "With") {
                        hasExpansions = 1;
                        Game.expEnabled = true;
                    } else {
                        hasExpansions = 0;
                        Game.expEnabled = false;
                    }
                    Game.makeCharacters(amountOfPlayers + 1);
                    Game.makeAI();
                } else {
                    gameover.setText("");
                    inGame = true;
                    rerollChecker = false;
                    gameoverBool = false;
                    Game.resetGame();
                    if (withOrWithoutExpansionsBox.getValue() == "With") {
                        hasExpansions = 1;
                        Game.expEnabled = true;
                    } else {
                        hasExpansions = 0;
                        Game.expEnabled = false;
                    }
                    Game.makeCharacters(amountOfPlayers + 1);
                    Game.makeAI();
                    startGame();
                }
                setTokens();
            }
        });
        return buttonNewGame;
    }

    /**
     * actionPanel: Creates the right side action panel
     *
     * @param die1 the number die1 is rolled on
     * @param die2 the number die2 is rolled on
     * @param die3 the number die3 is rolled on
     * @param die4 the number die4 is rolled on
     * @param die5 the number die5 is rolled on
     * @return Will return the full right side.
     */
    private ScrollPane actionPanel(int die1, int die2, int die3, int die4, int die5) {
        VBox vbox = new VBox();
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background: #d9b57f;");
        vbox.setStyle("-fx-background-color: #d9b57f;");
        vbox.setPadding(new Insets(15, 12, 15, 12));
        Button submit = new Button("Submit Actions and End Turn");
        if (Game.getTurnOver()) {
            submit.setText("End Turn");
        }
        if (hasExpansions == 1) {
            if (Game.playerList.get(0).getisDead() && !Game.playerList.get(0).getIsZombie()) {
                submit.setText("Next Turn");
            }
        } else {
            if (Game.playerList.get(0).getisDead() && !Game.playerList.get(0).getIsZombie()) {
                submit.setText("Next Turn");
            }
        }

        Text text = new Text("Action Panel:");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        HBox hbox1 = individualAction(die1, 0);
        HBox hbox2 = individualAction(die2, 1);
        HBox hbox3 = individualAction(die3, 2);
        HBox hbox4 = individualAction(die4, 3);
        HBox hbox5 = individualAction(die5, 4);

        if (duel1) {
            hbox4.setDisable(true);
        }
        if (duel2) {
            hbox5.setDisable(true);
        }
        vbox.getChildren().addAll(text, hbox1, hbox2, hbox3, hbox4, hbox5);
        if (die1 != 0 || gameoverBool || (Game.playerList.get(0).getIsZombie() && die1 != 0) || ((Game.playerList.get(0).getisDead() || die1 != 0) && !Game.playerList.get(0).getIsZombie())) {
            vbox.getChildren().addAll(submit);
        }
        if (gameoverBool) {
            submit.setDisable(true);
        }
        if (!Game.playerList.get(0).getisDead() || Game.playerList.get(0).getIsZombie()) {
            vbox.getChildren().addAll(actionTextList);
        }

        Game.getAiLogs().forEach((n) -> {
            Text aiText = new Text();
            aiText.setWrappingWidth(200);
            aiText.setText(n.toString());
            vbox.getChildren().add(aiText);
        });
        vbox.getChildren().addAll(gameover);
        setTokens();
        if (((Game.getDie(4).getNumber() == 1 || Game.getDie(4).getNumber() == 2) && Game.getDie(4).getExpansionNumber() == 3) && !duel2 && !Game.getTurnOver()) {
            submit.setDisable(true);
        }
        if (((Game.getDie(3).getNumber() == 1 || Game.getDie(3).getNumber() == 2) && Game.getDie(3).getExpansionNumber() == 3) && !duel1 && !Game.getTurnOver()) {
            submit.setDisable(true);
        }
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionTextList.getChildren().clear();

                //vbox.getChildren().removeAll(aiBox);
                if (!Game.getTurnOver()) {
                    Game.doActionsToTargetedPlayer(actionPanelChoices.get(0), actionPanelChoices.get(1), 0);
                    Game.doActionsToTargetedPlayer(actionPanelChoices.get(2), actionPanelChoices.get(3), 1);
                    Game.doActionsToTargetedPlayer(actionPanelChoices.get(4), actionPanelChoices.get(5), 2);
                    if (!((Game.getDie(3).getNumber() == 1 || Game.getDie(3).getNumber() == 2) && Game.getDie(3).getExpansionNumber() == 3)) {
                        Game.doActionsToTargetedPlayer(actionPanelChoices.get(6), actionPanelChoices.get(7), 3);
                    }
                    if (!((Game.getDie(4).getNumber() == 1 || Game.getDie(4).getNumber() == 2) && Game.getDie(4).getExpansionNumber() == 3)) {
                        Game.doActionsToTargetedPlayer(actionPanelChoices.get(8), actionPanelChoices.get(9), 4);
                    }

                }
                actionTextList = aiActions();
                if (!Game.playerList.get(0).getisDead()) {
                    vbox.getChildren().addAll(actionTextList);
                }
                Game.callAITurn();
                //Game.getAiLogs().forEach((n) -> vbox.getChildren().add(new Text(n.toString())));
                rerollChecker = false;
                duel1 = false;
                duel2 = false;
                Game.startNewTurn();
                Game.checkIfGameOver();
                gameover = printGameOver();
                vbox.getChildren().addAll(gameover);
                refreshEverything();

            }
        });
        scroll.setContent(vbox);
        return scroll;
    }

    /**
     * individualAction: Will create a single action in the action panel
     *
     * @param actionType The rolled number of the die.
     * @param location The number of the die.
     * @return Returns a individual action
     */
    private HBox individualAction(int actionType, int location) {
        HBox hbox = new HBox();
        ComboBox box = new ComboBox();
        ComboBox box2 = new ComboBox();
        Text text = new Text();
        Button duelButton1 = new Button("Challenge!");
        hbox.setPadding(new Insets(0, 0, 10, 0));
        Button duelButton2 = new Button("Challenge!");
        if (actionType == 0) {
            return hbox;
        }

        if (duel1 && !Game.getTurnOver()) {
            duelButton1.setDisable(true);
        }
        if (duel2 && !Game.getTurnOver()) {
            duelButton2.setDisable(true);
        }
        duelButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                duel1 = true;
                duel1Selection = actionPanelChoices.get(6);
                Game.doActionsToTargetedPlayer(actionPanelChoices.get(6), actionPanelChoices.get(7), location);
                refreshEverything();
            }
        });
        duelButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                duel2 = true;
                duel2Selection = actionPanelChoices.get(8);
                Game.doActionsToTargetedPlayer(actionPanelChoices.get(8), actionPanelChoices.get(9), location);
                refreshEverything();
            }
        });

        if (Game.getDie(location).getExpansionNumber() == 0) {
            Node current = Game.playerlist.getHead();
            if (Game.playerList.get(0).getIsZombie()) {
                current = Game.zombieCircular.getHead();
            }
            Character player = (Character) current.get();
            switch (actionType) {
                case 1:
                    Game.charListOneTrim(Game.playerList.get(0).getName());
                    if (!Game.getZombieOutbreakBoolean()) {
                        Game.getTrimList().forEach((n) -> box.getItems().add(n.getName()));
                    } else {
                        if (Game.playerList.get(0).getIsZombie() || Game.playerList.get(0).getIsZombieMaster()) {
                            Game.getTrimList().forEach((n) -> {
                                if (!n.getIsZombie() && !n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        } else {
                            Game.getTrimList().forEach((n) -> {
                                if (n.getIsZombie() || n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        }
                    }
                    text.setText("Attack (1): ");
                    break;
                case 2:
                    Game.charListTwoTrim(Game.playerList.get(0).getName());
                    if (!Game.getZombieOutbreakBoolean()) {
                        Game.getTrimList().forEach((n) -> box.getItems().add(n.getName()));
                    } else {
                        if (Game.playerList.get(0).getIsZombie() || Game.playerList.get(0).getIsZombieMaster()) {
                            Game.getTrimList().forEach((n) -> {
                                if (!n.getIsZombie() && !n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        } else {
                            Game.getTrimList().forEach((n) -> {
                                if (n.getIsZombie() || n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        }
                    }
                    text.setText("Attack (2): ");
                    break;
                case 3:
                    if (!Game.getZombieOutbreakBoolean()) {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            box.getItems().add(player.getName());
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    } else {
                        if (Game.playerList.get(0).getIsZombie() || Game.playerList.get(0).getIsZombieMaster()) {
//                            Game.zombieList.forEach((n) -> box.getItems().add(n.getName()));
                        } else {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getIsZombie() || !player.getIsZombieMaster()) {
                                    box.getItems().add(player.getName());
                                }
                                current = current.getNext();
                                player = (Character) current.get();
                            }
                        }
                    }

                    text.setText("Heal: ");
                    break;
                case 4:
                    text.setText("Gattling Gun: ");
                    box.getItems().addAll("EVERYONE");
                    box.setDisable(true);
                    break;
                case 5:
                    text.setText("Dynamite: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
                case 6:
                    text.setText("Arrow: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
            }
        }

        if (Game.getDie(location).getExpansionNumber() == 1) {
            switch (actionType) {
                case 1:
                    Game.charListOneTrim(realPlayerCharacter);
                    if (!Game.getZombieOutbreakBoolean()) {
                        Game.getTrimList().forEach((n) -> {
                            box.getItems().add(n.getName());
                            box2.getItems().add(n.getName());
                        });
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            Game.getTrimList().forEach((n) -> {
                                if (!n.getIsZombie()) {
                                    box.getItems().add(n.getName());
                                    box2.getItems().add(n.getName());
                                }
                            });
                        } else {
                            Game.getTrimList().forEach((n) -> {
                                if (n.getIsZombie() || n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                    box2.getItems().add(n.getName());
                                }
                            });
                        }
                    }

                    text.setText("Attack (1) 2 players: ");
                    break;
                case 2:
                    Game.charListTwoTrim(realPlayerCharacter);
                    if (!Game.getZombieOutbreakBoolean()) {
                        Game.getTrimList().forEach((n) -> {
                            box.getItems().add(n.getName());
                            box2.getItems().add(n.getName());
                        });
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            Game.getTrimList().forEach((n) -> {
                                if (!n.getIsZombie()) {
                                    box.getItems().add(n.getName());
                                    box2.getItems().add(n.getName());
                                }
                            });
                        } else {
                            Game.getTrimList().forEach((n) -> {
                                if (n.getIsZombie() || n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                    box2.getItems().add(n.getName());
                                }
                            });

                        }
                    }
                    text.setText("Attack (2) 2 Players: ");
                    break;
                case 3:
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    text.setText("Bullet: ");
                    break;
                case 4:
                    text.setText("Gattling Gun (2 amount): ");
                    box.getItems().addAll("EVERYONE");
                    box.setDisable(true);
                    break;
                case 5:
                    text.setText("Dynamite: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
                case 6:
                    text.setText("Arrow: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
            }
        }

        if (Game.getDie(location).getExpansionNumber() == 2) {
            Node current = Game.playerlist.getHead();
            Character player = (Character) current.get();
            switch (actionType) {
                case 1:
                    Game.charListOneTrim(Game.getCharacter(1).getName());
                    if (!Game.getZombieOutbreakBoolean()) {
                        Game.getTrimList().forEach((n) -> box.getItems().add(n.getName()));
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            Game.getTrimList().forEach((n) -> {
                                if (!n.getIsZombie()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        } else {
                            Game.getTrimList().forEach((n) -> {
                                if (n.getIsZombie() || n.getIsZombieMaster()) {
                                    box.getItems().add(n.getName());
                                }
                            });
                        }
                    }
                    text.setText("Attack (1): ");
                    break;
                case 2:
                    if (Game.playerList.get(0).getIsZombieMaster()) {
                        Game.zombieList.forEach((n) -> box.getItems().add(n.getName()));
                    } else {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            box.getItems().add(player.getName());
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    }
                    text.setText("Broken Arrow: ");
                    break;
                case 3:
                    if (Game.playerList.get(0).getIsZombieMaster()) {
                        Game.zombieList.forEach((n) -> box.getItems().add(n.getName()));
                    } else {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            box.getItems().add(player.getName());
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    }
                    text.setText("Heal: ");
                    break;
                case 4:

                    if (!Game.getZombieOutbreakBoolean()) {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            box.getItems().add(player.getName());
                            box2.getItems().add(player.getName());
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            Game.zombieList.forEach((n) -> {
                                box.getItems().add(n.getName());
                                box2.getItems().add(n.getName());
                            });
                        } else {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getIsZombie()) {
                                    box.getItems().add(player.getName());
                                    box2.getItems().add(player.getName());
                                    current = current.getNext();
                                    player = (Character) current.get();
                                }
                            }
                        }
                    }
                    text.setText("Heal 2 Players: ");
                    break;
                case 5:
                    text.setText("Dynamite: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
                case 6:
                    text.setText("Arrow: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
            }
        }

        if (Game.getDie(location).getExpansionNumber() == 3) {
            Node current = Game.playerlist.getHead();
            Character player = (Character) current.get();
            switch (actionType) {
                case 1:
                    text.setText("Duel: ");
                    if (location == 3) {
                        if (duel1Selection != "") {
                            box.getItems().add(duel1Selection);
                            break;
                        }
                    } else {
                        if (duel2Selection != "") {
                            box.getItems().add(duel2Selection);
                            break;
                        }
                    }

                    if (!Game.getZombieOutbreakBoolean()) {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            if (!player.getName().equals(realPlayerCharacter)) {
                                box.getItems().add(player.getName());
                            }
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getName().equals(realPlayerCharacter)) {
                                    box.getItems().add(player.getName());
                                }
                                current = current.getNext();
                                player = (Character) current.get();
                            }
                        } else {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getName().equals(realPlayerCharacter) && player.getIsZombie()) {
                                    box.getItems().add(player.getName());
                                }
                                current = current.getNext();
                                player = (Character) current.get();
                            }
                        }
                    }

                    if (location == 3) {
                        if (duel1) {
                            box.setDisable(true);
                        }
                    }
                    if (location == 4) {
                        if (duel2) {
                            box.setDisable(true);
                        }
                    }
                    break;
                case 2:
                    text.setText("Duel: ");
                    if (location == 3) {
                        if (duel1Selection != "") {
                            box.getItems().add(duel1Selection);
                            break;
                        }
                    } else {
                        if (duel2Selection != "") {
                            box.getItems().add(duel2Selection);
                            break;
                        }
                    }
                    if (location == 3) {
                        if (duel1) {
                            box.setDisable(true);
                        }
                    }
                    if (location == 4) {
                        if (duel2) {
                            box.setDisable(true);
                        }
                    }
                    if (!Game.getZombieOutbreakBoolean()) {
                        for (int i = 0; i < Game.playerlist.size; i++) {
                            if (!player.getName().equals(realPlayerCharacter)) {
                                box.getItems().add(player.getName());
                            }
                            current = current.getNext();
                            player = (Character) current.get();
                        }
                    } else {
                        if (Game.playerList.get(0).getIsZombieMaster()) {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getName().equals(realPlayerCharacter)) {
                                    box.getItems().add(player.getName());
                                }
                                current = current.getNext();
                                player = (Character) current.get();
                            }
                        } else {
                            for (int i = 0; i < Game.playerlist.size; i++) {
                                if (!player.getName().equals(realPlayerCharacter) && player.getIsZombie()) {
                                    box.getItems().add(player.getName());
                                }
                                current = current.getNext();
                                player = (Character) current.get();
                            }
                        }
                    }

                    break;
                case 3:
                    text.setText("Whiskey: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
                case 4:
                    text.setText("Gattling Gun: ");
                    box.getItems().addAll("EVERYONE");
                    box.setDisable(true);
                    break;
                case 5:
                    text.setText("Dynamite: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
                case 6:
                    text.setText("Arrow: ");
                    box.getItems().addAll("YOURSELF");
                    box.setDisable(true);
                    break;
            }
        }

        box.getSelectionModel().selectFirst();
        box2.getSelectionModel().selectFirst();
        hbox.setAlignment(Pos.CENTER_LEFT);
        if (actionType != 0) {
            hbox.getChildren().addAll(text, box);
        }
        if ((Game.getDie(location).getExpansionNumber() == 1 && (actionType == 1 || actionType == 2)) || (Game.getDie(location).getExpansionNumber() == 2 && (actionType == 4))) {
            hbox.getChildren().addAll(box2);
        }
        if ((Game.getDie(location).getExpansionNumber() == 3 && (actionType == 1 || actionType == 2) && location == 3)) {
            hbox.getChildren().add(duelButton1);
        }
        if ((Game.getDie(location).getExpansionNumber() == 3 && (actionType == 1 || actionType == 2) && location == 4)) {
            hbox.getChildren().add(duelButton2);
        }
        final int getNewLocation = location * 2;
        actionPanelChoices.set(getNewLocation, "");
        actionPanelChoices.set(getNewLocation + 1, "");
        actionPanelChoices.add(getNewLocation, (String) box.getValue());
        actionPanelChoices.add(getNewLocation + 1, (String) box2.getValue());
        box.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                actionPanelChoices.set(getNewLocation, (String) newValue);
            }

        });
        box2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                actionPanelChoices.set(getNewLocation + 1, (String) newValue);
            }

        });
        return hbox;
    }

    /**
     * arrowCounter: Will show an arrow picture and amount of arrows to the
     * screen.
     *
     * @param amount The number of arrows left
     * @return returns a VBox containing information about the arrows
     */
    private VBox arrowCounter(int amount, int chiefArrowAmount) {
        VBox vbox = new VBox();
        Label arrowAmount = new Label("Arrows left: " + amount);
        Label chiefAmount = new Label("Chief's Arrow Left: " + chiefArrowAmount);

        arrowAmount.setFont(new Font("verdana", 15));
        chiefAmount.setFont(new Font("verdana", 15));
        Image img = new Image("File:./assets/6.jpg");
        ImageView iv = new ImageView();
        iv.setImage(img);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(iv, arrowAmount);
        if (hasExpansions == 1) {
            vbox.getChildren().add(chiefAmount);
        }
        return vbox;
    }

    /**
     * aiActions: Will show what actions were taken by the AI during that round.
     *
     * @return returns a VBox containing information about the actions taken by
     * the AI.
     */
    private VBox aiActions() {
        VBox vbox = new VBox();

        vbox.maxWidth(50);
        //vbox.setPadding(new Insets(15, 5, 5, 5));
        Text title = new Text("Turn Actions:");
        Text actionOne = new Text();
        Text actionTwo = new Text();
        Text actionThree = new Text();
        Text actionFour = new Text();
        Text actionFive = new Text();
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        if (Game.getTurnOver()) {
            actionOne.setText("The Dynamite Blew up on " + Game.getCharacter(1).getName());
            vbox.getChildren().addAll(title, actionOne);
            return vbox;
        }
        actionOne.setText(checkDie(Game.getDie(0).getNumber(), 0, Game.getCharacter(1).getName(), actionPanelChoices.get(0), actionPanelChoices.get(1)));
        actionTwo.setText(checkDie(Game.getDie(1).getNumber(), 1, Game.getCharacter(1).getName(), actionPanelChoices.get(2), actionPanelChoices.get(3)));
        actionThree.setText(checkDie(Game.getDie(2).getNumber(), 2, Game.getCharacter(1).getName(), actionPanelChoices.get(4), actionPanelChoices.get(5)));
        actionFour.setText(checkDie(Game.getDie(3).getNumber(), 3, Game.getCharacter(1).getName(), actionPanelChoices.get(6), actionPanelChoices.get(7)));
        actionFive.setText(checkDie(Game.getDie(4).getNumber(), 4, Game.getCharacter(1).getName(), actionPanelChoices.get(8), actionPanelChoices.get(9)));

        actionOne.setWrappingWidth(200);
        actionTwo.setWrappingWidth(200);
        actionThree.setWrappingWidth(200);
        actionFour.setWrappingWidth(200);
        actionFive.setWrappingWidth(200);
        vbox.getChildren().add(title);

        if (!"".equals(actionOne.getText())) {
            vbox.getChildren().add(actionOne);
        }
        if (!"".equals(actionTwo.getText())) {
            vbox.getChildren().add(actionTwo);
        }
        if (!"".equals(actionThree.getText())) {
            vbox.getChildren().add(actionThree);
        }
        if (!"".equals(actionFour.getText())) {
            vbox.getChildren().add(actionFour);
        }
        if (!"".equals(actionFive.getText())) {
            vbox.getChildren().add(actionFive);
        }

        return vbox;
    }

    /**
     * checkDie: Will check the rolled die number and output an action
     * accordingly
     *
     * @param actionType the rolled die number.
     * @param name1 The person doing the action
     * @param name2 The person receiving the action.
     * @return Will return a singular AI action
     */
    private String checkDie(int actionType, int dieNumber, String playerName, String name1, String name2) {
        String curr = "";
        if(name1 == null) {
            return curr;
        }
        int expansionNumber = Game.getDie(dieNumber).getExpansionNumber();

        if (expansionNumber == 0) {

            if (actionType == 1) {
                curr = playerName + " shot " + name1 + " with (1) Attack.";
                if (dueltokens[1]) {
                    curr = "SHOT (1) WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[1] = Game.playerList.get(0).getDuelWoundTokens()[1];
                }
            }
            if (actionType == 2) {
                curr = playerName + " shot " + name1 + " with (2) Attack.";
                if (dueltokens[2]) {
                    curr = "SHOT (2) WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[2] = Game.playerList.get(0).getDuelWoundTokens()[2];
                }
            }
            if (actionType == 3) {
                curr = playerName + " healed " + name1 + " with beer.";
                if (name1.equals(realPlayerCharacter)) {
                    curr = playerName + " healed themselves with beer.";
                }
                if (dueltokens[0]) {
                    curr = "HEAL WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[0] = Game.playerList.get(0).getDuelWoundTokens()[0];
                }
            }
            if (actionType == 4) {
                if (Game.getGatlingGunUsed() && !printedGatling) {
                    printedGatling = true;
                    curr = playerName + " gattling gunned " + name1 + ".";
                }
            }
            if (actionType == 5) {
                if (Game.getTurnOver()) {
                    curr = "The Dynamite blew up on " + playerName + ".";
                }
            }
            if (actionType == 6) {
                curr = "" + playerName + " took an Arrow from the pile.";

                if (Game.getArrowsLeft() == 0) {
                    curr = playerName + " took the last Arrow from the pile. INDIANS ATTACK!";
                }
            }
        }

        if (expansionNumber == 1) {

            if (actionType == 1) {
                curr = playerName + " shot " + name1 + " and " + name2 + " with (1) Attack.";
                if (dueltokens[1]) {
                    curr = "SHOT (1) WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[1] = Game.playerList.get(0).getDuelWoundTokens()[1];
                }
            }
            if (actionType == 2) {
                curr = playerName + " shot " + name1 + " and " + name2 + " with (2) Attack.";
                if (dueltokens[2]) {
                    curr = "SHOT (2) WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[2] = Game.playerList.get(0).getDuelWoundTokens()[2];
                }
            }
            if (actionType == 3) {
                curr = playerName + " lost one HP to the bullet.";
            }
            if (actionType == 4) {
                if (Game.getGatlingGunUsed() && !printedGatling) {
                    printedGatling = true;
                    curr = playerName + " gattling gunned " + name1 + ".";
                }
            }
            if (actionType == 5) {
                if (Game.getTurnOver()) {
                    curr = "The Dynamite blew up on " + playerName + ".";
                }
            }
            if (actionType == 6) {
                curr = "" + playerName + " took an Arrow from the pile.";

                if (Game.getArrowsLeft() == 0) {
                    curr = playerName + " took the last Arrow from the pile. INDIANS ATTACK!";
                }
            }
        }

        if (expansionNumber == 2) {

            if (actionType == 1) {
                curr = playerName + " shot " + name1 + " with (1) Attack.";
                if (dueltokens[1]) {
                    curr = "SHOT (1) WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[1] = Game.playerList.get(0).getDuelWoundTokens()[1];
                }
            }
            if (actionType == 2) {
                curr = playerName + " removed an arrow from " + name1 + ".";
                if (name1.equals(realPlayerCharacter)) {
                    curr = playerName + " removed an arrow from themselves.";
                }
            }
            if (actionType == 3) {
                curr = playerName + " healed " + name1 + " with beer.";
                if (name1.equals(realPlayerCharacter)) {
                    curr = playerName + " healed themselves with beer.";
                }
                if (dueltokens[0]) {
                    curr = "HEAL WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[0] = Game.playerList.get(0).getDuelWoundTokens()[0];
                }
            }
            if (actionType == 4) {
                curr = playerName + " healed " + name1 + " and" + name2 + " with beer.";
                if (name1.equals(realPlayerCharacter) || name2.equals(realPlayerCharacter)) {
                    curr = playerName + " healed themselves and " + name2 + " with beer.";
                }
                if (name1.equals(realPlayerCharacter) && name2.equals(realPlayerCharacter)) {
                    curr = playerName + " has healed themselves 2 times with beer.";
                }
                if (dueltokens[0]) {
                    curr = "HEAL WAS NOT USED DUE TO WOUND TOKEN.";
                    dueltokens[0] = Game.playerList.get(0).getDuelWoundTokens()[0];
                }
            }
            if (actionType == 5) {
                if (Game.getTurnOver()) {
                    curr = "The Dynamite blew up on " + playerName + ".";
                }
            }
            if (actionType == 6) {
                curr = playerName + " took an Arrow from the pile.";

                if (Game.getArrowsLeft() == 0) {
                    curr = playerName + " took the last Arrow from the pile. INDIANS ATTACK!";
                }
            }
        }

        if (expansionNumber == 3) {

            if (actionType == 1) {
                curr = playerName + " challeneged " + name1 + " to a duel.";
            }
            if (actionType == 2) {
                curr = playerName + " challenged " + name1 + " to a duel.";
            }
            if (actionType == 3) {
                curr = playerName + " healed " + name1 + " with whiskey.";
                if (name1.equals("YOURSELF")) {
                    curr = playerName + " used the whiskey on themselves.";
                }
            }
            if (actionType == 4) {
                if (Game.getGatlingGunUsed() && !printedGatling) {
                    printedGatling = true;
                    curr = playerName + " gattling gunned " + name1 + ".";
                }
            }
            if (actionType == 5) {
                if (Game.getTurnOver()) {
                    curr = "The Dynamite blew up on " + playerName + ".";
                }
            }
            if (actionType == 6) {
                curr = playerName + " took an Arrow from the pile.";

                if (Game.getArrowsLeft() == 0) {
                    curr = playerName + " took the last Arrow from the pile. INDIANS ATTACK!";
                }
            }
        }
        return curr;
    }

    /**
     * createCharacterCard: Will create a character card with the given
     * information
     *
     * @param health The health of the player
     * @param name The name of the player
     * @param description The ability information of the player
     * @param role The role of the player
     * @param user If the character is an AI or player
     * @param imgAsset The asset picture of the player
     * @param arrows The amount of arrows the character has
     * @param dead If the player is dead or not
     * @return Will return a singular character card to the middle
     */
    private StackPane createCharacterCard(int health, String name, String description, String role, String user, String imgAsset, int arrows, boolean chiefs, boolean dead, boolean isZombie, boolean isDeadDead) {
        StackPane stackpane = new StackPane();
        GridPane.setMargin(stackpane, new Insets(5, 10, 5, 10));
        Image background = new Image("File:./assets/background.png");
        ImageView ivBackground = new ImageView();
        ivBackground.setImage(background);
        ivBackground.setFitHeight(300);
        ivBackground.setFitWidth(214);
        stackpane.getChildren().addAll(ivBackground);
        int chiefsA = 0;
        VBox vbox = new VBox();
        VBox textvbox = new VBox();
        //Add the title AI or Player
        Text userText = new Text(user);
        userText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        vbox.getChildren().addAll(userText);

        //Add the image of the character
        Image img = new Image("File:." + imgAsset);
        ImageView iv = new ImageView();
        iv.setImage(img);
        iv.setFitHeight(82);
        iv.setFitWidth(82);
        vbox.getChildren().addAll(iv);

        //Add the name of the character
        Label nameText = new Label("NAME: " + name);
        nameText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        nameText.setPadding(new Insets(10, 0, 1, 0));
        textvbox.getChildren().addAll(nameText);

        //Add the Life of the character
        Label healthText = new Label("LIFE: " + health);
        healthText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        healthText.setPadding(new Insets(1, 0, 1, 0));
        textvbox.getChildren().addAll(healthText);

        //Add the amount of arrows of the character
        Label arrowsText = new Label("Arrows: " + arrows);
        arrowsText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        arrowsText.setPadding(new Insets(1, 0, 1, 0));
        textvbox.getChildren().addAll(arrowsText);

        //Add the amount of arrows of the character
        if (chiefs) {
            chiefsA = 1;
        }
        Label chiefsText = new Label("Chief's Arrow: " + chiefsA);
        chiefsText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        chiefsText.setPadding(new Insets(1, 0, 1, 0));
        if (hasExpansions == 1) {
            textvbox.getChildren().addAll(chiefsText);
        }
        //Add the role of the character
        Label roleText = new Label();

        if (role == "Sheriff" || user == "Player" || dead || gameoverBool || role == "Master") {
            roleText.setText("ROLE: " + role);
        } else {
            roleText.setText("ROLE: ???");
        }
        if (role == "Master") {
            roleText.setTextFill(Color.GREEN);
        }
        roleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        roleText.setPadding(new Insets(1, 0, 1, 0));
        textvbox.getChildren().addAll(roleText);

        //Add the role of the character
        Text deadText = new Text("DEAD");
        deadText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        deadText.setFill(Color.RED);

        if ((dead && !isZombie) || isDeadDead) {
            textvbox.getChildren().addAll(deadText);
        }

        Text zombieText = new Text("ZOMBIE");
        zombieText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        zombieText.setFill(Color.GREEN);
        if (isZombie && hasExpansions == 1 && Game.getZombieOutbreakBoolean()) {
            textvbox.getChildren().addAll(zombieText);
        }
        //Add the description of the character
        Text descriptionText = new Text("ABILITY: " + description);

        descriptionText.setWrappingWidth(175);
        descriptionText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 9));
        textvbox.getChildren().addAll(descriptionText);
        textvbox.setMaxWidth(100);
        vbox.getChildren().addAll(textvbox);

        //Align the content to center
        vbox.setAlignment(Pos.TOP_CENTER);
        stackpane.getChildren().addAll(vbox);
        return stackpane;
    }

    /**
     * diceHBox: Will make a dice and a checkbox next to it.
     *
     * @param die The die img asset
     * @param checkbox The checkbox corresponding to the die
     * @return Will return a Die and checkbox next to it
     */
    private HBox dieHBox(String die, CheckBox checkbox, Button specialButton, int dieNumber) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(addDice(die), checkbox);
        if (Game.getZombieOutbreakBoolean() && (dieNumber == 3 || dieNumber == 4) && Game.playerList.get(0).getIsZombie()) {
            checkbox.setSelected(false);
            checkbox.setDisable(true);
        }
        if (Game.getDie(dieNumber).getNumber() == 6 && hasExpansions == 1 && Game.getChiefArrowsAmount() == 1 && !Game.getTurnOver()) {
            hbox.getChildren().add(specialButton);
            specialButton.setOnAction((ActionEvent event) -> {
                Game.takeChiefsArrow(Game.getCharacter(1));
                refreshEverything();
            });
        }

        if (Game.getDie(dieNumber).getNumber() == 5 && "BELLE STAR".equals(realPlayerCharacter) && !belleAbilityUsedThisTurn && !Game.getTurnOver()) {
            Button belleAbility = new Button();
            belleAbility.setText("Replace it with Gatling");

            belleAbility.setOnAction((ActionEvent event) -> {
                Game.getDie(dieNumber).setNumber(4);
                belleAbilityUsedThisTurn = true;
                refreshEverything();

            });
            hbox.getChildren().addAll(belleAbility);
        }

        if (Game.getDie(dieNumber).getNumber() == 6 && ("APACHE KID".equals(realPlayerCharacter)) && (Game.getChiefArrowsAmount() == 0) && (!Game.getCharacter(1).getChiefArrow()) && (!Game.getTurnOver()) && (Game.expEnabled)) {
            specialButton.setText("Take Chief's Arrow");
            hbox.getChildren().add(specialButton);

            specialButton.setOnAction((ActionEvent event) -> {
                Game.apacheKidAbitlity();
                refreshEverything();

            });
        }
        if (dieNumber == 2 && !rerollChecker && (hasExpansions == 1) && Game.getDie(dieNumber).getExpansionNumber() == 0 && !Game.playerList.get(0).getIsZombie()) {
            Button changeCowardDie = new Button("Change to Coward Die");
            Button changeLoudmouth = new Button("Change to Loudmouth Die");
            hbox.getChildren().addAll(changeCowardDie, changeLoudmouth);
            changeCowardDie.setOnAction((ActionEvent event) -> {
                Game.getDie(dieNumber).setExpansionNumber(2);
                borderPrimary.setLeft(combineLeft());
            });
            changeLoudmouth.setOnAction((ActionEvent event) -> {
                Game.getDie(dieNumber).setExpansionNumber(1);
                borderPrimary.setLeft(combineLeft());
            });
        }
        if ((dieNumber == 4 || dieNumber == 3) && !rerollChecker && (hasExpansions == 1) && Game.getDie(dieNumber).getExpansionNumber() == 0 && !Game.playerList.get(0).getIsZombie()) {

            Button changeDuelDie = new Button("Change to Duel Die");
            hbox.getChildren().addAll(changeDuelDie);

            changeDuelDie.setOnAction((ActionEvent event) -> {
                Game.getDie(dieNumber).setExpansionNumber(3);
                borderPrimary.setLeft(combineLeft());
            });
        }

        if (Game.getDie(dieNumber).getExpansionNumber() != 0 && !rerollChecker) {
            Button changeOriginal = new Button("Change to Original Die");
            changeOriginal.setOnAction((ActionEvent event) -> {
                Game.getDie(dieNumber).setExpansionNumber(0);
                borderPrimary.setLeft(combineLeft());
            });
            hbox.getChildren().addAll(changeOriginal);
        }
        return hbox;
    }
    
    /**
     * duelTokens: Container for the duel wound tokens
     * @return Returns a VBOX containing the duel tokens
     */
    private VBox duelTokens() {
        VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(duelTokensHBox("/assets/woundtoken1.jpg", dueltokens[0]), duelTokensHBox("/assets/woundtoken2.jpg", dueltokens[1]), duelTokensHBox("/assets/woundtoken3.jpg", dueltokens[2]), duelTokensHBox("/assets/woundtoken4.jpg", dueltokens[3]));
        return vbox;
    }
    
    /**
     * duelTokensHBox: Will return a Container for the duel wound token
     * @param image The duel wound token image
     * @param have If the player has that token
     * @return Will return an HBox containing a single token
     */
    private HBox duelTokensHBox(String image, boolean have) {
        HBox hbox = new HBox();
        int amount = 0;
        if (have) {
            amount = 1;
        }
        Label nameText = new Label(" x" + amount);
        nameText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(addDice(image), nameText);
        return hbox;
    }
    
    /**
     * combineLeft: The container for the whole left side
     * @return Will return the combination of the whole left side of the project
     */
    private VBox combineLeft() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(Dices(Game.getAsset(0), Game.getAsset(1), Game.getAsset(2), Game.getAsset(3), Game.getAsset(4)));
        if (hasExpansions == 1) {
            vbox.getChildren().addAll(duelTokens(), leftCounter());
        }
        return vbox;
    }
    
    /**
     * leftCounter: The container for the variables on the left side.
     * @return Will return the counters for the expansion extra cards
     */
    private VBox leftCounter() {
        VBox vbox = new VBox();
        Label duelWoundLeft = new Label(" Duel Wound Tokens Left in the Pile: " + Game.getTotalTokens());

        Label boneyardLeft = new Label(" Boneyard Cards Left in the Pile: " + Game.getBoneyardTotal());
        Label oneZombieHand = new Label(" 1 zombie hand cards active amount: " + Game.getBoneyard1Count());
        Label twoZombieHand = new Label(" 2 zombie hand cards active amount: " + Game.getBoneyard2Count());
        duelWoundLeft.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        boneyardLeft.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        oneZombieHand.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        twoZombieHand.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        vbox.getChildren().addAll(duelWoundLeft);
        if (amountOfPlayers + 1 >= 4) {
            vbox.getChildren().addAll(boneyardLeft, oneZombieHand, twoZombieHand);
        }
        return vbox;
    }
    /**
     * refreshEverything: Will refresh everything
     */
    private void refreshEverything() {
        setTokens();
        Game.checkIfGameOver();
        borderPrimary.setLeft(combineLeft());
        borderPrimary.setRight(actionPanel(Game.getDie(0).getNumber(), Game.getDie(1).getNumber(), Game.getDie(2).getNumber(), Game.getDie(3).getNumber(), Game.getDie(4).getNumber()));
        borderPrimary.setCenter(center());

    }
    
    /**
     * printGameOver: Will print the game over text to the action panel
     * @return Will Return ptext
     */
    private Text printGameOver() {
        int gameover = Game.getGameOverArray();
        Text gameOverText = new Text();
        gameOverText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        gameOverText.setFill(Color.RED);
        switch (gameover) {
            case 1:
                gameOverText.setText("THE SHERIFF HAS DIED. GAME OVER!");
                gameoverBool = true;
                break;
            case 2:
                gameOverText.setText("ALL THE EVILDOORS HAVE DIED. GAME OVER!");
                gameoverBool = true;
                break;
            case 3:
                gameOverText.setText("RENEGADE HAS KILLED EVERYONE. GAME OVER!");
                gameoverBool = true;
                break;
            case 4:
                gameOverText.setText("ALL ZOMBIES AND THEIR MASTER HAVE DIED! SURVIVORS WIN!");
                gameoverBool = true;
                break;
            case 5:
                gameOverText.setText("ALL SURVIVORS HAVE DIED! ZOMBIES WIN!");
                gameoverBool = true;
                break;
            case 6:
                gameOverText.setText("ALL PLAYERS DIED! ZOMBIES WIN!");
                gameoverBool = true;
                break;
            default:
                break;
        }
        return gameOverText;

    }
    /**
     * setTokens: Will set the toekns for the duel wound tokens in the gui
     */
    private void setTokens() {
        dueltokens = Game.playerList.get(0).getDuelWoundTokens();
    }
}
