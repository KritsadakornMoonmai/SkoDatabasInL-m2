package Panel;

import ReferensKlass.Kund;
import ReferensKlass.Repository;
import ReferensKlass.Varor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VarorPanel extends JFrame implements ActionListener {
    JPanel mainPanel = new JPanel();
    BeställningPanel beställningPanel;
    JPanel varorKorgInnerPanel;
    JButton sökVaror = new JButton("Sök");
    Repository repo = new Repository();
    JPanel innerPanel;
    int confirm;
    private Kund kund;
    List<Varor> varorInKorgList = new ArrayList<>();
    List<Varor> varorSökList = new ArrayList<>();
    int count = 1;

    JComboBox<String> varorBox = new JComboBox<>();
    JComboBox<String> färgBox = new JComboBox<>();
    JComboBox<String> storlekBox = new JComboBox<>();
    VarorPanel(Kund kund) throws IOException {
        this.kund = kund;
        mainPanel();
        pack();
    }

    JComboBox<String> getVarorBox() throws IOException {
        //JComboBox<String> varorBox = new JComboBox<>();
        varorBox.addItem("Ospecifierad");
        repo.getVaror().stream().map(Varor::getMärke).distinct().forEach(varorBox::addItem);
        //varorBox.addItemListener(ItemEvent::getItemSelectable);
        return varorBox;
    }

    JComboBox<String> getFärgBox() throws IOException {
        //JComboBox<String> färgBox = new JComboBox<>();
        färgBox.addItem("Ospecifierad");
        repo.getVaror().stream().map(Varor::getFärg).distinct().forEach(färgBox::addItem);
        //färgBox.addItemListener(ItemEvent::getItemSelectable);
        return färgBox;
    }

    JComboBox<String> getStorlekBox() throws IOException {
        //JComboBox<String> storlekBox = new JComboBox<>();
        storlekBox.addItem("Ospecifierad");
        repo.getVaror().stream().map(Varor::getStorlek).distinct()
                .forEach(integer -> storlekBox.addItem(Integer.toString(integer)));
        //storlekBox.addItemListener(ItemEvent::getItemSelectable);
        return storlekBox;
    }

    public void mainPanel() throws IOException {
        setLayout(new GridLayout(1, 2));
        JLabel välkommenLabel = new JLabel("Välkommen " + kund.getNamn() + "!");
        add(mainPanel);
        add(VarorKorgPanel());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(KategoryPanel(), BorderLayout.NORTH);
        mainPanel.add(välkommenLabel, BorderLayout.CENTER);
        mainPanel.add(VarorInnerPanel(), BorderLayout.SOUTH);

        setVisible(true);
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel VarorKorgPanel() {
        JPanel varorKorgPanel = new JPanel();
        JButton tillBeställningButton = new JButton("Till Beställning");
        varorKorgPanel.setLayout(new BorderLayout());
        varorKorgPanel.add(VarorKorgInnerPanel(), BorderLayout.NORTH);
        varorKorgPanel.add(tillBeställningButton, BorderLayout.SOUTH);

        //tillBeställningButton.addActionListener(e -> new BeställningPanel(varorInKorgList, kund));
        tillBeställningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                beställningPanel = new BeställningPanel(varorInKorgList, kund);
                setVisible(false);
            }
        });
        return varorKorgPanel;
    }

    public JPanel VarorKorgInnerPanel() {

        varorKorgInnerPanel = new JPanel();
        varorKorgInnerPanel.setLayout(new GridLayout(varorInKorgList.size(), 1));


        return varorKorgInnerPanel;
    }

    private JScrollPane VarorInnerPanel() {
        innerPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        int rowCount = 1;
        try {
            innerPanel.setLayout(new GridLayout(repo.getVaror().size(), 1));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            repo.getVaror().forEach(varor -> innerPanel.add(Varor(varor)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scrollPane;
    }

    private JPanel KategoryPanel() throws IOException {
        JPanel kategoryPanel = new JPanel();
        kategoryPanel.setLayout(new GridLayout(1, 4));
        kategoryPanel.add(getVarorBox());
        kategoryPanel.add(getFärgBox());
        kategoryPanel.add(getStorlekBox());
        kategoryPanel.add(sökVaror);
        sökVaror.addActionListener(e -> {
            try {
                System.out.println(getVarorBox().getSelectedItem() + "1");
                System.out.println(getFärgBox().getSelectedItem() + "1");
                System.out.println(getStorlekBox().getSelectedItem() + "1");
                for (int i = 0; i < repo.getVaror().size(); i++) {
                    System.out.println("Running" + i);
                    System.out.println(getVarorBox().getSelectedItem());
                    System.out.println(getFärgBox().getSelectedItem());
                    if (Objects.equals(varorBox.getSelectedItem(), repo.getVaror().get(i).getMärke()) &&
                            Objects.equals(färgBox.getSelectedItem(), repo.getVaror().get(i).getFärg()) &&
                            Objects.equals(storlekBox.getSelectedItem(), String.valueOf(repo.getVaror().get(i).getStorlek()))) {
                        System.out.println("passed");
                        varorSökList.clear();
                        System.out.println("clear 1");
                        innerPanel.removeAll();
                        innerPanel.revalidate();
                        innerPanel.repaint();

                        varorSökList.add(repo.getVaror().get(i));
                        System.out.println("clear 2");
                        varorSökList.forEach(varor -> innerPanel.add(Varor(varor)));
                        innerPanel.revalidate();
                        innerPanel.repaint();
                        System.out.println("clear 3");
                    } else if (Objects.equals(getVarorBox().getSelectedItem(), "Ospecifierad") &&
                            Objects.equals(getFärgBox().getSelectedItem(), "Ospecifierad") &&
                            Objects.equals(getStorlekBox().getSelectedItem(), "Ospecifierad")) {

                        System.out.println("clear 1");
                        innerPanel.removeAll();
                        innerPanel.revalidate();
                        innerPanel.repaint();

                        System.out.println("clear 2");
                        repo.getVaror().forEach(varor -> innerPanel.add(Varor(varor)));
                        innerPanel.revalidate();
                        innerPanel.repaint();
                        System.out.println("clear 3");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        return kategoryPanel;
    }

    private void AddIVaruKorg(JPanel panels) {
        if (panels != null) {
            System.out.println("Varokorg accessed before adds");
            varorKorgInnerPanel.add(panels);
            System.out.println("Varokorg accessed after adds");
            varorKorgInnerPanel.revalidate();
            varorKorgInnerPanel.repaint();
        }
    }

    private JPanel Varor(Varor varor) {

        JPanel varorPanel = new JPanel();
        JLabel namnLabel = new JLabel("Märke: ");
        JLabel färgLabel = new JLabel("Färg: ");
        JLabel storlekLabel = new JLabel("Storlek: ");
        JLabel prisLabel = new JLabel("Pris: ");
        JLabel namnAddLabel = new JLabel(varor.getMärke());
        JLabel färgAddLabel = new JLabel(varor.getFärg());
        JLabel storlekAddLabel = new JLabel(String.valueOf(varor.getStorlek()));
        JLabel prisAddLabel = new JLabel(String.valueOf(varor.getPris()));
        varorPanel.setLayout(new GridLayout(4, 2));
        varorPanel.add(namnLabel);varorPanel.add(namnAddLabel);
        varorPanel.add(färgLabel);varorPanel.add(färgAddLabel);
        varorPanel.add(storlekLabel);varorPanel.add(storlekAddLabel);
        varorPanel.add(prisLabel);varorPanel.add(prisAddLabel);
        VarorMouseListener(varorPanel, varor);
        varorPanel.setPreferredSize(new Dimension(250 , 50));
        System.out.println("Varor accessed "+count);
        count++;

        return varorPanel;
    }
    public void VarorMouseListener(JPanel varorPanel, Varor varor) {
        varorPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                confirm = JOptionPane.showConfirmDialog(null,
                        "Märke: "+varor.getMärke()+"\n"+
                                "Färg: "+varor.getFärg()+"\n"+
                                "Storlek: "+varor.getStorlek()+"\n"+
                                "Pris: "+varor.getPris()+"\n"+
                        "Lägger varorna i vagn?", "Bekräftelse", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    JLabel antal = new JLabel("Antal: 1");
                    varorInKorgList.add(varor);
                    AddIVaruKorg(Varor(varor));
                    System.out.println(varorInKorgList);
                    //varorInKorgList.forEach(varor -> AddIVaruKorg(Varor(varor)));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                varorPanel.setBackground(Color.PINK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                varorPanel.setBackground(UIManager.getColor("Panel.background"));
            }
        });
    }

    public void SökVaror() {

    }

    public static void main(String[] args) throws IOException {
        /*SwingUtilities.invokeLater(() -> {
            try {
                VarorPanel vp = new VarorPanel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
