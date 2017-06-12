package gov.nih.nci.evs.excel508;

import java.io.File;
import java.io.FileFilter;

public class Excel508 {
	//pass in site to look for Excel files Example:ftp://ftp1.nci.nih.gov/pub/cacore/EVS/FDA/CDRH/Archive/
	//find any Excel files and download.
	//Check Excel files and update properties and any other elements
	//Upload Excel files back to the specified site, this time using username and password
	//http://www.hhs.gov/web/policies/webstandards/508excelpresentations.html
	//Excel workbooks must observe the following:

//    Workbook properties for title, subject, keywords author, and language must be included;
//    All images in the worksheets with informational value must include alternative text;
//    Decorative images must not have alternative text added;
//    Each worksheet must have an appropriate name in the tab at the bottom on the workbook;
//    Column and row headings must be labeled;
//    Charts/graphs must include axis information.
//
//Workbooks posted as .XLS only must include a link to the Excel viewer. See the related standard: Provide File Type and Size with Downloadable Files.

//	Document Tables
//	Guidelines
//	3.1.
//	All tables should have a logical layout of the information based on rows and columns. In addition, the tables should be
//	oriented so that they are read from left to right and top to bottom.
//	
//	3.2.
//	All tables should have clear, concise and readily identifiable column headers.
//	3.3.
//	All tables should have clear, concise and readily identifiable row headers.
//	3.4.
//	The data are of the table should be absent of merged cells. Merged cells are only acceptable in the header row of the table.
//	ptable in the header row of the table.
//	
//	3.5.
//	Row/column headers should start in the first left hand column of the table.
//	-

//	4. 0
//	Additional Requirements
//	Guidelines
//	A.
//	The document file name should be concise, generally limited to 20 - 30 characters, but make the contents
//	of the file clear in the context in which it is presented.
//	

//	B.
//	Do not save an Excel Spreadsheet to PDF. The only way to effectively convert an Excel spreadsheet to PDF is to
//	Copy and Paste the spreadsheet into MS Word using landscape page setup and then convert it to a PDF from the
//	Word format. Then use the PAW plug in to convert.

//	C.
//	A separate text only version of the document should be provided when there is no other way to make the content
//	accessible. Organizational charts, complex graphics, flowcharts, etc. are examples of documents that will require a text
//	equivalent.
//	
//	D.
//	The document must utilize the recommended fonts i.e. Times New Roman, Verdana, Arial, Tahoma
//	, Calibri and Helvetica.
//	
//	
//	E.
//	The Document Properties (i.e. Subject, Author, Title, Company, Keywords,and Language) must be properly filled out.
//	
//	Note: For Author do not use individuals name or contractor name. Should use government organization name (i.e.,
//	Section 508 VA).
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//Alrighty.  So the ftp program runs into terminal time out errors.
//		String host = null;
//		String username = null;
//		String password = null;
//		
//		
//		if(args.length==1){
//			host = args[0];
//			FTP_layer.listFiles(host);
//		}
//
//		if(args.length==3){
//			host = args[0];
//			username = args[1];
//			password = args[2];
//			FTP_layer.listFiles(host, username, password);
//		}
		
		
		//Start processing the Excel files
		//pass in a directory location
		//the app should look in that directory and find any Excel files
		//It should then modify those Excel files to add the 508 components
		
		String dir = null;
		try{
			dir = args[0];
			File directory = new File(dir);
			File[] files = directory.listFiles(new ExcelFilter());
			for (int index = 0; index < files.length; index++){
				System.out.println(files[index].toString());
				Excel_Processing.readExcel(dir,files[index]);
				Excel_Processing.updateExcelProperties(dir, files[index]);
			}
			
		}
		catch (Exception e){
			System.out.println("Please provide directory location of Excel files");
		}
	}
	

	

}

class ExcelFilter implements FileFilter{

	public boolean accept(File file) {

		return file.getName().endsWith("xls");
	}

}
