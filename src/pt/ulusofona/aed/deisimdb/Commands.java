package pt.ulusofona.aed.deisimdb;

import java.security.PublicKey;
import java.util.*;

public class Commands {

    public Commands()
    {

    }


    public Result countMoviesMonthYear(ArrayList<String> entradas, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM)
    {
        if (entradas.get(0).length() == 1){ entradas.set(0,"0"+entradas.get(0)); }

        final Integer[] qntdFilmes = {0}; // nao sei porque isso funciona, mas funciona. Tambem deve ocupar mais memoria

        objetoFilmesHM.forEach((key,value) -> // isso parece lento, talvez precise fazer algo diferente
        {
            if (value.getMesAno().equals(entradas.get(0)+entradas.get(1)))
            {
                qntdFilmes[0]++;

            }
        });

        return new Result(true,"", qntdFilmes[0].toString());
    }



    public Result countMoviesDirector(ArrayList<String> entradas, HashMap<String, Integer> objetoRealizadoresHM)
    {
        String nomeCompleto = "";

        for (int i = 0; i < entradas.size(); i++)
        {
            nomeCompleto += entradas.get(i);

            if (i!=entradas.size()-1)
            {
                nomeCompleto += " ";
            }

        }
        System.out.println(nomeCompleto);

        if (objetoRealizadoresHM.containsKey(nomeCompleto))
        {
            return new Result(true,"",objetoRealizadoresHM.get(nomeCompleto).toString());
        }
        else { return new Result(false,"objeto Nao contém nome "+nomeCompleto,"0"); }
    }




