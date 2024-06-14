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

@Serializable(with = PolymorphicSpellCastingAbilityDetails.Serializer::class)
interface PolymorphicSpellCastingAbilityDetails {
    val spells: List<SpellDto>
    val componentsRequired: List<String>
    val dc: Int
    val ability: ReferenceDto

    @Serializable
    data class InnateSpellCasting(
        override val spells: List<SpellDto>,
        @SerialName("components_required")
        override val componentsRequired: List<String>,
        override val dc: Int,
        override val ability: ReferenceDto,
    ) : PolymorphicSpellCastingAbilityDetails

    @Serializable
    data class MagicianSpellActing(
        val level: Int,
        val modifier: Int,
        val school: String,
        val slots: Map<Int, Int>,
        override val spells: List<SpellDto>,
        @SerialName("components_required")
        override val componentsRequired: List<String>,
        override val dc: Int,
        override val ability: ReferenceDto,
    ) : PolymorphicSpellCastingAbilityDetails

    @Serializable
    data class SpellDto(
        val name: String,
        val level: Int,
        val notes: String? = null,
        val url: String,
        val usage: PolymorphicUsageLimitDto? = null,
    )

    object Serializer : KSerializer<PolymorphicSpellCastingAbilityDetails> {

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicSpellCastingAbilityDetails::class,
                    MagicianSpellActing::class,
                    MagicianSpellActing.serializer()
                )
                polymorphic(
                    PolymorphicSpellCastingAbilityDetails::class,
                    InnateSpellCasting::class,
                    InnateSpellCasting.serializer()
                )
            }
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicSpellCastingAbilityDetails::class).descriptor

        override fun serialize(encoder: Encoder, value: PolymorphicSpellCastingAbilityDetails) {
            when (value) {
                is MagicianSpellActing -> encoder.encodeSerializableValue(
                    MagicianSpellActing.serializer(),
                    value
                )

                is InnateSpellCasting -> encoder.encodeSerializableValue(
                    InnateSpellCasting.serializer(),
                    value
                )
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicSpellCastingAbilityDetails {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when {
                jsonElement.jsonObject.containsKey("level") ->
                    json.decodeFromJsonElement(
                        MagicianSpellActing.serializer(),
                        jsonElement
                    )

                else ->
                    json.decodeFromJsonElement(
                        InnateSpellCasting.serializer(),
                        jsonElement
                    )
            }
        }
    }
}