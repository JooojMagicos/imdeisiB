package pt.ulusofona.aed.deisimdb;

public class ObjetoAtor {

    private int id;
    private String nome;
    private String gender;
    private int movieId;

    public ObjetoAtor(int id, String nome, String gender, int movieId)
    {

        this.id = id;
        this.nome = nome;

        switch (gender)
        {
            case "F" -> this.gender = "Feminino";
            case "M" -> this.gender = "Masculino";
            default -> this.gender = "";

        }

        this.movieId = movieId;





    }
    public int getIdActor()
    {
        return id;
    }
    public int getMovieId()
    {
        return movieId;
    }
    public String getGender() { return gender; }

    @Override
    public String toString() {
        return id + " | " + nome + " | " + gender + " | " + movieId;
    }
}
