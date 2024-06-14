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
import kotlinx.serialization.modules.SerializersModule

@Serializable(with = PolymorphicAbility.Serializer::class)
sealed interface PolymorphicAbility {
    val name: String
    val desc: String

    @Serializable
    data class SpecialAbilityDto(
        override val name: String,
        override val desc: String
    ) : PolymorphicAbility

    @Serializable
    data class SavingThrowAbilityDto(
        override val name: String,
        override val desc: String,
        val dc: SavingThrowDto,
    ) : PolymorphicAbility

    @Serializable
    data class SpellCastingAbilityDto(
        override val name: String,
        override val desc: String,
        @SerialName("spellcasting")
        val spellCasting: PolymorphicSpellCastingAbilityDetails,
    ) : PolymorphicAbility

    object Serializer : KSerializer<PolymorphicAbility> {

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicAbility::class,
                    SpecialAbilityDto::class,
                    SpecialAbilityDto.serializer()
                )
            }
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicAbility::class).descriptor

        override fun serialize(encoder: Encoder, value: PolymorphicAbility) {
            when (value) {
                is SpecialAbilityDto -> encoder.encodeSerializableValue(
                    SpecialAbilityDto.serializer(),
                    value
                )

                is SavingThrowAbilityDto -> encoder.encodeSerializableValue(
                    SavingThrowAbilityDto.serializer(),
                    value
                )

                is SpellCastingAbilityDto -> encoder.encodeSerializableValue(
                    SpellCastingAbilityDto.serializer(),
                    value
                )
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicAbility {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when {
                jsonElement.jsonObject.containsKey("spellcasting") ->
                    json.decodeFromJsonElement(
                        SpellCastingAbilityDto.serializer(),
                        jsonElement
                    )

                jsonElement.jsonObject.containsKey("dc") ->
                    json.decodeFromJsonElement(
                        SavingThrowAbilityDto.serializer(),
                        jsonElement
                    )

                else ->
                    json.decodeFromJsonElement(
                        SpecialAbilityDto.serializer(),
                        jsonElement
                    )
            }
        }
    }
}