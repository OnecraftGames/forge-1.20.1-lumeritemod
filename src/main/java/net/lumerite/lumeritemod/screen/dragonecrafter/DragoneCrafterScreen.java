package net.lumerite.lumeritemod.screen.dragonecrafter;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lumerite.lumeritemod.LumeriteMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DragoneCrafterScreen extends AbstractContainerScreen<DragoneCrafterMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(LumeriteMod.MOD_ID, "textures/gui/dragone_crafter_gui.png");

    public DragoneCrafterScreen(DragoneCrafterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - 201) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, 201);

        renderProgressArrow(guiGraphics, x, y);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font,
                this.playerInventoryTitle.getString(),
                8,
                 90,
                0x808080,
                false
        );
        pGuiGraphics.drawString(this.font,
                this.title.getString(),
                8,
                -12,
                0x808080,
                false
        );
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 98, y + 52, 176, 0, menu.getScaledProgress(), 18);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

}
