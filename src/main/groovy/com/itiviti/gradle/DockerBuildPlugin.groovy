package com.itiviti.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerBuildPlugin implements Plugin<Project> {
    private static final char[] FORBIDDEN_CHARACTERS = ['/', '\\', ':', '<', '>', '"', '?', '*', '|' ] as char[]

    void apply(Project project) {
        project.apply plugin: 'base'
        project.apply plugin: 'publishing'

        def extension = project.extensions.create('docker', DockerPluginExtension)
        extension.dockerRegistry = project.properties.dockerRepo

        project.tasks.withType(DockerBuildTask) { DockerBuildTask buildTask ->
            project.tasks.assemble.dependsOn(buildTask)
            buildTask.imageName.set(project.provider { extension.fullImageName })
        }

        project.afterEvaluate {
            project.tasks.withType(DockerBuildTask) { DockerBuildTask buildTask ->

                def formattedImageName = format(buildTask.imageName.get())
                buildTask.tags.each { buildTag ->
                    def formattedTag = format(buildTag)
                    def pushTask = project.task("push_${formattedImageName}_$formattedTag", type: DockerPushTask) {
                        imageName.set(project.provider { buildTask.imageName.get() })
                        tag.set(project.provider { buildTag })
                    }
                    pushTask.dependsOn(buildTask)
                    project.tasks.publish.dependsOn(pushTask)
                }
            }
        }
    }

    static String format(String name) {
        FORBIDDEN_CHARACTERS.each { c -> name = name.replace(c, '_' as char) }
        return name
    }
}
