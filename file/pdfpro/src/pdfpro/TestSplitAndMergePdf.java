package pdfpro;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created on 2017/8/23
 * Author: youxingyang.
 */
public class TestSplitAndMergePdf {

    /**
     * 复制pdf文档
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param ranges   复制规则     "1-7"表示复制1到7页、"8-"表示复制从第八页之后到文档末尾
     */
    public static void copyPdf(String sourceFile ,String targetFile, String ranges)throws Exception{
        PdfReader pdfReader = new PdfReader(sourceFile);
        PdfStamper pdfStamper = new PdfStamper(pdfReader , new FileOutputStream(targetFile));
        pdfReader.selectPages(ranges);
        pdfStamper.close();
    }

    /**
     * 多个PDF合并功能
     * @param files     多个PDF的路径
     * @param savePath  生成的新PDF绝对路径
     */
    public static void mergePdfFiles(String[] files, String savePath)  {
        if (files.length > 0) {
            try {
                Document document = new Document(new PdfReader(files[0]).getPageSize(1));
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(savePath));
                document.open();
                for (String file : files) {
                    PdfReader reader = new PdfReader(file);
                    int n = reader.getNumberOfPages();
                    for (int j = 1; j <= n; j++) {
                        document.newPage();
                        PdfImportedPage page = copy.getImportedPage(reader, j);
                        copy.addPage(page);
                    }
                }
                document.close();
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }


    
    
    
    
    
	public static void manipulatePdf(String src, String dest, int pow) throws IOException, DocumentException {
		// reader for the src file
		PdfReader reader = new PdfReader(src);
		// initializations
		Rectangle pageSize = reader.getPageSize(1);
		Rectangle newSize = (pow % 2) == 0 ? new Rectangle(pageSize.getWidth(), pageSize.getHeight())
				: new Rectangle(pageSize.getHeight(), pageSize.getWidth());
		Rectangle unitSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
		for (int i = 0; i < pow; i++) {
			unitSize = new Rectangle(unitSize.getHeight() / 2, unitSize.getWidth());
		}
		int n = (int) Math.pow(2, pow);
		int r = (int) Math.pow(2, pow / 2);
		int c = n / r;
		// step 1
		Document document = new Document(newSize, 0, 0, 0, 0);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(String.format(dest, n)));
		// step 3
		document.open();
		// step 4
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page;
		Rectangle currentSize;
		float offsetX, offsetY, factor;
		int total = reader.getNumberOfPages();
		for (int i = 0; i < total;) {
			if (i % n == 0) {
				document.newPage();
			}
			currentSize = reader.getPageSize(++i);
			factor = Math.min(unitSize.getWidth() / currentSize.getWidth(),
					unitSize.getHeight() / currentSize.getHeight());
			offsetX = unitSize.getWidth() * ((i % n) % c)
					+ (unitSize.getWidth() - (currentSize.getWidth() * factor)) / 2f;
			offsetY = newSize.getHeight() - (unitSize.getHeight() * (((i % n) / c) + 1))
					+ (unitSize.getHeight() - (currentSize.getHeight() * factor)) / 2f;
			page = writer.getImportedPage(reader, i);
			cb.addTemplate(page, factor, 0, 0, factor, offsetX, offsetY);
		}
		// step 5
		document.close();
		reader.close();
	}
    
    
	
	public static void manipulatePdf2(String src, String dest, int pow) throws IOException, DocumentException {
		// reader for the src file
		PdfReader reader = new PdfReader(src);
		// initializations
		Rectangle pageSize = reader.getPageSize(1);
		Rectangle newSize = (pow % 2) == 0 ? new Rectangle(pageSize.getWidth(), pageSize.getHeight())
				: new Rectangle(pageSize.getHeight(), pageSize.getWidth());
		Rectangle unitSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
		for (int i = 0; i < pow; i++) {
			unitSize = new Rectangle(unitSize.getHeight() / 2, unitSize.getWidth());
		}
		int n = (int) Math.pow(2, pow);
		int r = (int) Math.pow(2, pow / 2);
		int c = n / r;
		// step 1
		Document document = new Document(newSize, 0, 0, 0, 0);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(String.format(dest, n)));
		// step 3
		document.open();
		// step 4
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page;
		Rectangle currentSize;
		float offsetX, offsetY, factor;
		int total = reader.getNumberOfPages();
		for (int i = 0; i < total;) {
			if (i % n == 0) {
				document.newPage();
			}
			currentSize = reader.getPageSize(++i);
			factor = Math.min(unitSize.getWidth() / currentSize.getWidth(),
					unitSize.getHeight() / currentSize.getHeight());
			offsetX = unitSize.getWidth() * ((i % n) % c)
					+ (unitSize.getWidth() - (currentSize.getWidth() * factor)) / 2f;
			offsetY = newSize.getHeight() - (unitSize.getHeight() * (((i % n) / c) + 1))
					+ (unitSize.getHeight() - (currentSize.getHeight() * factor)) / 2f;
			page = writer.getImportedPage(reader, i);
			cb.addTemplate(page, factor, 0, 0, factor, offsetX, offsetY);
			/*
			 * 1
			 * 0.70709807        420.96518     0.0
			 * 0.70709807        0.0051879883     0.0
			 * 0.70709807        420.96518     0.0
			 * 
			 * 3
			 * 0.35354903        210.48259     297.66
			 * 0.35354903        420.9626     297.66
			 * 0.35354903        631.4426     297.66
			 */
			//cb.addTemplate(page, 1, 0, 0, 1, 210, 631);
			//cb.addTemplate(page, factor, 0, 0, factor, offsetX, 250);
		}
		// step 5
		document.close();
		reader.close();
	}
    
    
    public static void main(String[] args) throws Exception {

        String source1 = "D:/javaworkspace/pdf/pdfpro/tempalate/1.pdf";
        String source2 = "D:/javaworkspace/pdf/pdfpro/tempalate/2.pdf";
        String source3 = "D:/javaworkspace/pdf/pdfpro/tempalate/3.pdf";
        String des1 = "D:/javaworkspace/pdf/pdfpro/tempalate/added.pdf";
        String des2 = "D:/javaworkspace/pdf/pdfpro/tempalate/added1.pdf";

       // copyPdf(sourceFile, targetFile, "1-10");
       // copyPdf(sourceFile, targetFile1, "11-");

        String[] files = {source1, source2, source3};
        //mergePdfFiles(files, "D:/javaworkspace/pdf/pdfpro/tempalate/added.pdf");
        
        manipulatePdf2(des1, des2, 1);

    }
    
    
    
    
    
    
    
    
    
}