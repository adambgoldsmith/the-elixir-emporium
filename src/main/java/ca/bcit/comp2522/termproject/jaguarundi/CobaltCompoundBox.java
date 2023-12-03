package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.image.Image;

import java.util.Objects;

public class CobaltCompoundBox extends IngredientBox{
    public final static Image COBALT_COMPOUND_BOX_SPRITE = new Image(Objects.requireNonNull(CobaltCompound.class.getResourceAsStream("frostfern_leaves_crate.png")));

    public CobaltCompoundBox() {
        super(new CobaltCompound(), COBALT_COMPOUND_BOX_SPRITE);
    }
}