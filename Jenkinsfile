node {
    def WORKSPACE = "/var/lib/jenkins/workspace/food-drug-service"
    def buildNumber = env.BUILD_NUMBER ?: '0'  // Flexible build number based on Jenkins environment
    def dockerImageTag = "rinkuls/food-drug-service:${buildNumber}"
    def helmChartName = "food-drug-service-chat"
    def releaseName = "food-drug-service-release"

    try {
        // Stage to clone the repository
        stage('Clone Repo') {
            git url: 'https://github.com/rinkuls/food-drug-service.git',
                credentialsId: 'springdeploy-user',
                branch: 'main'
        }

        // Build the application
        stage('Build') {
            bat "mvn clean install"
        }

        // Build Docker image
        stage('Build Docker Image') {
            echo "Building Docker image with tag: ${dockerImageTag}"

            // Build the Docker image
            def dockerImage = docker.build(dockerImageTag)

            // Verify the image is built correctly
            echo "Docker Image built successfully: ${dockerImageTag}"
        }

        // Push Docker Image to Docker Hub
        stage('Push Docker Image to Docker Hub') {
            withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials-id', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                // Log in to Docker Hub
                bat '''
                    docker login -u %DOCKER_HUB_USERNAME% -p %DOCKER_HUB_PASSWORD% || exit 255
                '''

                // Push Docker image to Docker Hub
                bat "docker push ${dockerImageTag} || exit 255"
            }
        }

        // Deploy to Kubernetes (Minikube) using Helm
        stage('Deploy to Minikube with Helm') {
            echo "Deploying to Minikube using Helm with image: ${dockerImageTag}"
            bat 'kubectl config use-context minikube'

            // Update Helm chart with new Docker image tag
            bat "helm upgrade --install ${releaseName} ${helmChartName} --set image.tag=${buildNumber} --namespace default || exit 255"

            // Verify Helm release status
            bat "helm status ${releaseName} || exit 255"

            // Optionally, check pod status after the deployment
            bat 'kubectl get pods --namespace default || exit 255'
        }

        //notifyBuild('SUCCESSFUL')
    } catch (Exception e) {
        echo "Error occurred: ${e.message}"
        currentBuild.result = "FAILED"
        // Notify on failure
        notifyBuild('FAILED')
        throw e
    } finally {
        echo "in finally"
    }
}

// Email notification function
def notifyBuild(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESSFUL'
    def now = new Date()
    def subject = "${buildStatus}, Job: ${env.JOB_NAME} FRONTEND - Deployment Sequence: [${env.BUILD_NUMBER}] "
    def subject_email = "Spring Boot Deployment"
    def details = """<p>${buildStatus} JOB </p>
    <p>Job: ${env.JOB_NAME} - Deployment Sequence: [${env.BUILD_NUMBER}] - Time: ${now}</p>
    <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME}</a>"</p>"""

    // Email notification
    emailext(
        to: "rinkulsharma123@gmail.com",
        subject: subject_email,
        body: details,
    )
}
