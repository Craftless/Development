package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsInviteScreen extends RealmsScreen {
   private static final Logger field_224213_a = LogManager.getLogger();
   private TextFieldWidget field_224214_b;
   private final RealmsServer field_224215_c;
   private final RealmsConfigureWorldScreen field_224216_d;
   private final Screen field_224217_e;
   private String field_224222_j;
   private boolean field_224223_k;

   public RealmsInviteScreen(RealmsConfigureWorldScreen p_i232207_1_, Screen p_i232207_2_, RealmsServer p_i232207_3_) {
      this.field_224216_d = p_i232207_1_;
      this.field_224217_e = p_i232207_2_;
      this.field_224215_c = p_i232207_3_;
   }

   public void tick() {
      this.field_224214_b.tick();
   }

   public void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.field_224214_b = new TextFieldWidget(this.minecraft.fontRenderer, this.width / 2 - 100, func_239562_k_(2), 200, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.invite.profile.name"));
      this.addListener(this.field_224214_b);
      this.setFocusedDefault(this.field_224214_b);
      this.addButton(new Button(this.width / 2 - 100, func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.buttons.invite"), (p_237844_1_) -> {
         this.func_224211_a();
      }));
      this.addButton(new Button(this.width / 2 - 100, func_239562_k_(12), 200, 20, DialogTexts.field_240633_d_, (p_237843_1_) -> {
         this.minecraft.displayGuiScreen(this.field_224217_e);
      }));
   }

   public void onClose() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
   }

   private void func_224211_a() {
      RealmsClient realmsclient = RealmsClient.func_224911_a();
      if (this.field_224214_b.getText() != null && !this.field_224214_b.getText().isEmpty()) {
         try {
            RealmsServer realmsserver = realmsclient.func_224910_b(this.field_224215_c.field_230582_a_, this.field_224214_b.getText().trim());
            if (realmsserver != null) {
               this.field_224215_c.field_230589_h_ = realmsserver.field_230589_h_;
               this.minecraft.displayGuiScreen(new RealmsPlayerScreen(this.field_224216_d, this.field_224215_c));
            } else {
               this.func_224209_a(I18n.format("mco.configure.world.players.error"));
            }
         } catch (Exception exception) {
            field_224213_a.error("Couldn't invite user");
            this.func_224209_a(I18n.format("mco.configure.world.players.error"));
         }

      } else {
         this.func_224209_a(I18n.format("mco.configure.world.players.error"));
      }
   }

   private void func_224209_a(String p_224209_1_) {
      this.field_224223_k = true;
      this.field_224222_j = p_224209_1_;
      RealmsNarratorHelper.func_239550_a_(p_224209_1_);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.minecraft.displayGuiScreen(this.field_224217_e);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack);
      this.font.drawString(matrixStack, I18n.format("mco.configure.world.invite.profile.name"), (float)(this.width / 2 - 100), (float)func_239562_k_(1), 10526880);
      if (this.field_224223_k) {
         this.drawCenteredString(matrixStack, this.font, this.field_224222_j, this.width / 2, func_239562_k_(5), 16711680);
      }

      this.field_224214_b.render(matrixStack, mouseX, mouseY, partialTicks);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }
}
