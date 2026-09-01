package com.itiviti.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.process.ExecOperations

import javax.annotation.Nullable
import javax.inject.Inject

class DockerLintTask extends DefaultTask {
    @InputFile
    @Nullable
    @Optional
    File dockerFile

    private final ExecOperations execOperations

    @Inject
    DockerLintTask(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    @org.gradle.api.tasks.TaskAction
    void lint() {
        execOperations.exec {
            executable 'docker'
            args 'run', '--rm', '-i', 'hadolint/hadolint'
            if (dockerFile) {
                standardInput = dockerFile.newInputStream()
            }
        }
    }
}
