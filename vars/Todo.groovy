def call(Map params = [:]) {
    // Start Default Arguments
    def args = [
            NEXUS_IP               : '172.31.14.124',
    ]
    args << params

    // End Default + Required Arguments
    pipeline{
        agent any
        environment{
            COMPONENT = "${args.COMPONENT}"
        }
        stages{
            stage('Preparing Artifacts'){
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
                        nexus
                    }
                }
            }
        }
    }
}