package essenmakanan.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RecipeStepList extends ArrayList<Step> {
    private ArrayList<String> steps;

    public RecipeStepList() {
        Scanner in = new Scanner(System.in);
        String input;
        boolean isAddingSteps = true;
        steps = new ArrayList<>();

        do {
            input = in.nextLine();

            if (input.equals("end")) {
                isAddingSteps = false;
            } else {
                addStep(input);
            }
        } while(isAddingSteps);
        System.out.println("done steps");
    }

    public RecipeStepList(String[] steps) {
        this.steps = new ArrayList<>(Arrays.asList(steps));
    }

    public void addStep(String input) {
        steps.add(input);
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void deleteStep(Step step) {
        remove(step);
    }

}