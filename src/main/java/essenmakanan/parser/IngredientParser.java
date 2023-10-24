package essenmakanan.parser;

import essenmakanan.exception.EssenMakananException;
import essenmakanan.ingredient.Ingredient;
import essenmakanan.ingredient.IngredientList;
import essenmakanan.ui.Ui;

public class IngredientParser {
    public void parseIngredientCommand(IngredientList ingredients, String command, String inputDetail)
            throws EssenMakananException {
        Ui ui = new Ui();
        switch(command) {
        case "add":
            String[] allIngredients = inputDetail.split("/i");

            for (String ingredient : allIngredients) {
                ui.printAddIngredientsSuccess(ingredient);
                ingredient = ingredient.strip();
                Ingredient newIngredient = new Ingredient(ingredient);
                ingredients.addIngredient(newIngredient);
            }
            break;
        case "view":
            ui.printAllIngredients(ingredients);
            break;
        default:
            throw new EssenMakananException("Invalid command! Valid commands are: 'add', 'view'");
        }
    }

    public static String parseIngredientTitle(String toAdd) {
        return toAdd.replace("i/", "");
    }
}