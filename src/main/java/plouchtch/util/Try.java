package plouchtch.util;

import plouchtch.functional.ThrowingAction;
import plouchtch.functional.ThrowablesRethrower;
import plouchtch.functional.ThrowingSupplier;
import org.slf4j.Logger;

import java.util.function.Function;
import java.util.function.Supplier;


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
	public static <T> Try<T> getting(ThrowingSupplier<T> supplier)
	{
		return new Try<T>(supplier);
	}

	// TODO: improve to not have a return value, for the sake for sane auto-completion
	public static Try doing(ThrowingAction action)
	{
		ThrowingSupplier<Void> wrappedAction = () ->
		{
			action.execute();
			return null;
		};

		return new Try<Void>(wrappedAction);
	}

	public T or(Function<Exception, T> handler)
	{
		try
		{
			return this.code.execute();
		}
		catch (Exception ex)
		{
			return handler.apply(ex);
		}
	}

	public <TException extends RuntimeException> T or(ThrowablesRethrower<Exception, TException> rethrower) throws TException
	{
		try
		{
			return this.code.execute();
		}
		catch (Exception ex)
		{
			// This should throw a TException
			rethrower.consume(ex);

			// should not hit
			throw new RuntimeException(rethrower.toString() + " did not throw an exception, throwing this exception instead");
		}
	}

	public T or(Supplier<T> valueSupplier)
	{
		return this.or((Exception ex) -> valueSupplier.get());
	}
}