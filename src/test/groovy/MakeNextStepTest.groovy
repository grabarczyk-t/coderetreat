import spock.lang.Specification

class MakeNextStepTest extends Specification {

    def "dead cell should remain dead when it has less than 3 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(0, 0)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        !nextAliveCells.contains(cell(1, 1))
    }

    def "dead cell should become alive when it has 3 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(0, 0),
            cell(2, 1)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        nextAliveCells.contains(cell(1, 1))
    }

    def "live cell should become dead when it has less than 2 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(1, 1)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        !nextAliveCells.contains(cell(1, 1))
    }

    def "live cell should become dead when it has more than 3 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(1, 1),
            cell(2, 1),
            cell(0, 0),
            cell(1, 2)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        !nextAliveCells.contains(cell(1, 1))
    }

    def "live cell should stay alive when it has 2 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(1, 1),
            cell(2, 1)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        nextAliveCells.contains(cell(1, 1))
    }

    def "live cell should stay alive when it has 3 alive neighbors"() {
        given:
        def initialAliveCells = [
            cell(1, 0),
            cell(1, 1),
            cell(2, 1),
            cell(0, 0)
        ].toSet()

        when:
        def nextAliveCells = new MakeNextStep(initialAliveCells).nextAliveCells()

        then:
        nextAliveCells.contains(cell(1, 1))
    }

    private HasCoordinates cell(int x, int y) {
        return new CreateCell(x, y)
    }
}
