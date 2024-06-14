package data.dto.monster

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule


@Serializable(with = PolymorphicUsageLimitDto.Serializer::class)
interface PolymorphicUsageLimitDto {

    @Serializable
    @SerialName("at will")
    object AtWill : PolymorphicUsageLimitDto

    @Serializable
    @SerialName("per day")
    data class PerDay(
        val times: Int,
        @SerialName("rest_types")
        val restTypes: List<String>? = null
    ) : PolymorphicUsageLimitDto

    @Serializable
    @SerialName("recharge on roll")
    data class RechargeOnRoll(
        val dice: String,
        @SerialName("min_value")
        val minValue: Int
    ) : PolymorphicUsageLimitDto


    object Serializer : KSerializer<PolymorphicUsageLimitDto> {

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            classDiscriminator = "type"
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicUsageLimitDto::class,
                    PerDay::class,
                    PerDay.serializer()
                )
                polymorphic(
                    PolymorphicUsageLimitDto::class,
                    AtWill::class,
                    AtWill.serializer()
                )
                polymorphic(
                    PolymorphicUsageLimitDto::class,
                    RechargeOnRoll::class,
                    RechargeOnRoll.serializer()
                )
            }
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicUsageLimitDto::class).descriptor

        override fun serialize(encoder: Encoder, value: PolymorphicUsageLimitDto) {
            when (value) {
                is AtWill -> encoder.encodeSerializableValue(
                    AtWill.serializer(),
                    value
                )

                is PerDay -> encoder.encodeSerializableValue(
                    PerDay.serializer(),
                    value
                )

                is RechargeOnRoll -> encoder.encodeSerializableValue(
                    RechargeOnRoll.serializer(),
                    value
                )
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicUsageLimitDto {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when (val itemType = jsonElement.jsonObject["type"]?.jsonPrimitive?.content) {
                "at will" -> json.decodeFromJsonElement(AtWill.serializer(), jsonElement)
                "per day" -> json.decodeFromJsonElement(PerDay.serializer(), jsonElement)
                "recharge on roll" -> json.decodeFromJsonElement(
                    RechargeOnRoll.serializer(),
                    jsonElement
                )

                else -> throw IllegalArgumentException("Unknown item type: $itemType")
            }
        }
    }
}