Reasons to use mocks:
- not yet available
- variability
- slow/latency


Whenever we're using annotations for mockito, we're forced to use @ExtendWith (requiring us to add mockito-junit-jupiter to the pom)


If we're using unnecessaryExpections, an error will be thrown warning us about it.

LENIENT (non-strict)
Allows us to run code with UnnecessaryExpectations 

Verify is mostly used to guarantee that a certain method has been called X amount of times - however this is not required to run the test.

<scope> X </scope> will only include the dependency within our chosen scope (per example, test scope)