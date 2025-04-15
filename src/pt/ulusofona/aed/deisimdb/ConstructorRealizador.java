package pt.ulusofona.aed.deisimdb;

public class ConstructorRealizador {


    private int id;
    private String nome;
    private int movieId;


    public ConstructorRealizador(int id, String nome, int movieId) // ctrl c ctrl v histórico do constructor filmes
    {
        this.id = id;
        this.nome = nome;
        this.movieId = movieId;

    }

    @Override
    public String toString() {
        return  id + " | " + nome + " | " + movieId;
    }
}
