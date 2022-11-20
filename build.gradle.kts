plugins {
    id("org.openjfx.javafxplugin") version "0.0.13"
    `java-library`
    jacoco
    checkstyle
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.hildan.github.changelog") version "1.11.1"
}

group = "org.hildan.fxgson"
description = "A set of type adapters for Google Gson to make JavaFX properties serialization more natural."

java {
    withJavadocJar()
    withSourcesJar()
}

javafx {
    version = "11"
    modules = listOf("javafx.base", "javafx.graphics")
}

repositories {
    mavenCentral()
}

val checkstyleConfig by configurations.creating {}

dependencies {
    api("com.google.code.gson:gson:2.8.6")
    compileOnlyApi("org.jetbrains:annotations:18.0.0")
    testImplementation("junit:junit:4.+")
    checkstyleConfig("org.hildan.checkstyle:checkstyle-config:2.4.1")
}

checkstyle {
    maxWarnings = 0
    toolVersion = "8.29"
    config = resources.text.fromArchiveEntry(checkstyleConfig, "checkstyle.xml")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = 1.0.toBigDecimal()
            }
        }
    }
}
tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

changelog {
    futureVersionTag = project.version.toString()
    sinceTag = "1.2.0"
    customTagByIssueNumber = mapOf(12 to "3.1.0")
}

nexusPublishing {
    packageGroup.set("org.hildan")
    repositories {
        sonatype()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            val githubUser = findProperty("githubUser") as String? ?: System.getenv("GITHUB_USER")
            val githubSlug = "$githubUser/${rootProject.name}"
            val githubRepoUrl = "https://github.com/$githubSlug"

            pom {
                name.set(project.name)
                description.set(project.description)
                url.set(githubRepoUrl)
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("joffrey-bion")
                        name.set("Joffrey Bion")
                        email.set("joffrey.bion@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:$githubRepoUrl.git")
                    developerConnection.set("scm:git:git@github.com:$githubSlug.git")
                    url.set(githubRepoUrl)
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["maven"])
}
