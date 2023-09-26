package com.xxy.chat.config.service

import com.alibaba.fastjson2.JSONObject
import com.xxy.chat.config.annotation.EtcdConfigBean
import com.xxy.chat.config.annotation.EtcdProperties
import com.xxy.chat.config.bean.EtcdStorageInfo
import io.etcd.jetcd.ByteSequence
import io.etcd.jetcd.Client
import io.etcd.jetcd.watch.WatchEvent
import io.etcd.jetcd.watch.WatchResponse
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import java.io.File
import javax.annotation.PostConstruct

@ConditionalOnBean(Client::class)
@Component
class EtcdConfigInitializer(
    private val client: Client,
    private val applicationContext: ApplicationContext
)  {

    @Value("\${config.packageName}")
    lateinit var packageName: String

    private val storage: MutableMap<String, EtcdStorageInfo> = mutableMapOf()

    @PostConstruct
    fun init() {
        val annotatedClasses = findClassesWithAnnotationInPackage(packageName)
        annotatedClasses.forEach { clazz ->
            val classAnnotation = clazz.getAnnotation(EtcdProperties::class.java)
            if (classAnnotation != null) {
                val classValue = classAnnotation.path
                storage[classValue] = EtcdStorageInfo(clazz, null, true)
            } else {
                val properties = clazz.fields
                properties.forEach {
                    val flag = it.getAnnotation(EtcdProperties::class.java)
                    if (flag != null) {
                        storage[flag.path] = EtcdStorageInfo(clazz, it, false)
                    }
                }
            }
        }
        if (storage.isNotEmpty()) {
            storage.keys.forEach { path ->
                client.kvClient.get(ByteSequence.from(path, Charsets.UTF_8)).thenApply {
                    saveValueToBean(path, it.kvs.first().value.toString())
                }.get()
            }
            storage.keys.forEach { path ->
                client.watchClient.watch(ByteSequence.from(path, Charsets.UTF_8),getEtcdWatchListener())
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
                    // 检查类上是否有注解
                    if (klass.getAnnotation(EtcdConfigBean::class.java)!=null) {
                        annotatedClasses.add(klass)
                    }
                }
            }
        }
        return annotatedClasses
    }

    private fun saveValueToBean(path: String, value: String) {
        val info = storage[path] ?: return
        val bean = this.applicationContext.getBean(info.clazz)
        if (info.flag) {
            val data = JSONObject.parseObject(value, info.clazz)
            BeanUtils.copyProperties(bean, data)
        } else {
            when(info.filed!!.type){
                String::class.java -> ReflectionUtils.setField(info.filed!!, bean, value)
                Int::class.java -> ReflectionUtils.setField(info.filed!!, bean, value.toInt())
                Long::class.java -> ReflectionUtils.setField(info.filed!!, bean, value.toLong())
                Double::class.java -> ReflectionUtils.setField(info.filed!!, bean, value.toDouble())
                Any::class.java -> ReflectionUtils.setField(info.filed!!, bean, JSONObject.parseObject(value, info.filed!!.type))
            }
        }
    }

    private fun getEtcdWatchListener(): (WatchResponse) -> Unit {
        val listener: (WatchResponse) -> Unit = { watchResponse ->
            for (event in watchResponse.events) {
                if (event.eventType == WatchEvent.EventType.PUT) {
                    saveValueToBean(
                        event.keyValue.key.toString(Charsets.UTF_8),
                        event.keyValue.value.toString(Charsets.UTF_8)
                    )
                }
            }
        }
        return listener
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