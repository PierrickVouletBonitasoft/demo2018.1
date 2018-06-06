/**
 * 
 */
package org.mycompany.connector;

import org.bonitasoft.engine.connector.ConnectorException;
import org.bonitasoft.engine.exception.AlreadyExistsException;
import org.bonitasoft.engine.bpm.document.DocumentAttachmentException;
import org.bonitasoft.engine.bpm.document.DocumentValue;


import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;

import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *The connector execution will follow the steps
 * 1 - setInputParameters() --> the connector receives input parameters values
 * 2 - validateInputParameters() --> the connector can validate input parameters values
 * 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
 * 4 - executeBusinessLogic() --> execute the connector
 * 5 - getOutputParameters() --> output are retrieved from connector
 * 6 - disconnect() --> the connector can close connection to remote server (if any)
 */
public class FTPDownloadImpl extends AbstractFTPDownloadImpl {

	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		//Get access to the connector input parameters
		//getFtpserver();
		//getUsername();
		//getPassword();
		//getFilename();
		//getPort();
		//getFileType();
	
		//TODO execute your business logic here 
		Logger logger= Logger.getLogger("org.bonitasoft");
				
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(getFtpserver(), getPort());
            ftpClient.login(getUsername(), getPassword());
            ftpClient.enterLocalPassiveMode();
            
            if(getFileType().equals("ASCII")){
            	ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
            	//logger.info("ftp ascii transfer");
            } else {
            	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            	//logger.info("ftp binary transfer");
            }
            //ftpClient.storeFile(remote, local)
 
            // Using InputStream retrieveFileStream(String)
            String remoteFile2 = getFilename();
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            
            //logger.info("number of bytes is "+inputStream.available());
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            
            Tika tika = new Tika();
            String mimeType = tika.detect(inputStream);
            //logger.info("detect - "+tika.detect(inputStream));
            
            // create DocumentValue
            DocumentValue dv = new DocumentValue(bytes, mimeType, getFilename());
            
            try {
				apiAccessor.getProcessAPI().addDocument(getExecutionContext().getProcessInstanceId(), getAttachToDoc(), getDescription(), dv);
			} catch (ProcessInstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentAttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            inputStream.close();
 
        } catch (IOException ex) {
        	logger.severe("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }		//WARNING : Set the output of the connector execution. If outputs are not set, connector fails
	
	 }

	@Override
	public void connect() throws ConnectorException{
		//[Optional] Open a connection to remote server
	
	}

	@Override
	public void disconnect() throws ConnectorException{
		//[Optional] Close connection to remote server
	
	}

}
