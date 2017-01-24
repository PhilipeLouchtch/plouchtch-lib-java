package net.coolicer.util;

import org.junit.Test;

public class AssertTest
{
	final boolean REALITY = true;
	final boolean EXPECTATION = false;

	private static class AssertTestCheckedException extends Exception {}

	@Test(expected = AssertTestCheckedException.class)
	public void givenFalseCondition_whenOrThrowWithCheckedExceptionSupplier_shouldThrowSuppliedException() throws Exception
	{
		AssertTestCheckedException assertTestCheckedException = new AssertTestCheckedException();
		try
		{
			Assert.that(REALITY == EXPECTATION).orThrow(() -> assertTestCheckedException);
		}
		catch (AssertTestCheckedException ex)
		{
			org.junit.Assert.assertSame(assertTestCheckedException, ex);
			throw ex;
		}
	}

	@Test
	public void givenValidCondition_whenOrThrowWithCheckedExceptionSupplier_shouldDoNothing() throws Exception
	{
		AssertTestCheckedException assertTestCheckedException = new AssertTestCheckedException();
		try
		{
			Assert.that(true).orThrow(() -> assertTestCheckedException);
		}
		catch (Exception ex)
		{
			org.junit.Assert.fail("Given a valid condition, must not throw");
		}
	}

	@Test
	public void givenFalseCondition_whenOrThrowWithRuntimeException_shouldThrowException() // NO THROWS IN SIGNATURE
	{
		RuntimeException runtimeException = new RuntimeException();
		try
		{
			Assert.that(true).orThrow(() -> runtimeException);
		}
		catch (RuntimeException ex)
		{
			org.junit.Assert.assertSame(runtimeException, ex);
			throw ex;
		}
	}
}