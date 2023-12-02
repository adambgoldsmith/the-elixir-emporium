package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.image.Image;

import java.util.Objects;

public class FrostfernLeavesBox extends IngredientBox{
    public final static Image FROSTFERNLEAVES_BOX_SPRITE = new Image(Objects.requireNonNull(FrostfernLeaves.class.getResourceAsStream("frostfern_leaves_crate.png")));

    public FrostfernLeavesBox() {
        super(new FrostfernLeaves(), FROSTFERNLEAVES_BOX_SPRITE);
    }
}