package com.robertotru.stringformat;


import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringFormatterTest {

	//<editor-fold desc="Preconditions">

	@Test
	void format_hasNullPattern() {
		// Given
		String messagePattern = null;
		Object arg1 = "Daniele Trunfio";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Message pattern cannot be null.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Message pattern cannot be null.");
	}

	@Test
	void format_hasNullArrayOfArgs() {
		// Given
		String messagePattern = "Pattern";
		Object[] arg1 = null;

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Array of arguments cannot be null.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Array of arguments cannot be null.");
	}

	//</editor-fold>

	//<editor-fold desc="Test happy cases">

	@Test
	void format_withNoArgsAndNoPattern() {
		// Given
		String messagePattern = "Hello, welcome to this test";

		// When
		String formattedString = StringFormatter.format(messagePattern);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo(messagePattern);
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";
		Object arg1 = "Daniele Trunfio";
		Object arg2 = "nice";

		// When
		String formattedString = StringFormatter.format(messagePattern, arg1, arg2);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg1, arg2);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("Hello Daniele Trunfio, welcome to this nice test");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withNullArg() {
		// Given
		String messagePattern = "{}";
		Object arg = null;

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("null");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withStringArray_repeatedObject() {
		// Given
		String messagePattern = "{} {}";
		MyObject v1 = new MyObject("Io");
		MyObject v2 = new MyObject("sono");
		MyObject v3 = new MyObject("vendetta");
		Object[] v4 = new Object[]{v1, v2, v3, null};
		v4[3] = v4; // recurse
		Object arg1 = "Ciao!";
		Object arg2 = v4;

		// When
		String formattedString = StringFormatter.format(messagePattern, arg1, arg2);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg1,  arg2);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("Ciao! [Io, sono, vendetta, [...]]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withStringArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new String[]{"Ciao", "mamma!"};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[Ciao, mamma!]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withCharArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new char[]{'a', 'b', 'c'};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[a, b, c]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withBooleanArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new boolean[]{true, false};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[true, false]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withByteArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new byte[]{1, 2};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[1, 2]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withShortArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new short[]{1, 2};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[1, 2]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withIntArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new int[]{123, 456};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[123, 456]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withLongArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new long[]{123, 456};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[123, 456]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withFloatArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new float[]{.1f, .2f};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[0.1, 0.2]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_withDoubleArray() {
		// Given
		String messagePattern = "{}";
		Object arg = new double[]{10.1, 100.2};

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("[10.1, 100.2]");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	//</editor-fold>

	//<editor-fold desc="Test Placeholder escaping">

	@Test
	void format_stringContainsSingleEscape() {
		// Given
		String messagePattern = "Hello \\{}, welcome to this {} test";
		Object arg = "nice";

		// When
		String formattedString = StringFormatter.format(messagePattern, arg);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("Hello {}, welcome to this nice test");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	@Test
	void format_stringContainsDoubleEscape() {
		// Given
		String messagePattern = "The file is available at path  C:\\\\{} but can be also found at http:\\\\\\{}";
		Object arg1 = "mytest.zip";
		Object arg2 = "www.onlineresources.com/mytest.zip";

		// When
		String formattedString = StringFormatter.format(messagePattern, arg1, arg2);
		CharSequence formattedCharSequence = StringFormatter.formatAsCharSequence(messagePattern, arg1, arg2);

		// Then
		Assertions.assertThat(formattedString)
				.isEqualTo("The file is available at path  C:\\mytest.zip but can be also found at " +
						"http:\\\\www.onlineresources.com/mytest.zip");
		Assertions.assertThat(formattedString)
				.isEqualTo(formattedCharSequence.toString());
	}

	//</editor-fold>

	//<editor-fold desc="Fails when number of args differs from number of placeholders">

	@Test
	void format_onePlaceholder_failsWithOneAdditionalArgs() {
		// Given
		String messagePattern = "{} prova, sa sa";
		Object arg1 = 123;
		Object argIgnored1 = "ignored1";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1, argIgnored1)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1, argIgnored1)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected 2 placeholders, while 1 argument was found: therefore, 1 argument is useless.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected 2 placeholders, while 1 argument was found: therefore, 1 argument is useless.");
	}

	@Test
	void format_onePlaceholder_failsWithManyAdditionalArgs() {
		// Given
		String messagePattern = "{} prova, sa sa";
		Object arg1 = 123;
		Object argIgnored1 = "ignored1";
		Object argIgnored2 = new Date();

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1, argIgnored1, argIgnored2)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1, argIgnored1, argIgnored2)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"Expected 3 placeholders, while 1 argument was found: therefore, 2 arguments are useless.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"Expected 3 placeholders, while 1 argument was found: therefore, 2 arguments are useless.");
	}

	@Test
	void format_moreThanOnePlaceholder_failsWithOneAdditionalArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";
		Object arg1 = "Daniele Trunfio";
		Object arg2 = "nice";
		Object argIgnored1 = "ignored1";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1, arg2, argIgnored1)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1, arg2, argIgnored1)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"Expected 3 placeholders, while 2 arguments were found: therefore, 1 argument is useless.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"Expected 3 placeholders, while 2 arguments were found: therefore, 1 argument is useless.");
	}

	@Test
	void format_moreThanOnePlaceholder_failsWithManyAdditionalArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";
		Object arg1 = "Daniele Trunfio";
		Object arg2 = "nice";
		Object argIgnored1 = "ignored1";
		Object argIgnored2 = new Date();

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1, arg2, argIgnored1, argIgnored2)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1, arg2, argIgnored1, argIgnored2)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected 4 placeholders, while 2 arguments were found: therefore, 2 arguments are " +
						"useless.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected 4 placeholders, while 2 arguments were found: therefore, 2 arguments are " +
						"useless.");
	}

	@Test
	void format_onePlaceholder_failsWithNoArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 1 argument, but none was given.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 1 argument, but none was given.");
	}

	@Test
	void format_moreThanOnePlaceholder_failsWithNoArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 1 argument, but none was given.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 1 argument, but none was given.");
	}

	@Test
	void format_twoPlaceholders_failsWithLessArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test";
		Object arg1 = "Johnny Dorelly";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 2 arguments, but only one was given.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 2 arguments, but only one was given.");
	}

	@Test
	void format_moreThanTwoPlaceholders_failsWithLessArgs() {
		// Given
		String messagePattern = "Hello {}, welcome to this {} test: time is {}.";
		Object arg1 = "Johnny Dorelly";
		Object arg2 = "nice";

		// When
		Throwable throwable1 = Assertions.catchThrowable(() ->
				StringFormatter.format(messagePattern, arg1, arg2)
		);
		Throwable throwable2 = Assertions.catchThrowable(() ->
				StringFormatter.formatAsCharSequence(messagePattern, arg1, arg2)
		);

		// Then
		Assertions.assertThat(throwable1)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 3 arguments, but only 2 were given.");
		Assertions.assertThat(throwable2)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Expected at least 3 arguments, but only 2 were given.");
	}

	//</editor-fold>

	private static class MyObject {
		final String value;

		public MyObject(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}


}