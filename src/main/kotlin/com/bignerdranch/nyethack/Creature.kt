package com.bignerdranch.nyethack
import java.util.Random

// 複数のオブジェクトに共通する振る舞いやプロパティのカテゴリが必要で、しかも継承が不適切な時はインターフェイスを使うべき
interface Fightable {
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
        get() = (0 until diceCount).map {
            Random().nextInt(diceSides + 1)
        }.sum()

    fun attack(opponent: Fightable): Int
}

abstract class Monster(val name: String,
val description: String,
override var healthPoints: Int) : Fightable {
    override fun attack(opponent: Fightable): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}
// 具体的な親クラスを作りたくないがコンストラクタを使いたかったりする時は抽象クラスが最適。
class Goblin(name: String = "Goblin",
description: String = "A nasty-looking goblin",
healthPoints: Int = 30) : Monster(name, description, healthPoints) {
    override val diceCount = 2
    override val diceSides = 8
}