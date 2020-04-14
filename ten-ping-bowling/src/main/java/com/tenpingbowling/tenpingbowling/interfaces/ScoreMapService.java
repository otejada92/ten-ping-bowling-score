package com.tenpingbowling.tenpingbowling.interfaces;



import com.tenpingbowling.tenpingbowling.models.Frame;
import com.tenpingbowling.tenpingbowling.models.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public interface ScoreMapService
{

    Map<Player, ArrayList<Frame>> getScoreMap();
    Map<Player, ArrayList<Frame>> buildScoreMap();
    void addPlayers(ArrayList<String> scoreLine);
    void addPlayerScores(ArrayList<String> bowlingGameInformation, Set<Player> players);

}
