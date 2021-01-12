package com.bignerdranch.nyethack

open class Room(val name: String) {
    protected open val dangerLevel = 5
    var monster: Monster? = Goblin()
    fun description() = "Room: $name\n" +
            "Danger level: $dangerLevel\n" +
            "Creature: ${monster?.description ?: "none."}"
    open fun load() = "Nothing much to see here..."
}

open class TownSquare : Room("Town Square") {
    override val dangerLevel = super.dangerLevel - 3
    private var bellSound = "GOWNG"
    final override fun load() = "The village rally and chear as enter!\n${ringBell()}"
    open fun ringBell() = "The ball tower announces your arrival. $bellSound"
}