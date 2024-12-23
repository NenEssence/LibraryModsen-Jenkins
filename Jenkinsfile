pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    if (fileExists('.git')) {
                        echo 'Git repository exists, fetching updates'
                        sh 'git fetch'

                        def changes = sh(script: 'git rev-list HEAD..origin/main --count', returnStdout: true).trim()

                        if (changes == '0') {
                            echo 'No updates found in the repository'
                        } else {
                            echo 'Updates found, pulling changes'
                            sh 'git pull'
                        }
                    } else {
                        echo 'Cloning new repository'
                        git branch: 'main', url: 'https://github.com/NenEssence/LibraryModsen-Jenkins'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project using Maven'
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests'
                sh 'mvn test'
            }
        }

        stage('Package Artifact') {
            steps {
                echo 'Packaging the application'
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }
        success {
            echo 'Build succeeded'
        }
        failure {
            echo 'Build failed'
        }
    }
}
