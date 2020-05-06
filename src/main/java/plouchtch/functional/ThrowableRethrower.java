package plouchtch.functional;

@FunctionalInterface
public interface ThrowingConsumer<TInitialException extends Throwable, TNewException extends RuntimeException>
{
	void consume(TInitialException thrownException) throws TNewException;
}
