package essenmakanan.recipe;

import essenmakanan.exception.EssenFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditRecipeTest {

    private RecipeList recipes;
    private Recipe recipeToEdit;

    @BeforeEach
    public void setUp() {
        recipes = new RecipeList();
        recipeToEdit = new Recipe("Bread", new String[]{"Prepare", "Bake"});
        recipes.addRecipe(recipeToEdit);
    }

    @Test
    public void editRecipeName_validInput_editSuccess() {
        String[] editDetails = {"edit", "n/Breads"};
        try {
            recipes.editRecipe(recipeToEdit, editDetails);
        } catch (EssenFormatException e) {
            e.handleException();
        }

        assertEquals("Breads", recipeToEdit.getTitle());
    }

    @Test
    public void editRecipeStep_validInput_editSuccess() {
        String[] editDetails = {"edit", "s/1,Prepare the dough"};
        try {
            recipes.editRecipe(recipeToEdit, editDetails);
        } catch (EssenFormatException e) {
            e.handleException();
        }

        String newStep = recipeToEdit.getRecipeSteps().getStepByIndex(0).getDescription();
        assertEquals("Prepare the dough", newStep);
    }

    @Test
    public void editRecipeNameAndStep_validInput_editSuccess() {
        String[] editDetails = {"edit", "n/Breads", "s/1,Prepare the dough"};
        try {
            recipes.editRecipe(recipeToEdit, editDetails);
        } catch (EssenFormatException e) {
            e.handleException();
        }

        String newStep = recipeToEdit.getRecipeSteps().getStepByIndex(0).getDescription();
        assertEquals("Prepare the dough", newStep);
        assertEquals("Breads", recipeToEdit.getTitle());
    }

    @Test
    public void editRecipe_invalidInput_exceptionThrown() {
        String[] editDetails = {"edit", "/nbreads"};

        assertThrows(EssenFormatException.class, () -> {
            recipes.editRecipe(recipeToEdit, editDetails);
        });
    }
}