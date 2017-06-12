 package gov.nih.nci.evs.excel508;

import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

public class FTP_layer {
//This will need to go to the specified directory and download the Excel files.
	//When ready, it will need to connect to the directory with username and upload the edited files.
	
	
	public static boolean listFiles(String dir, String username, String password){
		//Go to the specified path and retrieve and Excel files there
		
		Logger log = Logger.getLogger(FTP_layer.class);
		Logger.setLevel(Level.INFO);
		
		FileTransferClient ftp = null;
//		String host = "ftp1.nci.nih.gov";
		String host = "ncicbftp2.nci.nih.gov";
		String dirStub = "cacore/EVS/";
		//If retrieval succeeds, return true;
		boolean filesRetrieved = false;
		try{
			log.info("Creating new FTP client");
			ftp = new FileTransferClient();
			
			log.info("Setting remote host " + host);
			ftp.setRemoteHost(host);
			ftp.setUserName(username);
			ftp.setPassword(password);
			
			//connect to the server
			log.info("Connecting to server "+ host);
			ftp.connect();
			
			log.info("Current dir: "+ ftp.getRemoteDirectory());
			String navDir = dirStub+dir;
			log.info("Change directory to " + navDir);
			ftp.changeDirectory(navDir);
			log.info("Current dir: "+ ftp.getRemoteDirectory());
			
			log.info("Getting current directory listing");
			FTPFile[] files = ftp.directoryList(".");
			for (int i= 0; i< files.length; i++){
				log.info(files[i].toString());
				filesRetrieved = true;
			}
			
			return filesRetrieved;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public static boolean listFiles(String dir){
		//Go to the specified path and retrieve and Excel files there
		
		Logger log = Logger.getLogger(FTP_layer.class);
		Logger.setLevel(Level.INFO);
		
		FileTransferClient ftp = null;
		String host = "ftp1.nci.nih.gov";
		String dirStub = "pub/cacore/EVS/";
		//If retrieval succeeds, return true;
		boolean filesRetrieved = false;
		try{
			log.info("Creating new FTP client");
			ftp = new FileTransferClient();
			
			log.info("Setting remote host " + host);
			ftp.setRemoteHost(host);
			
			//connect to the server
			log.info("Connecting to server "+ host);
			ftp.connect();
			
			log.info("Current dir: "+ ftp.getRemoteDirectory());
			String navDir = dirStub+dir;
			ftp.changeDirectory(navDir);
			log.info("Current dir: "+ ftp.getRemoteDirectory());
			
			log.info("Getting current directory listing");
			FTPFile[] files = ftp.directoryList(".");
			for (int i= 0; i< files.length; i++){
				log.info(files[i].toString());
				filesRetrieved = true;
			}
			
			return filesRetrieved;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean uploadFiles(String path, String username, String password){
	//Go to specified path and upload files
		boolean filesUploaded = false;
		try{
			
			
			return filesUploaded;
		}catch(Exception e){
			return false;
		}
		
		
	}
}
