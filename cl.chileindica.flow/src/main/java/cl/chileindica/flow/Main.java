package cl.chileindica.flow;

import java.io.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import weka.core.converters.ArffSaver;

public class Main {

	public static void main(String args[]) throws Exception {
		
		go("/home/mnunez/Documentos/sgdoc/analisis/RutaSeguidaPorDocumento_05-10-2015.xls");
		go("/home/mnunez/Documentos/sgdoc/analisis/RutaSeguidaPorDocumento_05-10-2015 (1).xls");
		go("/home/mnunez/Documentos/sgdoc/analisis/RutaSeguidaPorDocumento_05-10-2015 (2).xls");
		go("/home/mnunez/Documentos/sgdoc/analisis/RutaSeguidaPorDocumento_05-10-2015 (3).xls");
		
		
		Instances filtered=null;
		
		
		ArffSaver save = new ArffSaver();
		
		save.setInstances(filtered);
		save.setFile(new File("output.arff"));
		save.writeBatch();
		
	}
	
	
	private static void process(Row row){
		 FastVector attributes = new FastVector(16);
		
		
		Attribute expediente = new Attribute("expediente");
		Attribute documento = new Attribute("documento");
		Attribute tipo = new Attribute("tipo");
		Attribute materia = new Attribute("materia");
		Attribute emisor = new Attribute("emisor");
		Attribute fecha = new Attribute("fecha");
		Attribute formato = new Attribute("formato");
		Attribute enviadoPor = new Attribute("enviadoPor");
		Attribute unidad = new Attribute("unidad");
		Attribute cargo= new Attribute("cargo");
		Attribute usuario = new Attribute("usuario");
		Attribute ingreso = new Attribute("ingreso");
		Attribute acuseRecibo = new Attribute("acuseRecibo");
		Attribute fechaDespacho = new Attribute("fechaDespacho");
		Attribute dias = new Attribute("dias");
		Attribute fechaArchivado= new Attribute("fechaArchivado");
		
		attributes.addElement(expediente);
		attributes.addElement(documento);
		attributes.addElement(tipo);
		attributes.addElement(materia);
		attributes.addElement(emisor);
		attributes.addElement(fecha);
		attributes.addElement(formato);
		attributes.addElement(enviadoPor);
		attributes.addElement(unidad);
		attributes.addElement(cargo);
		attributes.addElement(usuario);
		attributes.addElement(ingreso);
		attributes.addElement(acuseRecibo);
		attributes.addElement(fechaDespacho);
		attributes.addElement(dias);
		attributes.addElement(fechaArchivado);
		
		Instances set=new Instances("Rel",attributes,10);
		
		Instance instance=new Instance(16);
		instance.setValue(expediente, row.getCell(0).getStringCellValue());
		instance.setValue(documento, row.getCell(1).getStringCellValue());
		instance.setValue(tipo, row.getCell(2).getStringCellValue());
		instance.setValue(materia, row.getCell(3).getStringCellValue());
		instance.setValue(emisor, row.getCell(4).getStringCellValue());
		instance.setValue(fecha, row.getCell(5).getStringCellValue());
		instance.setValue(formato, row.getCell(6).getStringCellValue());
		instance.setValue(enviadoPor, row.getCell(7).getStringCellValue());
		instance.setValue(unidad, row.getCell(8).getStringCellValue());
		instance.setValue(cargo, row.getCell(9).getStringCellValue());
		instance.setValue(usuario, row.getCell(10).getStringCellValue());
		instance.setValue(ingreso, row.getCell(11).getStringCellValue());
		instance.setValue(acuseRecibo, row.getCell(12).getStringCellValue());
		instance.setValue(fechaDespacho, row.getCell(13).getStringCellValue());
		instance.setValue(dias, row.getCell(14).getStringCellValue());
		instance.setValue(fechaArchivado, row.getCell(15).getStringCellValue());
		
		set.add(instance);
		
		
		System.out.print(row.getCell(0).getStringCellValue() + "\t");// Expediente
		System.out.print(row.getCell(1).getStringCellValue() + "\t");// Documento
		System.out.print(row.getCell(2).getStringCellValue() + "\t");// Tipo
																		// Documento
		System.out.print(row.getCell(3).getStringCellValue() + "\t");// Materia
		System.out.print(row.getCell(4).getStringCellValue() + "\t");// Emisor
		System.out.print(row.getCell(5).getStringCellValue() + "\t");// Fecha
		System.out.print(row.getCell(6).getStringCellValue() + "\t");// Formato
		System.out.print(row.getCell(7).getStringCellValue() + "\t");// Enviado
																		// Por
		System.out.print(row.getCell(8).getStringCellValue() + "\t");// Unidad
																		// Organizacional
		System.out.print(row.getCell(9).getStringCellValue() + "\t");// Cargo
		System.out.print(row.getCell(10).getStringCellValue() + "\t");// Usuario

		System.out.print(row.getCell(11).getStringCellValue() + "\t");// Fecha
																		// Ingreso
																		// Bandeja
		System.out.print(row.getCell(12).getStringCellValue() + "\t");// Fecha
																		// Acuse
																		// Recibo
		System.out.print(row.getCell(13).getStringCellValue() + "\t");// Fecha
																		// Despacho
		System.out.print(row.getCell(14).getStringCellValue() + "\t");// Dias
		System.out.print(row.getCell(15).getStringCellValue() + "\t");// Fecha Archivado

		System.out.println();
	}
	
	public static void go(String f) throws Exception {
		
		
		
		FileInputStream file = new FileInputStream(new File(f));

		// Get the workbook instance for XLS file
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		int s = workbook.getNumberOfSheets();

		for (int i = 0; i < s; i++) {

			HSSFSheet sheet = workbook.getSheetAt(i);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();
				if(row.getRowNum()==0) continue;
				process(row);


			}
		}
		
		workbook.close();

	}

}

