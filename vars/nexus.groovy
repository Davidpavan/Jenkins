def nexus(COMPONENT) {
    command = "curl -f -v -u admin:admin123 --upload-file ${COMPONENT}.zip http://${NEXUS_IP}:8081/repository/${COMPONENT}/${COMPONENT}.zip"
    def execute_state=sh(returnStdout: true, script: command)
}

def make_artifacts(APP_TYPE, component){
    if(APP_TYPE == "NGINX"){
        command = "echo ${COMPONENT} && zip -r ${COMPONENT}.zip *"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    } else if(APP_TYPE == "GO"){
        command = "zip -r ${COMPONENT}.zip *"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    } else if(APP_TYPE == "JAVA"){
        command = "cp target/*.jar ${COMPONENT}.jar && zip -r ${COMPONENT}.zip ${COMPONENT}.jar"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    } else if(APP_TYPE == "NODEJS"){
        command = "zip -r ${COMPONENT}.zip node_modules server.js"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    }
}