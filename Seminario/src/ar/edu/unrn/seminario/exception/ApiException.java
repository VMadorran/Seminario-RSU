package ar.edu.unrn.seminario.exception;
@SuppressWarnings("serial")
public class ApiException extends Exception{
	
	public ApiException(String message) {
        super(message);
    }

}