package essenmakanan.parser;

import essenmakanan.exception.EssenException;
import essenmakanan.exception.EssenFormatException;
import essenmakanan.exception.EssenOutOfRangeException;
import essenmakanan.exception.EssenStorageFormatException;
import essenmakanan.ingredient.Ingredient;
import essenmakanan.ingredient.IngredientUnit;
import essenmakanan.recipe.Recipe;
import essenmakanan.recipe.RecipeIngredientList;
import essenmakanan.recipe.RecipeList;
import essenmakanan.recipe.RecipeStepList;
import essenmakanan.recipe.Step;
import essenmakanan.recipe.Tag;
import essenmakanan.ui.Ui;

import java.util.ArrayList;
import java.util.StringJoiner;

public class RecipeParser {

    public static int getRecipeIndex(RecipeList recipes, String input)
            throws EssenOutOfRangeException {
        int index;
        input = input.replace("r/", "");

        if (input.matches("\\d+")) { //if input only contains numbers
            index = Integer.parseInt(input) - 1;
        } else {
            index = recipes.getIndexOfRecipe(input);
        }

        if (!recipes.recipeExist(index)) {
            System.out.println("Your recipe name or id does not exist or it is invalid.");
            throw new EssenOutOfRangeException();
        }

        return index;
    }

    public void parseRecipeCommand(RecipeList recipes, String command, String inputDetail)
            throws EssenException {
        Ui ui = new Ui();
        switch(command) {
        case "add":
            String recipeName = inputDetail.substring(2);
            assert !recipeName.contains("r/") : "Recipe name should not contain key characters";

            Recipe newRecipe = new Recipe(recipeName);
            recipes.addRecipe(newRecipe);

            ui.printAddRecipeSuccess(recipeName);
            break;
        case "view":
            ui.printAllRecipes(recipes);
            break;
        default:
            throw new EssenException("Invalid command! Valid commands are: 'add', 'view'");
        }
    }

    public static String parseRecipeTitle(String toAdd) {
        return toAdd.replace("r/", "");
    }

    private static String convertStep(Step step)  {
        return step.getDescription() + " | " + step.getTag() + " | " + step.getEstimatedDuration();
    }

    public static String convertSteps(ArrayList<Step> steps) {
        StringJoiner joiner = new StringJoiner(" , ");

        for (Step step: steps) {
            joiner.add(convertStep(step));
        }

        return joiner.toString();
    }

    public static String convertIngredient(ArrayList<Ingredient> ingredients) {
        StringJoiner joiner = new StringJoiner(" , ");

        for (Ingredient ingredient: ingredients) {
            joiner.add(IngredientParser.convertToString(ingredient));
        }

        return joiner.toString();
    }

    public static RecipeStepList parseDataSteps(String stepsString) throws EssenStorageFormatException
            ,IllegalArgumentException {
        String[] parsedSteps = stepsString.split(" , ");
        ArrayList<Step> stepList = new ArrayList<>();

        for (String step : parsedSteps) {
            String[] parsedStep = step.split(" \\| ");

            if (parsedStep.length != 3) {
                throw new EssenStorageFormatException();
            }

            String stepDescription = parsedStep[0];
            Tag stepTag = Tag.valueOf(parsedStep[1]);
            int stepDuration = Integer.parseInt(parsedStep[2]);
            stepList.add(new Step(stepDescription, stepTag, stepDuration));
        }

        return new RecipeStepList(stepList);
    }

    public static RecipeIngredientList parseDataRecipeIngredients(String ingredientsString)
            throws EssenStorageFormatException {
        String[] parsedIngredients = ingredientsString.split(" , ");
        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        for (String ingredient : parsedIngredients) {
            String[] parsedIngredient = ingredient.split(" \\| ");

            if (parsedIngredient.length != 3 || parsedIngredient[1].isBlank()) {
                throw new EssenStorageFormatException();
            }

            String ingredientName = parsedIngredient[0];
            String ingredientQuantity = parsedIngredient[1];
            IngredientUnit ingredientUnit = IngredientUnit.valueOf(parsedIngredient[2]);
            ingredientList.add(new Ingredient(ingredientName, ingredientQuantity, ingredientUnit));
        }

        return new RecipeIngredientList(ingredientList);
    }

    public static String parseFilterRecipeInput(String input) throws EssenFormatException {
        input = input.replace("recipe ", "");
        if (!input.contains("i/")) {
            throw new EssenFormatException();
        }
        return input.strip();
    }

    public static int parseStepsDuration(String input) throws EssenFormatException{
        if (!input.contains("d/")) {
            throw new EssenFormatException();
        }
        String time = input.split("d/")[1];
        if (time.contains("minutes") || time.contains("mins")) {
            time = time.replace("minutes", "")
                .replace("mins", "")
                .trim();
            return Integer.parseInt(time);
        } else if (time.contains("hours") || time.contains("h") || time.contains("hour")) {
            time = time.replace("hours", "")
                .replace("h", "")
                .replace("hour", "")
                .trim();
            return (int) (Double.parseDouble(time)*60);
        } else {
            throw new EssenFormatException();
        }

    }
}
