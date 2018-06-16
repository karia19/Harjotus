package harjoitusTyo.domain;

import harjoitusTyo.domain.Artist;

public class Kappaleet {
    Integer id;
    Artist artist;
    String nimi;
    Integer vuosi;
    String kommenti;

    public Kappaleet(Integer id, Artist artist1, String nimi, Integer vuosi, String kommenti){
        this.id = id;
        this.nimi = nimi;
        this.vuosi = vuosi;
        this.kommenti = kommenti;
        artist = artist1;

    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public Artist getArtist() {
        return artist;
    }

    public Integer getVuosi() {
        return vuosi;
    }

    public String getArtistname(){
        return artist.getNimi();
    }

    public String getKommenti() {
        return kommenti;
    }
    @Override
    public String toString(){
        return getId()  + getArtistname() +"\n" +  getNimi() +" " + getVuosi() +"\nKommentti: "+ getKommenti();
    }
}
