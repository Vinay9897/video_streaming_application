/* groovylint-disable NestedBlockDepth */
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code
                checkout scm
            }
        }

        stage('Build Services') {
            parallel {
                stage('Build Stream Front-End') {
                    steps {
                        dir('stream-front-end') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Stream Back-End') {
                    steps {
                        dir('stream-backend') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('Run Tests') {
            parallel {
                stage('Test Stream Front-End') {
                    steps {
                        dir('stream-front-end') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Stream Back-End') {
                    steps {
                        dir('stream-backend') {
                            sh 'mvn test'
                        }
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            parallel {
                stage('Build and Push Stream Front-End') {
                    steps {
                        dir('stream-front-end') {
                            sh 'docker build -t stream-front-end/ .'
                            sh 'docker push /stream-front-end'
                        }
                    }
                }
                stage('Build and Push Stream Back-End') {
                    steps {
                        dir('stream-backend') {
                            sh 'docker build -t stream-backend .'
                            sh 'docker push stream-backend'
                        }
                    }
                }
            }
        }

        stage('Database Migration') {
            steps {
                // Example of running a migration script
                sh 'mysql -h localhost -u root -p root video-stream < path/to/migration.sql'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deployment logic for both services
                    sh 'ssh your-server "docker pull stream-front-end && docker run -d stream-front-end"'
                    sh 'ssh your-server "docker pull stream-backend && docker run -d stream-backend"'
                }
            }
        }
    }

    post {
        always {
            cleanWs() // Clean up workspace
        }
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Build or Deployment failed!'
        }
    }
}
