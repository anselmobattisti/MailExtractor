
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailExtractor {

    private static LinkedList<String> emailList = new LinkedList<>();
    
    // pasta onde estão os arquivos
    private static final String PATH = "c:\\emails\\";
    
    // arquivo resultante
    private static final String FILE_NAME = "resultado.txt";
    
    public static void main(String[] args) {
        scanDirectory();
    }

    public static void scanDirectory() {
        File actual = new File(PATH);
        for (File f : actual.listFiles()) {
            System.out.println("Processando :" + f.getAbsolutePath());
            readFile(f.getAbsolutePath());
        }
        writeToFile();
    }

    public static void writeToFile() {
        try {
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter(PATH+FILE_NAME));
            for (String s : emailList) {
                out.write(s.toLowerCase() + "\n");
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo "+e.getMessage());
        }
    }

    public static void getEmail(String line) {
        final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
        Pattern p = Pattern.compile(RE_MAIL);
        Matcher m = p.matcher(line);
        while (m.find()) {
            String tmp = m.group(1);
            if (!emailList.contains(tmp)) {
                emailList.add(tmp);
            }
        }
    }

    public static void readFile(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String strLine;
            while ((strLine = in.readLine()) != null) {
                getEmail(strLine);
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}