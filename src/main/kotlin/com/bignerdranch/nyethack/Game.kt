package com.bignerdranch.nyethack

import java.lang.Exception

fun main(args: Array<String>) {
    Game.play()
}

object Game {
    var currentRoom: Room = TownSquare()
    private val player = Player("Madrigal")

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer.")
        player.castFireball()
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())
            printPlayerStatus(player)
            print("> Enter your command: ")
            val input = readLine()
            println(GameInput(input).processCommand())
            if (input == "quit") {
                break
            }
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) " + "(Blessed: if (${player.isBlessed}) YES else NO)")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(args: String?) {
        private val input = args ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }
        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "quit" -> quit()
            "ring" -> ring()
            else -> commandNotFound()
        }

        private fun commandNotFound() =
            "I'm not quite sure what you're trying to do!"
    }

    private fun quit() =
        println("farewell message to the adventurer")

    private fun ring() =
        if (currentRoom.name == "Town Square") {
            TownSquare().ringBell()
        } else {
            println("you are not in town square!!")
        }


    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            println("OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}")
        } catch (e: Exception) {
            "Invalid direction: $directionInput."
        }
}