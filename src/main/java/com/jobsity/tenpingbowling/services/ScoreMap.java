package com.jobsity.tenpingbowling.services;

import com.jobsity.tenpingbowling.Component.BowlingScoreFile;
import com.jobsity.tenpingbowling.Enums.SystemConstant;
import com.jobsity.tenpingbowling.interfaces.ScoreFrameService;
import com.jobsity.tenpingbowling.interfaces.ScoreMapService;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ScoreMap implements ScoreMapService {

    @Autowired
    private ScoreFrameService scoreFrameService;

    @Autowired
    private BowlingScoreFile bowlingScoreFile;

    private Map<Player, ArrayList<Frame>> scoreMap = new HashMap<Player, ArrayList<Frame>>();

    @Override
    public Map<Player, ArrayList<Frame>> buildScoreMap() {
        ArrayList<String> bowlingGameInformation = new ArrayList<String>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(bowlingScoreFile));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                bowlingGameInformation.add(line);
            }

            addPlayers(bowlingGameInformation);
            addPlayerScores(bowlingGameInformation, scoreMap.keySet());

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return  scoreMap;
    }

    @Override
    public void addPlayers(ArrayList<String> bowlingGameInformation) {

        Player player;
        Iterator<String> scoreIterator  = bowlingGameInformation.iterator();
        do
        {

            String[] scoreLine = splitScoreLine(scoreIterator.next());

            if (isValidScoreLine(scoreLine))
            {
                String playerName = getPlayerNameFromLine(scoreLine);

                if (isValidPlayerName(playerName))
                {
                    player = new Player();

                    player.setName(playerName);

                    scoreMap.putIfAbsent(player, null);

                    if (hasMapValidKeySize(scoreMap.keySet()))
                        break;
                }
            }
            else
            {
                System.out.println("Invalid score line found");
                break;
            }
        }while (scoreIterator.hasNext());

    }

    @Override
    public void addPlayerScores(ArrayList<String> bowlingGameInformation, Set<Player> players) {

        ArrayList<String> scoreFilteredByPlayer;

        if (hasMapValidKeySize(players))
        {
            for (Player player : players)
            {
                scoreFilteredByPlayer = bowlingGameInformation
                        .stream()
                        .filter(filterScoreByPlayerName(player.getName()))
                        .map(this::getScoreFromLine)
                        .collect(Collectors.toCollection(ArrayList::new));

                ArrayList<Frame> frames = scoreFrameService.getFrames(scoreFilteredByPlayer);

                scoreMap.put(player, frames);
            }
        }

    }

    private String[] splitScoreLine(String line){
        return line.split("\\s");
    }

    private  boolean isValidScoreLine(String[] scoreLine){
        return scoreLine.length ==  SystemConstant.VALID_SCORE_LINE_LENGTH.getValue();
    }

    private String getPlayerNameFromLine(String[] scoreLine){
        return scoreLine[SystemConstant.PLAYER_INDEX.getValue()];
    }

    private String getPlayerNameFromLine(String scoreLine){
        return getPlayerNameFromLine(splitScoreLine(scoreLine));
    }

    private String getScoreFromLine(String scoreLine){
        return splitScoreLine(scoreLine)[SystemConstant.SCORE_INDEX.getValue()];
    }

    private boolean isValidPlayerName(String name){
        return !StringUtils.isEmpty(name) && !name.matches(".*\\d.*");
    }

    private boolean hasMapValidKeySize(Set<Player> players){
        return players.size() == SystemConstant.VALID_KEY_MAP_SIZE.getValue();
    }

    private Predicate<String> filterScoreByPlayerName(String playerNameFromMap){
        return playerName -> getPlayerNameFromLine(playerName).equals(playerNameFromMap);
    }
}
