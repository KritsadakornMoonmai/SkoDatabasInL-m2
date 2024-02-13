package Panel;

import ReferensKlass.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InloggningPanel extends JFrame implements ActionListener {

    JPanel inloggning = new JPanel();
    JLabel namnIns = new JLabel("Användarnamn: ");
    JLabel lösenordIns = new JLabel("Lösenord: ");
    JTextField namnTF = new JTextField(15);
    JTextField lösenordTF = new JTextField(15);

    JButton loggaInButton = new JButton("Logga in");

    VarorPanel varorPanel;

    InloggningPanel() throws IOException {
        StartPanel();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    void StartPanel() {

        inloggning.setLayout(new GridLayout(3, 2));
        inloggning.add(namnIns);inloggning.add(namnTF);
        inloggning.add(lösenordIns);inloggning.add(lösenordTF);
        inloggning.add(loggaInButton);
        loggaInButton.addActionListener(this);
        add(inloggning);

        pack();
        inloggning.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        InloggningPanel ip = new InloggningPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hasId = false;
        Repository rp = new Repository();
        if (e.getSource() == loggaInButton) {
            for (int i = 0; i < rp.getKund().size(); i++) {
                if (namnTF.getText().equals(rp.getKund().get(i).getNamn())
                        & lösenordTF.getText().equals(rp.getKund().get(i).getLösenord())) {
                    try {
                        varorPanel = new VarorPanel(rp.getKund().get(i));
                        setVisible(false);
                        hasId = true;
                        break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
