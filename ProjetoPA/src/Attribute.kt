/**
 * This class represents an attribute of an XML document.
 *
 * @property name The name of the attribute. It must follow the valid syntax for XML attribute names.
 * @property body The value of the attribute. By default, it is an empty string.
 *
 * @constructor Creates an instance of the XML attribute with a name and optionally a value.
 * @throws IllegalArgumentException if the attribute name is not valid.
 */
data class Attribute(private var name: String, var body: String = "") : XML() {

    init {
        require(name.matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$"))) {
            "The attribute name is not valid!"
        }
    }

    /**
     * Accepts a visitor that processes this attribute.
     *
     * @param visitor The visitor that will process this attribute.
     */
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    /**
     * Gets the name of the attribute.
     *
     * @return The name of the attribute.
     */
    fun getName(): String {
        return name
    }

    /**
     * Sets a new name for the attribute.
     *
     * @param newName The new name of the attribute. It must follow the valid syntax for XML attribute names.
     * @throws IllegalArgumentException if the new attribute name is not valid.
     */
    fun setName(newName: String) {
        require(newName.matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$"))) {
            "The attribute name is not valid!"
        }
        name = newName
    }

}
