package pt.ulusofona.aed.deisimdb;

public class ObjetoMovieVotes {
    private int movieId;
    private double movieRating;
    private int movieVotes;

    ObjetoMovieVotes(int movieId, double movieRating, int movieVotes)
    {
        this.movieId = movieId;
        this.movieRating = movieRating;
        this.movieVotes = movieVotes;
    }
    public int getMovieId(){
        return movieId;
    }
    public double getRating(){
        return movieRating;
    }


    @Override
    public String toString()
    {
        return movieId+" | "+movieRating+" | "+movieVotes;
    }
}
