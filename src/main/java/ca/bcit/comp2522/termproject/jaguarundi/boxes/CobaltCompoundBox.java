package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.holdables.CobaltCompound;
import javafx.scene.image.Image;

import java.util.Objects;

public class CobaltCompoundBox extends IngredientBox {
    public final static Image COBALT_COMPOUND_BOX_SPRITE = new Image(Objects.requireNonNull(CobaltCompoundBox.class.getResourceAsStream("cobalt_compound_crate.png")));

    public CobaltCompoundBox() {
        super(new CobaltCompound(), COBALT_COMPOUND_BOX_SPRITE);
    }
}