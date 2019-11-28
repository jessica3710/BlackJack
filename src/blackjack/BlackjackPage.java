/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jessica Zheng
 */
public class BlackjackPage extends JPanel {

    //grid layout lengths
    private static final int GRID_HEIGHT = 4;
    private static final int GRID_WIDTH = 3;

    private static JFrame window;

    private Image cardImages;
    private Deck deck;
    private Hand dealerHand;
    private Hand playerHand;

    //JLabels to allow the user to see their amounts
    JLabel dealerAmount;
    JLabel playerAmount;

    //JList for the list of cards
    JList playerList;
    JList dealerList;

    //CustomPanel for the grids that contain a card
    CustomPanel dealerPanel;
    CustomPanel playerPanel;

    JPanel[][] panels = null;

    DefaultListModel dealerModel;
    DefaultListModel playerModel;
    //An instance from BetPage
    BetPage betPage = BetPage.getInstance();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //sets the size and content in the blackjack game
        window = new JFrame("Blackjack");
        BlackjackPage content = new BlackjackPage();
        window.setContentPane(content);
        window.pack();  // Set size of window to preferred size of its contents.
        window.setResizable(false);  // User can't change the window's size.
        window.setLocation(100, 100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public BlackjackPage() {
        //the buttons for the game
        JButton doubleButton = new JButton("Double");
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");
        JButton newGameButton = new JButton("New Game");
        //new game button is hidden until the game is done
        newGameButton.setVisible(false);

        //loads an image for the game
        try {
            loadImage();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //grid layout set to the right height and width 
        setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
        //set a border around the game
        setBorder(BorderFactory.createLineBorder(new Color(130, 50, 40), 3));

        //initializes deck and shuffles it
        deck = new Deck();
        deck.shuffle();
        //dealerHand and playerHand have new hands
        dealerHand = new Hand();
        playerHand = new Hand();

        //initializes panels
        panels = new JPanel[GRID_HEIGHT][GRID_WIDTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                panels[i][j] = null;
                //sets two panels to have a custom panel to place a card image in
                if (i == 0 && j == 0) {
                    panels[i][j] = new CustomPanel();
                    dealerPanel = (CustomPanel) panels[i][j];
                } else if (i == 1 && j == 0) {
                    panels[i][j] = new CustomPanel();
                    playerPanel = (CustomPanel) panels[i][j];
                } else {
                    //rest of the panels are normal panels
                    panels[i][j] = new JPanel();
                }
                //adds the panel
                add(panels[i][j]);
            }
        }
        //hit button listener
        hitButton.addActionListener(new ActionListener() {
            @Override
            //when hit button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //hides the double button
                doubleButton.setVisible(false);
                //adds a new card from the deck
                Card newCard = deck.dealCard();
                playerHand.addCard(newCard);
                //updates and adds it to the list
                playerPanel.updateCard(newCard);
                playerModel.addElement(newCard.toString());
                //update the value
                updatePlayerValue();

                //if the player's sum is greater than 21
                if (playerHand.getSum() > 21) {
                    //check win function is called
                    checkWin();
                    //disables the hit and stand button
                    standButton.setEnabled(false);
                    hitButton.setEnabled(false);
                    //allows the user to play a new game
                    newGameButton.setVisible(true);
                }
            }
        });
        //adds the button to the grid layout
        panels[2][0].add(hitButton);

        //stand button listener
        standButton.addActionListener(new ActionListener() {
            @Override
            //when stand button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //disables the hit and double button
                hitButton.setEnabled(false);
                doubleButton.setVisible(false);
                //allows the user to play a new game
                newGameButton.setVisible(true);

                //unhides the card and plays the dealer
                unhideCard();
                playDealer();

                //while dealer's hand is smaller than 21 and smaller than the player hand
                //continues to play the dealer hand
                while (dealerHand.getSum() < 21 && playerHand.getSum() >= dealerHand.getSum()) {

                    playDealer();

                }

                //checks if the user won or lost
                checkWin();

                //stand button is disabled
                standButton.setEnabled(false);
            }
        });
        //adds the button to the grid layout
        panels[2][1].add(standButton);

        //initializes the dealer list and model
        dealerModel = new DefaultListModel();
        dealerList = new JList(dealerModel);
        //adds the dealer list to the grid layout
        panels[0][1].add(dealerList);

