#### Running SearchAnonymizerAPI

To build and Run the SearchAnonymizerAPI, use:

~~~bash
 ./gradlew shadowJar
 export JAR_VERSION=$(grep SEARCH_VERSION version.properties | cut -d= -f2)

 java -jar ./build/libs/search-anonymizer-${JAR_VERSION}-all.jar server config.yml
~~~