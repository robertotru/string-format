package com.robertotru.stringformat;

import java.util.HashMap;
import java.util.Map;

/**
 * Formatter for strings based on the well known {@code {}} placeholder.
 * This class is meant to be used as alternative to {@link String#format(String, Object...)}.
 */
public class StringFormatter {

	private static final String PLACEHOLDER = "{}";
	private static final char ESCAPE_CHAR = '\\';

	/**
	 * Formats a message pattern by replacing the instances of {@code {}} with the {@link #toString()} value of each
	 * given argument. Escaping of the placeholder string {@code {}} is obtained via {@code \\}.
	 * If the number of arguments exceed the number of placeholders, an exception is thrown.
	 * Examples:
	 * <ul>
	 *     <li>{@code format("Hello {} world", "fantastic")} returns {@code "Hello fantastic world"}</li>
	 *     <li>{@code format("Hello \\{} world", "fantastic")} returns {@code "Hello \\{} world"}</li>
	 *     <li>{@code format("Hello \\\\{} world", "fantastic")} returns {@code "Hello ||fantastic world"}</li>
	 * </ul>>
	 *
	 * @param messagePattern
	 * @param arguments
	 * @return
	 */
	public static String format(final String messagePattern, final Object... arguments) {
		return formatAsCharSequence(messagePattern, arguments).toString();
	}

	public static CharSequence formatAsCharSequence(final String messagePattern, final Object... arguments) {
		if (messagePattern == null) {
			throw new IllegalArgumentException("Message pattern cannot be null.");
		}
		if (arguments == null) {
			throw new IllegalArgumentException("Array of arguments cannot be null.");
		}

		final int numberOfArguments = arguments.length;
		final StringBuilder stringBuilder = new StringBuilder(messagePattern.length() + numberOfArguments * 7);

		int startSearchIndex = 0;
		int delimiterStartIndex;
		int argumentNumber = 0;
		do {
			delimiterStartIndex = messagePattern.indexOf(PLACEHOLDER, startSearchIndex);

			if (delimiterStartIndex == -1) {
				//<editor-fold desc="There are no more {} to be replaced">
				if (startSearchIndex == 0) {
					// oh, message pattern was just a message
					checkUsedArguments(argumentNumber, numberOfArguments);
				}
				//</editor-fold>
			} else {
				if (delimiterStartIndex == 0
						|| messagePattern.charAt(delimiterStartIndex - 1) != ESCAPE_CHAR) {
					// the placeholder is not escaped
					stringBuilder.append(messagePattern, startSearchIndex, delimiterStartIndex);
					checkArgumentExists(argumentNumber, numberOfArguments);
					appendParameter(stringBuilder, arguments[argumentNumber++]);
					startSearchIndex = delimiterStartIndex + 2;
				} else {
					// the placeholder is escaped
					if (delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == ESCAPE_CHAR) {
						// double escaped
						stringBuilder.append(messagePattern, startSearchIndex, delimiterStartIndex - 1);
						checkArgumentExists(argumentNumber, numberOfArguments);
						appendParameter(stringBuilder, arguments[argumentNumber++]);
					} else {
						// is escaped, so we just replace the \{} with {}
						stringBuilder.append(messagePattern, startSearchIndex, delimiterStartIndex - 1);
						stringBuilder.append(messagePattern, delimiterStartIndex, delimiterStartIndex + 2);
					}
					startSearchIndex = delimiterStartIndex + 2;
				}

			}

		}
		while (delimiterStartIndex != -1);

		checkUsedArguments(argumentNumber, numberOfArguments);

		// append the characters following the last {} pair.
		stringBuilder.append(messagePattern, startSearchIndex, messagePattern.length());
		return stringBuilder;
	}

	private static void checkArgumentExists(int argumentNumber, int numberOfArguments) {
		if (argumentNumber >= numberOfArguments) {
			final StringBuilder messageBuilder = new StringBuilder();
			if (argumentNumber == 0) {
				messageBuilder.append("Expected at least 1 argument, but none was given.");
			} else {
				messageBuilder.append("Expected at least ");
				messageBuilder.append(argumentNumber + 1);
				messageBuilder.append(" arguments, but only ");
				if (numberOfArguments == 1) {
					messageBuilder.append("one was given.");
				} else {
					messageBuilder.append(numberOfArguments)
							.append(" were given.");
				}
			}
			throw new IllegalArgumentException(messageBuilder.toString());
		}
	}

	private static void checkUsedArguments(final int argumentNumber, final int numberOfArguments) {
		if (argumentNumber < numberOfArguments) {
			final StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Expected ")
					.append(numberOfArguments)
					.append(" placeholder");
			if (numberOfArguments > 1) {
				messageBuilder.append("s");
			}
			messageBuilder.append(", while ")
					.append(argumentNumber)
					.append(" argument");
			if (argumentNumber > 1) {
				messageBuilder.append("s were");
			} else {
				messageBuilder.append(" was");
			}
			messageBuilder.append(" found: therefore, ");
			int numberOfUseless = numberOfArguments - argumentNumber;
			messageBuilder.append(numberOfUseless);
			if (numberOfUseless > 1) {
				messageBuilder.append(" arguments are useless.");
			} else {
				messageBuilder.append(" argument is useless.");
			}

			throw new IllegalArgumentException(messageBuilder.toString());
		}
	}

