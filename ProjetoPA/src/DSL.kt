/**
 * Creates an XML document with the specified version, encoding, and root entity.
 *
 * @param version The XML version.
 * @param encoding The XML encoding.
 * @param name The name of the root entity.
 * @param nestedText The optional text content of the root entity.
 * @param build The lambda function to build the document.
 * @return The created Document object.
 */
fun document(version: Double, encoding: String, name: String, nestedText: String = "", build: Document.() -> Unit): Document =
    Document(version, encoding, Entity(name, nestedText)).apply { build() }

/**
 * Adds an entity to the document.
 *
 * @param name The name of the entity.
 * @param nestedText The optional text content of the entity.
 * @param build The lambda function to build the entity.
 * @return The created Entity object.
 */
fun Document.entity(name: String, nestedText: String = "", build: Entity.() -> Unit): Entity {
    val entity = Entity(name, nestedText)
    addEntity(entity)
    entity.build()
    return entity
}

/**
 * Adds a nested entity to the current entity.
 *
 * @param name The name of the nested entity.
 * @param nestedText The optional text content of the nested entity.
 * @param build The lambda function to build the nested entity.
 * @return The created nested Entity object.
 */
fun Entity.entity(name: String, nestedText: String = "", build: Entity.() -> Unit = {}): Entity =
    Entity(name, nestedText, this).apply { build() }

/**
 * Adds an attribute to the current entity.
 *
 * @param name The name of the attribute.
 * @param value The value of the attribute.
 */
fun Entity.attribute(name: String, value: String) {
    addAttribute(Attribute(name, value))
}

/**
 * Finds a child entity by its name.
 *
 * @param name The name of the child entity.
 * @return The found child Entity object or null if not found.
 */
operator fun Entity.get(name: String): Entity? =
    getChildren().find { it.getName() == name }

/**
 * Invokes a lambda function to build the current entity.
 *
 * @param build The lambda function to build the entity.
 */
operator fun Entity.invoke(build: Entity.() -> Unit) {
    build()
}

/**
 * Adds a child entity to the current entity.
 *
 * @param entity The child entity to add.
 */
infix fun Entity.add(entity: Entity) {
    addEntityChild(entity)
}
