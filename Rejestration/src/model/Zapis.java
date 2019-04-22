package model;

public class Zapis {
    private Integer id;
    private Integer idUzytkownika;
    private Integer idWydarzenia;
    private String zgoda;

    public Zapis(Integer id, Integer id_uzytkownika, Integer id_wydarzenia, String zgoda) {
        this.id = id;
        this.idUzytkownika = id_uzytkownika;
        this.idWydarzenia = id_wydarzenia;
        this.zgoda = zgoda;
    }

    public Zapis(Integer id_uzytkownika, Integer id_wydarzenia, String zgoda) {
        this.idUzytkownika = id_uzytkownika;
        this.idWydarzenia = id_wydarzenia;
        this.zgoda = zgoda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(Integer id_uzytkownika) {
        this.idUzytkownika = id_uzytkownika;
    }

    public Integer getIdWydarzenia() {
        return idWydarzenia;
    }

    public void setIdWydarzenia(Integer id_wydarzenia) {
        this.idWydarzenia = id_wydarzenia;
    }

    public String getZgoda() {
        return zgoda;
    }

    public void setZgoda(String zgoda) {
        this.zgoda = zgoda;
    }
}
