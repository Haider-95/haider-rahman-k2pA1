package Model;

import jakarta.persistence.*;

/**
 * I den här klassen har vi    allt för att skapa en film i databesen.
 * det är en jpa-entity tabell som skapas i databesen som en taballe.
 */
@Entity //skapar en tabell i databasen som är jpa-entity
@Table(name = "Netflix") //den här entity tabellen kommer heta Netflix i databasen


public class Movie {
    /**
     * tom konstruktor måste jag ha för att jpa ska kunna skapa en instans
     */
    public Movie() {
    }

    @Id//@Id är min primärnyckel i databasen
    @GeneratedValue//id genereras i DB
    @Column(name = "id", nullable = false)//skapar columnen "id" i tabbellen Netflix plus att det blir obligatoriskt att ha en id och tillåter inte inget värde så man måste få ett


    /**
     *  Här är mina privata klassattribut.
     *  viktigt att veta att columnenerna skapas med de här attributerna
     *  dock behövde jag lägga till de själv kan, fråga håkan
     *  Jag använder Long för id om det skulle behövas stora värden, och String för  resten.
     */
    private Long id;

    private String title;

    private String genre;

    private int releaseyear; //int då det inte kan vara så stora världen här

    private String director;

    private String summary;


    /**
     * getter metoderna för att hämta och information
     */
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseyear() {
        return releaseyear;
    }

    public String getDirector() {
        return director;
    }

    public String getSummary() {
        return summary;
    }

    /**
     * setters för att kunna ge värden
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseyear(int releaseyear) {
        this.releaseyear = releaseyear;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}