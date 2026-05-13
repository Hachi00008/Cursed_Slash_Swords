package hachi00008.cursed_slash_swords.mixin;

import hachi00008.cursed_slash_swords.CursedSlashSwords;
import hachi00008.cursed_slash_swords.client.SoulClientData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBarRenderer.class)
public class ExperienceBarMixin {

    @Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
    private void redirectExtractBackground(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        int left = ((ExperienceBarRenderer) (Object)this).left(minecraft.getWindow());
        int top = ((ExperienceBarRenderer) (Object)this).top(minecraft.getWindow());

        int xpNeeded = player.getXpNeededForNextLevel();

        if (xpNeeded > 0) {
            ci.cancel();

            int totalWidth = 182;
            int gap = 2;
            int leftBarWidth = 140;
            int rightBarWidth = totalWidth - leftBarWidth - gap;

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    Identifier.withDefaultNamespace("hud/experience_bar_background"),
                    left, top, leftBarWidth, 5);

            int xpProgress = (int) (player.experienceProgress * (leftBarWidth +1));
            if (xpProgress > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                        Identifier.withDefaultNamespace("hud/experience_bar_progress"),
                        leftBarWidth, 5, 0, 0, left, top, Math.min(xpProgress, leftBarWidth), 5);
            }

            int rightBarX = left + leftBarWidth + gap;
            Identifier CUSTOM_BG = Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "hud/soul_experience_bar_background");
            Identifier CUSTOM_PROG = Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "hud/soul_experience_bar_progress");
            Identifier SOUL_GUI = Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "hud/soul_gui");

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CUSTOM_BG, rightBarX, top, rightBarWidth, 5);

            float currentSoul = (float) SoulClientData.currentSoul;
            float maxSoulPressure = (float) SoulClientData.maxSoulPressure;
            float customRatio = Math.min(currentSoul / maxSoulPressure, 1.0f);
            int customProgress = (int) (customRatio * rightBarWidth);
            if (customProgress > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CUSTOM_PROG,
                        rightBarWidth, 5, 0, 0, rightBarX, top, customProgress, 5);
            }

            String soulText = String.valueOf((int)currentSoul);
            int iconX = rightBarX + rightBarWidth - 2;
            int iconY = top - 4;
            int textX = rightBarX + rightBarWidth + 10;
            int textY = top - 2;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SOUL_GUI, iconX, iconY, 16, 16);
            graphics.text(minecraft.font, soulText, textX, textY, 0xFF55FFFF, true);
        }
    }
}
