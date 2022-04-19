package xyz.terrifictable.util;

import java.awt.*;

public class ColorUtil {

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int)(seconds * 1000)) / (float)(seconds * 1000);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static int getRainbowWave(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int)(seconds * 1000)) / (float)(seconds * 1000);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static Color rainbowEffect(long offset, float fade) {
        float hue = (float) (System.currentTimeMillis() + offset) / 1.0E10f % 1.0f;
        long c = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color color = new Color((int) c);
        return new Color(color.getRed() / 255.0f * fade, color.getGreen() / 255.0f * fade, color.getBlue() / 255.0f * fade, color.getAlpha() / 255.0f);
    }

    public static int colorEqTNum(int max, int current) {
        int green = Math.min(255 * current / max, 255);
        int red = 255 - green;
        int blue = Math.max(red - green, 0);

        return red * 65536 + green * 256 + blue;

    }
}
