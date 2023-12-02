package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.image.Image;
import java.util.Objects;

public class HogrootBox extends IngredientBox {
    public final static Image HOGROOT_BOX_SPRITE = new Image(Objects.requireNonNull(Hogroot.class.getResourceAsStream("hogroot_crate.png")));

    public HogrootBox() {
        super(new Hogroot(), HOGROOT_BOX_SPRITE);
    }
}
