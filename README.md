# kotlin-openapi-bindings

kotlin-openapi-bindings is a multiplatform library that provides bindings for OpenAPI JSON files. The library can parse and serialize to Kotlin data classes, and it takes care of union types and handles the x- properties.

# Installation
Add the dependency to your build.gradle.kts or build.gradle file:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("community.flock.community.flock.kotlinx.openapi.bindings:kotlin-openapi-bindings:VERSION")
}
```

Usage
To use kotlin-openapi-bindings, add it as a dependency to your project. Then, you can create an instance of OpenAPIObject by parsing an OpenAPI JSON file using the provided Json.decodeFromString() function:


# Usage
Here's a simple example of how to use kotlin-openapi-bindings:
```kotlin
import com.github.jacklt.kotlin.openapi.model.OpenAPI

val json = """
{
  "openapi": "3.0.0",
  "info": {
    "title": "My API",
    "version": "1.0.0"
  },
  "paths": {
    "/": {
      "get": {
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "properties": {
                    "message": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
"""

val obj = OpenAPI.decodeFromString(json)
val str = OpenAPI.encodeToString(obj)
```

# Contributing
Contributions to kotlin-openapi-bindings are welcome! To contribute, please fork the repository and submit a pull request.