package syntacticAnalyzer;

import lexicalAnalyzer.LexicalAnalyzer;
import syntacticTree.*;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class syntacticAnalyzer {

    private Queue<String> tokensQueue;
    private ArrayList<String> symbolsTable;
    private SyntacticTree source;

    private String[] keyWords = { "Qual", "tem", "Qual", "possui", "Qual", "está", "Qual", "foi", "Quais", "estão",
            "Quais", "iniciam", "Quais", "contém", "O", "formato", "O", "documento", "O", "ano", "O", "arquivo",
            "O", "título", "No", "máximo", "O", "foi", "O", "está" };

    private String[] subset1 = { "tem", "possui", "está", "criado" };
    private String[] subset2 = { "estão", "iniciam", "contém" };
    private String[] subset3 = { "formato", "documento", "ano", "arquivo", "título", "foi", "está" };
    private String[] subset4 = { "máximo" };

    private String[] rule1 = { "Qual", "tem", "<titulo>" };
    private String[] rule2 = { "Qual", "possui", "<palavra>" };
    private String[] rule3 = { "Qual", "está", "<pasta>" };
    private String[] rule4 = { "Qual", "criado", "<ano>" };

    private String[] rule5 = { "Quais", "estão", "<formato>" };
    private String[] rule6 = { "Quais", "iniciam", "<letra>" };
    private String[] rule7 = { "Quais", "contém", "<palavra>" };

    private String[] answer1 = { "formato", "<formato>" };
    private String[] answer2 = { "tamanho", "<palavra>" };
    private String[] answer3 = { "pasta", "<pasta>" };
    private String[] answer4 = { "ano", "<ano>" };
    private String[] answer5 = { "título", "<titulo>" };
    private String[] answer6 = { "inicia", "<letra>" };
    private String[] answer7 = { "palavra", "<palavra>" };

    public syntacticAnalyzer() {
    }

    public syntacticAnalyzer(Queue<String> tokensQueue, ArrayList<String> symbolsTable) {
        this.tokensQueue = tokensQueue;
        this.symbolsTable = symbolsTable;
        this.source = new SyntacticTree("Programa");
    }

    public void execute(Queue<String> tokensQueue) {
        String[] tokens = tokensQueue.toString()
                .replace("[", "")
                .replace("]", "")
                .replace("?", "")
                .trim()
                .split(",");
        if (tokens.length == 1) {
            System.out.println("Não entendi");
            return;
        }
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }

        this.indentifyPhrase(tokens);
    }

    public void indentifyPhrase(String[] instruction) {

        String firstWord = instruction[0];

        String typeInstruction = this.isWordStruction(firstWord);
        if (typeInstruction == " ") {
            System.out.println("Não entendi");
            return;
        }
        int indexSubSet = this.getIndexSubSet(typeInstruction);

        int indexRuleActivate = this.getIntructionRule(instruction, indexSubSet);
        if (indexRuleActivate == -1) {
            System.out.println("Não entendi");
            return;
        }

        String[] ruleActivate = this.getRuleActivate(indexSubSet, indexRuleActivate);

        String[] instructionConverted = getInstructionConverted(ruleActivate, instruction);
        if (instructionConverted == null) {
            return;
        }

        this.insertTokenMissingIntoSymbolsTable(instructionConverted);

        this.insertValuesIntoSyntacticTree(ruleActivate, instructionConverted);

    }

    public void insertTokenMissingIntoSymbolsTable(String[] instructionConverted) {
        final int positionMissingToken = 2;
        if (positionMissingToken < instructionConverted.length
                && !this.symbolsTable.contains(instructionConverted[positionMissingToken])) {
            this.symbolsTable.add(instructionConverted[positionMissingToken]);
        }
    }

    public String[] getInstructionConverted(String[] ruleActivated, String[] struction) {
        String[] instructionCompleted = { ruleActivated[0], ruleActivated[1], "" };

        if (ruleActivated.length == 3) {
            String sizeAndExtension = "";
            if (ruleActivated[0].equalsIgnoreCase("qual") && ruleActivated[1].equalsIgnoreCase("possui")) {
                String triggerNonStopWord = ruleActivated[1];
                for (int i = 0; i < struction.length; i++) {
                    if (struction[i].equalsIgnoreCase(triggerNonStopWord)) {

                        if (i + 2 < struction.length) {

                            String size = struction[i + 1];
                            String base = struction[i + 2];
                            if (isValidType("tamanho", size)) {
                                sizeAndExtension += struction[i + 1];
                            } else {
                                String nonFinalWord = getNonFinalWordMissing("tamanho");
                                if (nonFinalWord == "") {
                                    return null;
                                }
                                sizeAndExtension += nonFinalWord;
                            }

                            if (isValidType("unidade", base)) {
                                sizeAndExtension += struction[i + 2];
                            } else {
                                String nonFinalWord = getNonFinalWordMissing("unidade");
                                if (nonFinalWord == "") {
                                    return null;
                                }
                                sizeAndExtension += nonFinalWord;
                            }
                        } else {
                            if (i + 2 >= struction.length) {

                                String nonFinalWord = getNonFinalWordMissing("tamanho");
                                if (nonFinalWord == "") {
                                    return null;
                                }
                                sizeAndExtension += nonFinalWord;

                                nonFinalWord = getNonFinalWordMissing("unidade");
                                if (nonFinalWord == "") {
                                    return null;
                                }
                                sizeAndExtension += nonFinalWord;
                            } else if (i + 1 < struction.length) {
                                if (isValidType("unidade", struction[i + 1])) {
                                    String nonFinalWord = getNonFinalWordMissing("unidade");
                                    if (nonFinalWord == "") {
                                        return null;
                                    }
                                    sizeAndExtension += nonFinalWord;
                                    sizeAndExtension += struction[i + 1];
                                } else if (isValidType("inteiro", struction[i + 1])) {
                                    String nonFinalWord = getNonFinalWordMissing("tamanho");
                                    if (nonFinalWord == "") {
                                        return null;
                                    }
                                    sizeAndExtension += struction[i + 1];
                                    sizeAndExtension += nonFinalWord;

                                }
                            }
                        }
                    }
                }
                instructionCompleted[2] = sizeAndExtension;
            }

            else {
                String triggerNonStopWord = ruleActivated[1];
                for (int i = 0; i < struction.length; i++) {
                    if (struction[i].equalsIgnoreCase(triggerNonStopWord)) {
                        String type = ruleActivated[2].replace("<", "").replace(">", "");
                        if (i + 1 < struction.length) {
                            if (isValidType(type, struction[i + 1])) {
                                instructionCompleted[2] = struction[i + 1];

                            } else {
                                String nonFinalWord = getNonFinalWordMissing(type);
                                if (nonFinalWord == "") {
                                    return null;
                                }
                                instructionCompleted[2] = nonFinalWord;
                            }
                        } else {
                            String nonFinalWord = getNonFinalWordMissing(type);
                            if (nonFinalWord == "") {
                                return null;
                            }
                            instructionCompleted[2] = nonFinalWord;
                        }

                    }
                }
            }
        }

        return instructionCompleted;

    }

    public void insertValuesIntoSyntacticTree(String[] ruleStructure, String[] ruleConverted) {
        SyntacticTree instruction = new SyntacticTree("Instruction");

        for (int i = 0; i < ruleStructure.length; i++) {
            if (ruleStructure[i].startsWith("<") && ruleStructure[i].endsWith(">")) {
                SyntacticTree nonTerminal = new SyntacticTree(ruleStructure[i]);
                nonTerminal.addChild(new SyntacticTree(ruleConverted[i]));
                instruction.addChild(nonTerminal);
            } else {
                instruction.addChild(new SyntacticTree(ruleStructure[i]));
            }
        }

        this.source.addChild(instruction);
        this.source.print("");
    }

    public String getNonFinalWordMissing(String type) {
        Scanner sc = new Scanner(System.in);
        String value = "";
        do {
            System.out.printf("não reconheci qual %s, pode me informar o valor de %s ? s para sair: ", type, type);
            value = sc.nextLine();
            if (value.equalsIgnoreCase("s")) {
                return "";
            }
            String[] valueUser = value.split(" ");
            String valueWithoutStopWord = "";

            for (String stringUser : valueUser) {
                if (LexicalAnalyzer.isWordNotStopWord(stringUser)) {
                    valueWithoutStopWord += stringUser + " ";
                }
            }

            if (valueUser.length == 1 && isValidType(type, valueWithoutStopWord)) {
                return value;
            }
            String answerUser = getNonFinalWordFromAnswerUser(type, valueWithoutStopWord.split(" "));
            if (answerUser != "") {
                return answerUser;
            }
            System.out.println("Desculpe, ");

        } while (true);

    }

    public String getNonFinalWordFromAnswerUser(String type, String[] answerUser) {
        if (answerUser.length == 1) {
            if (isValidType(type, answerUser[0])) {
                return answerUser[0];
            } else {
                return "";
            }
        }

        for (int i = 0; i < answerUser.length; i++) {
            if (LexicalAnalyzer.similarityWithDifferenceTwoCharacters(type, answerUser[i])) {
                if (i + 1 >= answerUser.length) {
                    return "";
                }
                if (isValidType(type, answerUser[i + 1])) {
                    return answerUser[i + 1];
                } else {
                    return "";
                }
            }
        }
        return "";
    }

    public String recognizeType(String word) {
        if (word.matches("\\d+"))
            return "inteiro";
        if (word.matches("\\d+(\\.\\d+)?"))
            return "numero";
        if (word.matches("[a-zA-Z]{1}"))
            return "letra";
        if (word.matches("[a-zA-Z]+"))
            return "palavra";
        if (word.matches("pdf|docx|txt|html"))
            return "formato";
        if (word.equals("?") || word.equals("."))
            return "pontuacao";
        return "desconhecido";
    }

    public String[] getRuleActivate(int indexSubSet, int indexRuleActivate) {
        String[] keyWords = this.getSubSet(indexSubSet);
        String typeRule = this.getTypeRule(indexSubSet);

        if (typeRule.toLowerCase().equals("qual")
                && keyWords[indexRuleActivate].toLowerCase().equals("tem")) {
            return rule1;
        } else if (typeRule.toLowerCase().equals("qual")
                && keyWords[indexRuleActivate].toLowerCase().equals("possui")) {
            return rule2;
        } else if (typeRule.toLowerCase().equals("qual")
                && keyWords[indexRuleActivate].toLowerCase().equals("está")) {
            return rule3;
        } else if (typeRule.toLowerCase().equals("qual")
                && keyWords[indexRuleActivate].toLowerCase().equals("criado")) {
            return rule4;
        } else if (typeRule.toLowerCase().equals("quais")
                && keyWords[indexRuleActivate].toLowerCase().equals("estão")) {
            return rule5;
        } else if (typeRule.toLowerCase().equals("quais")
                && keyWords[indexRuleActivate].toLowerCase().equals("iniciam")) {
            return rule6;
        } else if (typeRule.toLowerCase().equals("quais")
                && keyWords[indexRuleActivate].toLowerCase().equals("contém")) {
            return rule7;
        }
        return null;
    }

    public String getTypeRule(int indexSubSet) {
        if (indexSubSet == 1) {
            return "Qual";
        } else if (indexSubSet == 2) {
            return "quais";
        } else if (indexSubSet == 3) {
            return "O";
        } else if (indexSubSet == 4) {
            return "No";
        }
        return "";
    }

    private boolean isValidType(String type, String value) {
        switch (type) {
            case "palavra":
                return value.matches("[a-zA-Z0-9]+");
            case "numero":
                return value.matches("\\d+(\\.\\d+)?");
            case "inteiro":
                return value.matches("\\d+");
            case "formato":
                return value.matches("(pdf|docx|doc|txt|xml|json|xls)");
            case "letra":
                return value.matches("[a-zA-Z]");
            case "titulo":
                return value.matches("[a-zA-Z]+");
            case "pasta":
                return value.matches("[a-zA-Z0-9]+");
            case "ano":
                return value.matches("\\d+");
            case "tamanho":
                return value.matches("\\d+");
            case "unidade":
                return value.toLowerCase().matches("(mb|kb|gb|b)");
            default:
                return false;
        }
    }

    public int getIntructionRule(String[] instruction, int indexSubSet) {
        String[] keyWords = this.getSubSet(indexSubSet);

        for (int i = 1; i < instruction.length; i++) {
            for (int j = 0; j < keyWords.length; j++) {
                if (LexicalAnalyzer.similarityWithDifferenceTwoCharacters(instruction[i], keyWords[j])) {
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
        if ((word.toLowerCase().startsWith("qual") || word.toLowerCase().contains("qual"))
                && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "qual")) {
            return "Qual";
        } else if ((word.toLowerCase().startsWith("quai") || word.toLowerCase().contains("quai"))
                && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "Quais")) {
            return "Quais";
        } else if (word.toLowerCase().contains("n")
                && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "No")) {
            return "No";
        } else if (word.toLowerCase().contains("o")
                && LexicalAnalyzer.similarityWithDifferenceTwoCharacters(word, "O")) {
            return "O";
        }

        return " ";
    }

}
