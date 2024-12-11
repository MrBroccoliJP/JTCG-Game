package Game.Types;

import Game.Map;
import Game.Screen;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;

public class HardDifficulty extends BaseDifficulty {
    public HardDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        super(map, screen, scoreSystem, soundManager, konamiMode);
        this.baseSpeed = 80;
        configureOrbs();
    }

    @Override
    protected void configureOrbs() {
        activeOrbs.add(new GoodOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BadOrb(map));
        activeOrbs.add(new BonusOrb(map));
        activeOrbs.add(new RainbowOrb(map));
    }
}