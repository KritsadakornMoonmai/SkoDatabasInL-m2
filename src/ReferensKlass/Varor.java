package ReferensKlass;

import java.awt.*;

public class Varor {

    private int id;
    private String märke;
    private String färg;
    private int storlek;
    private int pris;
    private int antal;

    public Varor(int id, String märke, String färg, int storlek, int pris, int antal) {
        this.id = id;
        this.märke = märke;
        this.färg = färg;
        this.storlek = storlek;
        this.pris = pris;
        this.antal = antal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMärke() {
        return märke;
    }

    public void setMärke(String märke) {
        this.märke = märke;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
}
