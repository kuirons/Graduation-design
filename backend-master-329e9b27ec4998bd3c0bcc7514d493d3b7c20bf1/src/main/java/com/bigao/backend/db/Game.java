package com.bigao.backend.db;

import java.io.Serializable;

public class Game implements Serializable {

    private static final long serialVersionUID = -3380268119071269866L;

    private int id;

    private String gameName;

    private String gameAlias;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameAlias() {
        return gameAlias;
    }

    public void setGameAlias(String gameAlias) {
        this.gameAlias = gameAlias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != game.id) return false;
        if (gameName != null ? !gameName.equals(game.gameName) : game.gameName != null) return false;
        return !(gameAlias != null ? !gameAlias.equals(game.gameAlias) : game.gameAlias != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gameName != null ? gameName.hashCode() : 0);
        result = 31 * result + (gameAlias != null ? gameAlias.hashCode() : 0);
        return result;
    }
}
