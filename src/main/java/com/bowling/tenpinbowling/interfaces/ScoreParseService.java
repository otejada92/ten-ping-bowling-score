package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface ScoreParseService
{
    Map<Player, List<Frame>> buildScoreMap(File bowlingFrameScore);
    Set<Player> retrievePlayers(List<String> scoreLine);
    List<Frame> retrieveScorePlayer(Player player, List<String> scoreFilteredByPlayers);
    List<String> parseBowlingGameInfo(File bowlingFrameScore) throws IOException;
}