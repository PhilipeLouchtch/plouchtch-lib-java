package net.coolicer.functional;

/**
 * This is basically a {@link java.util.function.Supplier} that has a checked Exception in its signature.
 * Useful for when the use of a lambra/anonymous-function is desired but where the lambda should not handle its exceptions
 * but should be delegated to the executer of the function.
 * @param <T> Type of the value returned by the supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T>
{
	T execute() throws Exception;
}