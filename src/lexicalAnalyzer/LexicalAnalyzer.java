package lexicalAnalyzer;

import java.util.HashMap;

public class LexicalAnalyzer {

    public LexicalAnalyzer() { }

    public void lexicalVerification(String instruction) {
        instruction = instruction.replace(" ", "");

        HashMap<Character, Boolean> symbols = this.symbolsAvailable();

        for(int i = 0; i < instruction.length() ; i++ ){
            if(symbols.get(instruction.charAt(i)) == null){
                throw new RuntimeException("Caractere inválido: '" + instruction.charAt(i) + "' (Unicode: " + (int)instruction.charAt(i) + ")");
            }
        }

    }

    public boolean similarityWithDifferenceTwoCharacters(String word1, String word2){

        word1 = this.removeAccentIfExists(word1.toLowerCase());
        word2 = this.removeAccentIfExists(word2.toLowerCase());
    
        int minLen = Math.min(word1.length(), word2.length());
        int diff = Math.abs(word1.length() - word2.length()); 
    
        for (int i = 0; i < minLen; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diff++;
            }
        }
    
        return diff <= 2;
    }

    private String removeAccentIfExists(String word){
        
        for (int i = 0; i < word.length(); i++) {
            char letterModify = this.characterAnAccent(word.charAt(i));
            if(letterModify != ' '){
                word = word.replace(word.charAt(i), letterModify);
            }
        }

        return word;
    }

    private char characterAnAccent(char letter){
       
        if (letter == 'à' || letter == 'á' || letter == 'â' || letter == 'ã' ){
            return 'a';
        }else if(letter == 'ç'){
            return 'c';
        }else if(letter == 'é' || letter == 'ê'){
            return 'e';
        }else if(letter == 'í'){
            return 'i';
        }else if(letter == 'ó' || letter == 'ô' || letter == 'õ'){
            return 'o';
        }else if(letter == 'ú'){
            return 'u';
        }
        
        return ' ';
    }

    private HashMap<Character, Boolean> symbolsAvailable(){
        HashMap<Character, Boolean> symbols = new HashMap<>();
        char[] validSymbols = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/','0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', '´', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~','À', 'Á', 'Â', 'Ã', 'Ç', 'É', 'Ê', 'Í', 'Ó', 'Ô', 'Õ', 'Ú', 'à', 'á', 'â', 'ã', 'ç', 'é', 'ê', 'í', 'ó', 'ô', 'õ', 'ú', 'ª', 'º', '°', '¨', '§'};

        for (char symbol : validSymbols) {
            symbols.put(symbol, true);
        }

        return symbols;

    }

    public HashMap<Character, Boolean> stopWordsList(){
        HashMap<String, Boolean> stopWords = new HashMap<>();
        String[] words = {"a", "à", "ao", "aos", "aquela", "aquelas", "aquele", "aqueles", "aquilo", "as", "às", "até", "com", "como", "da", "das", "de", "dela", "delas", "dele", "deles", "depois", "do", "dos", "e", "é", "ela", "elas", "ele", "eles", "em", "entre", "era", "eram", "éramos", "essa", "essas", "esse", "esses", "esta", "está", "estamos", "estão", "estar", "estas", "estava", "estavam", "estávamos", "este", "esteja", "estejam", "estejamos", "estes", "esteve", "estive", "estivemos", "estiver", "estivera", "estiveram", "estivéramos", "estiverem", "estivermos", "estivesse", "estivessem", "estivéssemos", "estou", "eu", "foi", "fomos", "for", "fora", "foram", "fôramos", "forem", "formos", "fosse", "fossem", "fôssemos", "fui", "há", "haja", "hajam", "hajamos", "hão", "havemos", "haver", "hei", "houve", "houvemos", "houver", "houvera", "houverá", "houveram", "houvéramos", "houverão", "houverei", "houverem", "houveremos", "houveria", "houveriam", "houveríamos", "houvermos", "houvesse", "houvessem", "houvéssemos", "isso", "isto", "já", "lhe", "lhes", "mais", "mas", "me", "mesmo", "meu", "meus", "minha", "minhas", "muito", "na", "não", "nas", "nem", "no", "nos", "nós", "nossa", "nossas", "nosso", "nossos", "num", "numa", "o", "os", "ou", "para", "pela", "pelas", "pelo", "pelos", "por", "qual", "quando", "que", "quem", "são", "se", "seja", "sejam", "sejamos", "sem", "ser", "será", "serão", "serei", "seremos", "seria", "seriam", "seríamos", "seu", "seus", "só", "somos", "sou", "sua", "suas", "também", "te", "tem", "tém", "temos", "tenha", "tenham", "tenhamos", "tenho", "terá", "terão", "terei", "teremos", "teria", "teriam", "teríamos", "teu", "teus", "teve", "tinha", "tinham", "tínhamos", "tive", "tivemos", "tiver", "tivera", "tiveram", "tivéramos", "tiverem", "tivermos", "tivesse", "tivessem", "tivéssemos", "tu", "tua", "tuas", "um", "uma", "você", "vocês", "vos"};
       
        for (String word : words) {
            stopWords.put(word, true);
        }

        return stopWords;
    }
}
