package cat.kiwi.minecraft.metcd

import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.model.ServerStatus
import com.google.gson.Gson
import io.etcd.jetcd.ByteSequence
import io.etcd.jetcd.options.GetOption

object MEtcd {
    fun modifyGateStatus(gameStatus: GameStatus) {
        MEtcdPlugin.gameStatus = gameStatus
    }

    fun getServerStatusList(serviceType: String): List<ServerStatus> {
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