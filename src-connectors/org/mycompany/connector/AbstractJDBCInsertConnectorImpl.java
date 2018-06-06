package org.mycompany.connector;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractJDBCInsertConnectorImpl extends AbstractConnector {

	protected final static String DRIVER_INPUT_PARAMETER = "driver";
	protected final static String URL_INPUT_PARAMETER = "url";
	protected final static String USERNAME_INPUT_PARAMETER = "username";
	protected final static String PASSWORD_INPUT_PARAMETER = "password";
	protected final static String SCRIPT_INPUT_PARAMETER = "script";
	protected final String ID_OUTPUT_PARAMETER = "id";

	protected final java.lang.String getDriver() {
		return (java.lang.String) getInputParameter(DRIVER_INPUT_PARAMETER);
	}

	protected final java.lang.String getUrl() {
		return (java.lang.String) getInputParameter(URL_INPUT_PARAMETER);
	}

	protected final java.lang.String getUsername() {
		return (java.lang.String) getInputParameter(USERNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getPassword() {
		return (java.lang.String) getInputParameter(PASSWORD_INPUT_PARAMETER);
	}

	protected final java.lang.String getScript() {
		return (java.lang.String) getInputParameter(SCRIPT_INPUT_PARAMETER);
	}

	protected final void setId(java.lang.Long id) {
		setOutputParameter(ID_OUTPUT_PARAMETER, id);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getDriver();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("driver type is invalid");
		}
		try {
			getUrl();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("url type is invalid");
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
			getScript();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("script type is invalid");
		}

	}

}
