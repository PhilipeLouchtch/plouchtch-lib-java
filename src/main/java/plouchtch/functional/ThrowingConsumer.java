package plouchtch.functional;

@FunctionalInterface
public interface ThrowingConsumer<TInitialException extends Exception, TNewException extends RuntimeException>
{
	void consume(TInitialException thrownException) throws TNewException;
}
