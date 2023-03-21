package com.itiviti.gradle

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.internal.os.OperatingSystem

import javax.annotation.Nullable

class DockerLintTask extends AbstractExecTask {
    @InputFile
    @Nullable
    @Optional
    File dockerFile

    DockerLintTask() {
        super(DockerLintTask)
    }

    @Override
    void exec(){
        if (OperatingSystem.current().isWindows()) {
            commandLine "powershell", "-c", "cat", "$dockerFile.absolutePath", "|", "docker", "run", "--rm", "-i", "hadolint/hadolint"
        } else {
            commandLine 'docker', 'run', '--rm', '-i', 'hadolint/hadolint', '<', "$dockerFile.absolutePath"
        }
    }
}
