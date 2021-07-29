#### Running SearchAnonymizerAPI

## QuickStart

To build and Run the SearchAnonymizerAPI, use:

~~~bash
 ./gradlew shadowJar
 export JAR_VERSION=$(grep SEARCH_VERSION version.properties | cut -d= -f2)

 java -jar ./build/libs/search-anonymizer-${JAR_VERSION}-all.jar server config.yml
~~~

## Development

During development, the SearchAnonymizerAPI utilizes the following docker containers:
- Aerospike (Port 3000)
- Presidio Analyzer  (Port 5001)
- Presidio Anonymizer  (Port 5002)

Gradle will check to see if these containers are running or not and if they are not present, will start them for you. To speed 
local development, you can prelaunch these containers with the following:

~~~bash
 ./gradlew start_aerospike start_presidioAnalyzer start_presidioAnonymizer
~~~

To stop these containers, use:
~~~bash
 ./gradlew stop_aerospike stop_presidioAnalyzer stop_presidioAnonymizer
~~~
