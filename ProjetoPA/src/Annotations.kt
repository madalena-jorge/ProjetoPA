import kotlin.reflect.KClass

/**
 * Annotation to mark a property as an XML attribute.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Attributes

/**
 * Annotation to mark a property as an XML entity.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Entitys

/**
 * Annotation to provide an XML class name.
 *
 * @property name The name of the XML class.
 */
@Target(AnnotationTarget.CLASS)
annotation class ClassName(val name: String)

/**
 * Annotation to define the order of the properties.
 *
 * @property value The order value.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Order(val value: Int)

/**
 * Annotation to ignore a property when generating XML.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Ignore()

/**
 * Annotation to specify a string transformer for a property.
 *
 * @property transformer The class of the string transformer.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XMLString(val transformer: KClass<out StringTransformer>)

/**
 * Annotation to specify an XML adapter for a class.
 *
 * @property adapter The class of the XML adapter.
 */
@Target(AnnotationTarget.CLASS)
annotation class XMLAdapter(val adapter: KClass<out Adapter>)
