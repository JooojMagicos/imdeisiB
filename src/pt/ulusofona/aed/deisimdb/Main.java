package pt.ulusofona.aed.deisimdb;
import javax.print.DocFlavor;
import java.io.*;
import java.util.*;



public class Main
{ // codigo dois


    // FODASE ARRAY LIST, DA PRA GUARDAR TUDO EM HASHMAP KKKKKKKKKKKKKKKK
    static HashMap<Integer,ObjetoFIlmes> objetoFilmesHM = new HashMap<>();
    static ArrayList<ObjetoFIlmes> objetoFilmes = new ArrayList<>();


    // HASHMAP ARRAYLIST GENEROS
    static HashMap<Integer,String> objetoGenerosHM = new HashMap<>();
    static ArrayList<ObjetoGeneros> objetoGeneros = new ArrayList<>();

    // HASHMAP ARRAYLIST REALIZADORES
    static ArrayList<ObjetoRealizador> objetoRealizadores = new ArrayList<>();
    static HashMap<String,Integer> objetoRealizadoresHM = new HashMap<>();
    static HashSet<Integer> objetoRealizadoresHM2 = new HashSet<>(); // esse hashset só serve pra ver se um realizador ja foi inserido na lista de realizadores.

    // HASHMAP ARRAYLIST ATORES
    static ArrayList<ObjetoAtor> objetoAtores= new ArrayList<>();
    static HashMap<String,ArrayList<ObjetoAtor>> objetoAtoresHM = new HashMap<>();
    static HashSet<Integer> objetoAtoresHS = new HashSet<>();


    static ArrayList<ObjetoGeneroFilmes> objetoGeneroFilmes = new ArrayList<>();
    static ArrayList<ObjetoLinhaIncorreta> objetoLinhasIncorretas = new ArrayList<>();
    static ArrayList<ObjetoMovieVotes> objetoMovieVotes = new ArrayList<>();


    static int linhasLidas = 0;
    static int linhasErradas = 0;
    static int primeiraLinhaErrada = -1;




    public static boolean parseFiles(File folder) throws IOException {

        // reseta as variaveis dessa bosta
        objetoFilmesHM = new HashMap<>();
        objetoFilmes = new ArrayList<>();

        objetoRealizadoresHM = new HashMap<>();
        objetoRealizadores = new ArrayList<>();
        objetoRealizadoresHM2 = new HashSet<>();

        objetoGenerosHM = new HashMap<>();
        objetoGeneros = new ArrayList<>();

        objetoAtores = new ArrayList<>();
        objetoAtoresHM = new HashMap<>();

        objetoLinhasIncorretas = new ArrayList<>();
        objetoGeneroFilmes = new ArrayList<>();
        objetoMovieVotes = new ArrayList<>();



//        if (!folder.exists() || !folder.isDirectory()) {
//            System.out.println("A pasta fornecida não existe ou não é um diretório.");
//            return false;
//        }


        File[] files = new File[] {

                new File(folder+"/movies.csv"),
                new File(folder+"/actors.csv"),
                new File(folder+"/directors.csv"),
                new File(folder+"/genres.csv"),
                new File(folder+"/genres_movies.csv"),
                new File(folder+"/movie_votes.csv")

        };


        for (File file : files) {
            try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {// *NOVO* agora com bufferedReader, é mais rápido que scanner para arquivos maiores

                System.out.println("Processando arquivo: " + file.getName());


                boolean primeiraLinhas = true;
                linhasErradas = 0;
                primeiraLinhaErrada = -1; // aqui reseta as variaveis  de contar linhas erradas e tal
                linhasLidas = 0;
                int i = 0;
                String linha;

                // isso lê os arquivos linha por linha
                while ((linha = scanner.readLine()) != null)
                {


                    String[] dados = linha.split(",");


                    switch (file.getName()) {
                        case "movies.csv" -> {

                            if (primeiraLinhas) {
                                primeiraLinhas = false;
                            } else {
                                if (dados.length == 5  && !dados[0].isEmpty() && !dados[1].isEmpty()&& !dados[2].isEmpty()&& !dados[3].isEmpty() && !dados[4].isEmpty())  {

                                    int id;
                                    String nome = dados[1].trim();
                                    String[] data = dados[4].trim().split("-");


                                    try
                                    {

                                        id = Integer.parseInt(dados[0].trim());

                                    }
                                    catch (NumberFormatException e)
                                    {

                                        linhasErradas++;

                                        if (primeiraLinhaErrada == -1 && i != 0){
                                            primeiraLinhaErrada = i;

                                        }

                                        continue;
                                    }





                                    if (objetoFilmesHM.containsKey(id))
                                    {
                                        linhasLidas++;
                                    }
                                    else
                                    {
                                        linhasLidas++;
                                        ObjetoFIlmes filme = new ObjetoFIlmes(id, nome, data);
                                        objetoFilmesHM.put(id,filme);
                                        objetoFilmes.add(filme);
                                    }

                                }

                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i != 0){
                                        primeiraLinhaErrada = i;

                                    }
                                }

                            }

                        }

                        case "actors.csv" -> // agora é só fazer isso pra mais 40 coisas diferentes e esperar que eu não me enforque no processo
                        {
                            if (primeiraLinhas) {
                                primeiraLinhas = false;

                            } else {
                                if (dados.length == 4 && !dados[2].isEmpty() && !dados[0].isEmpty() && !dados[1].isEmpty() && !dados[3].isEmpty()) {

                                    int id;
                                    int idFilme;
                                    linhasLidas++;


                                    try {

                                        id = Integer.parseInt(dados[0].trim());
                                        idFilme = Integer.parseInt(dados[3].trim());

                                    }catch (NumberFormatException e)
                                    {
                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i != 0){
                                            primeiraLinhaErrada = i;

                                        }
                                        continue;
                                    }

                                    String nome = dados[1].trim();
                                    String genero = dados[2].trim();
                                    ObjetoAtor ator = new ObjetoAtor(id, nome, genero, idFilme);
                                    objetoAtores.add(ator);
                                    if (objetoAtoresHM.containsKey(nome))
                                    {
                                        objetoAtoresHM.get(nome).add(ator);

                                    }
                                    else { ArrayList<ObjetoAtor> atorFilmes = new ArrayList<>(); atorFilmes.add(ator); objetoAtoresHM.put(nome,atorFilmes); }


                                    if (objetoFilmesHM.containsKey(idFilme))
                                    {
                                        ObjetoFIlmes filme = objetoFilmesHM.get(idFilme);
                                        int indexFilme = objetoFilmes.indexOf(filme);
                                        filme.setNumAtores(genero);
                                        objetoFilmesHM.replace(idFilme,filme);
                                        objetoFilmes.set(indexFilme,filme);

                                    }

                                }
                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i != 0){
                                        primeiraLinhaErrada = i;

                                    }
                                }
                            }

                        }

