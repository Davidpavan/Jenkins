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
        triggers {
            pollSCM('*/2 * * * 1-5')
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
            stage('Build code & Downloading dependencies '){
                steps{
                    script{
                        build = new nexus()
                        build.code_build ("${APP_TYPE}" , "${COMPONENT}")
                    }
                }
            }

            stage('Preparing Artifacts'){

                steps{
                    script{
                        prepare = new nexus()
                        prepare.make_artifacts ("${APP_TYPE}" , "${COMPONENT}")
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