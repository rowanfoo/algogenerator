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
                withKubeConfig([credentialsId: 'kubeconff' ]) {
                      sh 'kubectl config view'
                      sh 'kubectl create -f  deployment.yaml'
                 }



        }


    }


}
}
