# Teste e Qualidade de Software
> Pedro Pinho 109986

## Useful information
> Tests in maven are located in src/test/...

(org.junit.Test               <-- 4th version)

(org.junit.jupiter.api.Test   <-- 5th version)


> Unit testing with throwables:

```
public static < T extends Throwable > T assertThrows(Class< T > expectedType, Executable executable)
> Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
> If no exception is thrown, or if an exception of a different type is thrown, this method will fail.
```

## Useful commands
- mvn clean test jacoco:report


## Jacoco analysis
![Screenshot from 2024-02-16 10-58-19](https://github.com/pdPinho/TQS_109986/assets/125307135/72725d34-1480-4cc7-9809-fc088fb6434c)

When comparing or analysing jacoco results we should always keep in mind that test for **equals** and **hashcode** aren't necessarily needed, which can lead to "false positives".

However, when we analyse the rest, we come to face with some big problems, such as **fromArray(int[])** and **add(int)**, which could lead to conflicts even thought they are simple things.

### Fixing tests problems based on Jacoco
As we mentioned previously, big problems are our biggest concern, so after a careful analysis as to how to tackle them, we end up with the following
