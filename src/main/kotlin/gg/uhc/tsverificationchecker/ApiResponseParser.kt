package gg.uhc.tsverificationchecker

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.util.*

class ApiResponseParser : ResponseDeserializable<Array<ApiResponseParser.ApiMcAccount>> {
    data class ApiMcAccount(val uuid: UUID)

    override fun deserialize(content: String) = Gson().fromJson(content, Array<ApiMcAccount>::class.java)
}
