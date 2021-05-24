pipeline{
  agent any

  environment{
    PROJECT_NAME = "ROBOSHOP"
    UBUNTU_SSH_CRED = credentails('ubuntu-ssh')
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