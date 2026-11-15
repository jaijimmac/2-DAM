package Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CriaturasFantasticasException extends RuntimeException{
	
	private static final Logger logger = LogManager.getLogger(CriaturasFantasticasException.class);
	
	public CriaturasFantasticasException(String mensaje) {
    	super(mensaje);
    	
    	logger.error(mensaje);
    }    
	    
}

