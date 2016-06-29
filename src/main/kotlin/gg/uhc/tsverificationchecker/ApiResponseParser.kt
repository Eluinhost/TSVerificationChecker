package gg.uhc.tsverificationchecker

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class ApiResponseParser : ResponseDeserializable<Array<ApiResponseParser.ApiMcAccount>> {
    data class ApiMcAccount(val uuid: UUID)

    override fun deserialize(content: String) = jsonParser.fromJson(content, Array<ApiMcAccount>::class.java)

    companion object {
        private val jsonParser = GsonBuilder().registerTypeAdapter(UUID::class.java, UuidTypeAdapter()).create()
    }
}

class UuidTypeAdapter : JsonDeserializer<UUID> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): UUID {
        val asString = json.asString

        return when (asString.length) {
            32 -> UUID.fromString(arrayOf(0..7, 8..11, 12..15, 16..19, 20..31).map { asString.substring(it) }.joinToString(separator = "-"))
            36 -> UUID.fromString(asString)
            else -> throw JsonParseException("Invalid uuid $asString")
        }
    }
}