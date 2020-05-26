package com.xaviplacidpol.blindbloodblade.entities;

public class PlayerFactory {

    public PlayerFactory(){}

    /**
     * Retrieves an instance of Player based on the string given
     * @param playerType String to determine the instance to give
     * @return Player object with the instance required
     */
    public Player getPlayer(String playerType){
        if(playerType == null){
            return null;
        }
        if(playerType.equalsIgnoreCase("NINJA")){
            return new NinjaPlayer();
        } else if (playerType.equalsIgnoreCase(("RONIN"))){
            return new RoninPlayer();
        } else if (playerType.equalsIgnoreCase(("AUTOMATA"))){
            return new AutomataPlayer();
        }

        return null;
    }


}
