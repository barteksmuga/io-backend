package io.controllers;

import io.IoApplication;
import io.enums.ConvertTypes;
import io.exceptions.models.ConversionFailedException;
import io.helpers.Converter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/converter")
@RestController
public class ConvertController {
    private final static Logger logger = Logger.getLogger(ConvertController.class);

    @RequestMapping(value = "/txt-tex", method = RequestMethod.GET)
    public ResponseEntity convertTxtToTex (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException, ConversionFailedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.tex";
        int conversionResult = converter.run(ConvertTypes.TXT_TO_TEX, inputFileName, outputFileName);
        if (conversionResult != 0) {
            logger.error("File conversion failed.");
            throw new ConversionFailedException("File conversion failed.");
        }
        logger.info("GET convert text to tex: input file name: ["+inputFileName+"]");
        return ResponseEntity.status(HttpStatus.OK).body(outputFileName);
    }

    @RequestMapping(value = "/txt-pdf", method = RequestMethod.GET)
    public ResponseEntity convertTxtToPdf (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException, ConversionFailedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.pdf";
        int conversionResult = converter.run(ConvertTypes.TXT_TO_PDF, inputFileName, outputFileName);
        if (conversionResult != 0) {
            logger.error("File conversion failed.");
            throw new ConversionFailedException("File conversion failed.");
        }
        logger.info("GET convert text to pdf: input file name: ["+inputFileName+"]");
        return ResponseEntity.status(HttpStatus.OK).body(outputFileName);
    }

    @RequestMapping(value = "/tex-pdf", method = RequestMethod.GET)
    public ResponseEntity convertTexToPdf (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException, ConversionFailedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.pdf";
        int conversionResult = converter.run(ConvertTypes.TEX_TO_PDF, inputFileName, outputFileName);
        if (conversionResult != 0) {
            logger.error("File conversion failed.");
            throw new ConversionFailedException("File conversion failed.");
        }
        logger.info("GET convert tex to pdf: input file name: ["+inputFileName+"]");
        return ResponseEntity.status(HttpStatus.OK).body(outputFileName);
    }

    @RequestMapping(value = "/tex-txt", method = RequestMethod.GET)
    public ResponseEntity convertTexToTxt (@RequestParam("inputFileName") String inputFileName) throws IOException, InterruptedException, ConversionFailedException {
        Converter converter = new Converter(IoApplication.sourcePath);
        String outputFileName = inputFileName.split("\\.")[0] + "out.txt";
        int conversionResult = converter.run(ConvertTypes.TEX_TO_TXT, inputFileName, outputFileName);
        if (conversionResult != 0) {
            logger.error("File conversion failed.");
            throw new ConversionFailedException("File conversion failed.");
        }
        logger.info("GET convert tex to text: input file name: ["+inputFileName+"]");
        return ResponseEntity.status(HttpStatus.OK).body(outputFileName);
    }
}
