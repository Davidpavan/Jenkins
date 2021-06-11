folder('CI-Pipelines') {
    displayName('CI Pipelines')
    description('CI Pipelines')
}

pipelineJob('CI-Pipelines/frontend-ci') {
    configure { flowdefinition ->
        flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
            'triggers' {
                'hudson.triggers.SCMTrigger' {
                    'spec'('*/2 * * * 1-5')
                    'ignorePostCommitHooks'(false)
                }
            }
        }
            flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
            'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
                'userRemoteConfigs' {
                    'hudson.plugins.git.UserRemoteConfig' {
                        'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
                        'url'('https://github.com/Davidpavan/frontend.git')
                    }
                }
                'branches' {
                    'hudson.plugins.git.BranchSpec' {
                        'name'('*/tags/*')
                    }
                }
            }
            'scriptPath'('Jenkinsfile')
            'lightweight'(true)
        }
    }
}

pipelineJob('CI-Pipelines/todo-ci') {
    configure { flowdefinition ->
        flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
            'triggers' {
                'hudson.triggers.SCMTrigger' {
                    'spec'('*/2 * * * 1-5')
                    'ignorePostCommitHooks'(false)
                }
            }
        }
            flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
            'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
                'userRemoteConfigs' {
                    'hudson.plugins.git.UserRemoteConfig' {
                        'url'('https://github.com/Davidpavan/todo.git')
                        'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
                    }
                }
                'branches' {
                    'hudson.plugins.git.BranchSpec' {
                        'name'('*/tags/*')
                    }
                }
            }
            'scriptPath'('Jenkinsfile')
            'lightweight'(true)
        }
    }
}

pipelineJob('CI-Pipelines/login-ci') {
    configure { flowdefinition ->
        flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
            'triggers' {
                'hudson.triggers.SCMTrigger' {
                    'spec'('*/2 * * * 1-5')
                    'ignorePostCommitHooks'(false)
                }
            }
        }
            flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
            'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
                'userRemoteConfigs' {
                    'hudson.plugins.git.UserRemoteConfig' {
                        'url'('https://github.com/Davidpavan/login.git')
                        'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
                    }
                }
                'branches' {
                    'hudson.plugins.git.BranchSpec' {
                        'name'('*/tags/*')
                    }
                }
            }
            'scriptPath'('Jenkinsfile')
            'lightweight'(true)
        }
    }
}

pipelineJob('CI-Pipelines/user-ci') {
    configure { flowdefinition ->
        flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
            'triggers' {
                'hudson.triggers.SCMTrigger' {
                    'spec'('*/2 * * * 1-5')
                    'ignorePostCommitHooks'(false)
                }
            }
        }
            flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
            'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
                'userRemoteConfigs' {
                    'hudson.plugins.git.UserRemoteConfig' {
                        'url'('https://github.com/Davidpavan/users.git')
                        'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
                    }
                }
                'branches' {
                    'hudson.plugins.git.BranchSpec' {
                        'name'('*/tags/*')
                    }
                }
            }
            'scriptPath'('Jenkinsfile')
            'lightweight'(true)
        }
    }
}