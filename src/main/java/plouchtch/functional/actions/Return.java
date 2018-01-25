package plouchtch.functional.actions;

import org.slf4j.Logger;

import java.util.function.Function;
import java.util.function.Supplier;

public class Return<T>
{
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(Return.class);

	public static <T> Supplier<T> defaultValue(T value)
	{
		return () -> value;
	}

	public static <TException extends Exception, T> Function<TException, T> defaultValueAndLogException(T value)
	{
		return (TException ex) ->
		{
			logger.warn("Exception ({}) occurred, returning default value. Msg: '{}', Cause: '{}'.\n Here's the stacktrace: {}\n", ex.toString(), ex.getMessage(), ex.getCause(), ex.getStackTrace());

			return value;
		};
	}
}
