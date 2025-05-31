import syntacticAnalyzer.syntacticAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        syntacticAnalyzer la = new syntacticAnalyzer();
        try {
            la.indentifyPhrase("    O <formato> esta <pasta>");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
