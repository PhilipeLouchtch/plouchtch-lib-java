package net.coolicer.functional;

import java.util.function.Supplier;

public class ThrowRuntimeException implements Action
{
	private Supplier<? extends RuntimeException> runtimeExceptionSupplier;

	public ThrowRuntimeException(Supplier<? extends RuntimeException> runtimeExceptionSupplier)
	{
		// Sadly, can't use the Assertion framework on itself, infinite recursion calls...
		this.runtimeExceptionSupplier = runtimeExceptionSupplier;
	}

	@Override
	public void execute()
	{
		throw runtimeExceptionSupplier.get();
	}

}
