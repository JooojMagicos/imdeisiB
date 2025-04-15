package pt.ulusofona.aed.deisimdb;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static pt.ulusofona.aed.deisimdb.Main.getObjects;
import static pt.ulusofona.aed.deisimdb.Main.parseFiles;

import pt.ulusofona.aed.deisimdb.*;
import java.io.File;
import java.util.ArrayList;


public class Testes {

    @Nested
    class ConstructorAtorTest {

        @Test
        void testConstructorValidMale() {
            ConstructorAtor ator = new ConstructorAtor(1, "João", "M", 100);
            assertEquals("Masculino", ator.toString().split(" \\| ")[2], "Gênero deve ser Masculino");
        }

        @Test
        void testConstructorValidFemale() {
            ConstructorAtor ator = new ConstructorAtor(2, "Maria", "F", 101);
            assertEquals("Feminino", ator.toString().split(" \\| ")[2], "Gênero deve ser Feminino");
        }

        @Test
        void testConstructorInvalidGender() {
            ConstructorAtor ator = new ConstructorAtor(3, "Alex", "X", 102);
            assertNull(ator.toString().split(" \\| ")[2], "Gênero inválido deve resultar em null");
        }

        @Test
        void testGetIdActor() {
            ConstructorAtor ator = new ConstructorAtor(4, "Carlos", "M", 103);
            assertEquals(4, ator.getIdActor(), "O ID do ator deve ser 4");
        }

        @Test
        void testToStringFormat() {
            ConstructorAtor ator = new ConstructorAtor(5, "Ana", "F", 104);
            String expected = "5 | Ana | Feminino | 104";
            assertEquals(expected, ator.toString(), "Formato de saída do toString está incorreto");
        }
    }

    @Nested
    class ConstructorFilmeTest {

        @Test
        void testConstructorDateFormat() {
            ConstructorFilme filme = new ConstructorFilme(1, "Filme A", "01-12-2020",0);
            assertEquals("2020-12-01", filme.toString().split(" \\| ")[2], "Formato da data deve ser yyyy-MM-dd");
        }

        @Test
        void testGetIdFilme() {
            ConstructorFilme filme = new ConstructorFilme(2, "Filme B", "15-08-2019",0);
            assertEquals(2, filme.getIdFilme(), "O ID do filme deve ser 2");
        }

        @Test
        void testToStringFormatIdAbove1000() {
            ConstructorFilme filme = new ConstructorFilme(1500, "Filme C", "10-05-2018",0);
            String expected = "1500 | Filme C | 2018-05-10";
            assertEquals(expected, filme.toString(), "Formato de saída do toString está incorreto para ID >= 1000");
        }

        @Test
        void testToStringFormatIdBelow1000() {
            ConstructorFilme filme = new ConstructorFilme(500, "Filme D", "22-03-2017",2);
            String expected = "500 | Filme D | 2017-03-22 | 2";
            assertEquals(expected, filme.toString(), "Formato de saída do toString está incorreto para ID < 1000");
        }

        @Test
        void testInvalidDateFormat() {
            ConstructorFilme filme = new ConstructorFilme(3, "Filme E", "2020-12-01",0);
            assertNotEquals("2020-12-01", filme.toString().split(" \\| ")[2], "Data no formato errado não deve ser aceita");
        }
    }

    @Nested
    class ParseFilesTest {

        @Test
        void testParseFilesAndGetObjects() throws IOException {
            Main main = new Main();
            boolean parseSuccess = parseFiles(new File("files"));
            assertTrue(parseSuccess, "Os ficheiros devem ser lidos com sucesso");

            ArrayList<ConstructorFilme> filmes = getObjects(TipoEntidade.FILME);
            ArrayList<ConstructorAtor> atores = getObjects(TipoEntidade.ATOR);
            ArrayList<ConstructorRealizador> realizadores = getObjects(TipoEntidade.REALIZADOR);
            ArrayList<ConstructorGenero> generos = getObjects(TipoEntidade.GENERO_CINEMATOGRAFICO);
            ArrayList<ConstructorLinhaIncorreta> erros = getObjects(TipoEntidade.INPUT_INVALIDO);

            assertNotNull(filmes);
            assertNotNull(atores);
            assertNotNull(realizadores);
            assertNotNull(generos);
            assertNotNull(erros);

            assertTrue(filmes.size() != 0, "Devem existir pelo menos 1 filme");
            assertTrue(atores.size() != 0, "Devem existir pelo menos 1 ator");
            assertTrue(realizadores.size() != 0, "Devem existir pelo menos 1 realizador");
            assertTrue(generos.size() != 0, "Devem existir pelo menos 1 gênero");
        }
    }

    @Nested
    class ParseFilesTest2yyu {

        @Test
        void testParseFilesWithInvalidInput() throws IOException {
            File invalidFolder = new File("invalid_path");
            boolean result = parseFiles(invalidFolder);
            assertFalse(result, "O método deve retornar false para um diretório inválido.");
        }

        @Test
        void testParseFilesWithMissingData() throws IOException {
            File testFolder = new File("test_files/missing_data");
            boolean result = parseFiles(testFolder);
            assertTrue(result, "O método deve retornar true, mesmo com dados ausentes.");
        }

        @Test
        void testParseFilesWithCorrectData() throws IOException {
            File testFolder = new File("test_files/correct_data");
            boolean result = parseFiles(testFolder);
            assertTrue(result, "O método deve retornar true para dados corretos.");
        }

        @Test
        void testGetObjectsWithInvalidType() {
            boolean result = getObjects(TipoEntidade.INPUT_INVALIDO).isEmpty();
            assertTrue(result, "A busca por entidade inválida deve retornar uma lista vazia.");
        }
    }



}
