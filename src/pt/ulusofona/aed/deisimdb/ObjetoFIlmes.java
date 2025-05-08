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
    private final ArrayList<String> realizadores = new ArrayList<>();
    private int numGeneros;
    private final ArrayList<String> generosNomes = new ArrayList<>();

    public ObjetoFIlmes(int id, String nome, String[] ano, int numAtores)
    {

        this.id = id;
        this.nome = nome;
        this.numAtores = numAtores;

        this.ano = ano[2].trim()+"-"+ano[1].trim()+"-"+ano[0].trim();

    }


    // funçoes de set e get --------

    public int getIdFilme()
        {
            return id;
        }

    public String getNome() { return nome; }

    public String getMesAno() {
        String[] mesAno = ano.split("-");
        return mesAno[1]+mesAno[0];
    }

    public int getAno()
    {
        String[] anoano = ano.split("-");
        return Integer.parseInt(anoano[0]);
    }

    public int getNumAA() { return  numAtores;}

    public void setNumAtores(String gen) {
        if (gen.equals("F"))
        {
            this.numAtrizes++;
        }
        if (gen.equals("M"))
        {
            this.numAtores++;
        }
    }

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

            String realizadoresExt = "";

            for (int i = 0; i<realizadores.size(); i++)
            {
                realizadoresExt += realizadores.get(i);
                if (i != realizadores.size()-1)
                {
                    realizadoresExt += ",";
                }
            }

            String generosExt = "";

            for (int i = 0; i<generosNomes.size(); i++)
            {
                generosExt += generosNomes.get(i);
                if (i != generosNomes.size()-1)
                {
                    generosExt += ",";
                }
            }

            return  id + " | " + nome + " | " + ano + " | "+generosExt+" | "+ realizadoresExt +" | "+numAtores+" | "+numAtrizes; // generos e realizadores por extenso e ordem alfavbetica

        }

    }
}
