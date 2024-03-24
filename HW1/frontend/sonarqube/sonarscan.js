const scanner = require('sonarqube-scanner');

scanner(
    {
        serverUrl: 'http://localhost:9000',
        token: 'sqp_d58e68b5f3cea81ea387df525f06a1c58ce2d1bb',
        options: {
            'sonar.projectName': 'tqs-homework-frontend',
            'sonar.projectDescription': 'TQS bus ticket seller website',
            'sonar.projectKey': 'tqs-homework-frontend',
            'sonar.sources': 'src'
        }
    },
    () => process.exit()
)