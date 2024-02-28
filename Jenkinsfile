String module = "${env.module}"
String codePath = env.WORKSPACE

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    dir('lychee-server/console') {
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    dir('/data/code/jenkinsDeploy') {
                        sh 'pwd'  // 在指定目录下执行命令
                        echo '部署模块: ' + module
                        echo 'WORKSPACE: ' + codePath
                    }
                }
            }
        }
    }
}