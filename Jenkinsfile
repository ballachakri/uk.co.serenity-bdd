pipeline {
    agent {
        docker {
            // Official image with Maven + Java 17
            image 'maven:3.9.9-eclipse-temurin-17'
            // Cache Maven repo between builds for speed (optional but nice)
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['default', 'dev', 'qa', 'stage'],
            description: 'Serenity environment (-Denvironment)'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run browser in headless mode (-Dheadless.mode)'
        )
    }

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh """
                    mvn -B clean verify \
                      -Denvironment=${params.ENVIRONMENT} \
                      "-Dheadless.mode=${params.HEADLESS}"
                """
            }
        }

        stage('Publish Reports') {
            steps {
                // JUnit XML results (for Jenkins test trend)
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml,target/failsafe-reports/*.xml'

                // Serenity HTML report (needs "HTML Publisher" plugin installed)
                publishHTML([
                    reportDir: 'target/site/serenity',
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    reportName: 'Serenity Test Report'
                ])

                // Optional: archive all Serenity report files
                archiveArtifacts artifacts: 'target/site/serenity/**', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Build finished with status: ${currentBuild.currentResult}"
        }
        failure {
            echo "Build failed. Check Serenity report and JUnit test results."
        }
    }
}
