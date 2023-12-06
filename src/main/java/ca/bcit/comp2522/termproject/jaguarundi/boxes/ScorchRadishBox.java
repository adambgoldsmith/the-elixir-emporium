package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.ScorchRadish;
import javafx.scene.image.Image;

import java.util.Objects;

public class ScorchRadishBox extends IngredientBox {
    public final static Image SCORCHRADISH_BOX_SPRITE = new Image(Objects.requireNonNull(ScorchRadishBox.class.getResourceAsStream("scorch_radish_crate.png")));

    public ScorchRadishBox() {
        super(new ScorchRadish(), SCORCHRADISH_BOX_SPRITE);
    }
}
