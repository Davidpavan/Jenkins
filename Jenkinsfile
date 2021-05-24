pipeline{
  agent any

  environment{
    PROJECT_NAME = "ROBOSHOP"
  }
  tools {
          maven 'mvn-3.6'
      }

  stages{

   stage('Build') {
    steps{
      sh 'env'
    }
   }

   stage('Two'){
   environment{
      Project_Name = "Practice stage"
   }
    steps{
      sh 'echo ${Project_Name}'
    }
   }
  }

}