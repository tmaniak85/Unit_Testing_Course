package pl.devfoundry.testing.homework;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void unitShouldNotMoveWithoutFuel() {

//        given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 0, 10);

//        when
//        then
        assertThrows(IllegalStateException.class, () -> unit.move(10, 10));

    }

    @Test
    void unitShouldLooseFuelWhileMoving() {

//        given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 10, 10);

//        when
        unit.move(2, 2);
//        then
        assertThat(unit.getFuel(), equalTo(6));
    }

    @Test
    void movedUnitShouldReturnNewCoordinate() {

//        given
        Unit unit = new Unit(new Coordinates(0,0), 10, 10);

//        when
        Coordinates move = unit.move(2, 2);
//        then
        assertThat(move, equalTo(new Coordinates(2, 2)));
    }

    @Test
    void fuelingShouldNotExceedFuelLimit() {

//        given
        Unit unit = new Unit(new Coordinates(0,0), 10, 10);

//        when
        unit.tankUp();

//        given
        assertThat(unit.getFuel(), is(10));

    }

    @Test
    void currentCargoWeightCannotBeHigherThanMaxCargoWeight() {

//        given
        Unit unit = new Unit(new Coordinates(0, 0), 10, 10);
        Cargo cargo = new Cargo("Paczka", 11);

//        when
//        given
        assertThrows(IllegalStateException.class, () -> unit.loadCargo(cargo));
    }

    @Test
    void currentCargoWeightShouldBe0AfterUnloadingAllCargo() {

//        given
        Unit unit = new Unit(new Coordinates(10, 10), 10, 10);

//        when
        unit.unloadAllCargo();

//        then
        assertThat(unit.getLoad(), is(0));
    }



}
