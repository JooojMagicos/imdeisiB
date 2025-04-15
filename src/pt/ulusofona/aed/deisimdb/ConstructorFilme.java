package pt.ulusofona.aed.deisimdb;

public class ConstructorFilme {

    private int id;
    private String nome;
    private String ano;
    private int numAtores;



    public ConstructorFilme(int id, String nome, String ano, int numAtores)
    {
        this.id = id;
        this.nome = nome;
        this.numAtores = numAtores;
        String[] anoInvertido = ano.split("-");
        this.ano = anoInvertido[2].trim()+"-"+anoInvertido[1].trim()+"-"+anoInvertido[0].trim();


    }
    public int getIdFilme()
        {
            return id;
        }

    public String getNome() {
        return nome;
    }

    public String getAno() {
        return ano;
    }

    public void setNumAtores(int numAtores) {
        this.numAtores = numAtores;
    }

    @Override
    public String toString() {
        if (id>1000){
            return  id + " | " + nome + " | " + ano;
        }
        else
        {
            return  id + " | " + nome + " | " + ano + " | "+numAtores;
        }

    }
}
