package net.coolicer.functional;

@FunctionalInterface
public interface ThrowingAction<TException extends Exception>
{
	void execute() throws TException;
}
