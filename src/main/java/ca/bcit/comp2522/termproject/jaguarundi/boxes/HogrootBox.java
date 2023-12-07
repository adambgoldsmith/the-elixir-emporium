package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.Hogroot;
import javafx.scene.image.Image;
import java.util.Objects;
/**
 * A box that holds hogroot.
 *
 * @author Vivian , Adam
 * @version 2023
 */
public class HogrootBox extends IngredientBox {
    /**
     * The sprite for the hogroot box.
     */
    public final static Image HOGROOT_BOX_SPRITE = new Image(Objects.requireNonNull(HogrootBox.class.
            getResourceAsStream("hogroot_crate.png")));

    /**
     * Constructs a hogroot box.
     */
    public HogrootBox() {
        super(new Hogroot(), HOGROOT_BOX_SPRITE);
    }
}
