package oas.work.colony.client.gui;

public class LoadingAnimation {
    private int animationCounter; // Compteur pour l'animation des points
    private long lastRenderTime; // Pour suivre le temps de rendu

    public LoadingAnimation() {
        this.animationCounter = 0; // Initialiser le compteur d'animation
        this.lastRenderTime = System.currentTimeMillis(); // Initialiser le temps de rendu
    }

    public String getAnimatedMessage(String baseMessage) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRenderTime >= 500) { // Change toutes les 500 ms
            animationCounter = (animationCounter + 1) % 4; // 0 à 3
            lastRenderTime = currentTime; // Met à jour le temps
        }

        StringBuilder animatedMessage = new StringBuilder(baseMessage);
        for (int i = 0; i < animationCounter; i++) {
            animatedMessage.append("."); // Ajouter des points
        }
        return animatedMessage.toString();
    }
}
