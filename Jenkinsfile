pipeline{
  agent any

  environment{
    PROJECTNAME = "ROBOSHOP"
  }

  stages{

   stage('Build') {
    steps{
      sh 'echo ${PROJECTNAME}'
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