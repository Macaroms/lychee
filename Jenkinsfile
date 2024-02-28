String module = "${env.module}"

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    dir('lychee-server/' + module) {
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    dir('/data/code/jenkinsDeploy') {
                        echo '部署模块: ' + module
                        def codePath = env.WORKSPACE
                        echo "The code path is: ${codePath}"
                        echo "nohup java -jar -Dfile.encoding=utf-8 ${codePath}/lychee-server/${module}/target/${module}-0.0.1-SNAPSHOT.jar >> ${module}.log 2>&1 & echo $! > ${module}.pid"
                        sh "nohup java -jar -Dfile.encoding=utf-8 ${codePath}/lychee-server/${module}/target/${module}-0.0.1-SNAPSHOT.jar >> ${module}.log 2>&1 & echo $! > ${module}.pid"
                    }
                }
            }
        }
    }
}