import lexicalAnalyzer.LexicalAnalyzer;
public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer la = new LexicalAnalyzer();
        try {
            la.lexicalVerification("teste de sucesso!!! ");
            la.lexicalVerification("teste de falha ж");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
