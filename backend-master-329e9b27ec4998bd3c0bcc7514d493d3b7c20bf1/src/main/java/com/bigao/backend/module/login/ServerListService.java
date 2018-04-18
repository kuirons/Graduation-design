package com.bigao.backend.module.login;

import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.db.Game;
import com.bigao.backend.db.Platform;
import com.bigao.backend.db.Server;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by wait on 2015/11/28.
 */
@Service
public class ServerListService {

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    public void addGlobalAttribute(ModelAndView view) {
        List<Server> serverList = dbUtil4ServerLog.getAllServer();
        //排序
        List<Platform> platformList = dbUtil4ServerLog.getPlatformList();
        view.addObject("platform", platformList);
        view.addObject("server", serverList);
        List<Game> gameList = Lists.newArrayList();
        Game game = new Game();
        game.setId(1);
        game.setGameName("game1");
        gameList.add(game);
        view.addObject("gameList", gameList);
    }
}
