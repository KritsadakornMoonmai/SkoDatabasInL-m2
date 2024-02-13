package ReferensKlass;

import ReferensKlass.Varor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    Properties p = new Properties();

    public void Loader() {
        try {
            p.load(new FileInputStream("src/RepoProperties.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        p.getProperty("connectionString");
        p.getProperty("namn");
        p.getProperty("password");
    }

    public List<Varor> getVaror() throws IOException {
        p.load(new FileInputStream("src/RepoProperties.properties"));

        p.getProperty("connectionString");
        p.getProperty("namn");
        p.getProperty("password");


        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("namn"),
                p.getProperty("password"));
             Statement stat = c.createStatement();
             ResultSet res = stat.executeQuery("select * from Varor")
             ){

            List<Varor> varorList = new ArrayList<>();
            while (res.next()) {
                int id = res.getInt("id");
                String märke = res.getString("märke");
                String färg = res.getString("färg");
                int storlek = res.getInt("storlek");
                int pris = res.getInt("pris");
                int antal = res.getInt("antal");
                Varor varor = new Varor(id, märke, färg, storlek, pris, antal);
                varorList.add(varor);
            }
            return varorList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Kund> getKund() {
        Loader();
        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("namn"),
                p.getProperty("password"));
             Statement stat = c.createStatement();
             ResultSet res = stat.executeQuery("select * from Kund")
        ) {
            List<Kund> kundList = new ArrayList<>();
            while (res.next()) {
                int kundId = res.getInt("id");
                String kundNamn = res.getString("namn");
                String lösenord = res.getString("lösenord");
                Kund kund = new Kund();
                kund.setId(kundId);
                kund.setNamn(kundNamn);
                kund.setLösenord(lösenord);

                kundList.add(kund);
            }
            return kundList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Beställning> getBeställning() {

        Loader();
        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("namn"),
                p.getProperty("password"));
             Statement stat = c.createStatement();
             ResultSet res = stat.executeQuery("select * from Beställning")
        ) {
            List<Beställning> beställningList = new ArrayList<>();
            while (res.next()) {
                int beställningID = res.getInt("id");
                String datum = res.getString("datum");
                int kundId = res.getInt("kundId");
                Beställning beställning = new Beställning();
                beställning.setId(beställningID);
                beställning.setDatum(datum);
                beställning.setKundId(kundId);

                beställningList.add(beställning);
            }
            return  beställningList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void AddToCart(List<List<Integer>> BeställningList) throws IOException, SQLException {
        p.load(new FileInputStream("src/RepoProperties.properties"));

        p.getProperty("connectionString");
        p.getProperty("namn");
        p.getProperty("password");


        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("namn"),
                p.getProperty("password"));
             CallableStatement cs = c.prepareCall("call AddToCart(?, ?, ?, ?)");
        ) {

            BeställningList.stream().forEach(System.out::println);

            for (int i = 0; i < BeställningList.size(); i++) {
                List<Integer> list = new ArrayList<>();
                list = BeställningList.get(i);
                System.out.println(list.get(0) +" "+list.get(1));
                cs.setInt(1, list.get(0));
                cs.setInt(2, list.get(1));
                cs.setInt(3, list.get(2));
                cs.setInt(4, list.get(3));
                cs.executeQuery();
            }
        }
    }

}
