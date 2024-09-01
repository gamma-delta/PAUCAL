#!/usr/bin/env groovy

pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    agent any
    tools {
        jdk "jdk-21"
    }
    environment {
        discordWebhook = credentials('discordWebhook')
        CURSEFORGE_TOKEN = credentials('curseforgeApiKey')
        MODRINTH_TOKEN = credentials('modrinthApiKey')
    }
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build') {
            steps {
                echo 'Building'
                sh './gradlew build'
            }
        }
        stage('Publish') {
            when {
                anyOf {
                    branch '1.21'
                }
            }
            stages {
                stage('Deploy Previews') {
                    steps {
                        echo 'Deploying previews to various places'
                        sh './gradlew publish publishToDiscord'
                    }
                }
                stage('Deploy releases') {
                    steps {
                        echo 'Maybe deploy releases'
                        sh './gradlew --stacktrace publishCurseforge publishModrinth'
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts 'Common/build/libs/*+common-*.jar'
            archiveArtifacts 'Common/build/libs/*+common-*-javadoc.jar'
            archiveArtifacts 'Common/build/libs/*+common-*-sources.jar'
            archiveArtifacts 'Neoforge/build/libs/*+neoforge-*.jar'
            archiveArtifacts 'Neoforge/build/libs/*+neoforge-*-javadoc.jar'
            archiveArtifacts 'Neoforge/build/libs/*+neoforge-*-sources.jar'
            archiveArtifacts 'Fabric/build/libs/*+fabric-*.jar'
            archiveArtifacts 'Fabric/build/libs/*+fabric-*-javadoc.jar'
            archiveArtifacts 'Fabric/build/libs/*+fabric-*-sources.jar'
        }
    }
}