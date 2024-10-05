package oas.work.colony.client.gui;

import net.minecraft.client.gui.GuiGraphics;

public class CircleButton {

    public static void draw(GuiGraphics guiGraphics, int x, int y, boolean selected) {
        // Dessiner un cercle
        guiGraphics.fill(x, y, x + 12, y + 12, 0xFFFFFFFF); // Cercle extérieur
        if (selected) {
            guiGraphics.fill(x + 3, y + 3, x + 9, y + 9, 0xFF000000); // Cercle intérieur si sélectionné
        }
    }
}
