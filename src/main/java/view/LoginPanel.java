package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel{
    private JButton loginButton = new JButton("Anmelden");
    private JButton resetButton = new JButton("Konto anlegen");
    private JTextField userMail = new JTextField();
    private JPasswordField userPwd = new JPasswordField();
    private JLabel titelLabel = new JLabel("ITP-Fachbegriffstrainer Anmeldung");
    private JLabel userMailLabel = new JLabel("TGM-E-Mail:");
    private JLabel userPwdLabel = new JLabel("Passwort:");
    private JLabel messageLabel = new JLabel();

    public LoginPanel(ActionListener controller){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titelLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titelLabel, gbc);


        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(userMailLabel, gbc);

        gbc.gridx = 1;
        this.add(userMail, gbc);


        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(userPwdLabel, gbc);

        gbc.gridx = 1;
        this.add(userPwd, gbc);


        gbc.gridy = 3;
        gbc.gridx = 0;
        this.add(loginButton, gbc);
        loginButton.addActionListener(controller);
        loginButton.setFocusable(false);

        gbc.gridx = 1;
        this.add(resetButton, gbc);
        resetButton.addActionListener(controller);
        resetButton.setFocusable(false);


        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(messageLabel, gbc);


    }

    public String getMail(){
        return userMail.getText();
    }

    public String getPwd(){
        return String.valueOf(userPwd.getPassword());
    }

    public void wrongPwd(){
        messageLabel.setText("Falsches Passwort");
    }

    public void noUser(){
        messageLabel.setText("Kein Benutzer mit eingegebener Mail vorhanden");
    }
}
