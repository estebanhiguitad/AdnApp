pipeline {

  agent any


  options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
  }

  stages{

    stage('Unit Tests') {
      steps{
        echo "------------>Unit Tests<------------"
        sh './gradlew clean'
        sh './gradlew test'
      }
    }

    stage('Static Code Analysis') {
      steps{
        echo '------------>Análisis de código estático<------------'
        withSonarQubeEnv('Sonar') {
            sh "sonar-scanner -Dproject.settings=sonar-project.properties"
        }
      }
    }
  }
}
