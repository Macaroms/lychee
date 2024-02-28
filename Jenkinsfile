String module = "${env.module}"

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
                        def codePath = env.WORKSPACE
                        echo "The code path is: ${codePath}"
                    }
                }
            }
        }
    }
}