package pt.ulusofona.aed.deisimdb;
import java.io.*;
import java.util.*;


public class Main
{ // codigo dois

    static ArrayList<ConstructorFilme> objetoFilmes = new ArrayList<>();
    static ArrayList<ConstructorAtor> objetoAtores= new ArrayList<>();
    static ArrayList<ConstructorRealizador> objetoRealizadores = new ArrayList<>();
    static ArrayList<ConstructorGenero> objetoGeneros = new ArrayList<>();
    static ArrayList<ConstructorGeneroFilmes> objetoGeneroFilmes = new ArrayList<>();
    static ArrayList<ConstructorLinhaIncorreta> objetoLinhasIncorretas = new ArrayList<>();
    static ArrayList<ConstructorMovieVotes> objetoMovieVotes = new ArrayList<>();
    static int[] listaFilmes = new int[0];

    static int linhasLidas = 0;
    static int linhasErradas = 0;
    static int primeiraLinhaErrada = -1;




    public static boolean parseFiles(File folder) throws IOException {

        // reseta as variaveis dessa bosta
        objetoFilmes = new ArrayList<>();
        objetoGeneros = new ArrayList<>();
        objetoAtores = new ArrayList<>();
        objetoRealizadores = new ArrayList<>();
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


        // verifica os arquivo csv
        if (files.length == 0) {
            System.out.println("Nenhum arquivo CSV encontrado na pasta.");
        }




        boolean primeiraLinhas = true;
        // processa os csv

        int i = 0;
        int atoresNoFilme = 0;

        for (File file : files) {
            try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {// *NOVO* agora com bufferedReader, é mais rápido que scanner para arquivos maiores

                System.out.println("Processando arquivo: " + file.getName());


                primeiraLinhas = true;
                linhasErradas = 0;
                primeiraLinhaErrada = -1; // aqui reseta as variaveis  de contar linhas erradas e tal
                linhasLidas = 0;
                i = 0;
                String linha;

                // isso lê os arquivos linha por linha
                while ((linha = scanner.readLine()) != null) {


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




                                    try {
                                        id = Integer.parseInt(dados[0].trim());


                                    }catch (NumberFormatException e) {
                                        linhasErradas++;
                                        if (primeiraLinhaErrada == -1 && i != 0){
                                            primeiraLinhaErrada = i;

                                        }
                                        continue;
                                    }

                                    boolean existe = false;
                                                                                            // esse bagulho aqui é feito de uma forma muito burra
                                    for (ConstructorFilme filmeExistente : objetoFilmes) { // supostamente existe uma forma mais inteligente de fazer isso
                                        if (filmeExistente.getIdFilme() == id) {
                                            linhasLidas++;                                  // fazer as coisas dessa forma demora muito mais que o normal, é reparavel na execução do código
                                            existe = true;                                 // mas o prof disse que isso era propósital, portanto, do jeito burro será!

                                            break;
                                        }
                                    }

                                    if (!existe) {
                                        linhasLidas++;
                                        ConstructorFilme filme = new ConstructorFilme(id, nome, data,atoresNoFilme);
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
                                    atoresNoFilme =1;

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


                                    if (idFilme<1000 )
                                    {

                                        // *NOVO* criei esse for aqui para verificar se o filme já foi listado
                                        for (int filmes = 0; filmes < listaFilmes.length; filmes++)
                                        {
                                            if (listaFilmes[filmes] == idFilme) continue;

                                        }
                                        listaFilmes = Arrays.copyOf(listaFilmes, listaFilmes.length + 1);
                                        for (ConstructorAtor atorNoFilme : objetoAtores)


                                        {
                                            if (atorNoFilme.getMovieId() == idFilme)
                                            {
                                                atoresNoFilme++;

                                            }
                                        }
                                        for (ConstructorFilme filmeMudar : objetoFilmes)
                                        {
                                            if (filmeMudar.getIdFilme() == idFilme)
                                            {
                                               filmeMudar.setNumAtores(atoresNoFilme);
                                            }
                                        }

                                    }



                                    String nome = dados[1].trim();
                                    String genero = dados[2].trim();


                                    ConstructorAtor ator = new ConstructorAtor(id, nome, genero, idFilme);
                                    objetoAtores.add(ator);


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


                                    ConstructorRealizador realizador = new ConstructorRealizador(id, nome, movieId);
                                    objetoRealizadores.add(realizador);
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

                                    ConstructorGenero genero = new ConstructorGenero(genreName,genreId);
                                    objetoGeneros.add(genero);

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
                            if (primeiraLinhas)
                            {
                                primeiraLinhas = false;
                            }
                            else {
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

                                    ConstructorGeneroFilmes generoFilme = new ConstructorGeneroFilmes(movieId,genreId);
                                    objetoGeneroFilmes.add(generoFilme);

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

                                    ConstructorMovieVotes movieVotes1 = new ConstructorMovieVotes(movieId,movieRating,movieVotes);
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

                ConstructorLinhaIncorreta linhas = new ConstructorLinhaIncorreta(file.getName(), linhasLidas,linhasErradas,primeiraLinhaErrada);
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

        ArrayList<ConstructorLinhaIncorreta> filmes = objetoLinhasIncorretas;
        ArrayList<ConstructorFilme> filmes1 = objetoFilmes;

      for (Object printafilmes : filmes) // isso aqui testa se ele vai printar tudo direitinho, só basta mudar o .filme pra outra coisa
       {
           System.out.println(printafilmes.toString());
       }
      for (Object printafilmes : filmes1)
      {
          System.out.println(printafilmes.toString());
      }

    }
}