        //initializes the player list and model
        playerModel = new DefaultListModel();
        playerList = new JList(playerModel);
        //adds the dealer list to the grid layout
        panels[1][1].add(playerList);

        //dealer amount is unknown until updated
        dealerAmount = new JLabel("Unknown");
        //adds the dealer amount to the grid layout
        panels[0][2].add(dealerAmount);

        playerAmount = new JLabel();
        //adds the player amount to the grid layout
        panels[1][2].add(playerAmount);
        //calls the initialization method to set up the game
        initialization();

        //new game button listener
        newGameButton.addActionListener(new ActionListener() {
            @Override
            //when new game button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //gets an instance of Bet Page
                JFrame betPage = BetPage.getInstance();
                //allows user to see the Bet Page
                betPage.setVisible(true);
                //this window is dispposed
                window.dispose();

            }

        });
        //adds the new game button to the grid layout
        panels[2][2].add(newGameButton);
        //bet amount greater than the amount divided by 2, does not allow the user
        //to view the double button
        if ((betPage.getAmount() / 2) < betPage.getInputBet()) {
            doubleButton.setVisible(false);
        }
        //when the player hand is equal to 21, double button is not displayed
                if (playerHand.getSum() == 21) {
                    doubleButton.setVisible(false);
                }
        //double button listener
        doubleButton.addActionListener(new ActionListener() {
            @Override
            //when the double button is pressed it does the action below
            public void actionPerformed(ActionEvent e) {
                
                //sets the input bet to doubled the bet from the input
                betPage.setInputBet(betPage.getInputBet() * 2);
                
                Card newCard = deck.dealCard();
                //adds a new card, updated and lists it
                playerHand.addCard(newCard);
                playerPanel.updateCard(newCard);
                playerModel.addElement(newCard.toString());
                //updates the sum of the cards
                updatePlayerValue();
                
                //if the player hand doubles and equals 21, unhide the dealer's
                //card and play the dealer
                if (playerHand.getSum() == 21) {
                    unhideCard();
                    playDealer();
                    //play the dealer until he busts or is greater than the player hand
                    while (dealerHand.getSum() < 21 && playerHand.getSum() >= dealerHand.getSum() && playerHand.getSum() < 21) {

                        playDealer();

                    }
                    //calls the function to see who won
                    checkWin();
                }
                
                //if the player's hand is greater than 21 call the checkWin function
                //to display who won
                if (playerHand.getSum() > 21) {
                    checkWin();

                }
                
                //once the user presses double, the user cannot go anymore therefore
                //all the buttons are disabled
                hitButton.setEnabled(false);
                doubleButton.setVisible(false);
                standButton.setEnabled(false);
                //new game button appears for the user to play a new game
                newGameButton.setVisible(true);
                //play the dealer until he busts or is greater than the player hand
                while (dealerHand.getSum() < 21 && playerHand.getSum() >= dealerHand.getSum() && playerHand.getSum() < 21) {
                    //unhides the card and plays the dealer
                    unhideCard();
                    playDealer();
                }
                //player hand is smaller than 21, check who won
                if (playerHand.getSum() < 21) {
                    checkWin();
                }
            }
        });
        //adds the double button to the grid layout
        panels[3][0].add(doubleButton);
    }
    
    //a method to unhide the hidden card
    private void unhideCard() {
        for (Card c : dealerHand.getCards()) {
            if (c.getValueAsString().equals("Hidden Card")) {
                dealerModel.removeElement("Hidden Card");

            }
        }
    }
    
    //a method that initializes everything before the game starts
    //sets the game up
    private void initialization() {
        //adds the player to the list
        playerModel.addElement("Player");
        Card newCard = deck.dealCard();
        
        //adds two cards to the player hand, updates and loads the image
        playerHand.addCard(newCard);
        playerPanel.updateCard(newCard);
        playerModel.addElement(newCard.toString());
        newCard = deck.dealCard();
        playerHand.addCard(newCard);
        playerPanel.updateCard(newCard);
        playerModel.addElement(newCard.toString());
        //updates player value
        updatePlayerValue();
        
        //adds the dealer to the list
        dealerModel.addElement("Dealer");
        newCard = deck.dealCard();
        
        //adds one card to the player hand
        dealerHand.addCard(newCard);
        dealerPanel.updateCard(newCard);
        dealerModel.addElement(newCard.toString());
        //updates dealer value
        updateDealerValue();
        //hides the dealer card
        newCard = deck.dealCard(true);
        dealerHand.addCard(newCard);
        dealerModel.addElement(newCard.toString());
        //updates dealer value
        updateDealerValue();

    }

    /**
     * Load the image from the file "cards.png", which must be somewhere on the
     * classpath for this program. If the file is found, then cardImages will
     * refer to the Image. If not, then cardImages will be null.
     */
    private void loadImage() throws IOException {
        cardImages = ImageIO.read(getClass().getResource("cards.png"));

    }
    
    //a method that plays the dealer, updates value, adds new card, uploads image
    //and updates the value
    private void playDealer() {
        updateDealerValue();
        Card newCard;
        newCard = deck.dealCard();
        dealerHand.addCard(newCard);
        dealerModel.addElement(newCard.toString());
        dealerPanel.updateCard(newCard);
        updateDealerValue();
    }
    
    //a method to identify who won
    private void checkWin() {
        //when user busts, displays that they have lost
        if (playerHand.getSum() > 21) {
            loseDisplay();
        } else if (dealerHand.getSum() > 21) {
            //dealer busts, displays user wins
            winDisplay();
        } else if (dealerHand.getSum() > playerHand.getSum()) {
            //dealer hand greater than player hand means that dealer wins, user lost
            loseDisplay();
        } else if (playerHand.getSum() > dealerHand.getSum()) {
            //player hand greater than dealer hand means that player wins, dealer lost
            winDisplay();
        } else if (playerHand.getSum() == dealerHand.getSum()) {
            //if the both hands are equal, the game is tied
            JOptionPane.showMessageDialog(this,
                    "Tie Game!", ":)", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    //method that is called when the user wins
    private void winDisplay() {
        //adds the bet amount to the original amount
        betPage.setAmount(betPage.getAmount() + betPage.getInputBet());
        JOptionPane.showMessageDialog(this,
                "You Win!", ":)", JOptionPane.ERROR_MESSAGE);
    }
    
    //method that is called when the user loses
    private void loseDisplay() {
        //subtracts the bet amount from the original amount
        betPage.setAmount(betPage.getAmount() - betPage.getInputBet());
        JOptionPane.showMessageDialog(this,
                "You Lost!", ":(", JOptionPane.ERROR_MESSAGE);
    }
    
    //updates the player value
    private void updatePlayerValue() {
        playerAmount.setText(Integer.toString(playerHand.getSum()));

    }
    
    //updates the dealer value
    private void updateDealerValue() {
        dealerAmount.setText(Integer.toString(dealerHand.getSum()));

    }
    
    //custom panel for the two panels that have cards in them
    private class CustomPanel extends JPanel {

        Card card = null;

        public CustomPanel() {
            this(null);
        }
        //sets the colour behind the card image
        public CustomPanel(Card c) {
            card = c;
            setBackground(new Color(0, 120, 0));
            setForeground(Color.GREEN);
            setPreferredSize(new Dimension(120, 140));
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            if (card != null) {
                drawCard(g, card, 10, 15);
            }
        }

        /**
         * Draws a card in a 79x123 pixel picture of a card with its upper left
         * corner at a specified point (x,y). Drawing the card requires the
         * image file "cards.png".
         *
         * @param g The non-null graphics context used for drawing the card. If
         * g is null, a NullPointerException will be thrown.
         * @param card The card that is to be drawn. If the value is null, then
         * a face-down card is drawn.
         * @param x the x-coord of the upper left corner of the card
         * @param y the y-coord of the upper left corner of the card
         */
        public void drawCard(Graphics g, Card card, int x, int y) {
            int cx;    // x-coord of upper left corner of the card inside cardsImage
            int cy;    // y-coord of upper left corner of the card inside cardsImage
            if (card == null) {
                cy = 4 * 123;   // coords for a face-down card.
                cx = 2 * 79;
            } else {
                cx = (card.getValue() - 1) * 79;
                switch (card.getSuit()) {
                    case Card.CLUBS:
                        cy = 0;
                        break;
                    case Card.DIAMONDS:
                        cy = 123;
                        break;
                    case Card.HEARTS:
                        cy = 2 * 123;
                        break;
                    default:  // spades   
                        cy = 3 * 123;
                        break;
                }
            }
            g.drawImage(cardImages, x, y, x + 79, y + 123, cx, cy, cx + 79, cy + 123, this);
        }
        
        //updates card image
        public void updateCard(Card c) {
            card = c;
            repaint();
        }
    }

}
