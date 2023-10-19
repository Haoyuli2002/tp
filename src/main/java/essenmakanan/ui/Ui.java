package essenmakanan.ui;

import essenmakanan.ingredient.Ingredient;
import essenmakanan.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Ui {

    private final String DIVIDER = "--------------------------------------------";
    public void start() {
        System.out.println("Welcome to Essen Makanan!!! A one-stop place " +
                "to track the\n ingredients in your kitchen and store " +
                "your favourite recipes");

        System.out.println(DIVIDER);
    }

    public void bye() {
        System.out.println("Hope you had fun! See you again!");
    }

    public void functionSelect() {
        System.out.println("Enter the number for the function you want");
        System.out.println("1. Recipe");
        System.out.println("2. Ingredients");

        System.out.println(DIVIDER);
    }

    public void drawDivider() {
        System.out.println(DIVIDER);
    }

    public void showRecipeFunctions() {
        System.out.println("1. Add recipe. [add r/RECIPE_NAME]");
        System.out.println("2. Delete recipe. [delete r/RECIPE_ID]");
        System.out.println("3. View all recipes. [view r]");
    }

    public void showIngredientFunctions() {
        System.out.println("1. Add ingredient. [add i/INGREDIENT_NAME]");
        System.out.println("2. Delete recipe. [delete i/INGREDIENT_ID]");
        System.out.println("3. View all recipes. [view i]");
    }

    public void showRecentAddedRecipe(String recipeTitle) {
        System.out.println("Recipe: " + recipeTitle + " has been successfully created!");
    }

    public void showRecentAddedIngredient(String ingredientTitle) {
        System.out.println("Ingredient: " + ingredientTitle + " has been successfully created!");
    }

    public void printAllRecipes(ArrayList<Recipe> recipes) {
        int count = 1;
        for (Recipe recipe : recipes) {
            assert recipes.get(count - 1).getTitle().equals(recipe.getTitle())
                    : "Title is not matching with the current index";

            System.out.println(count + ". " + recipe);
            count++;
        }
    }

    public void printSpecificRecipes(int index, ArrayList<Recipe> recipes) {
        Recipe recipe = recipes.get(index);
        printStepsOfSpecificRecipe(recipe);
    }

    public void printSpecificRecipes(String title, ArrayList<Recipe> recipes) {
        Recipe wantedRecipe = recipes.stream().filter(recipe -> recipe.getTitle().equals(title)).findFirst().orElse(null);
        assert wantedRecipe != null : "There's no recipe with the given title in current recipe list";
        printStepsOfSpecificRecipe(wantedRecipe);
    }

    private static void printStepsOfSpecificRecipe(Recipe recipe) {
        List<String> steps = recipe.getRecipeSteps();
        int count = 1;
        for (String step : steps) {
            assert steps.get(count - 1).equals(step) : "The description of steps is not matching with the current index";

            System.out.println("Step " + count + ": " + step);
            count++;
        }
    }



    public void printAllIngredients(ArrayList<Ingredient> ingredients) {
        int ingredientCount = 0;

        for (Ingredient ingredient : ingredients) {
            ingredientCount++;
            System.out.println(ingredientCount + ". " + ingredient.getName());
        }
    }
}
