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

    private HashMap<Character, Boolean> symbolsAvailable(){
        HashMap<Character, Boolean> symbols = new HashMap<>();
        symbols.put('!', true);
        symbols.put('"', true);
        symbols.put('#', true);
        symbols.put('$', true);
        symbols.put('%', true);
        symbols.put('&', true);
        symbols.put('\'', true);
        symbols.put('(', true);
        symbols.put(')', true);
        symbols.put('*', true);
        symbols.put('+', true);
        symbols.put(',', true);
        symbols.put('-', true);
        symbols.put('.', true);
        symbols.put('/', true);
        symbols.put('0', true);
        symbols.put('1', true);
        symbols.put('2', true);
        symbols.put('3', true);
        symbols.put('4', true);
        symbols.put('5', true);
        symbols.put('6', true);
        symbols.put('7', true);
        symbols.put('8', true);
        symbols.put('9', true);
        symbols.put(':', true);
        symbols.put(';', true);
        symbols.put('<', true);
        symbols.put('=', true);
        symbols.put('>', true);
        symbols.put('?', true);
        symbols.put('@', true);
        symbols.put('A', true);
        symbols.put('B', true);
        symbols.put('C', true);
        symbols.put('D', true);
        symbols.put('E', true);
        symbols.put('F', true);
        symbols.put('G', true);
        symbols.put('H', true);
        symbols.put('I', true);
        symbols.put('J', true);
        symbols.put('K', true);
        symbols.put('L', true);
        symbols.put('M', true);
        symbols.put('N', true);
        symbols.put('O', true);
        symbols.put('P', true);
        symbols.put('Q', true);
        symbols.put('R', true);
        symbols.put('S', true);
        symbols.put('T', true);
        symbols.put('U', true);
        symbols.put('V', true);
        symbols.put('W', true);
        symbols.put('X', true);
        symbols.put('Y', true);
        symbols.put('Z', true);
        symbols.put('[', true);
        symbols.put('\\', true);
        symbols.put(']', true);
        symbols.put('^', true);
        symbols.put('_', true);
        symbols.put('`', true);
        symbols.put('´', true);
        symbols.put('a', true);
        symbols.put('b', true);
        symbols.put('c', true);
        symbols.put('d', true);
        symbols.put('e', true);
        symbols.put('f', true);
        symbols.put('g', true);
        symbols.put('h', true);
        symbols.put('i', true);
        symbols.put('j', true);
        symbols.put('k', true);
        symbols.put('l', true);
        symbols.put('m', true);
        symbols.put('n', true);
        symbols.put('o', true);
        symbols.put('p', true);
        symbols.put('q', true);
        symbols.put('r', true);
        symbols.put('s', true);
        symbols.put('t', true);
        symbols.put('u', true);
        symbols.put('v', true);
        symbols.put('w', true);
        symbols.put('x', true);
        symbols.put('y', true);
        symbols.put('z', true);
        symbols.put('{', true);
        symbols.put('|', true);
        symbols.put('}', true);
        symbols.put('~', true);
        symbols.put('À', true);
        symbols.put('Á', true);
        symbols.put('Â', true);
        symbols.put('Ã', true);
        symbols.put('Ç', true);
        symbols.put('É', true);
        symbols.put('Ê', true);
        symbols.put('Í', true);
        symbols.put('Ó', true);
        symbols.put('Ô', true);
        symbols.put('Õ', true);
        symbols.put('Ú', true);
        symbols.put('à', true);
        symbols.put('á', true);
        symbols.put('â', true);
        symbols.put('ã', true);
        symbols.put('ç', true);
        symbols.put('é', true);
        symbols.put('ê', true);
        symbols.put('í', true);
        symbols.put('ó', true);
        symbols.put('ô', true);
        symbols.put('õ', true);
        symbols.put('ú', true);
        symbols.put('ª', true);
        symbols.put('º', true);
        symbols.put('°', true);
        symbols.put('¨', true);
        symbols.put('§', true);


        return symbols;

    }
}
