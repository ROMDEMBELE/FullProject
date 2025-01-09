package common

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.error.MissingPropertyException

/**
 * Extension function to safely extract a string from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The string value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a string.
 */
fun JsonObject.safeGetString(propertyName: String): String {
    return this[propertyName]?.jsonPrimitive?.contentOrNull
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract an integer from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The integer value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not an integer.
 */
fun JsonObject.safeGetInt(propertyName: String): Int {
    return this[propertyName]?.jsonPrimitive?.intOrNull
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract a double from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The double value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a double.
 */
fun JsonObject.safeGetDouble(propertyName: String): Double {
    return this[propertyName]?.jsonPrimitive?.doubleOrNull
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract a boolean from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The boolean value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a boolean.
 */
fun JsonObject.safeGetBoolean(propertyName: String): Boolean {
    return this[propertyName]?.jsonPrimitive?.booleanOrNull
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract a JsonObject from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The JsonObject value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a JsonObject.
 */
fun JsonObject.safeGetJsonObject(propertyName: String): JsonObject {
    return this[propertyName]?.jsonObject
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract a JsonArray from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The JsonArray value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a JsonArray.
 */
fun JsonObject.safeGetJsonArray(propertyName: String): JsonArray {
    return this[propertyName]?.jsonArray
        ?: throw MissingPropertyException("Missing $propertyName property")
}

/**
 * Extension function to safely extract a JsonPrimitive from a JsonObject.
 *
 * @param propertyName The name of the property for error messages.
 * @return The JsonPrimitive value if found, otherwise throws MissingPropertyException.
 * @throws MissingPropertyException if the property is missing or not a JsonPrimitive.
 */
fun JsonObject.safeGetJsonPrimitive(propertyName: String): JsonPrimitive {
    return this[propertyName]?.jsonPrimitive
        ?: throw MissingPropertyException("Missing $propertyName property")
}