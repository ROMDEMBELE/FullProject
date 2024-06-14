package data.dto.monster

import data.dto.ReferenceDto
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
import kotlinx.serialization.modules.SerializersModule

@Serializable(with = PolymorphicDamage.Serializer::class)
interface PolymorphicDamage {

    @Serializable
    data class DamageDto(
        @SerialName("damage_type")
        val damageType: ReferenceDto,
        @SerialName("damage_dice")
        val damageDice: String,
        val notes: String? = null
    ) : PolymorphicDamage

    @Serializable
    @SerialName("damage")
    data class DamageOptionContainer(
        override val choose: Int,
        override val from: OptionContainer.OptionSet<DamageDto>,
    ) : PolymorphicDamage, OptionContainer<DamageDto>

    object Serializer : KSerializer<PolymorphicDamage> {
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicDamage::class,
                    DamageDto::class,
                    DamageDto.serializer()
                )
                polymorphic(
                    PolymorphicDamage::class,
                    DamageOptionContainer::class,
                    DamageOptionContainer.serializer()
                )
                polymorphic(
                    OptionContainer::class,
                    DamageOptionContainer::class,
                    DamageOptionContainer.serializer()
                )
            }
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicDamage::class).descriptor

        override fun deserialize(decoder: Decoder): PolymorphicDamage {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when {
                jsonElement.jsonObject.containsKey("damage_type") -> {
                    json.decodeFromJsonElement(DamageDto.serializer(), jsonElement)
                }

                jsonElement.jsonObject.containsKey("choose") -> {
                    json.decodeFromJsonElement(DamageOptionContainer.serializer(), jsonElement)
                }

                else -> throw IllegalArgumentException("Invalid PolymorphicDamage")
            }
        }

        override fun serialize(encoder: Encoder, value: PolymorphicDamage) {
            when (value) {
                is DamageDto -> encoder.encodeSerializableValue(DamageDto.serializer(), value)
                is DamageOptionContainer -> encoder.encodeSerializableValue(
                    DamageOptionContainer.serializer(),
                    value
                )
            }
        }

    }
}