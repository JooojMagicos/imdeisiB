package pt.ulusofona.aed.deisimdb;

public class ConstructorGeneroFilmes {

    private int genreId;
    private int movieId;

    public ConstructorGeneroFilmes(int genreId, int movieId)
    {
        this.genreId = genreId;
        this.movieId = movieId;
    }

    @Override
    public String toString() { return  genreId + " | " + movieId; }
}
