import org.javamodularity.moduleplugin.extensions.CompileTestModuleOptions

plugins {
    id("org.openjfx.javafxplugin") version "0.0.13"
    `java-library`
    jacoco
    checkstyle
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.hildan.github.changelog") version "1.12.1"
}

group = "org.hildan.fxgson"
description = "A set of type adapters for Google Gson to make JavaFX properties serialization more natural."

java {
    withJavadocJar()
    withSourcesJar()
}

modularity {
    // Compiles the library to bytecode level 8, so it is usable with JRE 8,
    // but also compiles module-info.java separately for Java 9+.
    // This doesn't build a multi-release jar, because it places module-info.class at the root of the jar.
    // See https://github.com/java9-modularity/gradle-modules-plugin#compilation-to-a-specific-java-release
    mixedJavaRelease(8)
}

// When mixedJavaRelease is set, the module-info.class is packaged into the sources and javadoc jars (by mistake?)
// se we have to exclude the file explicitly.
// See https://github.com/java9-modularity/gradle-modules-plugin/issues/220
tasks.getByName<Jar>("sourcesJar") {
    exclude("module-info.class")
}
tasks.getByName<Jar>("javadocJar") {
    exclude("module-info.class")
}

javafx {
    version = "11"
    modules = listOf("javafx.base", "javafx.graphics")
    // This removes any JavaFX dependency from the generated pom.xml, because consumers should define their own
    // dependencies on JavaFX to match the correct target platform.
    configuration = "compileOnly"
}

repositories {
    mavenCentral()
}

val checkstyleConfig: Configuration by configurations.creating {}

configurations {
    // provides JavaFX dependencies to tests (because they are added as compileOnly via the javafx plugin)
    testImplementation.get().extendsFrom(compileOnly.get())
}

dependencies {
    api("com.google.code.gson:gson:2.10")
    compileOnlyApi("org.jetbrains:annotations:23.0.0")
    testImplementation("junit:junit:4.13.2")
    checkstyleConfig("org.hildan.checkstyle:checkstyle-config:2.5.0")
}

checkstyle {
    maxWarnings = 0
    toolVersion = "8.29"
    config = resources.text.fromArchiveEntry(checkstyleConfig, "checkstyle.xml")
}

tasks {
    compileTestJava {
        extensions.configure<CompileTestModuleOptions> {
            // Fallback to classpath mode because with mixedJavaRelease using the module path seems to be broken.
            // Despite using classpath, the tests still seem to pick up missing declarations in module-info.java,
            // which may be good enough for now.
            isCompileOnClasspath = true
        }
    }
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = 1.0.toBigDecimal()
                }
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
