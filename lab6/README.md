# Quality gate 
When we first set up our SonarQube with its default settings, we will have Sonar reporting that our code is clean, however it won't "fail" it with its begining state. It will only be taking action on the upcoming increments. 

# SonarQube command
If we want to void writing the port on the verify command such as
`mvn clean verify sonar:sonar -Dsonar.host.url=[url] -Dsonar.projectKey=[project_name] -Dsonar.token=[key]`

Then we should add Global settings to our pom file

# 6.1 ex f
________________________________________________________________________________________________________________________________________________________
| Issue                     | Problem description                                                                       | How to solve it               |

| code smell (unused)       | Remove this unused import 'java.security.NoSuchAlgorithmException'.                       | Remove the unused import      |

| code smell (pitfall)      | Refactor the code in order to not assign to this loop counter from within the loop body.  | Use a while loop instead      |

| code smell (convention)   | Reorder the modifiers to comply with the Java Language Specification.                     | change order in this case     |

---------------------------------------------------------------------------------------------------------------------------------------------------------

# 6.2 ex a

Technical Debt - 1h 35min

In other words, we would have to spend around 1h and 35mins to fix these problems, or we can just ignore them... 
And that's how technical debt accumulates, by having the "build now, fix later" mentality.

## ex c
In terms of code coverage, it has 87.5% coverage and only 3 lines not covered.
