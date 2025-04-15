package pt.ulusofona.aed.deisimdb;

import java.util.AbstractMap;

public class ObjetoFIlmes {

    private int id;
    private String nome;
    private String ano;
    private int numAtores;
    private int numAtrizes;


    public ObjetoFIlmes(int id, String nome, String ano, int numAtores)
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

    public void setNumAtoresM(int numAtores) {
        this.numAtores = numAtores;
    }

    public void setNumAtoresF(int numAtores) { this.numAtrizes = numAtores; }

    @Override
    public String toString() { // o to string agora tem que devolver id - nome - ano - numgeneros - numRealizadores - numAM - numAF
        if (id>1000){
            return  id + " | " + nome + " | " + ano +" | "+numAtores+" | "+numAtrizes ;
        }
        else
        {
            return  id + " | " + nome + " | " + ano + " | "+numAtores+" | "+numAtrizes; // generos e realizadores por extenso e ordem alfavbetica

        }

    }
}
