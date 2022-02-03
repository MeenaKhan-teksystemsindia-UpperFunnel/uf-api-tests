node {

    def build_ok = true

    parameters {
                choice( name: 'env', choices: ['uat2', 'preprod2', 'qa1'], description: 'Environment')
            }

withEnv ([
		"ENV_UF_API_SSH_USER=tatacliq", \
		"ENV_UF_API_TEST=uf-api-tests", \

]) {
	stage ('init') {

		echo sh(returnStdout: true, script: "env")
	}

	stage('Enviorment UP') {
	}

	stage('Code checkout - TEST') {
		dir(ENV_UF_API_TEST) {
			git (
				credentialsId: 'c533b442-e6b9-484d-bed4-7104b0f7306f',
				url: 'https://github.com/tcs-chennai/UF_API_TESTS.git',
				branch: 'develop'
			)
		}
	}

    try{
        stage('RUN - TEST') {
                dir(ENV_UF_API_TEST) {

                try {
                sh "./gradlew runTests -Dtags=SMOKE -Denv=uat2"
                }finally {
                                    archive 'build/**/*'
                }
                }
            }
    } catch(e) {
            build_ok = false
            echo e.toString()
        }


	stage('Enviorment DOWN') {
	}

  	stage('Notify') {
  	        //mail bcc: '', body: 'Test mail body - BUILD_NUMBER $BUILD_NUMBER - ${BUILD_NUMBER} \n RUN_TESTS_DISPLAY_URL - ${RUN_TESTS_DISPLAY_URL} \n ${env.BUILD_NUMBER}', cc: '', from: '', replyTo: '', subject: 'Test mail - CI Testing', to: 'manjuk@testvagrant.com'
  	        emailext body: 'Test mail body - BUILD_NUMBER $BUILD_NUMBER - ${BUILD_NUMBER} \\n RUN_TESTS_DISPLAY_URL - ${env.RUN_TESTS_DISPLAY_URL} \\n ${env.BUILD_NUMBER} \n http://localhost:8080/job/UF_Flow/15/artifact/build/reportng-reports/html/overview.html \n http://localhost:8080/job/UF_Flow/${BUILD_NUMBER}/artifact/build/reportng-reports/html/overview.html', recipientProviders: [buildUser()], subject: 'Test mail Subject', to: 'manjuk@testvagrant.com'
  	}

    if(build_ok) {
        currentBuild.result = "SUCCESS"
    } else {
        currentBuild.result = "FAILURE"
    }
}
}