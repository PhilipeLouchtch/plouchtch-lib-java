package plouchtch.functional.actions;

import plouchtch.functional.ThrowablesRethrower;

public class Rethrow
{
	private Rethrow()
	{
	}

	public static <TException extends Throwable> ThrowablesRethrower<TException, RuntimeException> asRuntime()
	{
		return (TException ex) ->
		{
			throw new RuntimeException(ex);
		};
	}
}
