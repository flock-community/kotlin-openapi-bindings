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
- [Breaking changes from 0.1.x](#breaking-changes-from-01x)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Features

- Support for OpenAPI 3.0, 3.1, and 3.2 — selected automatically by the `openapi` version string
- Support for OpenAPI v2 (Swagger) specifications
- Kotlin Multiplatform support (JVM, JS, Native)
- Serialization and deserialization of OpenAPI JSON
- Strict typed bindings: unmodeled keywords throw rather than silently drop
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

`OpenAPIV3` is the single entry point for 3.0.x, 3.1.x, and 3.2.x. It reads the
`openapi` version string and returns a sealed `OpenAPIV3Model` whose variant
(`OpenAPIV30Model` / `OpenAPIV31Model` / `OpenAPIV32Model`) carries the
version-specific fields typed.

```kotlin
import community.flock.kotlinx.openapi.bindings.OpenAPIV3
import community.flock.kotlinx.openapi.bindings.OpenAPIV3Model
import community.flock.kotlinx.openapi.bindings.OpenAPIV30Model
import community.flock.kotlinx.openapi.bindings.OpenAPIV31Model
import community.flock.kotlinx.openapi.bindings.OpenAPIV32Model

val json = """
{
  "openapi": "3.1.0",
  "info": { "title": "My API", "version": "1.0.0" },
  "webhooks": {
    "ping": { "post": { "responses": { "200": { "description": "ok" } } } }
  }
}
"""

val decoded: OpenAPIV3Model = OpenAPIV3.decodeFromString(json)

when (decoded) {
    is OpenAPIV30Model -> /* 3.0 fields */ Unit
    is OpenAPIV31Model -> println(decoded.webhooks)          // typed
    is OpenAPIV32Model -> /* 3.2 fields */ Unit
}

// Encode roundtrips through the sealed interface.
val roundtripped: String = OpenAPIV3.encodeToString(decoded)
```

### OpenAPI v2 (Swagger)

`OpenAPIV2` mirrors the V3 shape for consistency: it reads the `swagger`
version string and returns a sealed `OpenAPIV2Model` whose variant
(`OpenAPIV20Model`) carries the version-specific fields typed. Today
only Swagger 2.0 is published, so there is one variant; the sealed
shape leaves room for future minors without breaking the public API.

```kotlin
import community.flock.kotlinx.openapi.bindings.OpenAPIV2
import community.flock.kotlinx.openapi.bindings.OpenAPIV2Model
import community.flock.kotlinx.openapi.bindings.OpenAPIV20Model

val json = """
{
  "swagger": "2.0",
  "info": { "title": "My API", "version": "1.0.0" },
  "paths": {
    "/": { "get": { "responses": { "200": { "description": "OK" } } } }
  }
}
"""

val decoded: OpenAPIV2Model = OpenAPIV2.decodeFromString(json)

when (decoded) {
    is OpenAPIV20Model -> println(decoded.host)
}

val roundtripped: String = OpenAPIV2.encodeToString(decoded)
```

### Handling x-properties

The library automatically handles x-properties (extensions) in OpenAPI specifications. These properties are stored in an `xProperties` field during parsing and are restored when serializing back to JSON.

## Breaking changes from 0.1.x

The 3.1 / 3.2 support release breaks the OpenAPI v3 surface (the library is still pre-1.0). Specifically:

1. **`OpenAPIV3Model` and `OpenAPIV2Model` are now sealed interfaces.** Reads against either still compile; instantiation and `copy()` move to the per-minor data classes — `OpenAPIV20Model`, `OpenAPIV30Model`, `OpenAPIV31Model`, `OpenAPIV32Model`.
2. **`Version.V2` and `Version.V3` are replaced** by `Version.V20`, `V30`, `V31`, `V32`, with `Version.fromSwaggerString` and `Version.fromOpenApiString` parsers.
3. **`OpenAPIV3.decodeFromString` and `OpenAPIV2.decodeFromString` now dispatch by version string.** A `3.1.0` document previously decoded into the same data class as 3.0 (silently dropping 3.1-only fields); it now decodes into `OpenAPIV31Model` and surfaces those fields typed.
4. **`Schema.exclusiveMinimum` / `Schema.exclusiveMaximum` are no longer on the shared `Schema` interface.** `OpenAPIV30Schema` has them as `Boolean?` and `OpenAPIV31Schema` / `OpenAPIV32Schema` as `Double?` — matching each version's spec.
5. **`OpenAPIModel.paths` is now nullable** (3.1 made it non-required). All concrete V3 subclasses' `paths` are nullable too; `OpenAPIV20Model.paths` stays non-null because Swagger 2.0 requires it.
6. **All `OpenAPIV2X` type names are renamed `OpenAPIV20X`** (Schema, Reference, Parameter, etc.). The directory layout moved files under `v20/`, `v30/`, `v31/`, `v32/` but the Kotlin package stays flat as `community.flock.kotlinx.openapi.bindings`.
7. **Strict mode is unchanged for 2.0 and 3.0 docs;** for 3.1/3.2 it newly rejects unmodeled JSON Schema 2020-12 keywords (`$id`, `$anchor`, `$dynamicRef`, `$dynamicAnchor`, `$schema`, `$comment`). If you hit one in the wild, please open an issue.

The OpenAPI 3.1/3.2 keywords that *are* modeled (and roundtrip losslessly):

- 3.1 document: `webhooks`, `jsonSchemaDialect`, optional `paths`, `components.pathItems`, `Info.summary`, `License.identifier`.
- 3.1 schema: `const`, `prefixItems`, `contentEncoding`, `contentMediaType`, `contentSchema`, `dependentRequired`, `dependentSchemas`, `unevaluatedProperties`, `unevaluatedItems`, `$defs`, numeric `exclusiveMinimum` / `exclusiveMaximum`, type-as-array (including `"null"`), `propertyNames`, `$ref` siblings (`summary`, `description`, `type`, `default`).
- 3.2: `query` HTTP method on `PathItem`, `additionalOperations` bucket, `TagObject.parent` / `.kind` / `.summary`.

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
