package pl.devfoundry.testing.meal;

import pl.devfoundry.testing.meal.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public void add(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> findByName(String mealName, boolean exactMatch) {

        List<Meal> result;

        if(exactMatch == true) {
            result = meals.stream()
                    .filter(meal -> meal.getName().equals(mealName))
                    .collect(Collectors.toList());
        } else {
            result = meals.stream()
                    .filter(meal -> meal.getName().startsWith(mealName))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<Meal> findByPrice(int mealPrice, TypeOfOperation typeOfOperation) {

        List<Meal> result;

        if(typeOfOperation.equals(TypeOfOperation.EQUAL)) {
            result = meals.stream()
                    .filter(meal -> meal.getPrice() == mealPrice)
                    .collect(Collectors.toList());
        } else if(typeOfOperation.equals(TypeOfOperation.HIGHER)) {
            result = meals.stream()
                    .filter(meal -> meal.getPrice() < mealPrice)
                    .collect(Collectors.toList());
        } else {
            result = meals.stream()
                    .filter(meal -> meal.getPrice() > mealPrice)
                    .collect(Collectors.toList());
        }

        return result;
    }

    public List<Meal> find(String mealName, boolean exactMatch, int mealPrice, TypeOfOperation typeOfOperation) {

        if(exactMatch == true && typeOfOperation.equals(TypeOfOperation.EQUAL)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().equals(mealName) && meal.getPrice() == mealPrice;
                    })
                    .collect(Collectors.toList());
        } else if(exactMatch == false && typeOfOperation.equals(TypeOfOperation.EQUAL)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().startsWith(mealName) && meal.getPrice() == mealPrice;
                    })
                    .collect(Collectors.toList());
        } else if(exactMatch == true && typeOfOperation.equals(TypeOfOperation.LOWER)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().equals(mealName) && meal.getPrice() < mealPrice;
                    })
                    .collect(Collectors.toList());
        } else if(exactMatch == false && typeOfOperation.equals(TypeOfOperation.LOWER)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().startsWith(mealName) && meal.getPrice() < mealPrice;
                    })
                    .collect(Collectors.toList());
        } else  if(exactMatch == true && typeOfOperation.equals(TypeOfOperation.HIGHER)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().equals(mealName) && meal.getPrice() > mealPrice;
                    })
                    .collect(Collectors.toList());
        } else   if(exactMatch == false && typeOfOperation.equals(TypeOfOperation.HIGHER)) {
            return meals.stream()
                    .filter(meal -> {
                        return meal.getName().startsWith(mealName) && meal.getPrice() > mealPrice;
                    })
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
