package org.mycompany.connector;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractFTPDownloadImpl extends AbstractConnector {

	protected final static String FTPSERVER_INPUT_PARAMETER = "ftpserver";
	protected final static String PORT_INPUT_PARAMETER = "port";
	protected final static String USERNAME_INPUT_PARAMETER = "username";
	protected final static String PASSWORD_INPUT_PARAMETER = "password";
	protected final static String FILENAME_INPUT_PARAMETER = "filename";
	protected final static String FILETYPE_INPUT_PARAMETER = "fileType";
	protected final static String ATTACHTODOC_INPUT_PARAMETER = "attachToDoc";
	protected final static String DESCRIPTION_INPUT_PARAMETER = "description";

	protected final java.lang.String getFtpserver() {
		return (java.lang.String) getInputParameter(FTPSERVER_INPUT_PARAMETER);
	}

	protected final java.lang.Integer getPort() {
		return (java.lang.Integer) getInputParameter(PORT_INPUT_PARAMETER);
	}

	protected final java.lang.String getUsername() {
		return (java.lang.String) getInputParameter(USERNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getPassword() {
		return (java.lang.String) getInputParameter(PASSWORD_INPUT_PARAMETER);
	}

	protected final java.lang.String getFilename() {
		return (java.lang.String) getInputParameter(FILENAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getFileType() {
		return (java.lang.String) getInputParameter(FILETYPE_INPUT_PARAMETER);
	}

	protected final java.lang.String getAttachToDoc() {
		return (java.lang.String) getInputParameter(ATTACHTODOC_INPUT_PARAMETER);
	}

	protected final java.lang.String getDescription() {
		return (java.lang.String) getInputParameter(DESCRIPTION_INPUT_PARAMETER);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getFtpserver();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("ftpserver type is invalid");
		}
		try {
			getPort();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("port type is invalid");
		}
		try {
			getUsername();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("username type is invalid");
		}
		try {
			getPassword();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("password type is invalid");
		}
		try {
			getFilename();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("filename type is invalid");
		}
		try {
			getFileType();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("fileType type is invalid");
		}
		try {
			getAttachToDoc();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("attachToDoc type is invalid");
		}
		try {
			getDescription();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("description type is invalid");
		}

	}

}
