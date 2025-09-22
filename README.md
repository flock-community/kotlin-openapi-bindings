![Maven Central Version](https://img.shields.io/maven-central/v/community.flock.kotlinx.openapi.bindings/kotlin-openapi-bindings)
[![Build](https://github.com/flock-community/kotlin-openapi-bindings/actions/workflows/build.yml/badge.svg)](https://github.com/flock-community/kotlin-openapi-bindings/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# Kotlin OpenAPI Bindings

A Kotlin Multiplatform library that provides bindings for OpenAPI JSON files. The library can parse and serialize OpenAPI specifications to Kotlin data classes, handling union types and x-properties (extensions).

## Table of Contents

- [Features](#features)
- [Supported Platforms](#supported-platforms)
- [Installation](#installation)
- [Usage](#usage)
  - [OpenAPI v3](#openapi-v3)
  - [OpenAPI v2 (Swagger)](#openapi-v2-swagger)
  - [Handling x-properties](#handling-x-properties)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Features

- Support for both OpenAPI v3 and v2 (Swagger) specifications
- Kotlin Multiplatform support (JVM, JS, Native)
- Serialization and deserialization of OpenAPI JSON
- Proper handling of union types
- Support for x-properties (extensions)
- Type-safe Kotlin data classes for OpenAPI models

## Supported Platforms

- JVM
- JavaScript (NodeJS)
- Native
  - macOS (x64, arm64)
  - Linux (x64)
  - Windows (x64)

## Installation

Add the dependency to your build.gradle.kts or build.gradle file:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("community.flock.kotlinx.openapi.bindings:kotlin-openapi-bindings:0.1.1")
}
```

## Usage

### OpenAPI v3

```kotlin
// Using community.flock.kotlinx.openapi.bindings.OpenAPIV3
// and community.flock.kotlinx.openapi.bindings.OpenAPIV3Model

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

// Parse OpenAPI JSON to Kotlin object
val obj: OpenAPIV3Model = OpenAPIV3.decodeFromString(json)

// Serialize a Kotlin object back to JSON
val str: String = OpenAPIV3.encodeToString(obj)
```

### OpenAPI v2 (Swagger)

```kotlin
// Using community.flock.kotlinx.openapi.bindings.OpenAPIV2
// and community.flock.kotlinx.openapi.bindings.OpenAPIV2Model

val json = """
{
  "swagger": "2.0",
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
"""

// Parse Swagger JSON to Kotlin object
val obj: OpenAPIV2Model = OpenAPIV2.decodeFromString(json)

// Serialize a Kotlin object back to JSON
val str: String = OpenAPIV2.encodeToString(obj)
```

### Handling x-properties

The library automatically handles x-properties (extensions) in OpenAPI specifications. These properties are stored in an `xProperties` field during parsing and are restored when serializing back to JSON.

## API Documentation

For detailed API documentation, please refer to the [Dokka documentation](https://flock-community.github.io/kotlin-openapi-bindings/).

## Contributing

Contributions to kotlin-openapi-bindings are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please make sure to update tests as appropriate.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
