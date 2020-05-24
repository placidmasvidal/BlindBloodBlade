package com.xaviplacidpol.blindbloodblade.entities;

public class PlayerFactory {

    private static PlayerFactory instance = new PlayerFactory();

    private PlayerFactory(){}

    public static PlayerFactory getInstance(){
        return instance;
    }

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
