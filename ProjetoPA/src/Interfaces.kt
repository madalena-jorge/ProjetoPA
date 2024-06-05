/**
 * Visitor interface for visiting entities and attributes.
 */
interface Visitor {
    /**
     * Visits an entity.
     *
     * @param entity The entity to visit.
     * @return `true` if the visit should continue, `false` otherwise.
     */
    fun visit(entity: Entity): Boolean = true
    /**
     * Called at the end of the visit of an entity.
     *
     * @param entity The entity for which the visit is ending.
     */
    fun endVisit(entity: Entity) { }
    /**
     * Visits an attribute.
     *
     * @param attribute The attribute to visit.
     */
    fun visit(attribute: Attribute) { }
}

/**
 * Abstract class representing an XML element.
 * Accepts a Visitor.
 *
 * @param visitor The visitor to accept.
 */
abstract class XML {
        abstract fun accept(visitor: Visitor)
}

/**
 * Interface for transforming strings.
 *
 * @param value The string value to transform.
 * @return The transformed string.
 */
interface StringTransformer {
        fun transform(value: String): String
}

/**
 * Interface for adapting an entity.
 *
 * @param entity The entity to adapt.
 */
interface Adapter {
    fun adapt(entity: Entity)
}
