import syntacticAnalyzer.syntacticAnalyzer;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.Levenshtein;

import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lexical = new LexicalAnalyzer();
        try {
            lexical.lexicalVerification("    Qual documento foi criado em 2024 ?");
            syntacticAnalyzer syntacticAnalyzer = new syntacticAnalyzer(lexical.getTokensQueue());
            syntacticAnalyzer.indentifyPhrase("   O <formato> esta <pasta>");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
