package cat.kiwi.minecraft.acloud

import cat.kiwi.minecraft.acloud.config.Config
import cat.kiwi.minecraft.acloud.model.GameStatus

import cat.kiwi.minecraft.acloud.model.ServerStatus
import com.google.gson.Gson
import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import java.util.*

object AgendaCloud {
    fun setGameStatus(gameStatus: GameStatus) {
        AgendaCloudPlugin.gameStatus = gameStatus
    }

    fun getGameStatus(): GameStatus {
        return AgendaCloudPlugin.gameStatus
    }

    fun getServerStatusList(serviceType: String): List<ServerStatus> {
        val healthClient = AgendaCloudPlugin.consulClient.healthClient()
        val nodes = healthClient.getHealthyServiceInstances(serviceType).response
        return nodes.map { Gson().fromJson(it.service.meta["serviceInfo"], ServerStatus::class.java) }
    }
}