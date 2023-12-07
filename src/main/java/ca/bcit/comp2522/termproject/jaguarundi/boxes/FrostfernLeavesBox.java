package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.FrostfernLeaves;
import javafx.scene.image.Image;

import java.util.Objects;
/**
 * A box that holds frostfern leaves.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class FrostfernLeavesBox extends IngredientBox {
    /**
     * The sprite for the frostfern leaves box.
     */
    public final static Image FROSTFERNLEAVES_BOX_SPRITE = new Image(Objects.
            requireNonNull(FrostfernLeavesBox.class.getResourceAsStream("frostfern_leaves_crate.png")));

    /**
     * Constructs a frostfern leaves box.
     */
    public FrostfernLeavesBox() {
        super(new FrostfernLeaves(), FROSTFERNLEAVES_BOX_SPRITE);
    }
}
