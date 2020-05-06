package plouchtch.functional;

@FunctionalInterface
public interface ThrowablesRethrower<TInitialException extends Throwable, TNewException extends RuntimeException>
{
	void consume(TInitialException thrownException) throws TNewException;
}