    public Result countActorsIn2Years(ArrayList<String> entradas, HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM)
    {
        int count = 0;
        long start = System.currentTimeMillis();
        for (ArrayList<ObjetoAtor> atores : objetoAtoresHM.values())
        {
            for (ObjetoAtor atorFilmes : atores)
            {
                if (objetoFilmesHM.get(atorFilmes.getMovieId()).getAno() == Integer.parseInt(entradas.get(0)))
                {
                    for (ObjetoAtor atorFilmes2 : atores)
                    {
                        if (objetoFilmesHM.get(atorFilmes2.getMovieId()).getAno() == Integer.parseInt(entradas.get(1)))
                        {
                            count++;
                            break;
                        }
                    }

                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (end - start) + " ms");

        return new Result(true,"", Integer.toString(count));
    }
    


    
    public Result countMoviesBetweenYearsWithNActors(ArrayList<String> entradas, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM)
    {
        var ref = new Object() {
            int qntFilmes = 0;
        };

        objetoFilmesHM.forEach((key,value) -> // isso parece lento, talvez precise fazer algo diferente
        {
            if (value.getAno() < Integer.parseInt(entradas.get(1))
                    && value.getAno() > Integer.parseInt(entradas.get(0))
                    && value.getQntAtor() > Integer.parseInt(entradas.get(2))
                    && value.getQntAtor() < Integer.parseInt(entradas.get(3)))
            {
                ref.qntFilmes += 1;
            }
        });

        return new Result(true,"", Integer.toString(ref.qntFilmes));
    }




    public Result getMoviesActorYear(ArrayList<String> entradas, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM,HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM)
    {
        ArrayList<String> filmesDoAtor = new ArrayList<>();
        String stringSaida = "";
        String nomeCompleto = "";
        for (int i = 1; i<entradas.size(); i++)
        {
            nomeCompleto += entradas.get(i);

            if (entradas.size()-1 != i)
            {
                nomeCompleto += " ";
            }
        }

        if (objetoAtoresHM.containsKey(nomeCompleto))
        {
            for (ObjetoAtor atores : objetoAtoresHM.get(nomeCompleto))
            {
                if (objetoFilmesHM.get(atores.getMovieId()).getAno() == Integer.parseInt(entradas.get(0)))
                {
                    filmesDoAtor.add(objetoFilmesHM.get(atores.getMovieId()).getNome());
                }
            }
        }

        else { return new Result(false,"Ator Inexistente","No results"); }

        for (int i = 0; i < filmesDoAtor.size(); i++)
        {
            stringSaida += filmesDoAtor.get(i);
            if (filmesDoAtor.size()-1 != i)
            {
                stringSaida += "\n";
            }
        }
        if (filmesDoAtor.isEmpty())
        {
            return new Result(false,"No results","No results");
        }
        return new Result(true,"",stringSaida);
    }


    public Result getMoviesWithActorContaining(ArrayList<String> entradas, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM,HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM)
    {

        ArrayList<String> results = new ArrayList<>();

        for (ArrayList<ObjetoAtor> value : objetoAtoresHM.values())
        {

            for (ObjetoAtor atores : value)
            {
                if (atores.getNome().contains(entradas.get(0)) && !results.contains(objetoFilmesHM.get(atores.getMovieId()).getNome())) { // NEM FODENDO QUE EU PASSEI 4 HORAS NISSO PRA DESCOBRIR QUE DA PRA FAZER CONTAINS COM CHARSEQUENCE
                    results.add(objetoFilmesHM.get(atores.getMovieId()).getNome());
                }

            }

        } // já ta melhor agora EDIT: AAAAAAAAAAAA ME MATE

        Collections.sort(results);
        String resultFinal = results.toString().replace("[","").replace("]","").replace(", ","\n");

        if (results.isEmpty())
        {
            return new Result(false,"No results","No results");
        }
        return new Result(true,"",resultFinal);
    }



    public Result getTop4YearsWithMoviesContaining(ArrayList<String> entradas, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM)
    {
        HashMap<Integer,Integer> qntFilmesPorAno = new HashMap<>();
        String entradaCompleta = entradas.toString().replace("[","").replace("]","").replace(", "," ");
        String qntdFilmes = "";

        for (ObjetoFIlmes filmes : objetoFilmesHM.values())
        {
            if (filmes.getNome().contains(entradaCompleta))
            {
                if (qntFilmesPorAno.containsKey(filmes.getAno()))
                {
                    qntFilmesPorAno.put(filmes.getAno(), qntFilmesPorAno.get(filmes.getAno()) + 1);
                }
                else
                {
                    qntFilmesPorAno.put(filmes.getAno(), 1);
                }
            }
        }
        for (Integer ano : qntFilmesPorAno.keySet())
        {
            qntdFilmes += ano + ":" + qntFilmesPorAno.get(ano) + "\n";
        }
        if (qntdFilmes.length() == 0)
        {
            return new Result(false,"No results","No results");
        }
        return new Result(true,"",qntdFilmes);
    }


    public Result topMonthMovieCount (ArrayList<String> entradas, HashMap<Integer, ObjetoFIlmes> objetoFilmesHM){
        HashMap<String, Integer> qntdFilmesPorMes = new HashMap<>();

        String mesComMaisFilmes = "";
        int maxCount = 0;



        for(ObjetoFIlmes filme : objetoFilmesHM.values()){
            if (filme.getAno() == Integer.parseInt(entradas.get(0))) {
                if (qntdFilmesPorMes.get(filme.getMesAno()) == null){
                    qntdFilmesPorMes.put(filme.getMesAno(), 1);
                }else {
                    qntdFilmesPorMes.put(filme.getMesAno(), qntdFilmesPorMes.get(filme.getMesAno()) + 1);
                }
                if (qntdFilmesPorMes.get(filme.getMesAno()) > maxCount) {
                    maxCount = qntdFilmesPorMes.get(filme.getMesAno());
                    mesComMaisFilmes = filme.getMes();
                   if (mesComMaisFilmes.charAt(0) == '0') {
                       mesComMaisFilmes = mesComMaisFilmes.substring(1);
                   }
                }
            }
        }

        return new Result(true, "", mesComMaisFilmes + ":" + maxCount);
    }

    public Result insertActor (ArrayList<String> entradas, HashMap<Integer, ObjetoFIlmes> objetoFilmesHM, HashSet<Integer> objetoAtoresHS, HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM, ArrayList<ObjetoAtor> objetoAtores, ArrayList<ObjetoFIlmes> objetoFilmes){
        String linhaCompleta = String.join("", entradas); // Junta todos os pedaços sem espaços
        String[] entradasSplitted = linhaCompleta.split(";");


        if (!objetoAtoresHS.contains(Integer.parseInt(entradasSplitted[0]))) {
            ObjetoAtor ator = new ObjetoAtor(Integer.parseInt(entradasSplitted[0]), entradasSplitted[1], entradasSplitted[2], Integer.parseInt(entradasSplitted[3]));
            objetoAtores.add(ator);

            if (objetoAtoresHM.containsKey(entradasSplitted[1])) {
                objetoAtoresHM.get(entradasSplitted[1]).add(ator);
            } else {
                ArrayList<ObjetoAtor> atorFilmes = new ArrayList<>();
                atorFilmes.add(ator);
                objetoAtoresHM.put(entradasSplitted[1], atorFilmes);
            }

            objetoAtoresHS.add(Integer.parseInt(entradasSplitted[0]));

            if (objetoFilmesHM.containsKey(Integer.parseInt(entradasSplitted[3]))) {
                ObjetoFIlmes filme = objetoFilmesHM.get(Integer.parseInt(entradasSplitted[3]));
                int indexFilme = objetoFilmes.indexOf(filme);
                filme.setNumAtores(entradasSplitted[2]);
                objetoFilmesHM.replace(Integer.parseInt(entradasSplitted[3]), filme);
                objetoFilmes.set(indexFilme, filme);
            }

            return new Result(true, "", "OK");
        } else {
            return new Result(false, "ID DUPLICADO", "Erro");
        }
    }

    public Result insertDirector(ArrayList<String> entradas, ArrayList<ObjetoRealizador> objetoRealizadores, HashMap<String,Integer> objetoRealizadoresHM, HashSet<Integer> objetoRealizadoresHM2, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM, ArrayList<ObjetoFIlmes> objetoFilmes){
        String[] entradasSplitted = entradas.get(0).split(";");
        if (!objetoRealizadoresHM2.contains(Integer.parseInt(entradasSplitted[0])) && objetoFilmesHM.containsKey(Integer.parseInt(entradasSplitted[3])))
        {
            objetoRealizadores.add(new ObjetoRealizador(Integer.parseInt(entradasSplitted[0]),entradasSplitted[1],Integer.parseInt(entradasSplitted[2])));
            objetoRealizadoresHM.put(entradasSplitted[1],1);
            objetoRealizadoresHM2.add(Integer.parseInt(entradasSplitted[0]));

            ObjetoFIlmes filme = objetoFilmesHM.get(Integer.parseInt(entradasSplitted[2]));
            int indexFilme = objetoFilmes.indexOf(filme);
            filme.setNumRealizadores(1);
            filme.setRealizadores(entradasSplitted[1]);
            objetoFilmesHM.replace(Integer.parseInt(entradasSplitted[2]),filme);
            objetoFilmes.set(indexFilme,filme);

            return new Result(true,"","OK");
        }
        else
        {
            return new Result(false,"ID DUPLICADO","Erro");
        }
    }

    public  Result topMoviesWithMoreGender(ArrayList<String> entradas, ArrayList<ObjetoFIlmes> objetoFilmes){
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        HashMap<String, Integer> anosGeneros = new HashMap<>();

        for (ObjetoFIlmes filmes : objetoFilmes)
        {
            if (!(filmes.getAno() == Integer.parseInt(entradas.get(1))))
            {
                continue;
            }
            else { anosGeneros.put(filmes.getNome(), filmes.getAtoresGenero(entradas.get(2))); }
        }
        list.addAll(anosGeneros.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        String anosGenerosOrdenado = "";

        for (Map.Entry<String, Integer> anosGeneros2 : list)
        {
            anosGenerosOrdenado += anosGeneros2.getKey() + ":" + anosGeneros2.getValue() + "\n";
        }
        return new Result(true,"",anosGenerosOrdenado);
    }


    public void help()
    {
        System.out.println("COUNT_MOVIES_MONTH_YEAR <month> <year>\n" + // feito
                "COUNT_MOVIES_DIRECTOR <full-name>\n" + // faisca
                "COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>\n" + // feito - corrigido
                "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <year-start> <year-end> <min> <max>\n" + // feito
                "GET_MOVIES_ACTOR_YEAR <year> <full-name>\n" + // feito
                "GET_MOVIES_WITH_ACTOR_CONTAINING <name>\n" + // feito
                "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>\n" + // feito
                "GET_ACTORS_BY_DIRECTOR <num> <full-name>\n" + // faisca
                "TOP_MONTH_MOVIE_COUNT <year>\n" + //feito
                "TOP_VOTED_ACTORS <num> <year>\n" + // faisca
                "TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>\n" + // jose
                "TOP_MOVIES_WITH_GENDER_BIAS <num> <year>\n" + // jose
                "TOP_6_DIRECTORS_WITHIN_FAMILY <year-start> <year-end>\n" + // jose
                "INSERT_ACTOR <id>;<name>;<gender>;<movie-id>\n" + // feito
                "INSERT_DIRECTOR <id>;<name>;<movie-id>\n" + // feito
                "DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>\n" + // jose
                "HELP\n" +
                "QUIT");
    }
}
