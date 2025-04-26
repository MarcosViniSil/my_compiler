import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer la = new LexicalAnalyzer();
        try {
            System.out.println(la.similarityWithDifferenceTwoCharacters("Exceção","Execução"));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
