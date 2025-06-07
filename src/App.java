import syntacticAnalyzer.syntacticAnalyzer;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.Levenshtein;
import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lexical = new LexicalAnalyzer();
        try {

            lexical.lexicalVerification("Quais documentos contém teste ?");
            syntacticAnalyzer syntacticAnalyzer = new syntacticAnalyzer(lexical.getTokensQueue(),lexical.getSymbolsTable());
            syntacticAnalyzer.execute();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
