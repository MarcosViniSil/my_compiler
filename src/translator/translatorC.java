package translator;

import syntacticTree.*;
import java.io.*;

public class translatorC{
    static final String os = System.getProperty("os.name").toLowerCase();
    static String userHome = System.getProperty("user.home");

    static String query1 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&matchBoolean=true&queryText=(%%22Publication%%20Title%%22:%s)%%20OR%%20(%%22Document%%20Title%%22:%s)";

    static String query2 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&matchBoolean=true&queryText=(%%22All%%20Metadata%%22:%s)%%20OR%%20(%%22Full%%20Text%%20.AND.%%20Metadata%%22:%s)";

    static String query3 =
    "comand";  

    static String query4 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&ranges=%s0101_%s1231_Search%%20Latest%%20Date";

    static String query5 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&matchBoolean=true&queryText=(%%22All%%20Metadata%%22:%s)%%20OR%%20(%%22Index%%20Terms%%22:%s)&ranges=1884_2026_Year";

    static String query6 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&matchBoolean=true&queryText=(%%22Publication%%20Title%%22:%s)%%20OR%%20(%%22Document%%20Title%%22:%s)%%20OR%%20(%%22Full%%20Text%%20Only%%22:%s)&ranges=1884_2026_Year";

    static String query7 =
    "https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&newsearch=true&matchBoolean=true&queryText=(%%22Publication%%20Title%%22:%s)%%20OR%%20(%%22Document%%20Title%%22:%s)%%20OR%%20(%%22Full%%20Text%%20Only%%22:%s)&ranges=1884_2026_Year";

    
    public translatorC(){}

    public static void translate(SyntacticTree sintaticTreee){
        if(sintaticTreee == null){
            return;
        }
        String instruction = getInstruction(getType(sintaticTreee),getValue(sintaticTreee));

        if(instruction.equalsIgnoreCase("comand line")){
            System.out.println("------------- RESPOSTA -------------");
            System.out.println();
            System.out.println("Os documentos que estão na pasta " + getValue(sintaticTreee));
            findDocumentsInsideFolder(getValue(sintaticTreee));
            System.out.println();
            System.out.println("------------- RESPOSTA -------------");
        }else{
            System.out.println("------------- RESPOSTA -------------");
            System.out.println();
            System.out.println("Para buscar basta inserir a url abaixo no navegador");
            System.out.println();
            System.out.println(instruction);
            System.out.println();
            System.out.println("------------- RESPOSTA -------------");
        }

    }

    public static String getType(SyntacticTree sintaticTreee){
        if(sintaticTreee == null){
            return "";
        }
        SyntacticTree instruction = sintaticTreee.children.get(0); 
        return instruction.getSecondWord();
    }

    public static String getValue(SyntacticTree sintaticTreee){
        if(sintaticTreee == null){
            return "";
        }
        SyntacticTree instruction = sintaticTreee.children.get(0); 
        return instruction.getLastLeaf();
    }

    public static boolean isWindows() {
        return (os.contains("win"));
    }

    public static boolean isMac() {
        return (os.contains("mac"));
    }

    public static boolean isUnix() {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }

    public static String getInstruction(String keyWord,String value){
        if(keyWord == null || keyWord == ""){
            return "";
        }
        if(value == null || value == ""){
            return "";
        }

        if(keyWord.equalsIgnoreCase("tem")){
            return String.format(query1, value, value);
        }else if(keyWord.equalsIgnoreCase("possui")){
            return String.format(query2, value, value);
        }else if(keyWord.equalsIgnoreCase("está")){
            return "comand line";
        }else if(keyWord.equalsIgnoreCase("criado")){
            return String.format(query4, value, value);
        }else if(keyWord.equalsIgnoreCase("estão")){
            return String.format(query5, value, value);
        }else if(keyWord.equalsIgnoreCase("iniciam")){
            return String.format(query6, value, value,value);
        }else if(keyWord.equalsIgnoreCase("contém")){
            return String.format(query7, value, value,value);
        }else{
            return value;
        }
    }

    public static void findDocumentsInsideFolder(String folderName){
        if(folderName == null || folderName == ""){
            return;
        }
        folderName = userHome + File.separator + folderName;

        String[] command;

        if (isWindows()) {
            command = new String[] {
                "cmd.exe", "/c",
                "cd \"" + folderName + "\" && dir *.pdf *.doc *.docx *.xls *.xlsx *.txt *.XML *.json /b /s"
            };

        } else{
            command = new String[] {
                "bash", "-c",
                "cd \"" + folderName + "\" && find . -type f \\( -iname \"*.pdf\" -o -iname \"*.doc\" -o -iname \"*.docx\" -o -iname \"*.xls\" -o -iname \"*.xlsx\" -iname \"*.txt\" -iname \"*.XML\" -iname \"*.json\" \\)"
            };

        }

        try {

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}