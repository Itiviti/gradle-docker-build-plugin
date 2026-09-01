package com.itiviti.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations

import javax.annotation.Nullable
import javax.inject.Inject

class DockerBuildTask extends DefaultTask {

    @Input
    final Property<String> imageName

    @Input
    List<String> tags = []

    @InputDirectory
    File basePath = new File('.')

    @InputFile
    @Nullable
    @Optional
    File dockerFile

    @Input
    @Optional
    List<String> buildArgs = []

    private final ExecOperations execOperations

    @Inject
    DockerBuildTask(ExecOperations execOperations) {
        this.execOperations = execOperations
        imageName = project.getObjects().property(String)
    }

    @TaskAction
    void buildImage() {
        execOperations.exec {
            executable 'docker'
            args 'build', basePath.path
            if (dockerFile) {
                args '-f', dockerFile.path
            }

            tags.each {
                args '-t', "${imageName.get()}:$it"
            }
            buildArgs.each {
                args '--build-arg', it
            }
        }
    }
}
