package com.xxy.chat.config.service

import com.xxy.chat.config.annotation.EtcdConfigBean
import com.xxy.chat.config.annotation.EtcdProperties
import io.etcd.jetcd.Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

@ConditionalOnBean(value = [Client::class])
@Component
class EtcdConfigInitializer(
    private val client: Client,
    private val context: ApplicationContext
) {
    @Value("config.packageName")
    private lateinit var packageName:String
    @Bean
    fun scanClassesWithAnnotation(): ApplicationRunner {
        return ApplicationRunner {
            val annotatedClasses = findClassesWithAnnotationInPackage(packageName)
            annotatedClasses.forEach {

            }
        }
    }

    private fun findClassesWithAnnotationInPackage(packageName: String): List<Class<*>> {
        val annotatedClasses = mutableListOf<Class<*>>()
        val classLoader = Thread.currentThread().contextClassLoader
        val packagePath = packageName.replace('.', '/')
        val resources = classLoader.getResources(packagePath)

        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            val file = File(resource.toURI())
            if (file.isDirectory) {
                val classNames = findClassNamesInDirectory(file, packageName)
                for (className in classNames) {
                    val klass = Class.forName(className)
                    val kClass = klass.kotlin
                    // 检查类上是否有注解
                    if (kClass.hasAnnotation<EtcdConfigBean>()) {
                        annotatedClasses.add(klass)
                    }
                }
            }
        }
        return annotatedClasses
    }

    private fun findClassNamesInDirectory(directory: File, packageName: String): List<String> {
        val classNames = mutableListOf<String>()
        val files = directory.listFiles()

        for (file in files!!) {
            if (file.isDirectory) {
                val subPackage = if (packageName.isEmpty()) file.name else "$packageName.${file.name}"
                classNames.addAll(findClassNamesInDirectory(file, subPackage))
            } else if (file.name.endsWith(".class")) {
                val className = "${packageName}.${file.name.substring(0, file.name.length - 6)}"
                classNames.add(className)
            }
        }

        return classNames
    }

}