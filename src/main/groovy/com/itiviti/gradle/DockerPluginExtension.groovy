package com.itiviti.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Project

@CompileStatic
class DockerPluginExtension {
    String dockerRegistry
    String imageName
    File   DockerFile

    DockerPluginExtension(Project project){
        this.DockerFile = project.file('Dockerfile')
    }

    String getFullImageName() {
        dockerRegistry ? "$dockerRegistry/$imageName" : imageName
    }
}