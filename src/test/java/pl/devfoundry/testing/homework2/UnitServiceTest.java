package pl.devfoundry.testing.homework2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.NoSuchElementException;
import java.util.Optional;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private UnitRepository unitRepository;

    @InjectMocks
    private UnitService unitService;


    @Test
    void addCargoShouldBeLoadedOnUnit() {

//        given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 20, 20);
        Cargo cargo = new Cargo("Cargo1", 5);
        given(cargoRepository.findCargoByName("Cargo1")).willReturn(Optional.of(cargo));

//        when
        unitService.addCargoByName(unit, "Cargo1");

//        then
        verify(cargoRepository).findCargoByName("Cargo1");
        assertThat(unit.getLoad(), is(5));
        assertThat(unit.getCargo().get(0), equalTo(cargo));

    }

    @Test
    void shouldThrowExceptionIfNoExistingCargoIsTryToBeAdded() {

//        given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 20, 20);
        given(cargoRepository.findCargoByName("Cargo1")).willReturn(Optional.empty());

//        when
//        then
        assertThrows(NoSuchElementException.class, () -> unitService.addCargoByName(unit, "Cargo1"));
        assertThat(unit.getLoad(), is(0));
        assertThat(unit.getCargo().size(), is(0));

    }

    @Test
    void givingCoordinatesShouldReturnCorrectUnitIfExisting() {

//    given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 0, 0);
        given(unitRepository.getUnitByCoordinates(coordinates)).willReturn(unit);

//    when
        Unit unit2 = unitService.getUnitOn(coordinates);

//    given
        verify(unitRepository).getUnitByCoordinates(coordinates);
        assertThat(unit2.getCoordinates(), sameInstance(coordinates));

    }

    @Test
    void shouldThrowExceptionIfUnitWithGivingCoordinatesIsNotExisting() {

        //    given
        Coordinates coordinates = new Coordinates(0, 0);
        given(unitRepository.getUnitByCoordinates(coordinates)).willReturn(null);


//    when
//    given
        assertThrows(NoSuchElementException.class, () -> unitService.getUnitOn(coordinates));


    }



}
