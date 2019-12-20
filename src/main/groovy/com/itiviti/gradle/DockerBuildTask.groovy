package com.itiviti.gradle


import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

import javax.annotation.Nullable

class DockerBuildTask extends AbstractExecTask {

    @Input
    Property<String> imageName

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

    DockerBuildTask() {
        super(DockerBuildTask)

        executable('docker')
        imageName = project.getObjects().property(String)
    }

    @Override
    void exec() {
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

        super.exec()
    }
}
