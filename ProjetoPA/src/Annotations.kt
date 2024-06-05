import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class Attributes

@Target(AnnotationTarget.PROPERTY)
annotation class Entitys

@Target(AnnotationTarget.CLASS)
annotation class ClassName(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class Order(val value: Int)

@Target(AnnotationTarget.PROPERTY)
annotation class Ignore()

@Target(AnnotationTarget.PROPERTY)
annotation class XMLString(val transformer: KClass<out StringTransformer>)

@Target(AnnotationTarget.CLASS)
annotation class XMLAdapter(val adapter: KClass<out Adapter>)
