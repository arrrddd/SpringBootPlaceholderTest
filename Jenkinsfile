pipeline {
    agent {
        docker {
            // Use a Docker image that has both Java 21 and Maven installed
            image 'maven:3.9.6-eclipse-temurin-21'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        DOCKER_IMAGE = 'my-app/placeholder'
    }

    stages {
        stage('Build & Unit Test') {
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
            //exit Maven container
            agent any
            steps {
                script {
                    // Build the image using the Dockerfile in the root
                    // We use the jar built in the previous stage (workspace is shared)
                    sh 'docker build -t ${DOCKER_IMAGE}:latest -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .'
                    sh 'docker image prune -f'
                }
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed!'
        }
        success {
            echo 'Pipeline succeeded!'
        }
    }
}
