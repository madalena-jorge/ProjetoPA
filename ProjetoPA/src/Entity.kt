 /**
 * This class represents an XML entity (tag).
 *
 * @property name The name of the entity.
 * @property nested_text The text nested inside the entity.
 * @property parent The parent entity of this entity, if any.
 * @property attributes The list of attributes associated with this entity.
 * @property children The list of child entities nested inside this entity.
 *
 * @constructor Creates an instance of an XML entity with the specified name, optional nested text, parent entity, attributes, and child entities.
 * @throws IllegalArgumentException if the entity name is not valid.
 */
data class Entity(private var name: String, private val nested_text: String = "", private var parent: Entity? = null,
                  private val attributes: MutableList<Attribute> = mutableListOf(), private val children: MutableList<Entity> = mutableListOf()): XML() {

    init {
        parent?.children?.add(this)
    }

    init {
        require(name.matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$"))) {
            "The entity name is not valid!"
        }
    }

     /**
      * Gets the nested text of the entity.
      *
      * @return The nested text of the entity.
      */
     fun getNestedText(): String {
         return nested_text
     }

    /**
     * Gets the name of the entity.
     *
     * @return The name of the entity.
     */
    fun getName(): String {
        return name
    }

    /**
     * Sets a new name for the entity.
     *
     * @param newName The new name for the entity.
     */
    fun setName(newName: String) {
        require(newName.matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$"))) {
            "The entity name is not valid!"
        }
        name = newName
    }

    /**
     * Adds an attribute to the entity.
     *
     * @param attribute The attribute to be added.
     */
    fun addAttribute(attribute: Attribute) {
        attributes.add(attribute)
    }

    /**
     * Removes an attribute from the entity.
     *
     * @param attribute The attribute to be removed.
     */
    fun removeAttribute(attribute: Attribute) {
        attributes.remove(attribute)
    }

    /**
     * Changes the body of a specific attribute.
     *
     * @param attribute The attribute whose body will be changed.
     * @param attributeBody The new body for the attribute.
     */
    fun changeAttributeBody(attribute: Attribute, attributeBody: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                entity.attributes.forEach {
                    if (it == attribute) {
                        it.body = attributeBody
                    }
                }
                return true
            }
        })
    }

    /**
     * Changes the name of a specific attribute.
     *
     * @param attribute The attribute whose name will be changed.
     * @param newName The new name for the attribute.
     */
    fun changeAttributeName(attribute: Attribute, newName: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                entity.attributes.forEach {
                    if (it == attribute) {
                        it.setName(newName)
                    }
                }
                return true
            }
        })
    }

    /**
     * Adds a child entity to this entity.
     *
     * @param entity The child entity to be added.
     */
    fun addEntityChild(entity: Entity) {
        entity.parent = this
        children.add(entity)
    }

    /**
     * Removes a child entity from this entity.
     *
     * @param entity The child entity to be removed.
     */
    fun removeEntityChild(entity: Entity) {
        entity.parent = null
        children.remove(entity)
    }

    /**
     * Gets the list of child entities.
     *
     * @return The list of child entities.
     */
    fun getChildren(): List<Entity> {
        return children
    }

    /**
     * Gets the parent entity.
     *
     * @return The parent entity, or null if there is no parent.
     */
    fun getParent(): Entity? {
        return parent
    }

    /**
     * Gets the list of attributes.
     *
     * @return The list of attributes.
     */
    fun getAttributes(): List<Attribute> {
        return attributes
    }

     /**
     * Accepts a visitor according to the Visitor design pattern.
     *
     * @param visitor The visitor to be accepted.
     */
    override fun accept(visitor: Visitor) {
        if (visitor.visit(this))
            children.forEach {
                it.accept(visitor)
                attributes.forEach {
                    it.accept(visitor)
                }
            }
        visitor.endVisit(this)
    }

    /**
     * Adds an attribute globally to all entities with the specified name.
     *
     * @param entityName The name of the entities to which the attribute will be added.
     * @param attributeName The name of the attribute to be added.
     * @param body The body of the attribute to be added.
     */
    fun addAttributeGlobally(entityName: String, attributeName: String, body: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                if (entity.name == entityName) {
                    entity.addAttribute(Attribute(attributeName, body))
                }
                return true
            }
        })
    }

    /**
     * Renames all entities with the specified old name to a new name globally.
     *
     * @param oldName The old name of the entities.
     * @param newName The new name for the entities.
     */
    fun renameEntityGlobally(oldName: String, newName: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                if (entity.name == oldName) {
                    entity.name = newName
                }
                return true
            }
        })
    }

    /**
     * Renames an attribute globally for all entities with the specified name.
     *
     * @param entityName The name of the entities.
     * @param oldName The old name of the attribute.
     * @param newName The new name of the attribute.
     */
    fun renameAttributeGlobally(entityName: String, oldName: String, newName: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                if (entity.name == entityName) {
                    entity.attributes.forEach {
                        if (it.getName() == oldName) {
                            it.setName(newName)
                        }
                    }
                }
                return true
            }
        })
    }

    /**
     * Removes entities globally with the specified name.
     *
     * @param entityName The name of the entities to be removed.
     */
    fun removeEntityGlobally(entityName: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                entity.parent?.children?.removeIf { it.name == entityName }
                return true
            }
        })
    }

    /**
     * Removes an attribute globally from all entities with the specified name.
     *
     * @param entityName The name of the entities.
     * @param attributeName The name of the attribute to be removed.
     */
    fun removeAttributeGlobally(entityName: String, attributeName: String) {
        accept(object : Visitor {
            override fun visit(entity: Entity): Boolean {
                if (entity.name == entityName) {
                    entity.attributes.removeIf { it.getName() == attributeName }
                }
                return true
            }
        })
    }

    /**
     * Converts the entity and its children to a string representation in XML format.
     *
     * @param indentation The indentation to be used for the XML string.
     * @return A string representing the entity in XML format.
     */
    fun toXMLString(indentation: String = ""): String {
        val attributesString = attributes.joinToString(" ") { "${it.getName()}=\"${it.body}\"" }
        val nestedTextString = if (nested_text.isNotBlank()) nested_text else ""
        val childrenString = children.joinToString("") { it.toXMLString("$indentation  ") }
        if (children.isEmpty() && attributesString != "" && nestedTextString == "")
            return "$indentation<$name $attributesString/>\n"
        if (children.isEmpty() && attributesString != "") {
            return "$indentation<$name $attributesString>$nestedTextString</$name>\n"
        }
        if (children.isNotEmpty() && attributesString != "") {
            return "$indentation<$name $attributesString>\n$childrenString$indentation</$name>\n"
        }
        return if (children.isEmpty()) {
            "$indentation<$name>$nestedTextString</$name>\n"
        } else {
            "$indentation<$name>\n$childrenString$indentation</$name>\n"
        }
    }

    /**
     * Returns a string representation of the entity.
     *
     * @return A string representing the entity.
     */
    override fun toString(): String {
        return "Entity(name='$name')"
    }

}
