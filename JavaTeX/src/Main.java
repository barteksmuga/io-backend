
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "C:/Users/Nyxgen/Downloads/JavaTeX/JavaTeX/src/Data/";
        String txtToTex = "pandoc " + path + "MediaWikiExample.txt" + " " + "-f mediawiki -t latex --standalone -o " + path + "MediaWikiExample.tex";
        String txtToPdf = "pandoc -s " + path + "file.txt -o " + path + "txt.pdf";
        String texToPdf = "pandoc " + path + "output1.tex" + " " + "--pdf-engine=xelatex -o " + path + "output1.pdf";
        String texToText = "pandoc -s " + path + "output1.tex -o " + path + "output1.text";
        String texToTxt = "pandoc -s " + path + "output1.tex -o " + path + "output1.txt";
        String textToHtml = "pandoc " + path + "output1.tex -s -o " + path + "output1.html";



        System.out.println("Converting .txt -> .tex");
        Process pr1 = Runtime.getRuntime().exec(txtToTex);
        processIndicator(pr1);
        processEndCode(pr1.waitFor());

        System.out.println("Converting .tex -> .pdf");
        Process pr2  = Runtime.getRuntime().exec(texToPdf);
        processIndicator(pr2);
        processEndCode(pr2.waitFor());

        System.out.println("Converting .txt -> .pdf ");
        Process pr3 = Runtime.getRuntime().exec(txtToPdf);
        processIndicator(pr3);
        processEndCode(pr3.waitFor());

        System.out.println("Converting .tex -> .text ");
        Process pr4 = Runtime.getRuntime().exec(texToText);
        processIndicator(pr4);
        processEndCode(pr4.waitFor());

        System.out.println("Converting .text -> .html ");
        Process pr5 = Runtime.getRuntime().exec(textToHtml);
        processIndicator(pr5);
        processEndCode(pr5.waitFor());

        System.out.println("Converting .text -> .html ");
        Process pr6 = Runtime.getRuntime().exec(texToTxt);
        processIndicator(pr6);
        processEndCode(pr6.waitFor());
    }

    public static void processIndicator(Process p)
    {
        for(int i = 0; p.isAlive(); ++i)
        {
            if(i/10==0)
            {
                System.out.print("\\ \r");
            }
            if(i/10==1)
            {
                System.out.print("| \r");
            }
            if(i/10==2)
            {
                System.out.print("/ \r");
            }
            if(i/10==3)
            {
                System.out.print("- \r");
            }
            if(i==40) i = 0;

        }
    }

    public static void processEndCode(int p)
    {
        if(p == 0)
        {
            System.out.println("Conversion executed successfully!");
        }
        else System.out.println("Conversion failed! Error code: " + p);
    }
}

