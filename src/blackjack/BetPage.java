/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jessica Zheng
 */
public class BetPage extends javax.swing.JFrame {
    
    //initial variables
    String input;
    int amount;
    int inputBet;
    
    private static BetPage bPage;

    /**
     * Creates new form betPage
     */
    BetPage() {
        initComponents();
        //amount set to 1000
        amount = 1000;
        betLine.setText("Your amount is $" + amount + " , below please place a reasonable amount.");
        //new game button  and lose message are hidden
        newGame.setVisible(false);
        loseMessage.setVisible(false);
    }
    
    //BetPage instance can be passed to other classes
    public static BetPage getInstance() {
        if (bPage == null) {
            bPage = new BetPage();
        }
        return bPage;
    }
    
    //getters and setters for input bet and amont
    public int getInputBet() {
        return inputBet;
    }

    public void setInputBet(int inputBet) {
        this.inputBet = inputBet;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        //updates the screen 
        betLine.setText("Your amount is $" + amount + " , below please place a reasonable amount.");
        if(amount == 0){
            //if the amount is 0, new game and lose message are shown
            newGame.setVisible(true);
            loseMessage.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        betButton = new javax.swing.JButton();
        newGame = new javax.swing.JButton();
        playersBet = new javax.swing.JTextField();
        betLine = new javax.swing.JLabel();
        loseMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));

        betButton.setText("Bet");
        betButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                betButtonActionPerformed(evt);
            }
        });

        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        betLine.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        betLine.setText("You have $1000,  below please place a reasonable amount.");

        loseMessage.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        loseMessage.setText("You lost, press new game to go back to the menu. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(newGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(betButton)
                .addGap(106, 106, 106))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(betLine))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(playersBet, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(loseMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(betLine)
                .addGap(41, 41, 41)
                .addComponent(playersBet, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(loseMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(betButton)
                    .addComponent(newGame))
                .addGap(90, 90, 90))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void betButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_betButtonActionPerformed
        //gets the text that is placed in the playersBet box and puts it in a string
        input = playersBet.getText();

        try {
            //changes the string to an integer
            inputBet = Integer.parseInt(input);
            
        } //catches exception when user puts in wrong input
        catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    //when user puts something other than a number value
                    "Wrong input. Please put an integer.", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //when user inserts a number less than 0
        if (inputBet <= 0){
            JOptionPane.showMessageDialog(this,
                    //when user puts something other than a number value
                    "Wrong input. Please put an integer.", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //when user inserts less than the total alerts user
        if (inputBet > amount) {
            JOptionPane.showMessageDialog(this,
                    "You do not have enough money.", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //opens up the blackjack game page and closes this page
        BlackjackPage.main(null);
        this.setVisible(false);
    }//GEN-LAST:event_betButtonActionPerformed

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        //opens the intro page and closes this one
        JFrame MainPage = new MainPage();
        MainPage.setVisible(true);
        this.dispose();
        //clears prior data and game progress
        bPage = null;
    }//GEN-LAST:event_newGameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BetPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BetPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BetPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BetPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BetPage().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton betButton;
    private javax.swing.JLabel betLine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loseMessage;
    private javax.swing.JButton newGame;
    private javax.swing.JTextField playersBet;
    // End of variables declaration//GEN-END:variables

}
