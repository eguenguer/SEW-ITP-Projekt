package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordChangePanel extends JPanel{
    private JButton changePwdButton = new JButton("Passwort ändern");
    private JButton backButton = new JButton("Zurück");
    private JPasswordField oldPwd = new JPasswordField();
    private JPasswordField newPwd= new JPasswordField();
    private JPasswordField confirmPwd = new JPasswordField();
    private JLabel titelLabel = new JLabel("Passwort ändern");
    private JLabel oldPwdLabel = new JLabel("Altes Passwort:");
    private JLabel newPwdLabel = new JLabel("Neues Passwort:");
    private JLabel confirmPwdLabel = new JLabel("Passwort bestätigen:");
    private JLabel messageLabel = new JLabel();

    public PasswordChangePanel(ActionListener controller){
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
        this.add(oldPwdLabel, gbc);

        gbc.gridx = 1;
        this.add(oldPwd, gbc);


        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(newPwdLabel, gbc);

        gbc.gridx = 1;
        this.add(newPwd, gbc);


        gbc.gridy = 3;
        gbc.gridx = 0;
        this.add(confirmPwdLabel, gbc);

        gbc.gridx = 1;
        this.add(confirmPwd, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        this.add(backButton, gbc);
        backButton.addActionListener(controller);
        backButton.setFocusable(false);

        gbc.gridx = 1;
        this.add(changePwdButton, gbc);
        changePwdButton.addActionListener(controller);
        changePwdButton.setFocusable(false);


        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(messageLabel, gbc);
    }

    public String getOldPwd(){
        return String.valueOf(oldPwd.getPassword());
    }

    public String getNewPwd(){
        return String.valueOf(newPwd.getPassword());
    }

    public String getConfirmPwd(){
        return String.valueOf(confirmPwd.getPassword());
    }

    public void successMsg(){
        messageLabel.setText("Passwort erfolgreich geändert!");
    }

    public void wrongOldPwd(){
        messageLabel.setText("Altes Passwort ist falsch");
    }

    public void pwdMismatch(){
        messageLabel.setText("Neue Passwörter stimmen nicht überein");
    }

    public void noChangePwd(){
        messageLabel.setText("Neues Passwort muss unterschiedlich sein");
    }

    public void allFields(){
        messageLabel.setText("Kein Feld darf leer sein");
    }
}