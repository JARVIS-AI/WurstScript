/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * About.java
 *
 * Created on 04.12.2011, 20:32:40
 */
package de.peeeq.wurstio.gui;

import de.peeeq.wurstscript.CompileTimeInfo;
import de.peeeq.wurstscript.utils.Utils;
import org.eclipse.jdt.annotation.Nullable;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Frotty
 */
public class About extends javax.swing.JDialog {
    final URI uri;
    final static String homepage = "http://peq.github.com/WurstScript/";
    public final static String version = CompileTimeInfo.version;
    final static String authors = "peq & Frotty";

    /**
     * Creates new form About
     */
    @SuppressWarnings("null")
    public About(@Nullable Frame parent, boolean modal) throws URISyntaxException {
        super(parent, modal);
        initComponents();
        uri = new URI(homepage);
        getContentPane().setBackground(Color.WHITE);

        GuiUtils.setWindowToCenterOfScreen(this);
        requestFocus();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        homepageButton = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        heading = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        versionTitle = new javax.swing.JLabel();
        authorTitle = new javax.swing.JLabel();
        homepageTitle = new javax.swing.JLabel();
        versionCurrent = new javax.swing.JLabel();
        authorCurrent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About WurstScript");
        setModal(true);
        setName("aboutBox"); // NOI18N
        setResizable(false);

        homepageButton.setForeground(new java.awt.Color(0, 0, 255));
        homepageButton.setText(homepage);
        homepageButton.setBorderPainted(false);
        homepageButton.setContentAreaFilled(false);
        homepageButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homepageButton.setFocusPainted(false);
        homepageButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homepageButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        homepageButton.setName("homepageButton"); // NOI18N
        homepageButton.addMouseListener(Utils.onClickDo(this::homepageButtonMouseClicked));


        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("wurst.jpg"))); // NOI18N
        logo.setName("logo"); // NOI18N
        logo.setLocation(0, 0);

        heading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        heading.setText("WurstScript");
        heading.setName("heading"); // NOI18N


        description.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        description.setText("A Wurst to Jass compiler plus built-in optimizer");
        description.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        description.setName("description"); // NOI18N

        versionTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        versionTitle.setText("Version:");
        versionTitle.setName("versionTitle"); // NOI18N

        authorTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        authorTitle.setText("Authors:");
        authorTitle.setName("authorTitle"); // NOI18N

        homepageTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        homepageTitle.setText("Homepage:");
        homepageTitle.setName("homepageTitle"); // NOI18N

        versionCurrent.setText(version);
        versionCurrent.setName("versionCurrent"); // NOI18N

        authorCurrent.setText(authors);
        authorCurrent.setName("authorCurrent"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 450, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(logo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                        .addComponent(heading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(homepageTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(authorTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(versionTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(authorCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                                        .addComponent(versionCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                                        .addComponent(homepageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 151, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(logo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(heading, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(description)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(versionTitle)
                                                        .addComponent(versionCurrent))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(authorTitle)
                                                        .addComponent(authorCurrent))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(homepageTitle)
                                                        .addComponent(homepageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homepageButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homepageButtonMouseClicked
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException ignored) {}
        }
    }//GEN-LAST:event_homepageButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                About dialog = new About(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(@Nullable WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            } catch (URISyntaxException ex) {
                Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel authorCurrent;
    private javax.swing.JLabel authorTitle;
    private javax.swing.JLabel description;
    private javax.swing.JLabel heading;
    private javax.swing.JButton homepageButton;
    private javax.swing.JLabel homepageTitle;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel versionCurrent;
    private javax.swing.JLabel versionTitle;
    // End of variables declaration//GEN-END:variables
}
