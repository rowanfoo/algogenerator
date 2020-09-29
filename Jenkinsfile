pipeline {

  environment {
       dockerImage = ""
       image_name=""
       name="algogenerator"
       portno="8004"
       targetport="10700"
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
                 image_name="rowanf/algogenerator:$BUILD_NUMBER"
                   dockerImage =  docker.build image_name
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


        stage('Build') {
                    steps {
                        sh 'ssh -p 1600 root@192.168.0.10 date'
                         sh "ssh -p 1600 root@192.168.0.10 ansible-playbook -vvv /home/rowan/myplaybook.yaml -e \"name=${name}\"  -e \"image_name=${image_name}\" -e \"portno=${portno}\" -e \"targetport=${targetport}\"  "
                    }
               }


   ////
    }


}




/*
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
                sh 'w'
            }
        }



}
}
*/
