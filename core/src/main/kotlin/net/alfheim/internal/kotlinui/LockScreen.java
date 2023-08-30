package net.alfheim.internal.kotlinui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class LockScreen extends Screen {
    private final Runnable onClosed;

    public LockScreen(Runnable onClosed) {
        super(Text.empty());

        this.onClosed = onClosed;
    }

    @Override
    public void close() {
        super.close();

        this.onClosed.run();
    }
}