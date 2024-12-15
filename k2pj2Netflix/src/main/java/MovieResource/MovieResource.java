package MovieResource;

import Model.Movie;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import MovieDatabase.MovieRepository;

import java.util.List;

/**
 *Min movieresource klass är en rest webbtjänst som sköter mina HTTP  anrop.
 * så de använder api endpoints för att hämta, uppdatera, deleta. och det vi gör genom är -api/movies och senare också api/movies/{id}
 *  och vi injectar movieRepository för att hantera databasoperationer.
 *  htttp statusar
 *  200 = ok
 *  201 = ny film skapad
 *  404 = not found
 */
@Path("/movies") // Anger bas-URL för alla endpoints i klassen
public class MovieResource {

    @Inject // Denna annotation injicerar MovieRepository så att den kan användas i klassen
    private MovieRepository movieRepository;// Hanterar databasoperationer för Movie

    @GET // hämtar url movies = "alla filmer"
    @Produces(MediaType.APPLICATION_JSON) // returnerar svaret som json
    public List<Movie> getMovies() {
        //hämtar listan med filmer från movierepository
        return movieRepository.findMovies();
    }

    /**
     * Hämtar en specifik film baserat på ID
     * get /api/movies/{id}
     *
     * @param id filmens unika ID
     * @return Ett Movie-objekt i JSON-format eller 404 om filmen inte finns.
     *      * HTTP-status: 200 OK (lyckad) eller 404 NOT FOUND (filmen hittas inte)
     */

    @GET //get request
    @Path("/{id}") //gör{id} till en variable i urlen
    @Produces(MediaType.APPLICATION_JSON)// Returnerar svaret som JSON
    public Response getMovieById(@PathParam("id") Long id) {  // Hämtar filmen från MovieRepository baserat på ID
        Movie movie = movieRepository.findMovie(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();  // Returnerar 404 om filmen inte finns

        }
        return Response.ok(movie).build();   // Returnerar filmen med status 200 OK
    }
    /**
     * Lägger till en ny film i databasen
     * Ep POST /movies
     *
     * @param movie Ett Movie-objekt som tas emot i JSON-format
     * @return En bekräftelse om att filmen har skapats
     * HTTP-status: 201 CREATED
     */
    @POST // postbegäran för att skapa ny resurs
    @Consumes(MediaType.APPLICATION_JSON)// Tar emot json i body
    @Produces(MediaType.APPLICATION_JSON)//returnar svar i json
    public Response addMovie(Movie movie) {
        movieRepository.createMovie(movie); //returnar svar skapat ny film i db
        return Response.status(Response.Status.CREATED).entity("Movie created").build();// Returnerar 201 CREATED med bekräftelse
    }

    /**
     * Uppdaterar en befintlig film baserat på ID.
     * Endpoint put api/movies/{id}
     *
     * @param id Filmens unika ID.
     * @param updateMovie Ett Movie-objekt med uppdaterad information.
     * @return En bekräftelse om att filmen har uppdaterats eller 404 om filmen inte hittas.
     * HTTP-status: 200 OK (uppdaterad) eller 404 NOT FOUND (filmen hittas inte).
     */
    @PUT // putbegäran för att uppdatera resurser
    @Path("/{id}")//gör{id} till en variable i urlen
    @Consumes(MediaType.APPLICATION_JSON) // Tar emot JSON i body
    public Response updateMovie(@PathParam("id") Long id, Movie updateMovie) {
        Movie existingMovie = movieRepository.findMovie(id);// Hämtar den befintliga filmen
        if (existingMovie == null) { // Returnerar 404 om filmen inte finns
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }

        existingMovie.setTitle(updateMovie.getTitle());  // Uppdaterar filmens titel

        movieRepository.updateMovie(existingMovie);// Sparar den uppdaterade filmen
        return Response.ok("Movie updated successfully").build();// Returnerar 200 OK med bekräftelse

    }
    /**
     * Tar bort en film baserat på ID.
     * Endpoint: DELETE /movies/{id}
     *
     * @param id Filmens unika ID.
     * @return En bekräftelse om att filmen har tagits bort eller 404 om filmen inte hittas.
     * HTTP-status: 200 OK (borttagen) eller 404 NOT FOUND (filmen hittas inte).
     */
    @DELETE//delete reuqest för att ta bort objekt
    @Path("/{id}")//gör{id} till en variable i urlen
    @Consumes(MediaType.APPLICATION_JSON)// Tar emot JSON-data
    public Response deleteMovie(@PathParam("id") Long id) {
        Movie existingMovie = movieRepository.findMovie(id);// Hämtar den befintliga filmen
        if (existingMovie == null) {// Returnerar 404 om filmen inte finns
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        movieRepository.deleteMovie(id); // Tar bort filmen
        return Response.ok("Movie deleted successfully").build(); // Returnerar 200 OK med bekräftelse
    }

}