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

	private static class MyCustomException extends Exception
	{
		public MyCustomException(String message)
		{
			super(message);
		}
	}

	@Test
	public void givenTrueCondition_whenOrThrowWithCustomException_shouldWorkIfArgumentCorrect()
	{
		final String this_is_the_argument = "This is the argument";
		Exception caughtEx = null;
		try
		{

			Assert.that(false).orThrow(MyCustomException.class, this_is_the_argument);
		}
		catch (MyCustomException ex)
		{
			org.junit.Assert.assertEquals(this_is_the_argument, ex.getMessage());
			caughtEx = ex;
		}

		if (caughtEx == null)
		{
			org.junit.Assert.fail("No exception was thrown");
		}
	}
}