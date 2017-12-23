package net.coolicer.util;

import net.coolicer.functional.actions.Rethrow;
import net.coolicer.functional.actions.Return;
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
	Logger mockedLoggerInReturn;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		/* Inject the logger */
		Field loggerField = Return.class.getDeclaredField("logger");
		loggerField.setAccessible(true);
		loggerField.set(null, mockedLoggerInReturn);
		loggerField.setAccessible(false);
	}

	@Test
	public void givenNonThrowingCode_whenExecutedByOrRethrowAsRuntime_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.getting(() -> expected).or(Rethrow.asRuntime());

		Assert.assertEquals(expected, s);
	}

	@Test
	public void givenNonThrowingCode_whenExecutedByOrIgnoreExceptionAndReturnDefault_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.getting(() -> expected).or(Return.defaultValue("wasn't me"));

		Assert.assertEquals(expected, s);
		Mockito.verifyNoMoreInteractions(mockedLoggerInReturn);
	}

	@Test
	public void givenNonThrowingCode_whenExecutedByOrLogExceptionAndReturnDefault_thenExpectedResultIsReturned()
	{
		final String expected = "This is fine";

		String s = Try.getting(() -> expected).or(Return.defaultValueAndLogException("wasn't me"));

		Assert.assertEquals(expected, s);
		Mockito.verifyNoMoreInteractions(mockedLoggerInReturn);
	}

	@Test
	public void givenThrowingCode_whenExecutedByOrRethrowAsRuntime_thenExceptionIsWrappedIntoRuntimeException()
	{
		try
		{
			String s = Try.getting(() -> { if (true) throw new Exception("Expect me"); else return "Not me";}).or(Rethrow.asRuntime());
		}
		catch (RuntimeException ex)
		{
			Assert.assertEquals("Expect me", ex.getCause().getMessage());
			return;
		}

		Assert.fail();
	}

	@Test
	public void givenThrowingCode_whenExecutedByOrIgnoreExceptionAndReturnDefault_thenNoExceptionIsThrownAndDefaultIsReturned()
	{
		final Exception theException = new Exception("Expect me");
		String defaultValue = "default";

		String s = Try.getting(() -> { if (true) { throw theException; } else return "Not me";}).or(Return.defaultValue(defaultValue));

		assertEquals(defaultValue, s);
		Mockito.verifyNoMoreInteractions(mockedLoggerInReturn);
	}

	@Test
	public void givenThrowingCode_whenExecutedByOrLogExceptionAndReturnDefault_thenNoExceptionIsThrownButLoggedAndDefaultIsReturned()
	{
		final Exception theException = new Exception("Expect me");
		String defaultValue = "default";

		String s = Try.getting(() -> { if (true) { throw theException; } else return "Not me";}).or(Return.defaultValueAndLogException(defaultValue));

		assertEquals(defaultValue, s);
		Mockito.verify(mockedLoggerInReturn, Mockito.only()).warn(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.any(StackTraceElement[].class));
	}

	@Test
	public void givenThrowingCode_whenVoidAction_shouldWork()
	{
		Try.doing(() -> "String".codePointCount(1, 1)).or(Rethrow.asRuntime());
	}

	@Test(expected = RuntimeException.class)
	public void givenThrowingCode_whenVoidActionThrows_shouldDoTheOr()
	{
		Try.doing(() -> "String".codePointCount(100, 1)).or(Rethrow.asRuntime());
	}
}