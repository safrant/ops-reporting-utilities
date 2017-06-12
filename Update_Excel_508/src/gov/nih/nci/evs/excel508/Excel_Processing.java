package gov.nih.nci.evs.excel508;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.MarkUnsupportedException;
import org.apache.poi.hpsf.NoPropertySetStreamException;
import org.apache.poi.hpsf.PropertySet;
import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hpsf.UnexpectedPropertySetTypeException;
import org.apache.poi.hpsf.WritingNotSupportedException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel_Processing {

    //Have column and row headers
	//no merged cells within the data
	//start header in the first left hand column
	//Must use Times New Roman, Verdana, Arial, Tahoma, Calibri or Helvetica.
	//The Document Properties (i.e. Subject, Author, Title, Company, Keywords,and Language) must be properly filled out.
	//Note: For Author do not use individuals name or contractor name. Should use government organization name


	
	public static void readExcel(String path, File file){
		try {
			Workbook wb = WorkbookFactory.create(file);
			
			POIFSReader r = new POIFSReader();
			r.registerListener(new MyPOIFSReaderListener(), "\005SummaryInformation");
			r.read(new FileInputStream(file));

		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void updateExcelProperties(String path, File file){
		try{
			InputStream is = new FileInputStream(file);
			POIFSFileSystem poifs = new POIFSFileSystem(is);
			is.close();


			DirectoryEntry dir =  poifs.getRoot();
			SummaryInformation si;
		     try
		     {
		         DocumentEntry siEntry = (DocumentEntry)
		             dir.getEntry(SummaryInformation.DEFAULT_STREAM_NAME);
		         DocumentInputStream dis = new DocumentInputStream(siEntry);
		         PropertySet ps = new PropertySet(dis);
		         dis.close();
		         si = new SummaryInformation(ps);
		     }
		     catch (FileNotFoundException ex)
		     {
		         /* There is no summary information yet. We have to create a new
		          * one. */
		         si = PropertySetFactory.newSummaryInformation();
		     } 
		     
		     /* Change the author to "NCI EVS". Any former author value will
		      * be lost. If there has been no author yet, it will be created. */
		     si.setAuthor("NCI EVS");		     
		     System.out.println("Author changed to " + si.getAuthor() + ".");
		     
		     /* Change the Title and Subject to match the filename, minus the extensions */
		     String filename = file.getName().substring(0,file.getName().length()-4);
		     si.setTitle(filename);
		     si.setSubject(filename);
		     
		     /* Set keyword to first word in file */
		     int space = filename.indexOf(" ");
		     String keyword = null;
		     if (space>0){
		        keyword = filename.substring(0,space);
		     }
		     if (keyword != null){
		     si.setKeywords(keyword);}
		     else
		     {
		    	 si.setKeywords("EVS");
		     }
		     
		     si.setLastAuthor("NCI EVS");
		     
		     /* Read the document summary information. */
		     DocumentSummaryInformation dsi;
		     try
		     {
		         DocumentEntry dsiEntry = (DocumentEntry)
		             dir.getEntry(DocumentSummaryInformation.DEFAULT_STREAM_NAME);
		         DocumentInputStream dis = new DocumentInputStream(dsiEntry);
		         PropertySet ps = new PropertySet(dis);
		         dis.close();
		         dsi = new DocumentSummaryInformation(ps);
		     }
		     catch (FileNotFoundException ex)
		     {
		         /* There is no document summary information yet. We have to create a
		          * new one. */
		         dsi = PropertySetFactory.newDocumentSummaryInformation();
		     }
		     
		     /*Specify the language is English */
		     /* Read the custom properties. If there are no custom properties yet,
		      * the application has to create a new CustomProperties object. It will
		      * serve as a container for custom properties. */
		     CustomProperties customProperties = dsi.getCustomProperties();
		     if (customProperties == null)
		         customProperties = new CustomProperties();
		     /* Insert some custom properties into the container. */
		     customProperties.put("Language", "English");
		     
		     dsi.setCompany("NCI EVS");
		     
		     /* Write the custom properties back to the document summary
		      * information. */
		     dsi.setCustomProperties(customProperties);
		     
		     /* Write the summary information and the document summary information
		      * to the POI filesystem. */
		     si.write(dir, SummaryInformation.DEFAULT_STREAM_NAME);
		     dsi.write(dir, DocumentSummaryInformation.DEFAULT_STREAM_NAME);

		     /* Write the POI filesystem back to the original file. Please note that
		      * in production code you should never write directly to the origin
		      * file! In case of a writing error everything would be lost. */
		     OutputStream out = new FileOutputStream(file);
		     poifs.writeFilesystem(out);
		     out.close();
			
		}
	    catch (FileNotFoundException ex)
	     {
	         ex.printStackTrace();
	     } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoPropertySetStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MarkUnsupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnexpectedPropertySetTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WritingNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
		

	}


	
}




class MyPOIFSReaderListener implements POIFSReaderListener
{
    public void processPOIFSReaderEvent(POIFSReaderEvent event)
    {
        SummaryInformation si = null;
        try
        {
            si = (SummaryInformation)
                 PropertySetFactory.create(event.getStream());
        }
        catch (Exception ex)
        {
            throw new RuntimeException
                ("Property set stream \"" +
                 event.getPath() + event.getName() + "\": " + ex);
        }
        final String title = si.getTitle();
        if (title != null)
            System.out.println("Title: \"" + title + "\"");
        else {
            System.out.println("Document has no title.");
        }
        
        final String author = si.getAuthor();
        if (title != null)
            System.out.println("Author: \"" + author + "\"");
        else {
            System.out.println("Document has no author.");
        }
        
    }
}





