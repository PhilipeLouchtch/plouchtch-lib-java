package net.coolicer.functional.actions;

import net.coolicer.functional.ThrowingConsumer;

public class Rethrow
{
	private Rethrow()
	{

	}

	public static <TException extends Exception> ThrowingConsumer<TException, RuntimeException> asRuntime()
	{
		return (TException ex) ->
		{
			throw new RuntimeException(ex);
		};
	}
}
