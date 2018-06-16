package harjoitusTyo.domain;

public class Artist {
    Integer id;
    String nimi;

    public Artist(Integer id, String nimi){
        this.id = id;
        this.nimi = nimi;
    }

    public String getNimi(){
        return nimi;
    }
    public Integer getId(){
        return id;
    }

    @Override
    public String toString() {
        return getId() + getNimi();
    }
}
