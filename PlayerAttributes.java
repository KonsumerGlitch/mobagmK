package com.zenmgmoba.model;

/**
 * 16 core player attributes (1-99 scale)
 */
public class PlayerAttributes {
    private int adaptability;
    private int fortitude;
    private int consistency;
    private int teamPlayer;
    private int leadership;
    private int tacticalAwareness;
    private int laning;
    private int teamFighting;
    private int riskTaking;
    private int positioning;
    private int skillShots;
    private int lastHitting;
    private int summonerSpells;
    private int stamina;
    private int injuryResistant;

    // Constructor
    public PlayerAttributes(int adaptability, int fortitude, int consistency,
                            int teamPlayer, int leadership, int tacticalAwareness,
                            int laning, int teamFighting, int riskTaking,
                            int positioning, int skillShots, int lastHitting,
                            int summonerSpells, int stamina, int injuryResistant) {
        this.adaptability = clamp(adaptability);
        this.fortitude = clamp(fortitude);
        this.consistency = clamp(consistency);
        this.teamPlayer = clamp(teamPlayer);
        this.leadership = clamp(leadership);
        this.tacticalAwareness = clamp(tacticalAwareness);
        this.laning = clamp(laning);
        this.teamFighting = clamp(teamFighting);
        this.riskTaking = clamp(riskTaking);
        this.positioning = clamp(positioning);
        this.skillShots = clamp(skillShots);
        this.lastHitting = clamp(lastHitting);
        this.summonerSpells = clamp(summonerSpells);
        this.stamina = clamp(stamina);
        this.injuryResistant = clamp(injuryResistant);
    }

    private int clamp(int value) {
        return Math.max(1, Math.min(99, value));
    }

    // Getters and setters
    public int getAdaptability() { return adaptability; }
    public void setAdaptability(int adaptability) { this.adaptability = clamp(adaptability); }

    public int getFortitude() { return fortitude; }
    public void setFortitude(int fortitude) { this.fortitude = clamp(fortitude); }

    public int getConsistency() { return consistency; }
    public void setConsistency(int consistency) { this.consistency = clamp(consistency); }

    public int getTeamPlayer() { return teamPlayer; }
    public void setTeamPlayer(int teamPlayer) { this.teamPlayer = clamp(teamPlayer); }

    public int getLeadership() { return leadership; }
    public void setLeadership(int leadership) { this.leadership = clamp(leadership); }

    public int getTacticalAwareness() { return tacticalAwareness; }
    public void setTacticalAwareness(int tacticalAwareness) { this.tacticalAwareness = clamp(tacticalAwareness); }

    public int getLaning() { return laning; }
    public void setLaning(int laning) { this.laning = clamp(laning); }

    public int getTeamFighting() { return teamFighting; }
    public void setTeamFighting(int teamFighting) { this.teamFighting = clamp(teamFighting); }

    public int getRiskTaking() { return riskTaking; }
    public void setRiskTaking(int riskTaking) { this.riskTaking = clamp(riskTaking); }

    public int getPositioning() { return positioning; }
    public void setPositioning(int positioning) { this.positioning = clamp(positioning); }

    public int getSkillShots() { return skillShots; }
    public void setSkillShots(int skillShots) { this.skillShots = clamp(skillShots); }

    public int getLastHitting() { return lastHitting; }
    public void setLastHitting(int lastHitting) { this.lastHitting = clamp(lastHitting); }

    public int getSummonerSpells() { return summonerSpells; }
    public void setSummonerSpells(int summonerSpells) { this.summonerSpells = clamp(summonerSpells); }

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = clamp(stamina); }

    public int getInjuryResistant() { return injuryResistant; }
    public void setInjuryResistant(int injuryResistant) { this.injuryResistant = clamp(injuryResistant); }

    /**
     * Calculate overall rating based on attributes
     */
    public int calculateOverall() {
        return (adaptability + fortitude + consistency + teamPlayer + leadership +
                tacticalAwareness + laning + teamFighting + riskTaking + positioning +
                skillShots + lastHitting + summonerSpells + stamina + injuryResistant) / 15;
    }
}
