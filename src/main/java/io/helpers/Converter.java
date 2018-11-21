package io.helpers;

import io.enums.ConvertTypes;

import java.io.IOException;
import java.util.Objects;

public class Converter {

    public Converter (String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int run (ConvertTypes convertType, String inputFileName, String outputFileName) throws IOException, InterruptedException {
        switch (convertType) {
            case TXT_TO_TEX:
                return convertTxtToTex(inputFileName, outputFileName);
            case TXT_TO_PDF:
                return convertTxtToPdf(inputFileName, outputFileName);
            case TEX_TO_PDF:
                return convertTexToPdf(inputFileName, outputFileName);
            case TEX_TO_TXT:
                return convertTexToTxt(inputFileName, outputFileName);
            case TEX_TO_TEXT:
                return convertTexToText(inputFileName, outputFileName);
            case TEXT_TO_HTML:
                return convertTextToHtml(inputFileName, outputFileName);
        }
        return -1;
    }

    private int convertTxtToTex (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc " + sourcePath + inputFileName + " -f mediawiki -t latex --standalone -o" + sourcePath + outputFileName;
        System.out.println("Converting .txt -> .tex");
        Process pr1 = Runtime.getRuntime().exec(command);
        processIndicator(pr1);
        processEndCode(pr1.waitFor());
        return pr1.waitFor();
    }

    private int convertTxtToPdf (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc -s " + sourcePath + inputFileName + " -o " + sourcePath + outputFileName;
        System.out.println("Converting .tex -> .pdf");
        Process pr2 = Runtime.getRuntime().exec(command);
        processIndicator(pr2);
        processEndCode(pr2.waitFor());
        return pr2.waitFor();
    }

    private int convertTexToPdf (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc " + sourcePath + inputFileName + " --pdf-engine=xelatex -o" + sourcePath + outputFileName;
        System.out.println("Converting .txt -> .pdf ");
        Process pr3 = Runtime.getRuntime().exec(command);
        processIndicator(pr3);
        processEndCode(pr3.waitFor());
        return pr3.waitFor();
    }

    private int convertTexToTxt (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc -s" + sourcePath + inputFileName + " -o " + sourcePath + outputFileName;
        System.out.println("Converting .tex -> .text ");
        Process pr4 = Runtime.getRuntime().exec(command);
        processIndicator(pr4);
        processEndCode(pr4.waitFor());
        return pr4.waitFor();
    }

    private int convertTexToText (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc -s " + sourcePath + inputFileName + " -o " + sourcePath + outputFileName;
        System.out.println("Converting .text -> .html ");
        Process pr5 = Runtime.getRuntime().exec(command);
        processIndicator(pr5);
        processEndCode(pr5.waitFor());
        return pr5.waitFor();
    }

    private int convertTextToHtml (String inputFileName, String outputFileName) throws IOException, InterruptedException {
        String command = "pandoc " + sourcePath + inputFileName + " -s -o " + sourcePath + outputFileName;
        System.out.println("Converting .text -> .html ");
        Process pr6 = Runtime.getRuntime().exec(command);
        processIndicator(pr6);
        processEndCode(pr6.waitFor());
        return pr6.waitFor();
    }

    private void processIndicator (Process p) {
        for (int i = 0; p.isAlive(); ++i) {
            if (i / 10 == 0) {
                System.out.print("\\ \r");
            }
            if (i / 10 == 1) {
                System.out.print("| \r");
            }
            if (i / 10 == 2) {
                System.out.print("/ \r");
            }
            if (i / 10 == 3) {
                System.out.print("- \r");
            }
            if (i == 40) {
                i = 0;
            }
        }
    }

    private void processEndCode (int p) {
        if (p == 0) {
            System.out.println("Conversion executed successfully!");
        } else {
            System.out.println("Conversion failed! Error code: " + p);
        }
    }

    public String getSourcePath () {
        return sourcePath;
    }

    public void setSourcePath (String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public ConvertTypes getConvertType () {
        return convertType;
    }

    public void setConvertType (ConvertTypes convertType) {
        this.convertType = convertType;
    }

    public String getOutputFileName () {
        return outputFileName;
    }

    public void setOutputFileName (String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getInputFileName () {
        return inputFileName;
    }

    public void setInputFileName (String inputFileName) {
        this.inputFileName = inputFileName;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Converter)) {
            return false;
        }
        Converter converter = (Converter) o;
        return Objects.equals(sourcePath, converter.sourcePath) &&
                convertType == converter.convertType &&
                Objects.equals(outputFileName, converter.outputFileName) &&
                Objects.equals(inputFileName, converter.inputFileName);
    }

    @Override
    public int hashCode () {
        return Objects.hash(sourcePath, convertType, outputFileName, inputFileName);
    }

    private String sourcePath;
    private ConvertTypes convertType;
    private String outputFileName;
    private String inputFileName;
}
