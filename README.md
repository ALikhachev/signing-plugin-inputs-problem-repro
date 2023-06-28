# Reproducer of the signing plugin publishing issue

1. Execute `./gradlew publish`

We have 2 different publications of the same code. The publication finishes without any issues.

<br/>

2. Execute `./gradlew publish -Psign-publications=true`

We still have the 2 publications, want to sign them using the signing plugin.

It fails with an error:
```
A problem was found with the configuration of task ':signPub2Publication' (type 'Sign').
  - Gradle detected a problem with the following location: 'path-to-the-project/signing-plugin-inputs-problem/build/libs/signing-plugin-inputs-problem-1.0-SNAPSHOT.jar.asc'.
    
    Reason: Task ':publishPub1PublicationToMavenRepository' uses this output of task ':signPub2Publication' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.
    
    Possible solutions:
      1. Declare task ':signPub2Publication' as an input of ':publishPub1PublicationToMavenRepository'.
      2. Declare an explicit dependency on ':signPub2Publication' from ':publishPub1PublicationToMavenRepository' using Task#dependsOn.
      3. Declare an explicit dependency on ':signPub2Publication' from ':publishPub1PublicationToMavenRepository' using Task#mustRunAfter.
    
    Please refer to https://docs.gradle.org/8.1.1/userguide/validation_problems.html#implicit_dependency for more details about this problem.

```

<br/>

3. Execute `./gradlew publish -Psign-publications=true -Papply-ugly-workaround=true`

We define cross dependencies between signing and publication tasks. It finishes almost without any issues.
However, now the same signature for the common files is being generated twice.

**Note:** the key used here should **not** be used anywhere outside the reproducer project.
