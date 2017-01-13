package net.coolicer.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class TryTest
{
	@Mock
	Logger mockedLogger;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		/* Inject the logger */
		Field loggerField = Try.class.getDeclaredField("logger");
		loggerField.setAccessible(true);
		loggerField.set(null, mockedLogger);
		loggerField.setAccessible(false);
	}

	@Test
	public void givenNonThrowingCode_whenExecutedByOrRethrowAsRuntime_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.toExecute(() -> expected).orRethrowAsRuntime();

		Assert.assertEquals(expected, s);
		Mockito.verifyNoMoreInteractions(mockedLogger);
	}

	@Test
	public void givenNonThrowingCode_whenExecutedByOrIgnoreExceptionAndReturnDefault_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.toExecute(() -> expected).orIgnoreExceptionAndReturnDefault("wasn't me");

		Assert.assertEquals(expected, s);
		Mockito.verifyNoMoreInteractions(mockedLogger);
	}


	@Test
	public void givenNonThrowingCode_whenExecutedByOrLogExceptionAndReturnDefault_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.toExecute(() -> expected).orLogExceptionAndReturnDefault("wasn't me");

		Assert.assertEquals(expected, s);
		Mockito.verifyNoMoreInteractions(mockedLogger);
	}


	@Test
	public void givenThrowingCode_whenExecutedByOrRethrowAsRuntime_thenExceptionIsWrappedIntoRuntimeException()
	{
		try
		{
			String s = Try.toExecute(() -> { if (true) throw new Exception("Expect me"); else return "Not me";}).orRethrowAsRuntime();
		}
		catch (RuntimeException rte)
		{
			Assert.assertEquals("Expect me", rte.getCause().getMessage());
			Mockito.verifyNoMoreInteractions(mockedLogger);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test
	public void givenThrowingCode_whenExecutedByOrIgnoreExceptionAndReturnDefault_thenNoExceptionIsThrownAndDefaultIsReturned()
	{
		final Exception theException = new Exception("Expect me");
		String s = Try.toExecute(() -> { if (true) { throw theException; } else return "Not me";}).orIgnoreExceptionAndReturnDefault("default");

		assertEquals("default", s);
		Mockito.verifyNoMoreInteractions(mockedLogger);
	}

	@Test
	public void givenThrowingCode_whenExecutedByOrLogExceptionAndReturnDefault_thenNoExceptionIsThrownButLoggedAndDefaultIsReturned()
	{
		final Exception theException = new Exception("Expect me");
		String s = Try.toExecute(() -> { if (true) { throw theException; } else return "Not me";}).orLogExceptionAndReturnDefault("default");

		assertEquals("default", s);
		Mockito.verify(mockedLogger, Mockito.only()).warn(Matchers.anyString(), Matchers.same(theException));
	}
}