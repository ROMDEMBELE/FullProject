package domain.model.encounter

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
enum class Condition(
    val title: String,
    val color: Color,
    val description: String
) {
    BLINDED(
        title = "Blinded",
        color = Color(0x000000),
        description = "A blinded creature can`t see and automatically fails any ability check that requires sight. Attack rolls against the creature have advantage, and the creature`s attack rolls have disadvantage"
    ),
    CHARMED(
        title = "Charmed",
        color = Color(0x5B0E88),
        description = "A charmed creature can`t attack the charmer or target the charmer with harmful abilities or magical effects. The charmer has advantage on any ability check to interact socially with the creature"
    ),
    DEAFENED(
        title = "Deafened",
        color = Color(0xD77B1E),
        description = "A deafened creature can`t hear and automatically fails any ability check that requires hearing"
    ),
    EXHAUSTION(
        title = "Exhaustion",
        color = Color(0x833E2E),
        description = "Some special abilities and environmental hazards, such as starvation and the long-term effects of freezing or scorching temperatures, can lead to a special condition called exhaustion. Exhaustion is measured in six levels.1 - Disadvantage on ability checks.2 - Speed halved.3 - Disadvantage on attack rolls and saving throws.4 - Hit point maximum halved.5 - Speed reduced to 0.6 - Death. Finishing a long rest reduces a creature`s exhaustion level by 1, provided that the creature has also ingested some food and drink."
    ),
    FRIGHTENED(
        title = "Frightened",
        color = Color(0x562B6B),
        description = "A frightened creature has disadvantage on ability checks and attack rolls while the source of its fear is within line of sight. The creature can`t willingly move closer to the source of its fear"
    ),
    GRAPPLED(
        title = "Grappled",
        color = Color(0xF3D3BE),
        description = "A grappled creature`s speed becomes 0. It can`t benefit from any bonus to its speed. The condition ends if the grappler is incapacitated. The condition ends if an effect removes the grappled creature from the reach of the grappler or grappling effect."
    ),
    INCAPACITATED(
        title = "Incapacitated",
        color = Color(0x78835D),
        description = "An incapacitated creature can`t take actions or reactions"
    ),
    INVISIBLE(
        title = "Invisible",
        color = Color(0xD4CE5E),
        description = "An invisible creature is impossible to see without the aid of magic or a special sense. The creature is heavily obscured. The creature`s location can be detected by any noise it makes or any tracks it leaves. Attack rolls against the creature have disadvantage, and the creature`s attack rolls have advantage."
    ),
    PARALYZED(
        title = "Paralyzed",
        color = Color(0xB4B4B4),
        description = "A paralyzed creature is incapacitated. Can`t move or speak. The creature automatically fails Strength and Dexterity saving throws. Attack rolls against the creature have advantage. Any attack that hits the creature is a critical hit if the attacker is within 5 feet of the creature."
    ),
    PETRIFIED(
        title = "Petrified",
        color = Color(0x595959),
        description = "A petrified creature is transformed, along with any nonmagical object it is wearing or carrying, into a solid inanimate substance (usually stone). Its weight increases by a factor of ten, and it ceases aging. The creature is incapacitated, can`t move or speak, and is unaware of its surroundings. Attack rolls against the creature have advantage. The creature automatically fails Strength and Dexterity saving throws. The creature has resistance to all damage. The creature is immune to poison and disease, although a poison or disease already in its system is suspended, not neutralized."
    ),
    POISONED(
        title = "Poisoned",
        color = Color(0x007930),
        description = "A poisoned creature has disadvantage on attack rolls and ability checks"
    ),
    PRONE(
        title = "Prone",
        color = Color(0xFFFEFF),
        description = "A prone creature`s only movement option is to crawl, unless it stands up and thereby ends the condition. The creature has disadvantage on attack rolls. An attack roll against the creature has advantage if the attacker is within 5 feet of the creature. Otherwise, the attack roll has disadvantage."
    ),
    RESTRAINED(
        title = "Restrained",
        color = Color(0x512E01),
        description = "A restrained creature`s speed becomes 0, and it can`t benefit from any bonus to its speed. Attack rolls against the creature have advantage, and the creature`s attack rolls have disadvantage. The creature has disadvantage on Dexterity saving throws."
    ),
    STUNNED(
        title = "Stunned",
        color = Color(0xDFBEFF),
        description = "A stunned creature is incapacitated, can`t move, and can speak only falteringly. The creature automatically fails Strength and Dexterity saving throws. Attack rolls against the creature have advantage."
    ),
    UNCONSCIOUS(
        title = "Unconscious",
        color = Color(0xB39E82),
        description = "An unconscious creature is incapacitated, can`t move or speak, and is unaware of its surroundings. The creature drops whatever it`s holding and falls prone. The creature automatically fails Strength and Dexterity saving throws. Attack rolls against the creature have advantage. Any attack that hits the creature is a critical hit if the attacker is within 5 feet of the creature."
    )
}