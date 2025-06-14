import syntacticAnalyzer.syntacticAnalyzer;

import java.util.Scanner;
import java.util.Queue;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.Levenshtein;
import lexicalAnalyzer.LexicalAnalyzer;

public class App {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lexical = new LexicalAnalyzer();
        syntacticAnalyzer syntacticAnalyzer = new syntacticAnalyzer(lexical.getTokensQueue(),lexical.getSymbolsTable());
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Digite sua solicitação ou 0 para sair");
                String instruction = sc.nextLine();
                if (instruction.equalsIgnoreCase("0")) {
                    break;
                }
                Queue<String> tokens = lexical.lexicalVerification(instruction);

                syntacticAnalyzer.execute(tokens);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

}
