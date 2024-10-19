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

    private Button marketPlaceButton;
    private Button installedButton;
    private Button infoButton;

    public StackyGUIScreen(StackyGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.loadingImages = true;
        this.loadingAnimation = new LoadingAnimation();

        CompletableFuture.runAsync(() -> {
            this.images = ImageLoader.loadImages("config/oas_work/Colony");
            this.loadingImages = false;
        });
    }

    @Override
    public void init() {
        super.init();

        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonSpacing = 10;

        int startX = (this.width - (3 * buttonWidth + 2 * buttonSpacing)) / 2;
        int y = 10;

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
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if (loadingImages) {
            String loadingMessage = loadingAnimation.getAnimatedMessage("Chargement");
            int messageWidth = Minecraft.getInstance().font.width(loadingMessage);
            int messageX = (this.width - messageWidth) / 2;
            int messageY = this.height / 2;

            guiGraphics.drawString(Minecraft.getInstance().font, loadingMessage, messageX, messageY, 0xFFFFFF);
            return;
        }

        if (images == null || images.isEmpty()) {
            return;
        }

        int startX = 40;
        int spacing = 100; // Adjusted spacing for 80x80 images

        int availableItems = Math.min(images.size(), (this.height - 40) / spacing);

        for (int i = 0; i < availableItems; i++) {
            int elementY = (i * spacing) + 40;

            int buttonX = 10;
            int buttonY = elementY + (80 / 2) - 9; // Adjusted button position for 80x80 images

            Button selectButton = Button.builder(Component.literal("◯"), button -> {
                // Action du bouton (pour l'instant, rien à faire)
            })
            .bounds(buttonX, buttonY, 18, 18)
            .build();

            this.addRenderableWidget(selectButton);

            BufferedImage image = images.get(i);
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int color = image.getRGB(x, y);
                    guiGraphics.fill(startX + x, elementY + y, startX + x + 1, elementY + y + 1, color);
                }
            }

            int lineY = elementY + 80 + 10; // Adjusted line position for 80x80 images
            guiGraphics.fill(0, lineY, this.width, lineY + 1, 0xFFFFFFFF); // Draw white line
        }
    }

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
        return false; 
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
