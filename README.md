How to run the project

1. Make sure JDK 21 is installed
2. Run command "./gradlew bootRun" in the project root directory (where gradlew file reside)
    * If opened from IntelliJ IDEA, you can just click play button (usually on top right position)
    * If you run it on Windows, use './gradlew.bat bootRun' instead
3. Access the API via port 8080
    * Or you can use the integrated Swagger by accessing ```http://localhost:8080/swagger-ui.html``` via browser

List of account that can be used are in account_list.json file

Conversion rate are set on model/Currency.java which based on USD

The conversion value:

1. USD : IDR = 1 : 15000
2. USD : MYR = 1 : 5