package oas.work.colony.client.gui;

import oas.work.colony.world.inventory.StackyGUIMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.components.Button;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StackyGUIScreen extends AbstractContainerScreen<StackyGUIMenu> {
    private List<BufferedImage> images;
    private boolean loadingImages;
    private LoadingAnimation loadingAnimation;

    // Déclarer les boutons pour le menu
    private Button marketPlaceButton;
    private Button installedButton;
    private Button infoButton;

    public StackyGUIScreen(StackyGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.loadingImages = true;
        this.loadingAnimation = new LoadingAnimation(); // Initialiser l'animation

        // Charger les images de manière asynchrone
        CompletableFuture.runAsync(() -> {
            this.images = ImageLoader.loadImages("config/oas_work/Colony");
            this.loadingImages = false;
        });
    }

    @Override
    public void init() {
        super.init();

        // Créer les boutons du menu en haut
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonSpacing = 10;

        // Position des boutons
        int startX = (this.width - (3 * buttonWidth + 2 * buttonSpacing)) / 2; // Centrer les boutons horizontalement
        int y = 10; // Position verticale des boutons

        // Créer les boutons avec le constructeur correct (en incluant la méthode de narration)
        marketPlaceButton = Button.builder(Component.literal("Market Place"), button -> onMarketPlaceButtonClick())
            .bounds(startX, y, buttonWidth, buttonHeight)
            .build();

        installedButton = Button.builder(Component.literal("Installed"), button -> onInstalledButtonClick())
            .bounds(startX + buttonWidth + buttonSpacing, y, buttonWidth, buttonHeight)
            .build();

        infoButton = Button.builder(Component.literal("Info"), button -> onInfoButtonClick())
            .bounds(startX + 2 * (buttonWidth + buttonSpacing), y, buttonWidth, buttonHeight)
            .build();

        this.addRenderableWidget(marketPlaceButton);
        this.addRenderableWidget(installedButton);
        this.addRenderableWidget(infoButton);
    }

    @Override
public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
    super.render(guiGraphics, mouseX, mouseY, partialTicks); // Render the menu buttons
    this.renderTooltip(guiGraphics, mouseX, mouseY);

    if (loadingImages) {
        // Afficher le message de chargement
        String loadingMessage = loadingAnimation.getAnimatedMessage("Chargement");
        int messageWidth = Minecraft.getInstance().font.width(loadingMessage);
        int messageX = (this.width - messageWidth) / 2;
        int messageY = this.height / 2;

        guiGraphics.drawString(Minecraft.getInstance().font, loadingMessage, messageX, messageY, 0xFFFFFF);
        return;
    }

    if (images == null || images.isEmpty()) {
        return; // Ne rien dessiner si les images ne sont pas encore chargées
    }

    int startX = 40; // Position initiale des images (X)
    int spacing = 120; // Espacement entre chaque élément pour inclure les images de 96x96 + marges

    int availableItems = Math.min(images.size(), (this.height - 40) / spacing); // Ajuster selon la nouvelle taille d'image

    // Dessiner les images et les boutons de sélection
    for (int i = 0; i < availableItems; i++) {
        int elementY = (i * spacing) + 40; // Baisser un peu pour laisser la place au menu en haut

        // Créer un bouton classique à la même position que le bouton radio
        int buttonX = 10; // Position X du bouton
        int buttonY = elementY + (96 / 2) - 9; // Centrer le bouton de 18x18 verticalement sur l'image

        Button selectButton = Button.builder(Component.literal("◯"), button -> {
            // Action du bouton (pour l'instant, rien à faire)
        })
        .bounds(buttonX, buttonY, 18, 18) // Dimension du bouton ajustée à 18x18
        .build();

        // Ajouter le bouton à l'interface
        this.addRenderableWidget(selectButton);

        // Dessiner l'image
        BufferedImage image = images.get(i);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                guiGraphics.fill(startX + x, elementY + y, startX + x + 1, elementY + y + 1, color);
            }
        }

        // Dessiner une ligne blanche pour séparer chaque ensemble
        int lineY = elementY + 96 + 10; // Ajuster pour être juste en dessous de l'image
        guiGraphics.fill(0, lineY, this.width, lineY + 1, 0xFFFFFFFF); // Dessiner un rectangle fin comme ligne
    }
}


    // Actions pour les boutons
    private void onMarketPlaceButtonClick() {
        System.out.println("Bouton Market Place cliqué !");
    }

    private void onInstalledButtonClick() {
        System.out.println("Bouton Installed cliqué !");
    }

    private void onInfoButtonClick() {
        System.out.println("Bouton Info cliqué !");
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        // Ne rien faire lorsque les boutons de sélection sont cliqués
        return false; 
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}