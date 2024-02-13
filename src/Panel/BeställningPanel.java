package Panel;

import ReferensKlass.Kund;
import ReferensKlass.Repository;
import ReferensKlass.Varor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeställningPanel extends JFrame{

    List<Varor> valdList;
    VarorPanel vp;

    JButton bekräfta = new JButton("Bekräfta");
    JButton backToVaror = new JButton("Avbryta");
    Kund kund;

    BeställningPanel(List<Varor> valdList, Kund kund) {
        this.valdList = valdList;
        this.kund = kund;
        try {
            mainPanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void mainPanel() throws IOException {

        Repository repo = new Repository();
        JPanel listPanel = new JPanel();
        JPanel underPanel = new JPanel();
        JButton bekräftaButton = new JButton("Betala");
        JButton tillbakaButton = new JButton("Tillbaka");

        setLayout(new BorderLayout());
        listPanel.setLayout(new GridLayout(valdList.size(), 1));
        underPanel.setLayout(new GridLayout(1, 3));
        add(listPanel, BorderLayout.NORTH);
        add(underPanel, BorderLayout.SOUTH);
        valdList.forEach(varor -> listPanel.add(varorPanel(varor)));
        int summaPris = valdList.stream().map(Varor::getPris).mapToInt(Integer::intValue).sum();
        System.out.println(summaPris);
        JLabel summa = new JLabel("Summa: "+ summaPris + " kr");

        underPanel.add(summa);
        underPanel.add(bekräftaButton);
        underPanel.add(tillbakaButton);

        bekräftaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<Integer>> AddToCartList = new ArrayList<>();
                for (int i = 0; i < valdList.size(); i++) {
                    List<Integer> AddListToCart = Arrays.asList(valdList.get(i).getId(),
                            kund.getId(),repo.getBeställning().size() + 1, 1);
                    System.out.println(valdList.get(i).getId() + "IDK");
                    AddToCartList.add(AddListToCart);
                }
                try {
                    repo.AddToCart(AddToCartList);
                    vp = new VarorPanel(kund);
                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
                valdList.clear();
                setVisible(false);
            }
        });
        tillbakaButton.addActionListener(e -> {
            try {
                vp = new VarorPanel(kund);
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private JPanel varorPanel(Varor varor) {
        JLabel listLabel = new JLabel("Märke: "+varor.getMärke()+"\n "+
                "Färg: "+varor.getFärg()+"\n "+
                "Storlek: "+varor.getStorlek()+"\n "+
                "Pris: "+varor.getPris() + "\n ");
        JPanel listPanel = new JPanel();
        listPanel.add(listLabel);
        listPanel.setPreferredSize(new Dimension(100, 20));

        return listPanel;
    }
    public static void main(String[] args) {

    }



}
