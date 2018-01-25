package plouchtch.lang.exception;

/**
 * Indicates the passing of a null-value argument to a method that disallows null parameters.
 * Is a fairly simple wrapper around RuntimeException and therefore, transitively, not a checked exception.
 */
public class NullArgumentException extends RuntimeException
{
	/**
	 * The name of the parameter being null. Is included in the error message.
	 * @param parameterName The name of the parameter that containes the null value.
	 */
	public NullArgumentException(String parameterName)
	{
		super(String.format("Parameter '%s' cannot be null", parameterName));
	}
}