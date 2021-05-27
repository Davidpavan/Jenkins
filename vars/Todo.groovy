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
            APP_TYPE        = "${args.APP_TYPE}"
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
            stage('Preparing Artifacts - NGINX'){
                when{
                    environment name: 'APP_TYPE', value: 'NGINX'
                }
                steps{
                    sh '''
                    echo ${COMPONENT}
                    zip -r ${COMPONENT}.zip *
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
            stage('Preparing Artifacts - JAVA'){
                steps{
                    sh '''
                    cp target/*.jar ${COMPONENT}.jar
                    zip -r ${COMPONENT}.zip ${COMPONENT}.jar
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

            stage('Preparing Artifacts-NODEJS'){
                when{
                    environment name: 'APP_TYPE', value: 'NODEJS'
                }
                steps{
                    sh '''
                    zip -r ${COMPONENT}.zip node_modules server.js
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