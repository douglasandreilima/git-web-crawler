# git-web-crawler project

Application to send an URL from the github repository and get all files and number of lines.
This run a web-scrapping in github repository to get informations and return.

--
This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Examples

### Request:
  Get to http://localhost:8080/request?url=https://github.com/douglasandreilima/data-structure-js
  
  OR Post to http://localhost:8080/request
  ```
  {
    "url": "https://github.com/douglasandreilima/data-structure-js"
  }
  ```
  
  Response:
  
  ```
{
  "urlRepository": "https://github.com/douglasandreilima/data-structure-js",
  "files": {
    "md": [
      {
        "fileName": "README.md",
        "fileExtension": "md",
        "totalLinesAndBytes": "3 lines (2 sloc) 105 Bytes"
      }
    ],
    "js": [
      {
        "fileName": "Stack.js",
        "fileExtension": "js",
        "totalLinesAndBytes": "97 lines (67 sloc) 1.63 KB"
      }
    ],
    "html": [
      {
        "fileName": "index.html",
        "fileExtension": "html",
        "totalLinesAndBytes": "13 lines (13 sloc) 344 Bytes"
      }
    ]
  }
}
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/git-web-crawler-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
