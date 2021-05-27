def call() {
    pipeline{
        agent{
            label 'JAVA'
        }
        environment {
            COMPONENT = "${COMPONENT}"
        }
        stages{
            stage('Downloading dependencies'){
                steps{
                    sh '''
                    npm install && npm run build
                    '''
                }
            }
            stage('Preparing Artifacts'){
                steps{
                    sh '''
                    zip -r frontend.zip *
                    '''
                }
            }
            stage('Upload Artifacts'){
                steps{
                    script{
                        upload = new nexus()
                        upload.nexus ("${COMPONENT}")
                    }
                }
            }
        }
    }
}