package community.flock.kotlinx.openapi.bindings.v2

import kotlinx.serialization.json.Json

@Deprecated("Use Swagger instead", replaceWith = ReplaceWith("Swagger"))
open class OpenAPI(
    json: Json = Json { prettyPrint = true },
) : Swagger(json) {
    companion object Default : OpenAPI()
}
