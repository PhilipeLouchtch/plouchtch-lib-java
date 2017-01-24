package net.coolicer.util;

import java.util.function.Supplier;

/**
 * A nice and simple helper class for more concise way of writing assertions of the form:<br /><br />
 * <code>if (!someCondition) {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;throws new TException(...);<br />
 * 	}</code>
 */
public class Assert
{
	/**
	 * Check if the given condition holds, it doesn't do anything noteworthy by itself and thus <b>must be chained</b> with a terminal operation
	 * @param condition Condition to assert
	 * @return An instance of Assert for further chaining.
	 */
	public static Assert that(boolean condition)
	{
		return new Assert(condition == false);
	}

	/**
	 * <b>Terminal operation of the Assert.</b><br /><br />
	 * Will throw the Exception supplied by exceptionSupplier argument if the assertion condition does not hold.<br/><br/>
	 * Furthermore, the return type of supplier is included in the throws signature of this method so that it is possible to throw checked exception during the
	 * dynamic assertions.
	 * @param exceptionSupplier Supplier function providing the exception to throw in case the condition fails the assert
	 * @param <TException> Type of the Exception thrown
	 * @throws TException The exception provided by the supplier
	 */
	public <TException extends Exception> void orThrow(Supplier<TException> exceptionSupplier) throws TException
	{
		if (assertFailed) { throw exceptionSupplier.get(); }
	}

	private boolean assertFailed;
	private Assert(boolean assertFailed)
	{
		this.assertFailed = assertFailed;
	}
}
