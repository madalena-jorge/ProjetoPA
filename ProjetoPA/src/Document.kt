import java.io.File

/**
 * This class represents the XML document as a whole.
 *
 * @property version The XML version, typically 1.0 or 1.1.
 * @property encoding The character encoding for the XML document.
 * @property root_entity The root entity of the XML document, which can be null.
 *
 * @constructor Creates an instance of an XML document with a specified version, encoding, and optionally a root entity.
 */
class Document(private val version: Double, private val encoding: String, val root_entity: Entity? = null) {

    /**
     * Adds an entity to the root entity.
     *
     * @param entity The entity to be added.
     */
    fun addEntity(entity: Entity) {
        root_entity?.addEntityChild(entity)
    }

    /**
     * Removes an entity from the root entity.
     *
     * @param entity The entity to be removed.
     */
    fun removeEntity(entity: Entity) {
        root_entity?.removeEntityChild(entity)
    }

    /**
     * Converts the XML document to a string representation.
     *
     * @return A string representing the XML document.
     */
    fun toXMLString(): String {
        val header = "<?xml version=\"$version\" encoding=\"$encoding\"?>\n"
        return header + root_entity?.toXMLString()
    }

    /**
     * Writes the XML document to a file named "XML File.txt".
     */
    fun writeToFile(path: String) {
        val file = File(path)
        val text = toXMLString()
        file.setWritable(true, false)
        file.writeText(text)
    }

    /**
     * Performs an XPath query on the XML document.
     *
     * @param expression The XPath expression as a string.
     * @return A list of entities matching the XPath expression.
     */
    fun XPath(expression: String): List<Entity> {
        val path = expression.split("/")
        var currentEntities = listOf(root_entity ?: return emptyList())
        for (p in path) {
            val matchingEntities = mutableListOf<Entity>()
            for (entity in currentEntities) {
                matchingEntities.addAll(entity.getChildren().filter { it.getName() == p })
            }
            currentEntities = matchingEntities
        }
        return currentEntities
    }

}
