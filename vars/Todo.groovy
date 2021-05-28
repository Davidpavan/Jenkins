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
        tools {
            go 'Go 1.11.4'
        }
        environment {
            COMPONENT       = "${args.COMPONENT}"
            NEXUS_IP        = "${args.NEXUS_IP}"
            PROJECT_NAME    = "${args.PROJECT_NAME}"
            SLAVE_LABEL     = "${args.SLAVE_LABEL}"
            APP_TYPE        = "${args.APP_TYPE}"
            GO111MODULE     = 'on'
        }
        stages{
            stage('Downloading dependencies '){
                when{
                    environment name: 'APP_TYPE', value: 'NGINX'
                }
                steps{
                    sh '''
                    npm install && npm run build
                    '''
                }
            }

            stage('Downloading dependencies'){
                when{
                    environment name: 'APP_TYPE', value: 'GO'
                }
                steps{
                    sh '''
                    go build
                    '''
                }
            }

            stage('compile code'){
                when{
                    environment name: 'APP_TYPE', value: 'MAVEN'
                }
                steps{
                    sh '''
                    mvn compile
                    '''
                }
            }
            stage('Make Package'){
                when{
                    environment name: 'APP_TYPE', value: 'MAVEN'
                }
                steps{
                    sh '''
                    mvn package
                    '''
                }
            }

            stage('Downloading Dependencies'){
                when{
                    environment name: 'APP_TYPE', value: 'NODEJS'
                }
                steps{
                    sh '''
                    npm install
                    '''
                }
            }
            stage('Preparing Artifacts'){

                steps{
                    script{
                        prepare = new nexus()
                        prepare.make_artifacts ("APP_TYPE" , "${COMPONENT}")
                    }
                    sh '''
                    ls
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