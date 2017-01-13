package net.coolicer.util;

import net.coolicer.functional.ThrowingSupplier;
import org.slf4j.Logger;


/**
 * This class helps you to execute some code that throws a checked exception, which is passed in as a checked-lamda function (see {@link ThrowingSupplier},
 * where the intention is to simply rethrow the checked exception a {@link RuntimeException}. <br /><br />
 *
 * The class should be used in one chained call, like so: <br />
 * {@code LocalDateTime myResult = Try.toExecute(() -> LocalDateTime.from(myDateTimeFormatter.parse(myDateAsString))).orRethrowAsRuntime();} <br /><br />
 *
 * Its operation is simple, call the static method toExecute(λ) to create a new instance of this class with the given λ-function. After which one of the possible
 * ways of executing the given λ.
 *
 * @param <T>
 */
public class Try<T>
{
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(Try.class);

	private ThrowingSupplier<T> code;

	private Try(ThrowingSupplier<T> code)
	{
		this.code = code;
	}

	/**
	 * Creates a new instance of the {@link Try<T>} class with the given {@link ThrowingSupplier<T>} λ function.
	 * @param supplier The λ to wrap
	 * @param <T> Type of the return value of the λ-supplier
	 * @return A new instance of Try that wraps the given λ
	 */
	public static <T> Try<T> toExecute(ThrowingSupplier<T> supplier)
	{
		return new Try<T>(supplier);
	}

	/**
	 * Executes the λ wrapped by the {@link Try<T>}
	 * @return The result of the λ when no exceptions were thrown
	 * @throws RuntimeException that wraps a checked exception thrown by λ
	 */
	public T orRethrowAsRuntime() throws RuntimeException
	{
		try
		{
			return code.execute();
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public T orIgnoreExceptionAndReturnDefault(T defaultValue)
	{
		try
		{
			return code.execute();
		}
		catch (Exception ex)
		{
			return defaultValue;
		}
	}

	public T orLogExceptionAndReturnDefault(T defaultValue)
	{
		try
		{
			return code.execute();
		}
		catch (Exception ex)
		{
			logger.warn("λ threw a checked exception, returning the defaultValue and logging the exception.", ex);
			return defaultValue;
		}
	}
}