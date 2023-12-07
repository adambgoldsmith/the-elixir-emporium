package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.CobaltCompound;
import javafx.scene.image.Image;

import java.util.Objects;
/**
 * A box that holds cobalt compound.
 *
 * @author Vivian , Adam
 * @version 2023
 *
 */


public class CobaltCompoundBox extends IngredientBox {
    /**
     * The sprite for the cobalt compound box.
     */
    public final static Image COBALT_COMPOUND_BOX_SPRITE = new Image(Objects.requireNonNull(CobaltCompoundBox.class.
            getResourceAsStream("cobalt_compound_crate.png")));

    /**
     * Constructs a cobalt compound box.
     */
    public CobaltCompoundBox() {
        super(new CobaltCompound(), COBALT_COMPOUND_BOX_SPRITE);
    }
}
