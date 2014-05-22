package treeset.extensions.database;

/**
 * Exception that can occur during database init
 *
 * Created by daemontus on 15/1/14.
 */
public class DatabaseInitException extends Exception {

	public DatabaseInitException() {
	}

	public DatabaseInitException(String detailMessage) {
		super(detailMessage);
	}

	public DatabaseInitException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public DatabaseInitException(Throwable throwable) {
		super(throwable);
	}
}
