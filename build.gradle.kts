import com.jfrog.bintray.gradle.BintrayExtension.*

plugins {
    id("com.jfrog.bintray") version "1.8.4"
    id("org.hildan.github.changelog") version "0.8.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
    `java-library`
    `maven-publish`
    jacoco
    checkstyle
}

group = "org.hildan.fxgson"
description = "A set of type adapters for Google Gson to make JavaFX properties serialization more natural."

val Project.labels get() = arrayOf("json", "gson", "javafx", "property")
val Project.licenses get() = arrayOf("MIT")

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
    implementation("org.jetbrains:annotations:18.0.0")
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
    sinceTag = "v1.2"
    releaseUrlTemplate = "https://bintray.com/joffrey-bion/maven/fx-gson/%s"
    releaseUrlTagTransform = { it.substring(1) }
    customTagByIssueNumber = mapOf(12 to "v3.1.0")
}

val githubUser = getPropOrEnv("githubUser", "GITHUB_USER")
val githubRepoName = rootProject.name
val githubSlug = "$githubUser/${rootProject.name}"
val githubRepoUrl = "https://github.com/$githubSlug"

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

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

bintray {
    user = getPropOrEnv("bintrayUser", "BINTRAY_USER")
    key = getPropOrEnv("bintrayApiKey", "BINTRAY_KEY")
    setPublications("maven")
    publish = true

    pkg(closureOf<PackageConfig> {
        repo = getPropOrEnv("bintrayRepo", "BINTRAY_REPO")
        name = project.name
        desc = project.description
        setLabels(*project.labels)
        setLicenses(*project.licenses)

        websiteUrl = githubRepoUrl
        issueTrackerUrl = "$githubRepoUrl/issues"
        vcsUrl = "$githubRepoUrl.git"
        githubRepo = githubSlug

        version(closureOf<VersionConfig> {
            desc = project.description
            vcsTag = project.version.toString()
            gpg(closureOf<GpgConfig> {
                sign = true
            })
            mavenCentralSync(closureOf<MavenCentralSyncConfig> {
                sync = true
                user = getPropOrEnv("ossrhUserToken", "OSSRH_USER_TOKEN")
                password = getPropOrEnv("ossrhKey", "OSSRH_KEY")
            })
        })
    })
}
tasks.bintrayUpload {
    dependsOn(tasks.build)
}

fun Project.getPropOrEnv(propName: String, envVar: String? = null): String? =
    findProperty(propName) as String? ?: System.getenv(envVar)
