package cat.kiwi.minecraft.metcd.utils

import cat.kiwi.minecraft.metcd.model.GameStatus
import de.tr7zw.nbtapi.NBTItem
import jdk.jshell.Snippet.Status
import org.bukkit.inventory.ItemStack

fun ItemStack.setMEtcdCondition(condition: GameStatus): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("metcd")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("metcd")
    }
    uisMetadata.setObject("condition", condition)
    uisMetadata.setBoolean("isMEtcd", true)
    return nbtItem.item
}

fun ItemStack.setMEtcdCondition(condition: GameStatus, serverName: String): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("metcd")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("metcd")
    }
    uisMetadata.setObject("condition", condition)
    uisMetadata.setBoolean("isMEtcd", true)
    uisMetadata.setString("serverName", serverName)
    return nbtItem.item
}

val ItemStack.isMEtcdItem: Boolean
    get() {
        if (this.type.isAir) return false
        return NBTItem(this).getCompound("metcd")?.getBoolean("isMEtcd") ?: false
    }

val ItemStack.MEtcdStatus: GameStatus
    get() {
        if (this.type.isAir) return GameStatus.EXITED
        val compound = NBTItem(this).getCompound("metcd") ?: return GameStatus.EXITED
        return compound.getObject("condition", GameStatus::class.java) ?: GameStatus.EXITED
    }
val ItemStack.serverName: String
    get() {
        if (this.type.isAir) return ""
        val compound = NBTItem(this).getCompound("metcd") ?: return ""
        return compound.getString("serverName") ?: ""
    }