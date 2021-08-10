def call(Map params = [:]) {
    // Start Default Arguments
    def args = [
            SLAVE_LABEL               : "DOCKER"
    ]
    args << params

    // End Default + Required Arguments
    pipeline{
        agent{
            node {
                label "${args.SLAVE_LABEL}"
            }
        }
        triggers {
            pollSCM('* * * * 1-5')
        }
        environment {
            COMPONENT       = "${args.COMPONENT}"
            PROJECT_NAME    = "${args.PROJECT_NAME}"
            SLAVE_LABEL     = "${args.SLAVE_LABEL}"
            APP_TYPE        = "${args.APP_TYPE}"
        }
        stages{
            stage('Build code & Downloading dependencies '){
                steps{
                    script{
                        sh '''
                          docker build -t local .
                        '''
                    }
                }
            }

        }
    }
}