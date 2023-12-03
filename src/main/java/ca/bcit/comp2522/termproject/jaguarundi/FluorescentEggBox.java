package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.image.Image;

import java.util.Objects;

public class FluorescentEggBox extends IngredientBox{
    public final static Image FLUORESCENT_EGG_BOX_SPRITE = new Image(Objects.requireNonNull(FluorescentEgg.class.getResourceAsStream("fluorescent_egg_crate.png")));

    public FluorescentEggBox() {
        super(new FluorescentEgg(), FLUORESCENT_EGG_BOX_SPRITE);
    }
}