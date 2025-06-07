package lexicalAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class LexicalAnalyzer {

    private ArrayList<String> symbolsTable = new ArrayList<>();
    private Queue<String> tokensQueue = new LinkedList<>();

    public LexicalAnalyzer() {
    }

    public void lexicalVerification(String instruction) {

        if(instruction.replaceAll(" ", "") == ""){
            return;
        }
        String instructionAux = instruction.replace(" ", "");

        HashMap<Character, Boolean> symbols = this.symbolsAvailable();

        for (int i = 0; i < instructionAux.length(); i++) {
            if (symbols.get(instructionAux.charAt(i)) == null) {
                throw new RuntimeException("Caractere inválido: '" + instructionAux.charAt(i) + "' (Unicode: "
                        + (int) instructionAux.charAt(i) + ")");
            }
        }

        this.insertWordsIntoSymbolsTable(instruction);
        this.insertTokensIntoTokensQueue(instruction);

    }

    private void insertTokensIntoTokensQueue(String instruction) {

        String[] instructions = instruction.split(" ");
        for (int i = 0; i < instructions.length; i++) {
            if (isWordNotStopWord(instructions[i])) {

                if(this.isInstructionContainNumber(instructions[i])){
                    this.separateNumbers(instructions[i]);
                }else{
                    this.separateText(instructions[i]);
                }

            }
        }


    }

    private void insertWordsIntoSymbolsTable(String instruction) {
        instruction = instruction.replaceAll("\\d+", "");

        String[] instructions = instruction.split(" ");

        for (int i = 0; i < instructions.length; i++) {
            if (isWordNotStopWord(instructions[i])) {
                this.insertOnlyWordsOnSymbolsTable(instructions[i]);
            }
        }


    }

    private void insertOnlyWordsOnSymbolsTable(String instruction){
        String partition = "";
        for (int j = 0; j < instruction.length(); j++) {

            if (this.isCharSeparator(instruction.charAt(j))) {
                if (partition != "") {
                    this.symbolsTable.add(partition);
                    this.symbolsTable.add(String.valueOf(instruction.charAt(j)));
                    partition = "";
                }else{
                    this.symbolsTable.add(String.valueOf(instruction.charAt(j)));
                }
            } else {
                partition += instruction.charAt(j);
            }

        }
        if (partition != "") {
            this.symbolsTable.add(partition);
        }

    }

    private void separateText(String instruction) {
        String partition = "";

        for (int j = 0; j < instruction.length(); j++) {

            if (this.isCharSeparator(instruction.charAt(j))) {
                if (partition != "") {
                    this.tokensQueue.add(partition);
                    this.tokensQueue.add(String.valueOf(instruction.charAt(j)));
                    partition = "";
                } else {
                    this.tokensQueue.add(String.valueOf(instruction.charAt(j)));
                }
            } else {
                partition += instruction.charAt(j);
            }

        }
        if (partition != "") {
            this.tokensQueue.add(partition);
        }

    }

    private void separateNumbers(String instructions) {
        int j = 0;
        while (j < instructions.length()) {
            char c = instructions.charAt(j);

            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (j < instructions.length() && Character.isDigit(instructions.charAt(j))) {
                    number.append(instructions.charAt(j));
                    j++;
                }
                this.tokensQueue.add(number.toString());
            } else {
                StringBuilder text = new StringBuilder();
                while (j < instructions.length() && !Character.isDigit(instructions.charAt(j))) {
                    text.append(instructions.charAt(j));
                    j++;
                }
                this.separateText(text.toString());
            }
        }
    }

    public static boolean similarityWithDifferenceTwoCharacters(String word1, String word2) {

        word1 = LexicalAnalyzer.removeAccentIfExists(word1.toLowerCase()).toLowerCase();
        word2 = LexicalAnalyzer.removeAccentIfExists(word2.toLowerCase()).toLowerCase();

        int minLen = Math.min(word1.length(), word2.length());
        int diff = Math.abs(word1.length() - word2.length());

        if (diff > 2) {
            return false;
        }

        for (int i = 0; i < minLen; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diff++;
            }
        }

        return diff <= 2;
    }

    public static String removeAccentIfExists(String word) {

        for (int i = 0; i < word.length(); i++) {
            char letterModify = LexicalAnalyzer.characterAnAccent(word.charAt(i));
            if (letterModify != ' ') {
                word = word.replace(word.charAt(i), letterModify);
            }
        }

        return word;
    }
    private boolean isCharSeparator(char candidate){
        HashMap<Character, Boolean> separators = this.separators();
        return separators.get(candidate) != null && separators.get(candidate);
    }
    public static boolean isWordNotStopWord(String word){
        HashMap<String, Boolean> stopWords = stopWordsList();
        return stopWords.get(word) == null;
    }    
    private boolean isInstructionContainNumber(String instruction){
        return instruction.matches(".*\\d.*");
    }

    public static char characterAnAccent(char letter) {

        if (letter == 'à' || letter == 'á' || letter == 'â' || letter == 'ã') {
            return 'a';
        } else if (letter == 'ç') {
            return 'c';
        } else if (letter == 'é' || letter == 'ê') {
            return 'e';
        } else if (letter == 'í') {
            return 'i';
        } else if (letter == 'ó' || letter == 'ô' || letter == 'õ') {
            return 'o';
        } else if (letter == 'ú') {
            return 'u';
        }

        return ' ';
    }

    private HashMap<Character, Boolean> separators() {
        HashMap<Character, Boolean> separators = new HashMap<>();
        char[] validSeparators = { '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';',
                '<', '=', '>', '?', '@', 'ª', 'º', '°', '¨', '§' };

        for (char separator : validSeparators) {
            separators.put(separator, true);
        }

        return separators;
    }

    private HashMap<Character, Boolean> symbolsAvailable() {
        HashMap<Character, Boolean> symbols = new HashMap<>();
        char[] validSymbols = { '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[',
                '\\', ']', '^', '_', '`', '´', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', 'À', 'Á', 'Â', 'Ã', 'Ç',
                'É', 'Ê', 'Í', 'Ó', 'Ô', 'Õ', 'Ú', 'à', 'á', 'â', 'ã', 'ç', 'é', 'ê', 'í', 'ó', 'ô', 'õ', 'ú', 'ª', 'º',
                '°', '¨', '§' };

        for (char symbol : validSymbols) {
            symbols.put(symbol, true);
        }

        return symbols;

    }

    public static HashMap<String, Boolean> stopWordsList() {
        HashMap<String, Boolean> stopWords = new HashMap<>();
        String[] words = { "é", "aos", "aquela", "aquelas", "aquele", "aqueles", "aquilo", "as", "às", "até",
                "com", "como", "da", "das", "de", "dela", "delas", "dele", "deles", "depois", "do", "dos", "e", "é",
                "ela", "elas", "ele", "eles", "em", "entre", "era", "eram", "éramos", "essa", "essas", "esse", "esses",
                "estamos", "estavam", "estávamos", "este",
                "esteja", "estejam", "estejamos", "estes", "esteve", "estive", "estivemos", "estiver", "estivera",
                "estiveram", "estivéramos", "estiverem", "estivermos", "estivesse", "estivessem", "estivéssemos",
                "estou", "eu", "fomos", "for", "fora", "foram", "fôramos", "forem", "formos", "fosse", "fossem",
                "fôssemos", "fui", "há", "haja", "hajam", "hajamos", "hão", "havemos", "haver", "hei", "houve",
                "houvemos", "houver", "houvera", "houverá", "houveram", "houvéramos", "houverão", "houverei",
                "houverem", "houveremos", "houveria", "houveriam", "houveríamos", "houvermos", "houvesse", "houvessem",
                "houvéssemos", "isso", "isto", "já", "lhe", "lhes", "mais", "mas", "me", "mesmo", "meu", "meus",
                "minha", "minhas", "muito", "na", "não", "nas", "nem", "no", "nos", "nós", "nossa", "nossas", "nosso",
                "nossos", "num", "numa", "para", "pela", "pelas", "pelo", "pelos", "por",
                "quando", "que", "quem", "são", "se", "seja", "sejam", "sejamos","ser", "será", "serão",
                "serei", "seremos", "seria", "seriam", "seríamos", "seu", "seus", "só", "somos", "sou", "sua", "suas",
                "também", "tenham", "tenhamos", "tenho", "terá", "terão", "terei",
                "teremos", "teria", "teriam", "teríamos", "teu", "teus", "teve", "tinha", "tinham", "tínhamos", "tive",
                "tivemos", "tiver", "tivera", "tiveram", "tivéramos", "tiverem", "tivermos", "tivesse", "tivessem",
                "tivéssemos", "tu", "tua", "tuas", "um", "uma", "você", "vocês", "vos" };

        for (String word : words) {
            stopWords.put(word, true);
        }

        return stopWords;
    }

    public ArrayList<String> getSymbolsTable() {
        return this.symbolsTable;
    }

    public Queue<String> getTokensQueue() {
        return this.tokensQueue;
    }
}