                        case "directors.csv" -> {
                            if (primeiraLinhas) {
                                primeiraLinhas = false;
                            } else {
                                if (dados.length == 3 && !dados[0].isEmpty() && !dados[1].isEmpty() && !dados[2].isEmpty()) {

                                    int id;
                                    int movieId;
                                    linhasLidas++;

                                    try {

                                        id = Integer.parseInt(dados[0].trim());
                                        movieId = Integer.parseInt(dados[2].trim());
                                    } catch (NumberFormatException e) {
                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i!= 0)
                                        {
                                            primeiraLinhaErrada = i;
                                        }
                                        break;
                                    }
                                    String nome = dados[1].trim();


                                    ObjetoRealizador realizador = new ObjetoRealizador(id, nome, movieId);
                                    objetoRealizadores.add(realizador);
                                    if (!objetoRealizadoresHM.containsKey(nome))
                                    {
                                        objetoRealizadoresHM.put(nome,1);
                                        objetoRealizadoresHM2.add(id);
                                    }
                                    else
                                    {
                                        objetoRealizadoresHM.replace(nome,objetoRealizadoresHM.get(nome),objetoRealizadoresHM.get(nome)+1);
                                    }

                                    if (objetoFilmesHM.containsKey(movieId))
                                    {
                                        ObjetoFIlmes filme = objetoFilmesHM.get(movieId);
                                        int indexFilme = objetoFilmes.indexOf(filme);
                                        filme.setNumRealizadores(1);
                                        filme.setRealizadores(nome);
                                        objetoFilmesHM.replace(movieId,filme);
                                        objetoFilmes.set(indexFilme,filme);
                                    }



                                }
                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i != 0)
                                    {
                                        primeiraLinhaErrada = i;
                                    }
                                }
                            }

                        }
                        case "genres.csv" ->
                        {
                            if (primeiraLinhas)
                            {
                             primeiraLinhas = false;
                            }
                            else {
                                if (dados.length == 2 && !dados[0].isEmpty() && !dados[1].isEmpty()){
                                    int genreId;
                                    String genreName = dados[1].trim();
                                    linhasLidas++;

                                    try {
                                        genreId = Integer.parseInt(dados[0].trim());

                                    }catch (NumberFormatException e)
                                    {

                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i!=0)
                                        {
                                            primeiraLinhaErrada = i;
                                        }
                                        break;

                                    }

                                    ObjetoGeneros genero = new ObjetoGeneros(genreName,genreId);
                                    objetoGeneros.add(genero);
                                    objetoGenerosHM.put(genreId,genreName);

                                }
                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i!=0)
                                    {
                                        primeiraLinhaErrada = i;
                                    }
                                }
                            }
                        }
                        case "genres_movies.csv" ->
                        {
                            int numGporfilme = 0;
                            if (primeiraLinhas)
                            {
                                primeiraLinhas = false;
                            }
                            else
                            {

                                if (dados.length == 2 && !dados[0].isEmpty() && !dados[1].isEmpty()){

                                    int genreId;
                                    int movieId;
                                    linhasLidas++;

                                    try {
                                        genreId = Integer.parseInt(dados[0].trim());
                                        movieId = Integer.parseInt(dados[1].trim());

                                    }catch (NumberFormatException e)
                                    {
                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i!=0)
                                        {
                                            primeiraLinhaErrada = i;
                                        }
                                        break;
                                    }

                                    ObjetoGeneroFilmes generoFilme = new ObjetoGeneroFilmes(movieId,genreId);
                                    objetoGeneroFilmes.add(generoFilme);


                                    if (objetoFilmesHM.containsKey(movieId))
                                    {
                                        ObjetoFIlmes filme = objetoFilmesHM.get(movieId);
                                        int indexFilme = objetoFilmes.indexOf(filme);

                                        filme.setGeneros(objetoGenerosHM.get(genreId));
                                        filme.setNumGeneros(1);

                                        objetoFilmesHM.replace(movieId,filme);
                                        objetoFilmes.set(indexFilme,filme);
                                    }


                                }
                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i!=0)
                                    {
                                        primeiraLinhaErrada = i;
                                    }
                                }
                            }
                        }
                        case "movie_votes.csv" ->
                        {
                            if (primeiraLinhas)
                            {
                                primeiraLinhas = false;
                            }
                            else {
                                if (dados.length == 3 && !dados[0].isEmpty() && !dados[1].isEmpty() && !dados[2].isEmpty()){

                                    int movieVotes;
                                    int movieId;
                                    double movieRating;
                                    linhasLidas++;

                                    try {

                                        movieId = Integer.parseInt(dados[0].trim());
                                        movieRating = Double.parseDouble(dados[1].trim());
                                        movieVotes = Integer.parseInt(dados[2].trim());

                                    }catch (NumberFormatException e)
                                    {
                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i!=0)
                                        {
                                            primeiraLinhaErrada = i;
                                        }
                                        break;
                                    }

                                    ObjetoMovieVotes movieVotes1 = new ObjetoMovieVotes(movieId,movieRating,movieVotes);
                                    objetoMovieVotes.add(movieVotes1);

                                }
                                else
                                {
                                    linhasErradas++;
                                    if (primeiraLinhaErrada == -1 && i!=0)
                                    {
                                        primeiraLinhaErrada = i;
                                    }
                                }
                            }
                        }
                    }

                    i++;

                }

                ObjetoLinhaIncorreta linhas = new ObjetoLinhaIncorreta(file.getName(), linhasLidas,linhasErradas,primeiraLinhaErrada);
                objetoLinhasIncorretas.add(linhas);

            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado: " + file.getName());

            }


        }

        return true;  // Retorna true se todos os arquivos foram processados com sucesso
    }


    public static ArrayList getObjects(TipoEntidade tipo) {

        switch (tipo) // esse  bagulho aqui só busca os arraylist criados la em cima, n serve pra mais nada. 😢
        {
            case ATOR ->
            {
                return objetoAtores;
            }
            case FILME ->
            {
                return objetoFilmes;
            }
            case REALIZADOR ->
            {
                return objetoRealizadores;
            }
            case GENERO_CINEMATOGRAFICO ->
            {
                return objetoGeneros;
            }
            case INPUT_INVALIDO ->
            {
                return objetoLinhasIncorretas;
            }

        }

        return new ArrayList<>(); // lembrar de criar um negocio pra deixar isso mais bonitinho, retorna um array vazio caso o tipoentidade incorreto
    }

    public static Result execute(String command)
    {

        ArrayList<String> entradas = new ArrayList<>();
        String comando = "";
        String atual = "";

        for (int i = 0; i<command.length();i++)
        {

            if (command.charAt(i) == ' ' && comando.isEmpty())
            {
                comando = atual;
                atual = "";
                continue;
            }

            if (command.charAt(i) == ' ')
            {
                entradas.add(atual);
                atual = "";
                continue;
            }

            if (command.length()-1 == i)
            {
                atual += command.charAt(i);
                entradas.add(atual);
                continue;
            }

            atual += command.charAt(i);
        }

        switch (comando)
        {

            case "HELP" ->
            {
                new Commands().help();
            }

            case "COUNT_MOVIES_MONTH_YEAR" -> // passou os testes
            {
                return new Commands().countMoviesMonthYear(entradas,objetoFilmesHM);
            }

            case "COUNT_MOVIES_DIRECTOR" ->
            {
                return new Commands().countMoviesDirector(entradas,objetoRealizadoresHM);
            }

            case "COUNT_ACTORS_IN_2_YEARS" ->
            {
                return new Commands().countActorsIn2Years(entradas,objetoAtoresHM,objetoFilmesHM);
            }
            case "GET_MOVIES_ACTOR_YEAR" ->
            {
                return new Commands().getMoviesActorYear(entradas,objetoFilmesHM,objetoAtoresHM);

            }
            case "GET_MOVIES_WITH_ACTOR_CONTAINING" ->
            {
                return new Commands().getMoviesWithActorContaining(entradas,objetoFilmesHM,objetoAtoresHM);
            }
            case "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING" ->
            {
                return new Commands().getTop4YearsWithMoviesContaining(entradas,objetoFilmesHM);
            }
            case "GET_ACTORS_BY_DIRECTOR" ->
            {
                return new Result(false,"incompleto","No results");
            }
            case "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS" ->
            {
                return new Commands().countMoviesBetweenYearsWithNActors(entradas,objetoFilmesHM);
            }
            case "TOP_MONTH_MOVIE_COUNT" ->
            {
                return new Commands().topMonthMovieCount(entradas, objetoFilmesHM);
            }
            case "TOP_MOVIES_WITH_MORE_GENDER" ->
            {


                List<Map.Entry<String, Integer>> list = new ArrayList<>();
                HashMap<String, Integer> anosGeneros = new HashMap<>();
                ArrayList<String> filmes = new ArrayList<>();
                ArrayList<String> filmesOrganizados = new ArrayList<>();
                String filmesOrganizadosString = "";
                int contador = 0;

                for (ObjetoFIlmes filmes2 : objetoFilmes)
                {
                    if (filmes2.getAno() == Integer.parseInt(entradas.get(1)) && contador < Integer.parseInt(entradas.get(0 )))
                    {
                        contador++;
                        anosGeneros.put(filmes2.getNome(), filmes2.getAtoresGenero(entradas.get(2)));

                    }

                }

                list.addAll(anosGeneros.entrySet());

                list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

                for (int i = list.get(0).getValue(); i>=list.get(list.size()-1).getValue();i--)
                {

                    for (Map.Entry<String, Integer> list1 : list)
                    {
                        if (list1.getValue() == i)
                        {
                            filmes.add(list1.getKey()+":"+list1.getValue());
                        }
                    }

                    filmes.sort(Comparator.comparing(CharSequence::toString, String::compareToIgnoreCase));
                    filmesOrganizados.addAll(filmes);
                    filmes.clear();


                }
                filmesOrganizadosString = filmesOrganizados.toString().replace("[","").replace("]","").replace(", ","\n");
                return new Result(true,"",filmesOrganizadosString);

            }
            case "INSERT_DIRECTOR" ->
            {
                return new Commands().insertDirector(entradas,objetoRealizadores, objetoRealizadoresHM, objetoRealizadoresHM2, objetoFilmesHM, objetoFilmes);
            }case "INSERT_ACTOR" ->
            {
                return new Commands().insertActor(entradas, objetoFilmesHM, objetoAtoresHS, objetoAtoresHM, objetoAtores, objetoFilmes);
            }
        }

        return new Result(false,"","");
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Bem-vindo ao deisIMDB");
        long start = System.currentTimeMillis();
        boolean parse0k = parseFiles(new File("files"));

        if(!parse0k)
        {

            System.out.println("Erro na leitura dos ficheiros");
            return;

        }

        long end = System.currentTimeMillis();
        System.out.println("Ficheiros lidos com sucesso em " + (end - start)+ " ms");

        ArrayList<ObjetoLinhaIncorreta> filmes = objetoLinhasIncorretas;
        ArrayList<ObjetoFIlmes> filmes1 = objetoFilmes;

      for (Object printafilmes : filmes) // isso aqui testa se ele vai printar tudo direitinho, só basta mudar o .filme pra outra coisa
       {
           System.out.println(printafilmes.toString()+"\n");
       }


//      for (Object printafilmes : filmes1)
//      {
//          System.out.println(printafilmes.toString());
//
//      }


      HashMap<String,String> hmcoisa = new HashMap<>();
      hmcoisa.put("a","b");
      hmcoisa.put("c","b");

      start = System.currentTimeMillis();

      System.out.println(execute("TOP_MOVIES_WITH_MORE_GENDER 5 2011 F").result);

      filmes1 = objetoFilmes;

//      for (Object printafilmes : filmes1)
//      {
//          System.out.println(printafilmes.toString());
//      }


      end = System.currentTimeMillis();


      System.out.println("demorou "+ (end-start) +" ms");

    }
}