package cat.kiwi.minecraft.metcd.service

import cat.kiwi.minecraft.metcd.MEtcdPlugin
import cat.kiwi.minecraft.metcd.model.ServerStatus
import com.google.gson.Gson
import io.etcd.jetcd.ByteSequence
import io.etcd.jetcd.options.GetOption
import java.nio.charset.Charset

class QueryService {
    companion object {
        fun serverStatus(serviceType: String): List<ServerStatus> {
            val kvClient = MEtcdPlugin.etcdClient.kvClient
            val prefix = ByteSequence.from("/agenda/service/$serviceType", Charsets.UTF_8)
            val option = GetOption.newBuilder().isPrefix(true).build()
            val response = kvClient.get(prefix, option).get()

            val list = mutableListOf<ServerStatus>()
            val gson = Gson()

            response.kvs.forEach {
                val json = it.value.toString(Charsets.UTF_8)
                val serverStatus = gson.fromJson(json, ServerStatus::class.java)
                list.add(serverStatus)
            }

            return list
        }
    }
}