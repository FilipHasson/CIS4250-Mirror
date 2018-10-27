package api.object;

import api.object.Nutrients.Macros;
import api.object.Nutrients.Minerals;
import api.object.Nutrients.Vitamins;

public class Nutrition {
    private int id;
    private Macros macros;
    private Minerals minerals;
    private Vitamins vitamins;

    public Nutrition (int id, Macros macros, Minerals minerals, Vitamins vitamins){

    }

    public Nutrition(){
        this(0,null,null,null);
    }
}
