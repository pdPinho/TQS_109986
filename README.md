# Teste e Qualidade de Software
> Pedro Pinho 109986

## Useful information
- Tests in maven are located in src/test/...
> org.junit.Test               <-- 4th version
> org.junit.jupiter.api.Test   <-- 5th version

> Unit testing with throwables:

```
`public static < T extends Throwable > T assertThrows(Class< T > expectedType, Executable executable)`
> Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
> If no exception is thrown, or if an exception of a different type is thrown, this method will fail.
```

## Useful commands
- mvn clean test jacoco:report
