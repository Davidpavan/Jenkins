def nexus(COMPONENT) {
    command = "curl -f -v -u admin:admin123 --upload-file ${COMPONENT}.zip http://192.168.0.84:8081/repository/${COMPONENT}/${COMPONENT}.zip"
    def execute_state=sh(returnStdout: true, script: command)
}
