package net.bunten.enderscape.client.hud;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.EnderscapeConfig;
import net.bunten.enderscape.client.EnderscapeClient;
import net.bunten.enderscape.client.mixin.MusicManagerAccess;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class DebugHud extends HudElement {

    public DebugHud() {
        super(Enderscape.id("debug"), RenderPhase.AFTER_HUD);
    }

    private final DecimalFormat df1 = new DecimalFormat("0.0");

    private String shorten(String value, String r) {
        value = value.replace(".ogg", "");
        char[] array = value.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == r.charAt(0)) {
                String first = String.valueOf(value.subSequence(0, i));
                value = value.replaceFirst(first, "");
                array = value.toCharArray();
                i = 0;
            }
        }
        return value.replace(r, "");
    }

    private LinkedHashMap<List<String>, Boolean> getDebugText() {
        LinkedHashMap<List<String>, Boolean> map = new LinkedHashMap<>();
        map.put(getClientInfo(), config.debugHudClientInfo.get());
        map.put(getMusicInfo(), config.debugHudMusicInfo.get());
        map.put(getPlayerInfo(), config.debugHudPlayerInfo.get());
        return map;
    }

    private List<String> getClientInfo() {
        List<String> list = Lists.newArrayList();

        list.add("Client");
        list.add("  fps: " + Minecraft.getInstance().getFps());
        list.add("  guiScale: " + Minecraft.getInstance().options.guiScale().get());
        list.add("  renderDistance: " + Minecraft.getInstance().options.renderDistance().get());
        list.add("  postMirrorUseTicks: " + EnderscapeClient.postMirrorUseTicks);

        return list;
    }

    private List<String> getMusicInfo() {
        List<String> list = Lists.newArrayList();

        MusicManagerAccess access = (MusicManagerAccess) (Minecraft.getInstance().getMusicManager());
        var current = access.getCurrentMusic();

        list.add("Music");
        list.add("  Volume: " + (int) ((Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC)) * 100) + "%");
        list.add("  Playing: " + Minecraft.getInstance().getSoundManager().isActive(current));
        list.add("  Current event: " + (current != null ? shorten(current.getLocation().getPath(), ".") : "null"));
        list.add("  Current track: " + (current != null ? shorten(current.getSound().getPath().getPath(), "/") : "null"));
        Music music = Minecraft.getInstance().getSituationalMusic();
        list.add("  Next event: " + shorten(Minecraft.getInstance().getSituationalMusic().getEvent().value().getLocation().getPath(), "."));
        list.add("    getMinDelay: " + music.getMinDelay());
        list.add("    getMaxDelay: " + music.getMaxDelay());
        list.add("    replaceCurrentMusic: " + music.replaceCurrentMusic());
        list.add("  Playing next event: " + Minecraft.getInstance().getMusicManager().isPlayingMusic(music));
        list.add("  Delay: " + access.getNextSongDelay() + " (" + String.format("%02d", access.getNextSongDelay() / 20 / 60) + ":" + String.format("%02d", (access.getNextSongDelay() / 20) % 60) + ")");

        return list;
    }

    private List<String> getPlayerInfo() {
        LocalPlayer player = Minecraft.getInstance().player;
        List<String> list = Lists.newArrayList();

        if (Minecraft.getInstance().level != null && player != null) {
            Vec3 vel;
            if (player.isPassenger()) {
                vel = Objects.requireNonNull(player.getVehicle()).getDeltaMovement();
            } else {
                vel = player.getDeltaMovement();
            }

            list.add("Player");

            list.add("  BlockPos: " + player.blockPosition().toShortString());
            list.add("  Dimension: " + player.level().dimension().location().toString());

            float f = (float) (player.getX() - 0);
            float h = (float) (player.getZ() - 0);

            list.add("  Center Distance: " + (int) Mth.sqrt(f * f + h * h));

            list.add("  Velocity");

            list.add("    x: " + df1.format(vel.x));
            list.add("    y: " + df1.format(vel.y));
            list.add("    z: " + df1.format(vel.z));
            list.add("    lengthSqr: " + df1.format(vel.lengthSqr()));
            list.add("    horizontalDistanceSqr: " + df1.format(vel.horizontalDistanceSqr()));

            list.add("  fallDistance: " + (int) player.fallDistance);

            list.add("  XY Rotation: " + (int) Mth.wrapDegrees(player.getXRot()) + " / " + (int) Mth.wrapDegrees(player.getYRot()));
        }

        return list;
    }

    public String getLongestString(List<String> list) {
        String longest = "";
        for (String string : list) {
            if (string.length() > longest.length()) {
                longest = string;
            }
        }
        return longest;
    }

    public void render(GuiGraphics graphics, DeltaTracker delta) {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().getDebugOverlay().showDebugScreen() || Minecraft.getInstance().options.hideGui || !EnderscapeConfig.getInstance().debugHudEnabled.getAsBoolean()) return;

        graphics.pose().pushPose();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        float total = switch(Minecraft.getInstance().options.guiScale().get()){
            case 3 -> 0.6666F;
            case 4 -> 0.5F;
            default -> 1;
        };

        graphics.pose().scale(total, total, total);

        int x = 2;
        int y = 2;

        for (List<String> list : getDebugText().keySet()) {
            if (!getDebugText().get(list)) continue;

            graphics.fill(x, y, x + Minecraft.getInstance().font.width(getLongestString(list)) + 10, y + (list.size() * 12) + 4, 0x4F052E60);

            for (String string : list) {
                if (!string.isEmpty()) {
                    graphics.drawString(Minecraft.getInstance().font, string, x + 4, y + 4, 0xEFFFFFFF);
                }
                y += 12;
            }
            y += 6;
        }

        graphics.pose().popPose();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}