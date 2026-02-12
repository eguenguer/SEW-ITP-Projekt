package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel{
    private JButton registerButton = new JButton("Konto anlegen");
    private JButton backToLoginButton = new JButton("Anmelden");
    private JTextField userMail = new JTextField();
    private JPasswordField userPwd = new JPasswordField();
    private JPasswordField userPwdRepeat = new JPasswordField();
    private JLabel titelLabel = new JLabel("ITP-Fachbegriffstrainer Registrierung");
    private JLabel userMailLabel = new JLabel("TGM-E-Mail:");
    private JLabel userPwdLabel = new JLabel("Passwort:");
    private JLabel userPwdRepeatLabel = new JLabel("Passwort wiederholen:");
    private JLabel messageLabel = new JLabel();

    public RegistrationPanel(ActionListener controller){
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
        this.add(userPwdRepeatLabel, gbc);

        gbc.gridx = 1;
        this.add(userPwdRepeat, gbc);



        gbc.gridy = 4;
        gbc.gridx = 0;
        this.add(backToLoginButton, gbc);
        backToLoginButton.addActionListener(controller);
        backToLoginButton.setFocusable(false);
        backToLoginButton.setActionCommand("backToLogin");

        gbc.gridx = 1;
        this.add(registerButton, gbc);
        registerButton.addActionListener(controller);
        registerButton.setFocusable(false);
        registerButton.setActionCommand("register");


        gbc.gridy = 5;
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

    public String getPwdRepeat(){
        return String.valueOf(userPwdRepeat.getPassword());
    }

    public void resetFields(){
        userMail.setText("");
        userPwd.setText("");
        userPwdRepeat.setText("");
        messageLabel.setText("");
    }

    public void successMsg(){
        messageLabel.setText("Registrierung erfolgreich");
    }

    public void invalidMail(){
        messageLabel.setText("Es muss eine tgm-Mail sein");
    }

    public void pwdMismatch(){
        messageLabel.setText("Passwörter stimmen nicht überein");
    }

    public void userExists(){
        messageLabel.setText("User existiert bereits");
    }

    public void allFields(){
        messageLabel.setText("Kein Feld darf leer sein");
    }
}