package game;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL, // use this status to tell that current instance has "grown".
    TRADE, //use this status to allow actor to engage in trade.

    CONVERSES,
    POWER_STAR,

    BREAK_SHELL,

    GOT_ATTACKED,

    CARRIED


}
