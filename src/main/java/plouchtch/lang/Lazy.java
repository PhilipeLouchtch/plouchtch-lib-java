package plouchtch.lang;

import plouchtch.lang.exception.NullArgumentException;

import java.util.function.Supplier;

/**
 * Provides a simple, easy to use interface for making Lazy-initialized objects
 * @param <T>
 */
public class Lazy<T>
{
	private Supplier<T> valueSupplier;
	private Boolean isSupplied = false;
	private T value = null;

	/**
	 * Creates the Lazy-evaluated wrapper. The given supplier-function object will supply
	 * @param valueSupplier
	 */
	public Lazy(Supplier<T> valueSupplier)
	{
		if (valueSupplier == null) { throw new NullArgumentException("valueSupplier"); }

		value = null;
		isSupplied = false;

		this.valueSupplier = valueSupplier;
	}

	/**
	 * Returns the wrapped value. When called the first time, will use the Supplier function to create the wrapped value.
	 * Thread-safe
	 * @return either a new instance or a previously-instantiated value of <tt>T</tt>, provided by the <tt>valueSupplier</tt> <tt>Supplier</tt> ctor argument.
	 */
	public synchronized T get()
	{
		if (!isSupplied)
		{
			this.value = valueSupplier.get();

			// We don't need to supply the value anymore.
			// Set the valueSupplier to null to allow it to be GC'd
			isSupplied = true;
			valueSupplier = null;
		}

		return this.value;
	}
}