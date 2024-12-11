package Game.Types;

import Game.Map;
import Game.Screen;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;

/**
 * Represents the Medium difficulty level for the game.
 * This class extends BaseDifficulty and customizes the game speed and orb configurations
 * for the medium difficulty mode.
 */
public class MediumDifficulty extends BaseDifficulty {

    /**
     * Constructs a HardDifficulty object with the provided game components.
     *
     * @param map         The game map.
     * @param screen      The screen renderer.
     * @param scoreSystem The scoring system.
     * @param soundManager The sound manager.
     * @param konamiMode  Whether Konami mode [cheat mode] is active.
     */
    public MediumDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        super(map, screen, scoreSystem, soundManager, konamiMode);
        this.baseSpeed = 120;
        configureOrbs();
    }

    /**
     * Configures the orbs for the normal difficulty level.
     * Adds a mixture of 1xGoodOrb, 1xBonusOrb, 3xBadOrb and 1xRainbowOrb to the activeOrbs list.
     */
    @Override
    protected void configureOrbs() {
        activeOrbs.add(new GoodOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BonusOrb(map));
        activeOrbs.add(new RainbowOrb(map));
    }
}