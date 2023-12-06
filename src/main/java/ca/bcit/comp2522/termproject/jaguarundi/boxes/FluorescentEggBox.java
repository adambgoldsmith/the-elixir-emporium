package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.FluorescentEgg;
import javafx.scene.image.Image;

import java.util.Objects;

public class FluorescentEggBox extends IngredientBox {
    public final static Image FLUORESCENT_EGG_BOX_SPRITE = new Image(Objects.requireNonNull(FluorescentEggBox.class.getResourceAsStream("fluorescent_egg_crate.png")));

    public FluorescentEggBox() {
        super(new FluorescentEgg(), FLUORESCENT_EGG_BOX_SPRITE);
    }
}