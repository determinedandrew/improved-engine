package com.yihaomen.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {

	public static void main(String[] args) {
		creatPDF();
	}

	public static void creatPDF() {
		try {
			for(int i=0;i<10;i++){			
				OutputStream file = new FileOutputStream(new File("D:\\pdf\\" + i + ".pdf"));
				Document document = new Document();
				PdfWriter.getInstance(document, file);
				document.open();
				document.add(new Paragraph("Hello, the " + i + "st PDF Document"));
				document.add(new Paragraph("Hello, the " + i + "st PDF Document"));
				document.add(new Paragraph(new Date().toString()));
				document.close();
				file.close();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}