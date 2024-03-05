# Review questions:

> a) Identify a couple of examples that use AssertJ expressive methods chaining.
- assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());  || A_EmployeeRepositoryTest.java
- assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());   || A_EmployeeRepositoryTest.java
- assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());   || B_EmployeeService_UniTtest.java

> b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).
- B_EmployeeService_UnitTest is mocking the behaviour of a repository (employeeRepository)

> c) What is the difference between standard @Mock and @MockBean?
- @Mock is used to write fast tests where we mock objects. (favours isolation)
- @MockBean is used when we rely on Spring Boot container and want to mock one of the container beans.

Whilst @Mock can be used in Spring Boot, it isn't ideal due to creating standalone mock objects that are not managed by Spring. This can be cumbersome and lead to errors since it requires manually injecting the mock into the Spring Context.
On the other hand, @MockBean replaces any existing bean with a mockito mock and allows you to use things such as @SpringBootTest and @WebMvcTest (both expect Mocks to be managed by Spring context). On top of that, @MockBean makes code more readable.

> d) What is the role of the file "application-integrationtest.properties"? In which conditions will it be used?
- As the name indicates, it will be used specifically during integration tests.
- As to what the file does, it simply overrides properties in our tests if we are to include @TestPropertySource.

> e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences.
- Differences:

C) Runs simplified and light environment - mocking the results of controllers and not having repository component involved. In this case we are mocking EmployeeService.

D) Test the entire Spring boot application but do not include API client, by mocking it.

E) Same as D) but we use a REST client for requests.
