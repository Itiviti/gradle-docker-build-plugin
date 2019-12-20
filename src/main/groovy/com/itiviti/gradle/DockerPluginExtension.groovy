package com.itiviti.gradle

import groovy.transform.CompileStatic

@CompileStatic
class DockerPluginExtension {
    String dockerRegistry
    String imageName

    String getFullImageName() {
        dockerRegistry ? "$dockerRegistry/$imageName" : imageName
    }
}