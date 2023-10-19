package essenmakanan.parser;

import essenmakanan.exception.EssenMakananException;
import essenmakanan.recipe.Recipe;
import essenmakanan.recipe.RecipeList;
import essenmakanan.ui.Ui;

public class RecipeParser {

    public void parseRecipeCommand(RecipeList recipes, String command, String inputDetail) throws EssenMakananException{
        switch(command) {
        case "add":
            String recipeName = inputDetail.substring(2);
            assert !recipeName.contains("r/") : "Recipe name should not contain key characters";

            Recipe newRecipe = new Recipe(recipeName);
            recipes.addRecipe(newRecipe);

            Ui.printAddRecipeSuccess(recipeName);
            break;
        case "view":
            Ui.printAllRecipes(recipes);
            break;
        default:
            throw new EssenMakananException("Invalid command! Valid commands are: 'add', 'view'");
        }
    }
}
