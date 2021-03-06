folder('CI-Pipelines') {
    displayName('CI Pipelines')
    description('CI Pipelines')
}

def component = ["frontend", "login", "users", "todo"];

def count=(component.size()-1)
for (i in 0..count) {
    def j=component[i]
  pipelineJob("CI-Pipelines/${j}-ci") {
      configure { flowdefinition ->
          flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
              'triggers' {
                  'hudson.triggers.SCMTrigger' {
                      'spec'('* * * * 1-5')
                      'ignorePostCommitHooks'(false)
                  }
              }
          }
          flowdefinition << delegate.'definition'(class: 'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition', plugin: 'workflow-cps') {
              'scm'(class: 'hudson.plugins.git.GitSCM', plugin: 'git') {
                  'userRemoteConfigs' {
                      'hudson.plugins.git.UserRemoteConfig' {
                          'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
                          'url'('https://github.com/Davidpavan/'+j+'.git')
                      }
                  }
                  'branches' {
                      'hudson.plugins.git.BranchSpec' {
                          'name'('*/tags/*')
                      }
                  }
              }
              'scriptPath'('Jenkinsfile-Docker')
              'lightweight'(true)
          }
      }
  }
}

pipelineJob("Deployment Pipeline") {
    configure { flowdefinition ->
        flowdefinition << delegate.'definition'(class: 'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition', plugin: 'workflow-cps') {
            'scm'(class: 'hudson.plugins.git.GitSCM', plugin: 'git') {
                'userRemoteConfigs' {
                    'hudson.plugins.git.UserRemoteConfig' {
                        'url'('https://github.com/Davidpavan/Jenkins.git')
                    }
                }
                'branches' {
                    'hudson.plugins.git.BranchSpec' {
                        'name'('main')
                    }
                }
            }
            'scriptPath'('Jenkinsfile-Deployment')
            'lightweight'(true)
        }
    }
  }
