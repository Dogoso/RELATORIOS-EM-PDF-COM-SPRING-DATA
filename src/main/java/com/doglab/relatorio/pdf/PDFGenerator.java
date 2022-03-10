package com.doglab.relatorio.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.doglab.relatorio.dao.ProdutoDAO;
import com.doglab.relatorio.orm.Produto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFGenerator {
	
	private final Integer COLUMNS_AMOUNT;
	private final ProdutoDAO PRODUTODAO;
	private List<Produto> produtos = new ArrayList<>();
	
	public PDFGenerator(ProdutoDAO produtoDao)
	{
		this.PRODUTODAO = produtoDao;
		this.COLUMNS_AMOUNT = produtoDao.countColumns();
	}
	
	public void Generate(String path, String text) 
			throws FileNotFoundException, DocumentException
	{
		Document document = new Document();
		
		PdfWriter.getInstance(document, new FileOutputStream(path+".pdf"));
		
		document.open();

		PRODUTODAO.listOutOfStock().forEach(produtos::add);
		PdfPTable table = new PdfPTable(COLUMNS_AMOUNT);
	    table.setSpacingBefore(20);
		
		try {
			addTableHeader(table);
			addRows(table);
			document.add(getTitle(text));
			document.add(getWarning("Produtos fora de estoque"));
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();
	}

	private Paragraph getTitle(String text)
	{
		Font font = new Font();
		font.setSize(30);
		font.setStyle(Font.BOLD);
		font.setColor(BaseColor.BLACK);
		
		Paragraph title = new Paragraph(text, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		return title;
	}
	
	private Paragraph getWarning(String text)
	{
		Font font = new Font();
		font.setColor(BaseColor.RED);
		
		Paragraph warn = new Paragraph("("+text+")", font);
		warn.setAlignment(Paragraph.ALIGN_CENTER);
		
		return warn;
	}
	
	private void addTableHeader(PdfPTable table) {
		Font font = new Font();
		font.setColor(BaseColor.WHITE);
	    Stream.of("ID", "Nome", "Descrição", "Preço", "Quantidade")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table) 
	{
	    this.produtos.forEach(product -> {
	    	table.addCell(product.getId().toString());
	    	table.addCell(product.getDescription());
	    	table.addCell(product.getName());
	    	table.addCell(product.getValue().toString());
	    	table.addCell(product.getAmount().toString());
	    });
	}
	
}
