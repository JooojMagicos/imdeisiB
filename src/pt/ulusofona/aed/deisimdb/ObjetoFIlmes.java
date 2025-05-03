package pt.ulusofona.aed.deisimdb;


import java.util.ArrayList;
import java.util.Collections;

public class ObjetoFIlmes {

    private final int id;
    private final String nome;
    private final String ano;
    private int numAtores;
    private int numAtrizes;
    private int numRealizadores;
    private ArrayList<String> realizadores = new ArrayList<>();
    private int numGeneros;
    private ArrayList<String> generosNomes = new ArrayList<>();

    public ObjetoFIlmes(int id, String nome, String ano, int numAtores)
    {

        this.id = id;
        this.nome = nome;
        this.numAtores = numAtores;
        String[] anoInvertido = ano.split("-");
        this.ano = anoInvertido[2].trim()+"-"+anoInvertido[1].trim()+"-"+anoInvertido[0].trim();

    }


    // funçoes de set e get --------

    public int getIdFilme()
        {
            return id;
        }

    public String getNome() { return nome; }

    public String getAno() {
        return ano;
    }

    public void setNumAtoresM(int numAtores) {
        this.numAtores = numAtores;
    }

    public void setNumAtoresF(int numAtores) { this.numAtrizes = numAtores; }

    public void setNumRealizadores(int numRealizadores) { this.numRealizadores += numRealizadores; }

    public void setNumGeneros(int numGeneros){ this.numGeneros += numGeneros; }

    public void setRealizadores(String realizadores){ this.realizadores.add(realizadores); Collections.sort(this.realizadores);}

    public void setGeneros(String generos){ this.generosNomes.add(generos); Collections.sort(this.generosNomes);}

    // funçoes de set e get ---------


    // funçao to string pra printar essa merda toda
    @Override
    public String toString() { // o to string agora tem que devolver id - nome - ano - numgeneros - numRealizadores - numAM - numAF
        if (id>1000)
        {
            return  id + " | " + nome + " | " + ano +" | "+numGeneros+" | "+numRealizadores+" | "+numAtores+" | "+numAtrizes;
        }
        else
        {

            return  id + " | " + nome + " | " + ano + " | "+generosNomes+" | "+ realizadores +" | "+numAtores+" | "+numAtrizes; // generos e realizadores por extenso e ordem alfavbetica

        }

    }
}
