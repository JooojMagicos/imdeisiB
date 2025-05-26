package pt.ulusofona.aed.deisimdb;

public class ObjetoRealizador {


    private int id;
    private String nome;
    private int movieId;


    public ObjetoRealizador(int id, String nome, int movieId) // ctrl c ctrl v histórico do constructor filmes
    {
        this.id = id;
        this.nome = nome;
        this.movieId = movieId;

    }
    public int getMovieId() {
        return movieId;
    }
    public String getNome() {
        return nome;
    }



    @Override
    public String toString() {
        return  id + " | " + nome + " | " + movieId;
    }
}

