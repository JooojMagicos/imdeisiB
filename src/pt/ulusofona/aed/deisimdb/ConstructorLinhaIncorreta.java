package pt.ulusofona.aed.deisimdb;

public class ConstructorLinhaIncorreta {

    private String nomeArquivo;
    private int linhasLidas;
    private int linhasErradas;
    private int primeiraLinhaErrada;

    public ConstructorLinhaIncorreta(String nomeArquivo, int linhasLidas, int linhasErradas, int primeiraLinhaErrada)
    {
        this.nomeArquivo = nomeArquivo;
        this.linhasLidas = linhasLidas;
        this.linhasErradas = linhasErradas;
        this.primeiraLinhaErrada = primeiraLinhaErrada;
    }


    @Override
    public String toString()
    {
        return nomeArquivo+" | "+ linhasLidas+" | "+linhasErradas+" | "+primeiraLinhaErrada;
    }
}
