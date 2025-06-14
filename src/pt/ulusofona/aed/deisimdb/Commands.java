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


        if (objetoRealizadoresHM.containsKey(nomeCompleto))
        {
            return new Result(true,"",objetoRealizadoresHM.get(nomeCompleto).toString());
        }
        else { return new Result(false,"objeto Nao contém nome "+nomeCompleto,"0"); }
    }




    public Result countActorsIn2Years(ArrayList<String> entradas, HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM)
    {
        int count = 0;

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
                    && value.getNumAtores()
                    > Integer.parseInt(entradas.get(2))
                    && value.getNumAtores() < Integer.parseInt(entradas.get(3)))
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

        nomeCompleto = String.join(" ", entradas.subList(1, entradas.size()));


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

        filmesDoAtor.sort(Comparator.reverseOrder());
        stringSaida = filmesDoAtor.toString().replace("[","").replace("]","").replace(", ","\n");

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
        int count = 0;

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
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(qntFilmesPorAno.entrySet());

        list.sort((e1, e2) -> {
            int cmp = Integer.compare(e2.getValue(), e1.getValue()); // valor descrescente
            if (cmp == 0) {
                return Integer.compare(e1.getKey(), e2.getKey());    // chave crescente
            }
            return cmp;
        });

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : list)
        {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
            count++;
            if (count == 4)
            {
                break;
            }
        }
        qntdFilmes = sb.toString();
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

    public Result insertActor (ArrayList<String> entradas, HashMap<Integer, ObjetoFIlmes> objetoFilmesHM, HashSet<Integer> objetoAtoresHS, HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM, ArrayList<ObjetoAtor> objetoAtores, HashMap<Integer,ArrayList<String>> objetoAtoresHM2)
    {
        String linhaCompleta = entradas.toString().replace("[","").replace("]","").replace(", "," ");
        String[] entradasSplitted = linhaCompleta.split(";");

        if (entradasSplitted.length != 4) { return new Result(false,"entrada invalida","Erro"); }

        if (objetoAtoresHS.contains(Integer.parseInt(entradasSplitted[0])))
        {
            return new Result(false,"id DUPLICADO","Erro");
        }
        else
        {
            if (objetoFilmesHM.containsKey(Integer.parseInt(entradasSplitted[3])))
            {

                ObjetoFIlmes filme = objetoFilmesHM.get(Integer.parseInt(entradasSplitted[3]));
                filme.setNumAtores(entradasSplitted[2]);
                filme.setAtoresObj(new ObjetoAtor(Integer.parseInt(entradasSplitted[0]), entradasSplitted[1], entradasSplitted[2],Integer.parseInt(entradasSplitted[3]) ));
                objetoFilmesHM.replace(Integer.parseInt(entradasSplitted[3]), filme);


            } else {
                return new Result(false, "filme inexistente", "Erro");
            }

            objetoAtores.add(new ObjetoAtor(Integer.parseInt(entradasSplitted[0]), entradasSplitted[1], entradasSplitted[2],Integer.parseInt(entradasSplitted[3]) ));
            ArrayList<ObjetoAtor> filmesDele = new ArrayList<>();
            filmesDele.add(new ObjetoAtor(Integer.parseInt(entradasSplitted[0]), entradasSplitted[1], entradasSplitted[2],Integer.parseInt(entradasSplitted[3])));
            objetoAtoresHM.put(entradasSplitted[1], filmesDele);
            objetoAtoresHS.add(Integer.parseInt(entradasSplitted[0]));

            if (objetoAtoresHM2.containsKey(Integer.parseInt(entradasSplitted[3])))
            {
                objetoAtoresHM2.get(Integer.parseInt(entradasSplitted[3])).add(entradasSplitted[1]);
            }
            else
            {
                objetoAtoresHM2.put(Integer.parseInt(entradasSplitted[3]), new ArrayList<>(List.of(entradasSplitted[1])));
            }


        }

        return new Result(true,"","OK");

    }

    public Result getGenderBias(ArrayList<String> entradas, ArrayList<ObjetoFIlmes> objetoFilmes)
    {
        HashMap<String,Integer> generoBias = new HashMap<>();

        int contador = 0;
        int limite = Integer.parseInt(entradas.get(0));
        int ano = Integer.parseInt(entradas.get(1));

        for (ObjetoFIlmes filmes : objetoFilmes)
        {
            if (filmes.getNumAtores() >= 11 && filmes.getAno() ==ano)
            {
                generoBias.put(filmes.getNome()+":"+filmes.getGenderBiasGender(),filmes.getGenderBias());
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(generoBias.entrySet());

        list.sort((e1, e2) -> {
            int compare = Integer.compare(e2.getValue(), e1.getValue()); // decrescente
            if (compare == 0) {
                return e1.getKey().compareToIgnoreCase(e2.getKey());     // alfabetico
            }
            return compare;
        });

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : list) {
            contador++;
            if (contador > limite)
            {
                break;
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return new Result(true, "", sb.toString().trim());
    }


    public Result insertDirector(ArrayList<String> entradas, ArrayList<ObjetoRealizador> objetoRealizadores, HashMap<String,Integer> objetoRealizadoresHM, HashSet<Integer> objetoRealizadoresHM2, HashMap<Integer,ObjetoFIlmes> objetoFilmesHM, ArrayList<ObjetoFIlmes> objetoFilmes, HashMap<String,ArrayList<ObjetoRealizador>> objetoRealizadoresARHM)
        {

        String linhaCompleta = entradas.toString().replace("[","").replace("]","").replace(", "," ");
        String[] entradasSplitted = linhaCompleta.split(";");

        if (entradasSplitted.length != 3) { return new Result(false,"entrada invalida","Erro"); }

        if (objetoRealizadoresHM.containsKey(entradasSplitted[1]) || objetoRealizadoresHM2.contains(Integer.parseInt(entradasSplitted[0])))
        {
            return new Result(false,"nome  ou id DUPLICADO","Erro");
        }
        else
        {
            if (objetoFilmesHM.containsKey(Integer.parseInt(entradasSplitted[2]))) // insere o realizador no filme se o filme existir
            {

                ObjetoFIlmes filme = objetoFilmesHM.get(Integer.parseInt(entradasSplitted[2]));
                int indexFilme = objetoFilmes.indexOf(filme);
                filme.setNumRealizadores(1); // adiciona 1 no filme
                filme.setRealizadores(entradasSplitted[1]); // seta o nome do cara
                objetoFilmesHM.replace(Integer.parseInt(entradasSplitted[2]), filme); // faz replace do objeto filme no hashmap
                objetoFilmes.set(indexFilme,filme); // faz replace no arraylist, tem que estar na mesma ordem por causa dos testes do professor.


            } else {
                return new Result(false, "filme inexistente", "Erro");
            }

            ObjetoRealizador novoRealizador = new ObjetoRealizador(Integer.parseInt(entradasSplitted[0]), entradasSplitted[1], Integer.parseInt(entradasSplitted[2]));
            objetoRealizadores.add(novoRealizador); // cria um novo objeto realizador
            objetoRealizadoresHM.put(entradasSplitted[1], 1);
            objetoRealizadoresHM2.add(Integer.parseInt(entradasSplitted[0]));


            ArrayList<ObjetoRealizador> novosFilmeRealizador = new ArrayList<>();
            novosFilmeRealizador.add(novoRealizador);
            objetoRealizadoresARHM.put(entradasSplitted[1],novosFilmeRealizador);

        }

            return new Result(true,"","OK");

    }

    public  Result topMoviesWithMoreGender(ArrayList<String> entradas, ArrayList<ObjetoFIlmes> objetoFilmes){

        Map<String, Integer> anosGeneros = new HashMap<>();

        int contador = 0;
        int limite = Integer.parseInt(entradas.get(0));
        int ano = Integer.parseInt(entradas.get(1));
        String genero = entradas.get(2);

        for (ObjetoFIlmes filme : objetoFilmes) {
            if (filme.getAno() == ano) {
                anosGeneros.put(filme.getNome(), filme.getAtoresGenero(genero));
            }
        }

        List<Map.Entry<String, Integer>> ordenados = new ArrayList<>(anosGeneros.entrySet());

        ordenados.sort((e1, e2) -> {
            int compare = Integer.compare(e2.getValue(), e1.getValue()); // valor decrescente
            if (compare == 0) {
                return e1.getKey().compareToIgnoreCase(e2.getKey());     // chave alfabética
            }
            return compare;
        });


        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ordenados) {
            contador++;
            if (contador > limite)
            {
                break;
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return new Result(true, "", sb.toString().trim());
    }

    public Result getActorsByDirector (ArrayList<String> entradas, HashMap<String,ArrayList<ObjetoRealizador>> objetoRealizadoresARHM, HashMap<Integer,ArrayList<String>> objetoAtoresHM2){

        StringBuilder nomeBuilder = new StringBuilder();

        for (int i = 1; i < entradas.size(); i++) {
            nomeBuilder.append(entradas.get(i));
            if (i != entradas.size() - 1) {
                nomeBuilder.append(" ");
            }
        }
        ArrayList<ObjetoRealizador> realizadors = new ArrayList<>();
        String nomeCompleto = nomeBuilder.toString();
        HashMap<String,Integer> ocorrencias = new HashMap<>();

        if (objetoRealizadoresARHM.containsKey(nomeCompleto) == true)
        {
            realizadors = new ArrayList<>(objetoRealizadoresARHM.get(nomeCompleto));

        }
        else { return new Result(false,"Diretor Inexistente","No results"); }

        for (ObjetoRealizador value : realizadors)
        {
            if (objetoAtoresHM2.containsKey(value.getMovieId()))
            {

                for (String nomeAtor : objetoAtoresHM2.get(value.getMovieId()))
                {

                    if (ocorrencias.containsKey(nomeAtor))
                    {
                        ocorrencias.get(nomeAtor);

                        ocorrencias.replace(nomeAtor, ocorrencias.get(nomeAtor) + 1);
                    }
                    else
                    {

                        ocorrencias.put(nomeAtor, 1);
                    }
                }
            }
        }

        if (objetoRealizadoresARHM.get(nomeCompleto) == null) { return new Result(false,"Diretor Inexistente","No results"); }



        StringBuilder saidaBuilder = new StringBuilder();
        for (String nomeAtor : ocorrencias.keySet()) {
            int vezes = ocorrencias.get(nomeAtor);

            if (vezes >= Integer.parseInt(entradas.get(0))) {
                saidaBuilder.append(nomeAtor).append(":").append(vezes).append("\n");
            }
        }
        saidaBuilder.delete(saidaBuilder.length()-1,saidaBuilder.length());

        return new Result(true, "", saidaBuilder.toString());
    }



    public void help()
    {
        System.out.println("COUNT_MOVIES_MONTH_YEAR <month> <year>\n" + // feito
                "COUNT_MOVIES_DIRECTOR <full-name>\n" + // feito
                "COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>\n" + // feito - corrigido
                "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <year-start> <year-end> <min> <max>\n" + // feito
                "GET_MOVIES_ACTOR_YEAR <year> <full-name>\n" + // feito
                "GET_MOVIES_WITH_ACTOR_CONTAINING <name>\n" + // feito
                "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>\n" + // feito - corrigido
                "GET_ACTORS_BY_DIRECTOR <num> <full-name>\n" + // feito- por corrigir
                "TOP_MONTH_MOVIE_COUNT <year>\n" + //feito
                "TOP_VOTED_ACTORS <num> <year>\n" + // faisca
                "TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>\n" + // feito - corrigido
                "TOP_MOVIES_WITH_GENDER_BIAS <num> <year>\n" + // em progresso
                "TOP_6_DIRECTORS_WITHIN_FAMILY <year-start> <year-end>\n" + // jose
                "INSERT_ACTOR <id>;<name>;<gender>;<movie-id>\n" + // feito - quebrado
                "INSERT_DIRECTOR <id>;<name>;<movie-id>\n" + // feito - vcorrigido
                "DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>\n" + // jose
                "HELP\n" +
                "QUIT");
    }
}
