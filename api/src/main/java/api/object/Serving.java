package api.object;

public class Serving {
    private int food_id;
    private int servings;
    private Meal.Type type;

    public Serving(int food_id, int servings, Meal.Type type) {
        this.food_id = food_id;
        this.servings = servings;
        this.type = type;
    }

    public Serving(){
        this(0,0,null);
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Meal.Type getType() {
        return type;
    }

    public void setType(Meal.Type type) {
        this.type = type;
    }
}
