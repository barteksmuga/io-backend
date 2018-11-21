package io.controllers;

import io.IoApplication;
import io.enums.ConvertTypes;
import io.helpers.Converter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/converter")
@RestController
public class ConvertController {

    @RequestMapping(value = "/txt-tex", method = RequestMethod.GET)
    public String convertTxtToTex (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.tex";
        int conversionResult = converter.run(ConvertTypes.TXT_TO_TEX, inputFileName, outputFileName);
        return conversionResult == 0 ? "TXT => TEX Success" : "Error occured! Conversion failed";
    }

    @RequestMapping(value = "/txt-pdf", method = RequestMethod.GET)
    public String convertTxtToPdf (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.pdf";
        int conversionResult = converter.run(ConvertTypes.TXT_TO_PDF, inputFileName, outputFileName);
        return conversionResult == 0 ? "TXT => PDF Success" : "Error occured! Conversion failed";
    }

    @RequestMapping(value = "/tex-pdf", method = RequestMethod.GET)
    public String convertTexToPdf (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.pdf";
        int conversionResult = converter.run(ConvertTypes.TEX_TO_PDF, inputFileName, outputFileName);
        return conversionResult == 0 ? "TEX => PDF Success" : "Error occured! Conversion failed";
    }

    @RequestMapping(value = "/tex-txt", method = RequestMethod.GET)
    public String convertTexToTxt (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.txt";
        int conversionResult = converter.run(ConvertTypes.TEX_TO_TXT, inputFileName, outputFileName);
        return conversionResult == 0 ? "TEX => TXT Success" : "Error occured! Conversion failed";
    }
}
