package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.ScorchRadish;
import javafx.scene.image.Image;

import java.util.Objects;
/**
 * A box that holds scorch radishes.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class ScorchRadishBox extends IngredientBox {
    /**
     * The sprite for the scorch radish box.
     */
    public final static Image SCORCHRADISH_BOX_SPRITE = new Image(Objects.requireNonNull(ScorchRadishBox.class.
            getResourceAsStream("scorch_radish_crate.png")));

    /**
     * Constructs a scorch radish box.
     */

    public ScorchRadishBox() {
        super(new ScorchRadish(), SCORCHRADISH_BOX_SPRITE);
    }
}
