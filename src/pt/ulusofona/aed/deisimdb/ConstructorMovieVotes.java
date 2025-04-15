package pt.ulusofona.aed.deisimdb;

public class ConstructorMovieVotes {
    private int movieId;
    private double movieRating;
    private int movieVotes;

    ConstructorMovieVotes(int movieId, double movieRating, int movieVotes)
    {
        this.movieId = movieId;
        this.movieRating = movieRating;
        this.movieVotes = movieVotes;
    }

    @Override
    public String toString()
    {
        return movieId+" | "+movieRating+" | "+movieVotes;
    }
}
