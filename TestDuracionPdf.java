import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class TestDuracionPdf {

    private static double averageTime=0;
    private static int numberOfCases=0;

    private static int totalTime=0;

    public static void main(String[] args) {
        showAverageTimeBetweenPDFs();
    }




    public static void showAverageTimeBetweenPDFs(){

        try{
            File mainDirectory= new File("src/main/java/pdfs");
            processDirectory(mainDirectory);
            averageTime=(double)(totalTime/numberOfCases)/60;
            System.out.printf("el promedio fue de %s  minutos",averageTime);


        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void processDirectory(File directory){
        File[] bsps= directory.listFiles();
        for(File bsp : bsps){
            if(bsp.isDirectory()){
                processDirectory(bsp);
            }else{
                processPDF(bsp);
            }
        }
    }

    public static void processPDF(File pdfFile){
        try{
            numberOfCases+=1;
            PDDocument doc = PDDocument.load(pdfFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);
            String texto = pdfStripper.getText(doc);
            Pattern patron = Pattern.compile("\\b(\\d+) min y (\\d+) seg\\b");
            Matcher matcher = patron.matcher(texto);

            int minutos=0, segundos=0;
            while (matcher.find()) {
                minutos = Integer.parseInt(matcher.group(1));
                segundos = Integer.parseInt(matcher.group(2));
                System.out.println("Minutos: " + minutos + ", Segundos: " + segundos);

            }

            totalTime+=minutos*60+segundos;
            System.out.println("tiempo total en segundos : "+totalTime);
            doc.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }



}
