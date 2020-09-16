pipeline {

 environment {
     dockerImage = ""
  }
    agent any

    tools {
        maven 'maven'
    }


    stages {
        stage('Compile Stage') {
            steps {
                sh 'echo hello'
                sh 'pwd'
                sh 'mvn -version'
                sh 'mvn compile'
            }
        }

        stage('Build Stage') {
            steps {
                sh 'echo Build time'
                sh 'pwd'
                sh 'mvn package -DskipTests'
            }
        }

        stage('DOCKER TIME'){
            steps{
                script {
                   dockerImage =  docker.build "rowanf/algogenerator:$BUILD_NUMBER"
                    sh 'pwd'
                }
            }
        }

        stage('DEPLOY '){
            steps{
                script {
                    docker.withRegistry( '', "mydocker-cred" ) {
                        dockerImage.push()
                    }
                }
            }
        }
    }


}
