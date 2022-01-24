package pl.devfoundry.testing.homework;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void ConstructorFailedIfXOrYLessThan0() {

//        given
//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, 1));
    }

    @Test
    void ConstructorFailedIfXOrYGreaterThan100() {

//        given
//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, 101));
    }

    @Test
    void copyShouldReturnNewObject() {

//        given
        Coordinates coordinates1 = new Coordinates(10, 10);

//        when
        Coordinates coordinates2 = Coordinates.copy(coordinates1, 0, 0);

//        then
        assertThat(coordinates1, not(sameInstance(coordinates2)));
        assertThat(coordinates1, equalTo(coordinates2));
    }

    @Test
    void copyShouldReturnAddCoordinates() {

//        given
        Coordinates coordinates1 = new Coordinates(10, 10);
//        when
        Coordinates coordinates2 = Coordinates.copy(coordinates1, 10, 10);
//        then
        assertThat(coordinates2.getX(), equalTo(20));
        assertThat(coordinates2.getY(), equalTo(20));
    }
}
