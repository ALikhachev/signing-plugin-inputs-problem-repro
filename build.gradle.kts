plugins {
    java
    `maven-publish`
    signing
}

group = "org.example"
version = "1.0-SNAPSHOT"

publishing {
    repositories {
        maven(rootDir.resolve("repo"))
    }
    publications {
        create<MavenPublication>("pub1") {
            groupId = "org.example"
            artifactId = "pub1"
            from(components["java"])
        }
        create<MavenPublication>("pub2") {
            groupId = "org.example"
            artifactId = "pub2"
            from(components["java"])
        }
    }
}

if (providers.gradleProperty("sign-publications").orNull.toBoolean()) {
    signing {
        sign(publishing.publications)
    }
}

if (providers.gradleProperty("apply-ugly-workaround").orNull.toBoolean()) {
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(tasks.withType<Sign>())
    }
}