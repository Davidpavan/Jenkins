def call(Map params = [:]) {
    // Start Default Arguments
    def args = [
            NEXUS_IP               : 'somee',
    ]
    args << params

    // End Default + Required Arguments
    pipeline{
        agent{
            label 'JAVA'
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