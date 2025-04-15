package pt.ulusofona.aed.deisimdb;

public class ConstructorGenero {

    private String genero;
    private int id;

    public ConstructorGenero(String genero, int id)
    {
        this.id = id;
        this.genero = genero;


    }
    public int getGeneroId()
    {
        return id;
    }

    @Override

    public String toString()
    {
        return id+" | "+genero;
    }
}
