
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Running ver 1 (wiki-script -> .tex) . . .");
        String path = "C:/Users/Nyxgen/Downloads/JavaTeX/JavaTeX/src/Data/";
        String fileOutput = "output1.tex";
        String fileScript = "MediaWikiExample.txt";
        String command = "pandoc " + path + fileScript + " " + "-f mediawiki -t latex --standalone -o " + path + fileOutput;

        int process = Runtime.getRuntime().exec(command).waitFor();
        if(process == 0)
        {
            System.out.println("Conversion executed successfully!");
        }
        else System.out.println("Conversion failed! Error code: " + process);


        System.out.println("Converting .tex -> .pdf . . .");
       // command = "pdflatex -output-directory " + path + " " + path + "output1.tex";
        command = "pandoc " + path + "output1.tex" + " " + "--pdf-engine=xelatex -o " + path + "output1.pdf";
        process = Runtime.getRuntime().exec(command).waitFor();
        if(process == 0)
        {
            System.out.println("Conversion executed successfully!");
        }
        else System.out.println("Conversion failed! Error code: " + process);

        System.out.println("Converting .txt -> .pdf . . .");
        command = "pandoc -s " + path + "file.txt -o " + path + "txt.pdf";

        process = Runtime.getRuntime().exec(command).waitFor();
        if(process == 0)
        {
            System.out.println("Conversion executed successfully!");
        }
        else System.out.println("Conversion failed! Error code: " + process);
    }
}

