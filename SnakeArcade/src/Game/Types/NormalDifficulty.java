package Game.Types;

import Game.Map;
import Game.Screen;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;

/**
 * Represents the Normal difficulty level for the game.
 * This class extends BaseDifficulty and customizes the game speed and orb configurations
 * for the normal difficulty mode.
 */
public class NormalDifficulty extends BaseDifficulty {

    /**
     * Constructs a NormalDifficulty object with the provided game components.
     *
     * @param map         The game map.
     * @param screen      The screen renderer.
     * @param scoreSystem The scoring system.
     * @param soundManager The sound manager.
     * @param konamiMode  Whether Konami mode [cheat mode] is active.
     */
    public NormalDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        super(map, screen, scoreSystem, soundManager, konamiMode);
        this.baseSpeed = 200;
        configureOrbs();
    }

    /**
     * Configures the orbs for the normal difficulty level.
     * Adds a mixture of 2xGoodOrb, 2xBonusOrb, and 2xRainbowOrb to the activeOrbs list.
     */
    @Override
    protected void configureOrbs() {
        activeOrbs.add(new GoodOrb(map));
        activeOrbs.add(new GoodOrb(map));
        activeOrbs.add(new BonusOrb(map));
        activeOrbs.add(new BonusOrb(map));
        activeOrbs.add(new RainbowOrb(map));
        activeOrbs.add(new RainbowOrb(map));
    }
}