package org.infernalstudios.enemyexp.content;

import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class EEAnimations {

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation SPRINT = RawAnimation.begin().thenLoop("sprint");
    public static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");

    // Sprinter
    public static final RawAnimation STAGGERED_USED = RawAnimation.begin().then("staggered", Animation.LoopType.PLAY_ONCE);

    // Slugger
    public static final RawAnimation CHARGE = RawAnimation.begin().thenPlayAndHold("charge");
    public static final RawAnimation DASH = RawAnimation.begin().thenLoop("dash");

    // Goblin Thief
    public static final RawAnimation SUSPICIOUS = RawAnimation.begin().thenLoop("suspicious");
    public static final RawAnimation SNEAKY = RawAnimation.begin().thenLoop("sneaky");
    public static final RawAnimation SPRINT_PANIC = RawAnimation.begin().thenLoop("sprint_panic");
    public static final RawAnimation POUNCE_LAND = RawAnimation.begin().thenPlay("pounce").thenPlay("land");
    public static final RawAnimation SPRINT_CONFIDENT = RawAnimation.begin().thenLoop("sprint_confident");

    // Meature
    public static final RawAnimation DANCE = RawAnimation.begin().thenLoop("dance");
    public static final RawAnimation HAPPY = RawAnimation.begin().thenPlay("happy");
    public static final RawAnimation LEAP = RawAnimation.begin().thenPlay("leap");

    // Equestrian
    public static final RawAnimation PANICKED = RawAnimation.begin().thenLoop("panicked");
    public static final RawAnimation TROT = RawAnimation.begin().thenLoop("trot");
    public static final RawAnimation PREPARE = RawAnimation.begin().thenPlayAndHold("prepare");
    public static final RawAnimation GALLOP = RawAnimation.begin().thenLoop("gallop");

    // Vampire
    public static final RawAnimation VAMPIRE_ALERT = RawAnimation.begin().then("alert", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation VAMPIRE_CHASE = RawAnimation.begin().thenLoop("chase");
    public static final RawAnimation VAMPIRE_FLYING = RawAnimation.begin().thenLoop("flying");
    public static final RawAnimation VAMPIRE_HOVER = RawAnimation.begin().thenLoop("hover");
    public static final RawAnimation VAMPIRE_WINGS_FLAPPING = RawAnimation.begin().thenLoop("wings_flapping");

    public static final RawAnimation VAMPIRE_DODGE_BACK = RawAnimation.begin().then("dodge_back", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation VAMPIRE_DODGE_FORWARD = RawAnimation.begin().then("dodge_forward", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation VAMPIRE_HURT_STANDING = RawAnimation.begin().then("hurt_standing", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation VAMPIRE_HURT_FLYING = RawAnimation.begin().then("hurt_flying", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation VAMPIRE_FALLING = RawAnimation.begin().then("falling_start", Animation.LoopType.PLAY_ONCE).thenLoop("falling_loop");
    public static final RawAnimation VAMPIRE_FALLING_END = RawAnimation.begin().then("falling_end", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation VAMPIRE_DIE_GROUND = RawAnimation.begin().then("die_ground", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation VAMPIRE_DIE_AIR = RawAnimation.begin().then("die_air", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation VAMPIRE_BITER_SPAWN = RawAnimation.begin().then("biter_spawn", Animation.LoopType.PLAY_ONCE);

    // Biter
    public static final RawAnimation BITER_FORWARD = RawAnimation.begin().thenLoop("forward");
    public static final RawAnimation BITER_WOBBLING = RawAnimation.begin().thenLoop("wobbling");
    public static final RawAnimation BITER_WINGS_FLAPPING = RawAnimation.begin().thenLoop("wings_flapping");
    public static final RawAnimation BITER_DIE = RawAnimation.begin().then("die", Animation.LoopType.PLAY_ONCE);

    // Gallant
    private static final RawAnimation DORMANT = RawAnimation.begin().thenPlay("dormant");
    private static final RawAnimation DORMANT_NO_SHIELD = RawAnimation.begin().thenPlay("dormant_no_shield");
    private static final RawAnimation AWAKEN = RawAnimation.begin().thenPlay("awaken");
    private static final RawAnimation AWAKEN_NO_SHIELD = RawAnimation.begin().thenPlay("awaken_no_shield");
    private static final RawAnimation IDLE_NO_SHIELD = RawAnimation.begin().thenPlay("idle_no_shield");
    private static final RawAnimation RAAAHG = RawAnimation.begin().thenPlay("raaahg");
    private static final RawAnimation RAAAHG_NO_SHIELD = RawAnimation.begin().thenPlay("raaahg_no_shield");
    private static final RawAnimation WALK_NO_SHIELD = RawAnimation.begin().thenPlay("walk_no_shield");
    private static final RawAnimation SPRINT_NO_SHIELD = RawAnimation.begin().thenPlay("sprint_no_shield");
    private static final RawAnimation SPRINT_ATTACK = RawAnimation.begin().thenPlay("sprint_attack");
    private static final RawAnimation DASH_NO_SHIELD = RawAnimation.begin().thenPlay("dash_no_shield");
    private static final RawAnimation DASH_ATTACK = RawAnimation.begin().thenPlay("dash_attack");
    private static final RawAnimation BLOCKED = RawAnimation.begin().thenPlay("blocked");
    private static final RawAnimation BLOCKED_NO_SHIELD = RawAnimation.begin().thenPlay("blocked_no_shield");
    private static final RawAnimation PARRY = RawAnimation.begin().thenPlay("parry");
    private static final RawAnimation PARRIED = RawAnimation.begin().thenPlay("parried");
    private static final RawAnimation VULNERABLE = RawAnimation.begin().thenPlay("vulnerable");
    private static final RawAnimation HIT_VULNERABLE = RawAnimation.begin().thenPlay("hit_vulnerable");
    private static final RawAnimation RESET = RawAnimation.begin().thenPlay("reset");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("death");


    private EEAnimations() {
    }
}