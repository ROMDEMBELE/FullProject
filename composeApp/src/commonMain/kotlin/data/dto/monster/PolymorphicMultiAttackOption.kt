package data.dto.monster

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule

@Serializable(with = PolymorphicMultiAttackOption.Serializer::class)
interface PolymorphicMultiAttackOption {
    @Serializable
    @SerialName("action")
    data class OptionAction(
        @SerialName("action_name")
        val actionName: String,
        val count: Int,
        val type: String
    ) : PolymorphicMultiAttackOption

    @Serializable
    @SerialName("multiple")
    data class OptionMultiple(
        val items: List<OptionAction>,
    ) : PolymorphicMultiAttackOption

    object Serializer : KSerializer<PolymorphicMultiAttackOption> {
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            classDiscriminator = "option_type"
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicMultiAttackOption::class,
                    OptionMultiple::class,
                    OptionMultiple.serializer()
                )
                polymorphic(
                    PolymorphicMultiAttackOption::class,
                    OptionAction::class,
                    OptionAction.serializer()
                )
            }
        }

        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicMultiAttackOption::class).descriptor

        override fun deserialize(decoder: Decoder): PolymorphicMultiAttackOption {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when (val optionType = jsonElement.jsonObject["option_type"]?.jsonPrimitive?.content) {
                "multiple" -> json.decodeFromJsonElement(OptionMultiple.serializer(), jsonElement)
                "action" -> json.decodeFromJsonElement(OptionAction.serializer(), jsonElement)
                else -> throw SerializationException("Unknown option type $optionType")
            }
        }

        override fun serialize(encoder: Encoder, value: PolymorphicMultiAttackOption) {
            when (value) {
                is OptionMultiple -> encoder.encodeSerializableValue(OptionMultiple.serializer(), value)
                is OptionAction -> encoder.encodeSerializableValue(OptionAction.serializer(), value)
            }

        }
    }
}