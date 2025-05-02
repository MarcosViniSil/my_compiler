import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer la = new LexicalAnalyzer();
        try {
            la.lexicalVerification("olá, como. o dia está ?hoje?!44aa!, tudo asasas bem44 444aa a4a aa44 aa44ff90 aa44!hjh");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
