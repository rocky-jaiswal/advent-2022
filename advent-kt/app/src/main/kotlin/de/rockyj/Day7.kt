package de.rockyj

import kotlin.math.abs

data class File(val name: String, val size: Long = 0L)

class Directory(val name: String, val isRoot: Boolean = false) {
    var subDirectories: List<Directory> = mutableListOf<Directory>()
    var files: List<File> = mutableListOf<File>()
    var totalSize = 0L

    override fun toString(): String {
        return this.name
    }
}

class FileSystem(val rootDirectory: Directory) {
    var smallDirectories: List<Directory> = mutableListOf<Directory>()
    var smallDirectories2: List<Directory> = mutableListOf<Directory>()
    private var activeDirectory = rootDirectory

    fun changeActiveDirectory(dirName: String) {
        // we can check if changed dir is a child of active dir
        this.activeDirectory = this.activeDirectory.subDirectories.find { it.name == (this.activeDirectory.name + "/" + dirName) } ?: rootDirectory
    }

    fun addDirectory(dirName: String) {
        this.activeDirectory.subDirectories = this.activeDirectory.subDirectories.plus(Directory(this.activeDirectory.name + "/" + dirName))
    }

    fun addFile(file: File) {
        this.activeDirectory.files = this.activeDirectory.files.plus(file)
    }

    private fun findParent(dir: Directory, currentName: String) {
        val parent = dir.subDirectories.find {
            it.name == currentName
        }
        if (parent == null) {
            return dir.subDirectories.forEach { findParent(it, currentName) }
        } else {
            this.activeDirectory = dir
        }
    }

    fun changeToLastDirectory() {
        findParent(this.rootDirectory, this.activeDirectory.name)
    }

    private fun getSubSize(dir: Directory): Long {
        if (dir.subDirectories.isEmpty()) {
            return dir.files.sumOf { f -> f.size }
        }
        return dir.files.map { f -> f.size }.sum() + dir.subDirectories.map { sd -> getSubSize(sd) }.sum()
    }

    fun traverseForSize(dir: Directory = this.rootDirectory) {
        val s = getSubSize(dir)
        dir.totalSize = s
        dir.subDirectories.forEach { traverseForSize(it) }
    }

    fun traverseForSelection(dir: Directory = this.rootDirectory) {
        if (dir.totalSize < 100000) {
            this.smallDirectories = this.smallDirectories.plus(dir)
        }
        dir.subDirectories.forEach { traverseForSelection(it) }
    }

    fun traverseForSelection2(dir: Directory = this.rootDirectory, spaceNeeded: Long) {
        if (dir.totalSize >= spaceNeeded) {
            this.smallDirectories2 = this.smallDirectories2.plus(dir)
        }
        dir.subDirectories.forEach { traverseForSelection2(it, spaceNeeded) }
    }

    override fun toString(): String {
        return this.rootDirectory.toString()
    }
}


fun isChangeDirectoryCommand(command: String): Boolean {
    return command.matches(Regex("""^\$\s(cd)\s(.*)""")) && !command.endsWith((".."))
}

fun isChangeDirectoryUpCommand(command: String): Boolean {
    return command.matches(Regex("""^\$\s(cd)\s(.*)""")) && command.endsWith((".."))
}

fun isListCommand(command: String): Boolean {
    return command.matches(Regex("""^\$\s(ls)"""))
}

fun isDirectory(line: String): Boolean {
    return line.matches(Regex("""^(dir)(\s)(.*)"""))
}

fun isFile(line: String): Boolean {
    return line.matches(Regex("""^(\d+)(\s)(.*)"""))
}

private fun buildFS(input: List<String>): Unit {
    val rootDirectory = Directory("/", true)
    val fileSystem: FileSystem = FileSystem(rootDirectory)

    input.map { it.trim() }.forEach {line ->
        if (isChangeDirectoryCommand(line)) {
            val dirName = line.substring(5)
            fileSystem.changeActiveDirectory(dirName)
        }
        if (isChangeDirectoryUpCommand(line)) {
            fileSystem.changeToLastDirectory()
        }
        if (isDirectory(line)) {
            fileSystem.addDirectory(line.substring(4))
        }
        if (isFile(line)) {
            val fileDetails = line.split(Regex("""\s"""))
            val file = File(fileDetails[1], fileDetails[0].toLong())
            fileSystem.addFile(file)
        }
        // if (isListCommand(line)) {
        // }
    }

    // part 1
    fileSystem.traverseForSize()
    fileSystem.traverseForSelection()
    println(fileSystem.smallDirectories.sumOf { it.totalSize })

    // part 2 starts here
    val spaceOccupied = abs(70000000L - fileSystem.rootDirectory.totalSize)
    val spaceNeeded = abs(30000000L - spaceOccupied)
    fileSystem.traverseForSelection2(fileSystem.rootDirectory, spaceNeeded)
    println(fileSystem.smallDirectories2.map { Pair(it.name, it.totalSize) }.sortedBy { p -> p.second })
}

fun main() {
    val lines = fileToArr("day7_2.txt")
    println(buildFS(lines))
}
