plugins {
    id("org.javamodularity.moduleplugin") version "1.8.12"
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
    mixedJavaRelease(8)
}

tasks.getByName<Jar>("sourcesJar") {
    // When the mixedJavaRelease is set, the module-info.class will be packaged into the sourcesJar
    exclude("module-info.class")
}

tasks.compileTestJava {
    extensions.configure<org.javamodularity.moduleplugin.extensions.CompileTestModuleOptions> {
        isCompileOnClasspath = true
    }
}

javafx {
    version = "11"
    modules = listOf("javafx.base", "javafx.graphics")
    configuration = "compileOnly"
}

repositories {
    mavenCentral()
}

val checkstyleConfig by configurations.creating {}

dependencies {
    api("com.google.code.gson:gson:2.10")
    compileOnlyApi("org.jetbrains:annotations:23.0.0")
    testImplementation("junit:junit:4.13.2")
    checkstyleConfig("org.hildan.checkstyle:checkstyle-config:2.5.0")
}

configurations {
    testImplementation.get().extendsFrom(compileOnly.get())
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
