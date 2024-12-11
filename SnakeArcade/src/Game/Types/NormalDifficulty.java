package Game.Types;

import Game.Map;
import Game.Screen;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;

public class NormalDifficulty extends BaseDifficulty {
    public NormalDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        super(map, screen, scoreSystem, soundManager, konamiMode);
        this.baseSpeed = 200;
        configureOrbs();
    }

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