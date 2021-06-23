package com.robertotru.textresolver;

import java.time.LocalDate;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class TextResolverBenchmark {

	@Param({"This is %s a String test. Now we have a double %s. This is a date %s. Finally we have a boolean %s.",
			"Now I escape %%s. But we have a String test %s. A double %s. A date %s. A boolean %s. Another escape %%s",
			"String: %s. Double %s. Date %s. Boolean %s.",
			"What we have now is a String %s, followed by a double %s, a date %s, and a boolean %s.",
			"Yet another one: %s-%s-%s-%s."})
	public String templateStringFormat;

	@Param({"This is {} a String test. Now we have a double {}. This is a date {}. Finally we have a boolean {}.",
			"Now I escape \\{}. But we have a String test {}. A double {}. A date {}. A boolean {}. Another escape \\{}",
			"String: {}. Double {}. Date {}. Boolean {}.",
			"What we have now is a String {}, followed by a double {}, a date {}, and a boolean {}.",
			"Yet another one: {}-{}-{}-{}."})
	public String templateStringFormatter;

	String param1 = "John Snow";

	double param2 = 1234.567890;

	LocalDate param3 = LocalDate.now();

	boolean param4 = true;

	@Benchmark
	public void stringFormat(Blackhole blackhole) {
		blackhole.consume(String.format(
				templateStringFormat,
				param1,
				param2,
				param3,
				param4
		));
	}


	@Benchmark
	public void textResolver(Blackhole blackhole) {
		blackhole.consume(TextResolver.resolve(
				templateStringFormatter,
				param1,
				param2,
				param3,
				param4
		));
	}

}
