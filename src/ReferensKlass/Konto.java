package ReferensKlass;

public class Konto {
    private int id;
    private int kundId;
    private String användarnamn;
    private String lösenord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKundId() {
        return kundId;
    }

    public void setKundId(int kundId) {
        this.kundId = kundId;
    }

    public String getAnvändarnamn() {
        return användarnamn;
    }

    public void setAnvändarnamn(String användarnamn) {
        this.användarnamn = användarnamn;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }
}
