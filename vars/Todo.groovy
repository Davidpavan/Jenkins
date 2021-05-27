def call(Map params = [:]) {
    // Start Default Arguments
    def args = [
            NEXUS_IP               : '192.168.0.84',
    ]
    args << params

    // End Default + Required Arguments
    pipeline{
        agent{
            label "${args.SLAVE_LABEL}"
        }
        environment {
            COMPONENT       = "${args.COMPONENT}"
            NEXUS_IP        = "${args.NEXUS_IP}"
            PROJECT_NAME    = "${args.PROJECT_NAME}"
            SLAVE_LABEL     = "${args.SLAVE_LABEL}"

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
                    echo ${COMPONENT}
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