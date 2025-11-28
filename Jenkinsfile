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
                // Windows environment -> use bat
                bat """
                    mvn -B clean verify ^
                      -Denvironment=${params.ENVIRONMENT} ^
                      "-Dheadless.mode=${params.HEADLESS}"
                """
            }
        }
    }

    post {
        // This ALWAYS runs, even if mvn fails
        always {
            echo "Publishing test results and Serenity report..."

            // JUnit XML test results (Surefire)
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/TEST-*.xml'

            // Serenity HTML report (HTML Publisher plugin required)
            publishHTML(target: [
                reportDir: 'target/site/serenity',
                reportFiles: 'index.html',
                reportName: 'Serenity Test Report',
                keepAll: true,
                alwaysLinkToLastBuild: true
            ])

            // Optional: archive the full Serenity report
            archiveArtifacts artifacts: 'target/site/serenity/**', fingerprint: true

            echo "Build finished with status: ${currentBuild.currentResult}"
        }

        failure {
            echo "Build failed. Check Serenity report and JUnit results."
        }
    }
}
