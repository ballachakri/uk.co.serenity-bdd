pipeline {
    agent any

    options {
        timestamps()
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
                // assumes "mvn" is on PATH for the Jenkins user
                bat """
                    mvn -B clean verify ^
                      -Denvironment=${params.ENVIRONMENT} ^
                      "-Dheadless.mode=${params.HEADLESS}"
                """
            }
        }

        stage('Publish Reports') {
            steps {
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml,target/failsafe-reports/*.xml'

                publishHTML([
                    reportDir: 'target/site/serenity',
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    reportName: 'Serenity Test Report'
                ])

                archiveArtifacts artifacts: 'target/site/serenity/**', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Build finished with status: ${currentBuild.currentResult}"
        }
        failure {
            echo "Build failed. Check Serenity report and JUnit results."
        }
    }
}
