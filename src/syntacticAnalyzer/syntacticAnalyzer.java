package syntacticAnalyzer;

import lexicalAnalyzer.LexicalAnalyzer;
import java.util.Queue;

public class syntacticAnalyzer {

    private Queue<String> tokensQueue;
    private String[] keyWords = { "Qual", "tem", "Qual", "possui", "Qual", "está", "Qual", "foi", "Quais", "estão",
            "Quais",
            "iniciam", "Quais", "contém", "O", "formato", "O", "documento", "O", "ano", "O", "arquivo",
            "O", "título", "No", "máximo", "O", "foi", "O", "está" };

    private String[] subset1 = { "tem", "possui", "está", "foi" };
    private String[] subset2 = { "estão", "iniciam", "contém" };
    private String[] subset3 = { "formato", "documento", "ano", "arquivo", "título", "foi", "está" };
    private String[] subset4 = {  "máximo" };

    public SyntacticAnalyzer(){}

    // public SyntacticAnalyzer(Queue<String> tokensQueue) {
    //     this.tokensQueue = tokensQueue;
    // }

    public void indentifyPhrase(String instruction) {
        instruction = instruction.trim().replaceAll("\\s+", " ");
        if (instruction.length() == 0) {
            return;
        }

        String[] instructionParts = instruction.split(" ");
        String firstWord = instructionParts[0];

        String typeInstruction = this.isWordStruction(firstWord);
        if (typeInstruction == " ") {
            System.out.println("Não entendi1");
            return;
        }
        int indexSubSet = this.getIndexSubSet(typeInstruction);
        
        int indexRuleActivate = this.getIntructionRule(instruction,indexSubSet);
        if(indexRuleActivate == -1){
            System.out.println("Não entendi2");
            return;
        }

        System.out.println(this.getRuleActivate(indexSubSet, indexRuleActivate));

    }

    public String getRuleActivate(int indexSubSet, int indexRuleActivate){
        String[] keyWords = this.getSubSet(indexSubSet);
        String typeRule = this.getTypeRule(indexSubSet);
        return typeRule+" "+keyWords[indexRuleActivate];
    }

    public String getTypeRule(int indexSubSet){
        if(indexSubSet == 1){
            return "Qual";
        }else if(indexSubSet == 2){
            return "quais";
        }else if(indexSubSet == 3){
            return "O";
        }else if(indexSubSet == 4){
            return "No";
        }
        return "";
    }

    public int getIntructionRule(String instruction, int indexSubSet) {
        String[] keyWords = this.getSubSet(indexSubSet);

        String[] instructionParts = instruction.split(" ");
        for (int i = 1; i < instructionParts.length; i++) {
            for (int j = 0; j < keyWords.length; j++) {
                if (LexicalAnalyzer.similarityWithDifferenceTwoCharacters(instructionParts[i], keyWords[j])) {
                    return j;
                }
            }
        }

        return -1;
    }

    public String[] getSubSet(int index) {
        if (index == 1) {
            return subset1;
        } else if (index == 2) {
            return subset2;
        } else if (index == 3) {
            return subset3;
        } else if (index == 4) {
            return subset4;
        }
        return new String[0];
    }

    public int getIndexSubSet(String prefix) {
        if (prefix.equals("Qual")) {
            return 1;
        } else if (prefix.equals("Quais")) {
            return 2;
        } else if (prefix.equals("O")) {
            return 3;
        } else if (prefix.equals("No")) {
            return 4;
        }
        return -1;
        
    }

    public String isWordStruction(String word) {
        if((word.toLowerCase().startsWith("qual") || word.toLowerCase().contains("qual"))  && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "qual")  ){
            return "Qual";
        }else if((word.toLowerCase().startsWith("quai") || word.toLowerCase().contains("quai")) && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "Quais")){
            return "Quais";
        }else if(word.toLowerCase().contains("n") && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "No")){
            return "No";
        }else if(word.toLowerCase().contains("o") && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "O")){
            return "O";
        }

        return " ";
    }

}

