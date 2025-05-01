import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer la = new LexicalAnalyzer();
        try {
            la.lexicalVerification("olá, como o dia está hoje?!@aa tudo bem44");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
