package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.FluorescentEgg;
import javafx.scene.image.Image;

import java.util.Objects;
/**
 * A box that holds fluorescent eggs.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class FluorescentEggBox extends IngredientBox {
    /**
     * The sprite for the fluorescent egg box.
     */
    public final static Image FLUORESCENT_EGG_BOX_SPRITE = new Image(Objects.requireNonNull(FluorescentEggBox.class.
            getResourceAsStream("fluorescent_egg_crate.png")));
    /**
     * Constructs a fluorescent egg box.
     */
    public FluorescentEggBox() {
        super(new FluorescentEgg(), FLUORESCENT_EGG_BOX_SPRITE);
    }
}
