class MakeNextStep(
    private val hasCurrentAliveCells: Set<HasCoordinates>
) {
    fun nextAliveCells() = aliveCellsThatSurvived() + deadCellsThatBecameAlive()

    private fun aliveCellsThatSurvived(): Set<HasCoordinates> {
        return currentAliveCells()
            .filter { keepCellAliveIf -> keepCellAliveIf.aliveNeighbors().size in 2..3 }
            .toSet()
    }

    private fun deadCellsThatBecameAlive(): Set<HasCoordinates> {
        return deadCellsWithPotentialToBecomeAlive()
            .filter { makeCellAliveIf -> makeCellAliveIf.aliveNeighbors().size == 3 }
            .toSet()
    }

    private fun deadCellsWithPotentialToBecomeAlive(): Set<HasCoordinates> {
        return currentAliveCells()
            .flatMap { mapCellToIts -> mapCellToIts.deadNeighbors() }
            .toSet()
    }

    private fun HasCoordinates.aliveNeighbors(): Set<HasCoordinates> {
        return this.neighbors()
            .filter { keepCellIfItIs -> keepCellIfItIs in currentAliveCells() }
            .toSet()
    }

    private fun HasCoordinates.deadNeighbors(): Set<HasCoordinates> {
        return this.neighbors()
            .filter { keepCellIfItIs -> keepCellIfItIs !in currentAliveCells() }
            .toSet()
    }

    private fun HasCoordinates.neighbors(): Set<HasCoordinates> {
        val createNeighborCell = CreateNeighborCell(this)
        return setOf(
            createNeighborCell.upperLeftCorner(),
            createNeighborCell.upperRightCorner(),
            createNeighborCell.lowerLeftCorner(),
            createNeighborCell.lowerRightCorner(),
            createNeighborCell.leftSide(),
            createNeighborCell.rightSide(),
            createNeighborCell.topSide(),
            createNeighborCell.bottomSide()
        )
    }

    private fun currentAliveCells() = hasCurrentAliveCells
}

interface HasCoordinates {
    fun x(): Int
    fun y(): Int
}

data class CreateCell(
    val hasX: Int,
    val hasY: Int
) : HasCoordinates {
    override fun x() = hasX
    override fun y() = hasY
}

class CreateNeighborCell(
    private val hasOriginalCell: HasCoordinates
) {
    fun leftSide() = CreateCell(cell().x() - 1, cell().y())
    fun rightSide() = CreateCell(cell().x() + 1, cell().y())
    fun topSide() = CreateCell(cell().x(), cell().y() + 1)
    fun bottomSide() = CreateCell(cell().x(), cell().y() - 1)
    fun upperLeftCorner() = CreateCell(cell().x() - 1, cell().y() + 1)
    fun upperRightCorner() = CreateCell(cell().x() + 1, cell().y() + 1)
    fun lowerLeftCorner() = CreateCell(cell().x() - 1, cell().y() - 1)
    fun lowerRightCorner() = CreateCell(cell().x() + 1, cell().y() - 1)

    private fun cell() = hasOriginalCell
}




