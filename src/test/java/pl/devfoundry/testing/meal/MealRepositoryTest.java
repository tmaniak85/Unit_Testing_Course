package pl.devfoundry.testing.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.devfoundry.testing.meal.Meal;
import pl.devfoundry.testing.meal.MealRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MealRepositoryTest {

    MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {

//        given
        Meal meal = new Meal(10, "Pizza");

//        when
        mealRepository.add(meal);

//        then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));

    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {

//        given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

//        when
        mealRepository.delete(meal);

//        then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    @Test
    void shouldBeAbleToFindMealByExactName() {

        //        given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pi");

        mealRepository.add(meal);
        mealRepository.add(meal2);

//        when
        List<Meal> result = mealRepository.findByName("Pizza", true);

//        then
        assertThat(result.size(), is(1));

    }

    @Test
    void shouldBeAbleToFindMealByStartingLetters() {

        //        given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pi");

        mealRepository.add(meal);
        mealRepository.add(meal2);

//        when
        List<Meal> result = mealRepository.findByName("Pi", false);

//        then
        assertThat(result.size(), is(2));

    }



    @Test
    void shouldBeAbleToFindMealByExactPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        mealRepository.add(meal);

//        when
        List<Meal> result = mealRepository.findByPrice(10, TypeOfOperation.EQUAL);

//        then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByPriceWherePriceIsHigherThanGiving() {

        //        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

        //        when
        List<Meal> result = mealRepository.findByPrice(10, TypeOfOperation.HIGHER);

//        then
        assertThat(result.size(), is(2));

    }

    @Test
    void shouldBeAbleToFindMealByPriceWherePriceIsLowerThanGiving() {

        //        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

        //        when
        List<Meal> result = mealRepository.findByPrice(10, TypeOfOperation.LOWER);

//        then
        assertThat(result.size(), is(2));

    }

    @Test
    void shouldFindByExactNameAndExactPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("Pizza", true, 8, TypeOfOperation.EQUAL);

//        then
        assertThat(result.size(), is(1));

    }

    @Test
    void shouldFindByFirstLetterAndExactPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("P", false, 8, TypeOfOperation.EQUAL);

//        then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal));

    }

    @Test
    void shouldFindByExactNameAndLowerPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("Pizza", true, 9, TypeOfOperation.LOWER);

//        then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal));

    }

    @Test
    void shouldFindByFirstLetterAndLowerPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("P", false, 9, TypeOfOperation.LOWER);

//        then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal));

    }

    @Test
    void shouldFindByExactNameAndHigherPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("Pizza4", true, 9, TypeOfOperation.HIGHER);

//        then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal4));

    }

    @Test
    void shouldFindByFirstLetterAndHigherPrice() {

//        given
        Meal meal = new Meal(8, "Pizza");
        Meal meal1 = new Meal(9, "Pizza1");
        Meal meal2 = new Meal(10, "Pizza2");
        Meal meal3 = new Meal(11, "Pizza3");
        Meal meal4 = new Meal(12, "Pizza4");
        mealRepository.add(meal);
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        mealRepository.add(meal4);

//        when
        List<Meal> result = mealRepository.find("P", false, 11, TypeOfOperation.HIGHER);

//        then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal4));

    }




}
