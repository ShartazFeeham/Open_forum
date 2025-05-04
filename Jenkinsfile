def services = ['configserver', 'discoveryserver', 'gateway', 'posts', 'notification', 'review', 'users']

pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'feeham'
        REGISTRY_CREDENTIALS_ID = 'dockerhub_id'
        DOCKER_REGISTRY_URL = 'https://registry.hub.docker.com'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ShartazFeeham/Open_forum.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    services.each { service ->
                        stage("Build ${service}") {
                            dir(service) {
                                echo "🔨 Building Docker image for ${service}"
                                docker.build("${DOCKER_REGISTRY}/open_forum_${service}:${env.BUILD_NUMBER}")
                            }
                        }
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    services.each { service ->
                        stage("Push ${service}") {
                            def imageName = "${DOCKER_REGISTRY}/open_forum_${service}"
                            def image = docker.image("${imageName}:${env.BUILD_NUMBER}")

                            echo "📦 Pushing Docker image for ${service}..."

                            docker.withRegistry(DOCKER_REGISTRY_URL, REGISTRY_CREDENTIALS_ID) {
                                image.push("${env.BUILD_NUMBER}")
                                image.push("latest")
                            }

                            echo "✅ Pushed Docker image for ${service}"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully.'
        }
        failure {
            echo '❌ Pipeline failed.'
        }
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
            echo '🧹 Cleanup completed.'
        }
    }
}
