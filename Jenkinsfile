String module = "${env.module}"
def skipDeploy = false

pipeline {
    agent any

    environment {
        GIT_CREDENTIAL_ID = 'gitee-auth'
    }

    stages {

//         stage('Checkout') {
//             steps {
//                 script {
//                     checkout([
//                         $class: 'GitSCM',
//                         branches: [[name: 'master']],
//                         userRemoteConfigs: [[url: 'https://gitee.com/jiangwei97/lychee.git', credentialsId: env.gitee-auth]]
//                     ])
//                 }
//             }
//         }

        stage('Build') {
            steps {
                script {
                    dir('lychee-server/' + module) {
                        sh 'mvn clean install -DskipTests'
                        if (module == "common"){
                            // 跳过第三个阶段
                            skipDeploy = true
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                expression { skipDeploy == false } // 当条件为假时执行第三个阶段
            }
            steps {
                script {
                    dir('/data/code/jenkinsDeploy') {
                        echo '部署模块: ' + module
                        def workspace = env.WORKSPACE
                        echo "workspace: " + workspace
                        def cmd = 'java -jar -Dfile.encoding=utf-8 ' + workspace + '/lychee-server/' + module + '/target/' + module + '-0.0.1-SNAPSHOT.jar >> ' + module + '.log 2>&1 & echo $! > ' + module + '.pid'
                        println cmd
                        sh cmd
                    }
                }
            }
        }
    }
}