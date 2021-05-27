def nexus() {
    command = "curl -f -v -u admin:admin123 --upload-file frontend.zip http://192.168.0.84:8081/repository/frontend/frontend.zip"
    def execute_state=sh(returnStdout: true, script: command)
}
