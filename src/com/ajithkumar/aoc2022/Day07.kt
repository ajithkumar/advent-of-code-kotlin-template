package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*

sealed interface FileSystemNode {
    fun size() :Int
}
class File(val name: String, private val size: Int): FileSystemNode {
    override fun size(): Int = size
}
class Directory(val name: String, val children: MutableList<FileSystemNode>): FileSystemNode {
    lateinit var parent: Directory
    private var size: Int = 0

    fun allDirectories(): List<Directory> {
        val result = mutableListOf<Directory>()
        result.add(this)
        val childrenResult = this.children.filterIsInstance<Directory>()
            .flatMap { it.allDirectories() }
        result.addAll(childrenResult)
        return result
    }

    fun addChildren(node: FileSystemNode) {
        children.add(node)
    }

    fun findDirectory(name: String): Directory {
        return children.filter { it is Directory && it.name == name }[0] as Directory
    }

    override fun size(): Int {
        if(size > 0) return size
        size = children.sumOf { it.size() }
        return size
    }
}

class CommandLineInterpreter(private val input: List<String>) {
    private lateinit var rootDirectory: Directory
    private lateinit var currentDirectory: Directory

    fun parse(): Directory {
        input.forEach {
            when {
                "$ cd /" in it -> setRootDirectory()
                "$ cd .." in it -> currentDirectory = currentDirectory.parent
                "$ cd" in it -> {
                    val (_prompt,_cmd, dirName) = it.split(" ")
                    val directory = currentDirectory.findDirectory(dirName)
                    currentDirectory = directory
                }
                "$ ls" in it -> {}
                "dir" in it -> {
                    val (_cmd, dirName) = it.split(" ")
                    addDirToChildren(dirName)
                }
                else -> {
                    val (size,name) = it.split(" ")
                    addFileToChildren(name, size.toInt())
                }
            }
        }
        return rootDirectory
    }

    private fun setRootDirectory() {
        rootDirectory = Directory("/", mutableListOf())
        rootDirectory.parent = rootDirectory
        currentDirectory = rootDirectory
    }

    private fun addDirToChildren(name: String) {
        val newDir = Directory(name, mutableListOf())
        newDir.parent = currentDirectory
        currentDirectory.addChildren(newDir)
    }

    private fun addFileToChildren(name: String, size: Int) {
        currentDirectory.addChildren(File(name, size))
    }
}

fun main() {
    fun buildDirectoryStructure(input: List<String>): Directory {
        return CommandLineInterpreter(input).parse()
    }

    fun part1(input: List<String>): Int {
        val rootDirectory = buildDirectoryStructure(input)
        return rootDirectory.allDirectories()
            .filter { it.size() <= 100_000 }
            .sumOf { it.size() }
    }

    fun part2(input: List<String>): Int {
        val totalSpace = 70_000_000
        val requiredSpace = 30_000_000
        val rootDirectory = buildDirectoryStructure(input)
        val minSizeToDelete = requiredSpace - (totalSpace - rootDirectory.size())
        return rootDirectory.allDirectories()
            .filter { it.size() >= minSizeToDelete }
            .minByOrNull { directory -> directory.size() }!!
            .size()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
