package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TenthFrameCalculation implements CalculationStrategy {

    @Autowired
    @Qualifier("normalCalculation")
    private CalculationStrategy normalCalculation;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... extraFrames) {

        int unProcessedFrameScore = 0;

        switch (unProcessedFrame.getFrameScoreType())
        {

            case NORMAL:
            {
                unProcessedFrameScore += normalCalculation.calculateScore(unProcessedFrame, extraFrames);
                break;
            }
            default:
            {
                unProcessedFrameScore = Stream.of(unProcessedFrame.getFirstRoll(), unProcessedFrame.getSecondRoll(), unProcessedFrame.getThirdRoll())
                        .mapToInt(roll -> getIntegerRollScore(roll,"0"))
                        .sum();
            }
        }

        return unProcessedFrameScore;
    }
}
