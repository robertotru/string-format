# text-resolver
Helper class for an efficient string argument injection.


#JMH Benchmark: Comparing String#format
There are some JMH tests comparing Java `String.format` with the provided utility.
Overall, average throughput goes from 0.001703125 to 0.00769448. Hence, the new utility improves the throughput by a 
352%