	private static void appendParameter(final StringBuilder stringBuilder,
	                                    final Object object) {
		if (appendObject(stringBuilder, object)) {
			return;
		}

		appendObjectOrArray(stringBuilder, object, new HashMap<>());
	}

	private static boolean appendObject(final StringBuilder stringBuilder, final Object object) {
		if (object == null) {
			stringBuilder.append("null");
			return true;
		}
		if (!object.getClass().isArray()) {
			stringBuilder.append(object);
			return true;
		}
		return false;
	}

	private static void appendObjectOrArray(final StringBuilder stringBuilder,
	                                        final Object object,
	                                        final Map<Object[], Object> alreadyVisited) {
		if (appendObject(stringBuilder, object)) {
			return;
		}

		if (object instanceof boolean[]) {
			appendBooleanArray(stringBuilder, (boolean[]) object);
		} else if (object instanceof float[]) {
			appendFloatArray(stringBuilder, (float[]) object);
		} else if (object instanceof double[]) {
			appendDoubleArray(stringBuilder, (double[]) object);
		} else if (object instanceof short[]) {
			appendShortArray(stringBuilder, (short[]) object);
		} else if (object instanceof int[]) {
			appendIntArray(stringBuilder, (int[]) object);
		} else if (object instanceof long[]) {
			appendLongArray(stringBuilder, (long[]) object);
		} else if (object instanceof byte[]) {
			appendByteArray(stringBuilder, (byte[]) object);
		} else if (object instanceof char[]) {
			appendCharArray(stringBuilder, (char[]) object);
		} else {
			objectArrayAppend(stringBuilder, (Object[]) object, alreadyVisited);
		}

	}

	private static void appendBooleanArray(final StringBuilder stringBuilder, final boolean[] booleans) {
		stringBuilder.append('[');
		final int len = booleans.length;
		for (int i = 0; i < len; i++) {
			stringBuilder.append(booleans[i]);
			if (i != len - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendFloatArray(final StringBuilder stringBuilder, final float[] floats) {
		stringBuilder.append('[');
		final int len = floats.length;
		for (int i = 0; i < len; i++) {
			stringBuilder.append(floats[i]);
			if (i != len - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendDoubleArray(final StringBuilder stringBuilder, final double[] doubles) {
		stringBuilder.append('[');
		final int len = doubles.length;
		for (int i = 0; i < len; i++) {
			stringBuilder.append(doubles[i]);
			if (i != len - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendShortArray(final StringBuilder stringBuilder, final short[] shorts) {
		stringBuilder.append('[');
		final int numberOfElements = shorts.length;
		for (int i = 0; i < numberOfElements; i++) {
			stringBuilder.append(shorts[i]);
			if (i != numberOfElements - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendIntArray(final StringBuilder stringBuilder, final int[] ints) {
		stringBuilder.append('[');
		final int numberOfElements = ints.length;
		for (int i = 0; i < numberOfElements; i++) {
			stringBuilder.append(ints[i]);
			if (i != numberOfElements - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendLongArray(final StringBuilder stringBuilder, final long[] longs) {
		stringBuilder.append('[');
		final int numberOfElements = longs.length;
		for (int i = 0; i < numberOfElements; i++) {
			stringBuilder.append(longs[i]);
			if (i != numberOfElements - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendByteArray(final StringBuilder stringBuilder, final byte[] bytes) {
		stringBuilder.append('[');
		final int numberOfElements = bytes.length;
		for (int i = 0; i < numberOfElements; i++) {
			stringBuilder.append(bytes[i]);
			if (i != numberOfElements - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void appendCharArray(final StringBuilder stringBuilder, final char[] chars) {
		stringBuilder.append('[');
		final int numberOfElements = chars.length;
		for (int i = 0; i < numberOfElements; i++) {
			stringBuilder.append(chars[i]);
			if (i != numberOfElements - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
	}

	private static void objectArrayAppend(final StringBuilder stringBuilder,
	                                      final Object[] objects,
	                                      final Map<Object[], Object> alreadyVisited) {
		stringBuilder.append('[');
		if (!alreadyVisited.containsKey(objects)) {
			alreadyVisited.put(objects, null);
			final int numberOfElements = objects.length;
			for (int i = 0; i < numberOfElements; i++) {
				appendObjectOrArray(stringBuilder, objects[i], alreadyVisited);
				if (i != numberOfElements - 1) {
					stringBuilder.append(", ");
				}
			}
			// allow repeats in siblings
			alreadyVisited.remove(objects);
		} else {
			stringBuilder.append("...");
		}
		stringBuilder.append(']');
	}

}
