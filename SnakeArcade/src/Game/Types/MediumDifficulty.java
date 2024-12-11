package Game.Types;

import Game.Map;
import Game.Screen;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;

public class MediumDifficulty extends BaseDifficulty {
    public MediumDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        super(map, screen, scoreSystem, soundManager, konamiMode);
        this.baseSpeed = 120;
        configureOrbs();
    }

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