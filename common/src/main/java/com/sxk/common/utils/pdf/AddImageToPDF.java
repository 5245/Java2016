package com.sxk.common.utils.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class AddImageToPDF {

    public void createPDFFromImage(String inputFile, String imagePath, String outputFile) throws IOException {
        try (PDDocument doc = PDDocument.load(new File(inputFile))) {
            //we will add the image to the first page.
            PDPage page = doc.getPage(0);

            // createFromFile is the easiest way with an image file
            // if you already have the image in a BufferedImage, 
            // call LosslessFactory.createFromImage() instead
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true)) {
                // contentStream.drawImage(ximage, 20, 20 );
                // better method inspired by http://stackoverflow.com/a/22318681/535646
                // reduce this value if the image is too large
                float scale = 1f;
                contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            }
            doc.save(outputFile);
        }
    }

    /**
     * This will load a PDF document and add a single image on it.
     * <br>
     * see usage() for commandline
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException {
        AddImageToPDF app = new AddImageToPDF();
        String file = "D:\\a_back\\10.pdf";
        String savePath = "D:\\a_back\\10_10.pdf";
        String imagePath = "D:\\a_back\\1.jpg";
        app.createPDFFromImage(file, imagePath, savePath);
    }

}
