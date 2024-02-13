package ReferensKlass;

public class Beställning {
    private int id;
    private String datum;
    private int kundId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getKundId() {
        return kundId;
    }

    public void setKundId(int kundId) {
        this.kundId = kundId;
    }
}
