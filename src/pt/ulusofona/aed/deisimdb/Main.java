package pt.ulusofona.aed.deisimdb;
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
    static HashMap<Integer,String> objetoRealizadoresHM = new HashMap<>();

    static ArrayList<ObjetoAtor> objetoAtores= new ArrayList<>();
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

        objetoGenerosHM = new HashMap<>();
        objetoGeneros = new ArrayList<>();

        objetoAtores = new ArrayList<>();
        objetoLinhasIncorretas = new ArrayList<>();
        objetoGeneroFilmes = new ArrayList<>();
        objetoMovieVotes = new ArrayList<>();



        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("A pasta fornecida não existe ou não é um diretório.");
            return false;
        }


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
                                    String data = dados[4].trim();

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

                                    boolean existe = false;



                                    if (objetoFilmesHM.containsKey(id)) {

                                        linhasLidas++;
                                        existe = true;

                                    }



                                    if (!existe)
                                    {

                                        linhasLidas++;
                                        ObjetoFIlmes filme = new ObjetoFIlmes(id, nome, data,0);
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


                                    if (objetoFilmesHM.containsKey(idFilme))
                                    {
                                        ObjetoFIlmes filme = objetoFilmesHM.get(idFilme);
                                        objetoFilmes.remove(filme);
                                        filme.setNumAtores(genero);
                                        objetoFilmesHM.replace(idFilme,filme);
                                        objetoFilmes.add(filme);


                                    }




                                    // adicionando o ator ao objeto



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


                                    if (objetoFilmesHM.containsKey(movieId))
                                    {
                                        ObjetoFIlmes filme = objetoFilmesHM.get(movieId);
                                        filme.setNumRealizadores(1);
                                        filme.setRealizadores(nome);
                                        objetoFilmesHM.replace(movieId,filme);
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
                                        objetoFilmes.remove(filme);
                                        filme.setGeneros(objetoGenerosHM.get(genreId));
                                        filme.setNumGeneros(1);
                                        objetoFilmesHM.replace(movieId,filme);
                                        objetoFilmes.add(filme);
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
      for (Object printafilmes : filmes1)
      {
          System.out.println(printafilmes.toString());
      }

    }
}