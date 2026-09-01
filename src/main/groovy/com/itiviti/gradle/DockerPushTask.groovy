package com.itiviti.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import javax.inject.Inject

class DockerPushTask extends DefaultTask {
    @Input
    final Property<String> imageName

    @Input
    final Property<String> tag

    private final ExecOperations execOperations

    @Inject
    DockerPushTask(ExecOperations execOperations) {
        this.execOperations = execOperations
        imageName = project.getObjects().property(String)
        tag = project.getObjects().property(String)
    }

    @TaskAction
    void push() {
        def image = "${imageName.get()}${tag.get() ? ":${tag.get()}" : ''}"
        execOperations.exec {
            executable 'docker'
            args 'push', image
        }
    }
}
