package pt.ulusofona.aed.deisimdb;

public class ObjetoGeneroFilmes {

    private int genreId;
    private int movieId;

    public ObjetoGeneroFilmes(int genreId, int movieId)
    {
        this.genreId = genreId;
        this.movieId = movieId;
    }

    @Override
    public String toString() { return  genreId + " | " + movieId; }
}
