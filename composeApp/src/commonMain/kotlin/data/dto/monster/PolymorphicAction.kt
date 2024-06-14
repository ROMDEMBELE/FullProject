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

@Serializable(with = PolymorphicAction.Serializer::class)
sealed interface PolymorphicAction {
    val name: String
    val desc: String

    @Serializable
    data class AttackActionDto(
        override val name: String,
        override val desc: String,
        @SerialName("attack_bonus")
        val attackBonus: Int,
        val damage: List<PolymorphicDamage>,
    ) : PolymorphicAction

    @Serializable
    data class SimpleActionDto(
        override val name: String,
        override val desc: String,
        val usage: PolymorphicUsageLimitDto? = null,
    ) : PolymorphicAction

    @Serializable
    data class SavingThrowActionDto(
        override val name: String,
        override val desc: String,
        val usage: PolymorphicUsageLimitDto? = null,
        val dc: SavingThrowDto,
        val damage: List<PolymorphicDamage>? = null
    ) : PolymorphicAction


    @Serializable
    data class MultiAttackActionDto(
        override val name: String,
        override val desc: String,
        @SerialName("multiattack_type")
        val multiAttackType: String,
        @SerialName("action_options")
        val attackOption: MultiAttackActionOptionContainer? = null,
        val actions: List<PolymorphicMultiAttackOption.OptionAction> = emptyList()
    ) : PolymorphicAction {

        @Serializable
        @SerialName("action")
        data class MultiAttackActionOptionContainer(
            override val choose: Int,
            override val from: OptionContainer.OptionSet<PolymorphicMultiAttackOption>,
        ) : OptionContainer<PolymorphicMultiAttackOption>
    }

    object Serializer : KSerializer<PolymorphicAction> {

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                polymorphic(
                    PolymorphicAction::class,
                    AttackActionDto::class,
                    AttackActionDto.serializer()
                )
                polymorphic(
                    PolymorphicAction::class,
                    SavingThrowActionDto::class,
                    SavingThrowActionDto.serializer()
                )
                polymorphic(
                    PolymorphicAction::class,
                    MultiAttackActionDto::class,
                    MultiAttackActionDto.serializer()
                )
                polymorphic(
                    OptionContainer::class,
                    MultiAttackActionDto.MultiAttackActionOptionContainer::class,
                    MultiAttackActionDto.MultiAttackActionOptionContainer.serializer()
                )
            }
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicAction::class).descriptor

        override fun serialize(encoder: Encoder, value: PolymorphicAction) {
            when (value) {
                is AttackActionDto -> encoder.encodeSerializableValue(
                    AttackActionDto.serializer(),
                    value
                )

                is MultiAttackActionDto -> encoder.encodeSerializableValue(
                    MultiAttackActionDto.serializer(),
                    value
                )

                is SavingThrowActionDto -> encoder.encodeSerializableValue(
                    SavingThrowActionDto.serializer(),
                    value
                )

                is SimpleActionDto -> encoder.encodeSerializableValue(
                    SimpleActionDto.serializer(),
                    value
                )
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicAction {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            when {
                jsonElement.jsonObject.containsKey("multiattack_type") -> {
                    return json.decodeFromJsonElement(
                        MultiAttackActionDto.serializer(),
                        jsonElement
                    )
                }

                jsonElement.jsonObject.containsKey("attack_bonus") -> {
                    return json.decodeFromJsonElement(
                        AttackActionDto.serializer(),
                        jsonElement
                    )
                }

                jsonElement.jsonObject.containsKey("dc") -> {
                    return json.decodeFromJsonElement(
                        SavingThrowActionDto.serializer(),
                        jsonElement
                    )
                }

                else -> {
                    return json.decodeFromJsonElement(
                        SimpleActionDto.serializer(),
                        jsonElement
                    )
                }
            }
        }
    }

}