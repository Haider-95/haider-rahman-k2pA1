package MovieDatabase;

import Model.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;


import java.util.List;

/** i den här klassern hanterar vi allt som händer i db för Movie-Entity
 * Klassen använder jpa:s entitymanager för att
 * skapa, hämta, uppdatera och ta bort data från tabellen "Netflix" i databasen
 */

@ApplicationScoped  //skapar bara en instans av objektet
@Transactional //gör alla metoder här kan köras  inom en transaktion
public class MovieRepository {

    @PersistenceContext //kommunicerar med db
    private EntityManager entityManager;

    /**
     * hämtar alla filmer från db
     * @return  en lista med movie-objekt från tabbellen netflix
     */

    public List<Movie> findMovies() {
        //skapar en sql fråga som hämtar alla rader från netflix
        String sql = "SELECT * FROM NETFLIX";
        Query query = entityManager.createNativeQuery(sql);
        //skickar sql  fårga till db pch hämtar movie-objekt
        List<Movie> movies = query.getResultList(); //
        return movies; // returnerar listan med movies
    }

    /**
     * skapar en film (movie-objekt) den sparas i db genom ->
     * @param movie ett movie-objekt som sparas i db
     */
    public void createMovie(Movie movie) {
        entityManager.persist(movie); //persist för att spara i db
    }

    /**
     * @param movie det Movie-objekt som innehåller uppdaterad information
     */
    public void updateMovie(Movie movie) {
        entityManager.merge(movie); // merge för  uppdatere raderna i db

    }

    /**
     * hämtar en specifik film
     * @param id hämtar baserat på id
     * @return Movie-objekt hämtas eller null  syns i metoden i klass movieResource
     */
    public Movie findMovie(Long id) {
        return entityManager.find(Movie.class, id); //find för att hitta rätt m-objekt baserat på id

    }

    /**
     * tar bort en movie-objekt
     * @param id tar botrt en objekt baserta på id
     */
    public void deleteMovie(Long id) {
        Movie movie = findMovie(id);//hitta filmen
        if (movie != null) {// se oim den finns
            entityManager.remove(movie);//tar bort filmen
        }
    }
}