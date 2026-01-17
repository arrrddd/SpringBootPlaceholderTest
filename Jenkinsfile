pipeline {
    // 1. Start on the host machine first so we can set up our tools
    agent any

    tools {
        // 2. Jenkins downloads the Docker CLI here
        docker 'docker'
    }

    environment {
        DOCKER_IMAGE = 'my-app/placeholder'
    }

    stages {
        stage('Build & Unit Test') {
            // 3. Move the Maven container requirement here
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    publishHTML (target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report'
                    ])
                }
            }
        }

        stage('Mutation Testing') {
            // Run this in the same Maven container
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            when {
                branch 'main'
            }
            steps {
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'
            }
            post {
                always {
                    publishHTML (target: [
                        allowMissing: true,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'target/pit-reports',
                        reportFiles: 'index.html',
                        reportName: 'PIT Mutation Report'
                    ])
                }
            }
        }

        stage('Build Docker Image') {
            // 4. This stage uses 'agent any' by default (stepping out to your PC)
            steps {
                script {
                    sh 'docker build -t ${DOCKER_IMAGE}:latest -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .'
                    sh 'docker image prune -f'
                }
            }
        }
    }

    post {
        failure { echo 'Pipeline failed!' }
        success { echo 'Pipeline succeeded!' }
    }
}