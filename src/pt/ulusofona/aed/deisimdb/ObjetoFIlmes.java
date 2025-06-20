package pt.ulusofona.aed.deisimdb;


import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ObjetoFIlmes {

    private final int id;
    private final String nome;
    private final String ano;
    private int numAtores = 0;
    private final ArrayList<ObjetoAtor> atoresObj = new ArrayList<>();
    private int numAtrizes = 0;
    private int numRealizadores;
    private final ArrayList<String> realizadores = new ArrayList<>();
    private int numGeneros;
    private final ArrayList<String> generosNomes = new ArrayList<>();
    private int genderBias = 0;


    public ObjetoFIlmes(int id, String nome, String[] ano)
    {

        this.id = id;
        this.nome = nome;


        this.ano = ano[2].trim()+"-"+ano[1].trim()+"-"+ano[0].trim();

    }


    // funçoes de set e get --------

    public int getIdFilme()
        {
            return id;
        }

    public String getNome() { return nome; }

    public String getGenderBiasGender()
    {
        if (numAtrizes > numAtores)
        {
            return "F";
        }
        else { return "M"; }
    }

    public int getGenderBias()
    {

        double result;
        genderBias = numAtores + numAtrizes;

        if(numAtores>numAtrizes)
        {
            result = genderBias;
            System.out.println((numAtores*100)/result);
            result = Math.round((numAtores*100)/result);
            System.out.println(result);
            return (int)result;
        }
        else
        {
            result = genderBias;
            System.out.println((numAtrizes*100)/result);
            result = Math.round((numAtrizes*100)/result);
            System.out.println(result);
            return (int)result;
        }

    }

    public int getAtoresGenero(String genero)
    {
        if(genero.toUpperCase().equals("F")) { return numAtrizes; }
        if(genero.toUpperCase().equals("M")) { return numAtores; }
        else { return 0; }
    }

    public ArrayList<ObjetoAtor> getAtoresObj() { return atoresObj; }


    public String getMesAno() {
        String[] mesAno = ano.split("-");
        return mesAno[1]+mesAno[0];
    }
    public String getMes()
    {
        return ano.split("-")[1];
    }

    public int getMovieID(){
        return id;
    }

    public int getAno()
    {
        String[] anoano = ano.split("-");
        return Integer.parseInt(anoano[0]);
    }

    public int getNumAtores() { return numAtores+numAtrizes; }


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

    public void setAtoresObj(ObjetoAtor atorobj ){ this.atoresObj.add(atorobj); }

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
