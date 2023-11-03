package essenmakanan.storage;

import essenmakanan.exception.EssenFileNotFoundException;
import essenmakanan.ingredient.Ingredient;
import essenmakanan.ingredient.IngredientList;
import essenmakanan.recipe.Recipe;
import essenmakanan.recipe.RecipeList;
import essenmakanan.recipe.Step;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StorageTest {

    private static String DATA_INVALID_PATH = "src/test/data/invalid.txt";
    private static String DATA_RECIPE_TEST_PATH = "src/test/data/recipes.txt";
    private static String DATA_INGREDIENT_TEST_PATH = "src/test/data/ingredients.txt";
    private static String DATA_EMPTY_RECIPE_TEXT_PATH = "src/test/data/emptyRecipe.txt";

    @Test
    public void accessIngredientDatabase_invalidPath_throwsEssenFileNotFoundException() {
        IngredientStorage ingredientStorage = new IngredientStorage(DATA_INVALID_PATH);
        assertThrows(EssenFileNotFoundException.class, ingredientStorage::restoreSavedData);
    }

    @Test
    public void accessRecipeDatabase_invalidPath_throwsEssenFileNotFoundException() {
        RecipeStorage recipeStorage = new RecipeStorage(DATA_INVALID_PATH);
        assertThrows(EssenFileNotFoundException.class, recipeStorage::restoreSavedData);
    }

    @Test
    public void restoreSavedIngredients_storedIngredients_returnsFilledIngredientList() throws Exception {
        IngredientStorage ingredientStorage = new IngredientStorage(DATA_INGREDIENT_TEST_PATH);
        IngredientList ingredients = new IngredientList(ingredientStorage.restoreSavedData());

        assertEquals("bread", ingredients.getIngredients().get(0).getName());
        assertEquals("2", ingredients.getIngredients().get(0).getQuantity());
        assertEquals("g", ingredients.getIngredients().get(0).getUnit().getValue());

        assertEquals("cheese", ingredients.getIngredients().get(1).getName());
        assertEquals("10", ingredients.getIngredients().get(1).getQuantity());
        assertEquals("pc", ingredients.getIngredients().get(1).getUnit().getValue());

        assertEquals("carrot", ingredients.getIngredients().get(2).getName());
        assertEquals("5", ingredients.getIngredients().get(2).getQuantity());
        assertEquals("kg", ingredients.getIngredients().get(2).getUnit().getValue());
    }

    @Test
    public void restoreSavedRecipes_storedRecipes_returnFilledRecipeList() throws Exception {
        RecipeStorage recipeStorage = new RecipeStorage(DATA_RECIPE_TEST_PATH);
        RecipeList recipes = new RecipeList(recipeStorage.restoreSavedData());

        Recipe recipe = recipes.getRecipe(0);

        assertEquals("bread", recipe.getTitle());

        Step step = recipe.getRecipeSteps().getStepByIndex(0);
        assertEquals("step1", step.getDescription());
        assertEquals(1, step.getTag().getPriority());

        Ingredient ingredient = recipe.getRecipeIngredients().getIngredientByIndex(0);

        assertEquals("bread", ingredient.getName());
        assertEquals("5", ingredient.getQuantity());
        assertEquals("kg", ingredient.getUnit().getValue());
    }

    @Test
    public void restoreEmptyRecipes_storedRecipes_returnRecipeWithEmptyAttributes() throws Exception {
        RecipeStorage recipeStorage = new RecipeStorage(DATA_EMPTY_RECIPE_TEXT_PATH);
        RecipeList recipes = new RecipeList(recipeStorage.restoreSavedData());

        Recipe recipe = recipes.getRecipe(0);

        assertEquals("soup", recipe.getTitle());

        assertTrue(recipe.getRecipeSteps().getSteps().isEmpty());
        assertTrue(recipe.getRecipeIngredients().getIngredients().isEmpty());
    }
}