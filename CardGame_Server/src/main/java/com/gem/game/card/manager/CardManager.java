package com.gem.game.card.manager;


import com.gem.game.card.model.*;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CardManager {

    private List<CardModel> cards;


    @PostConstruct
    public void init() {
        cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            cards.add(new CardModel(i, CardType.RO, "cards/Ro_" + i + ".png"));
            cards.add(new CardModel(i, CardType.CO, "cards/Co_" + i + ".png"));
            cards.add(new CardModel(i, CardType.TEP, "cards/Tep_" + i + ".png"));
            cards.add(new CardModel(i, CardType.BICH, "cards/Bich_" + i + ".png"));
        }
    }

    public List<CardModel> getCards(List<Integer> ids) {
        List<CardModel> cardModels = new ArrayList<>();
        for (Integer id : ids) {
            cardModels.add(cards.get(id));
        }
        return cardModels;
    }

    public CardResult getScoreId(List<Integer> ids) {
        List<CardModel> cardModels = getCards(ids);
        return getScore(cardModels);
    }

    public CardResult getScore(List<CardModel> userCards) {
        List<CardModel> cards = userCards.stream().sorted(Comparator.comparingInt(CardModel::getValue)).collect(Collectors.toList());
        CardModel cardOne = cards.get(0);
        CardModel cardTwo = cards.get(1);
        CardModel cardThree = cards.get(2);
        int score = 0;
        for (CardModel cardModel : cards) {
            if (cardModel.getValue() < 10) {
                score += cardModel.getValue();
            }
        }
        if (score > 9 && score < 20) {
            score = score - 10;
        } else if (score > 19) {
            score = score - 20;
        }
        if (checkLieng(cardOne, cardTwo, cardThree)) {
            return new CardResult(userCards, score, ResultType.LIENG);
        } else if (checkSap(cardOne, cardTwo, cardThree)) {
            return new CardResult(userCards, score, ResultType.SAP);
        } else if (checkAnh(cardOne, cardTwo, cardThree)) {
            return new CardResult(userCards, score, ResultType.ANH);
        } else {
            return new CardResult(userCards, score, ResultType.NONE);
        }
    }

    public String checkWinner(List<CardResult> cardResults) {




        return "";
    }

    private boolean compareCardResult(CardResult r1, CardResult r2) {
        if (r1.getType() == ResultType.SAP) {
            if (r2.getType() != ResultType.SAP) {
                return true;
            } else {



            }
        }

    }

    private boolean compareCardType(List<CardModel> card1, List<CardModel> card2) {
        List<CardModel> cardOne = card1.stream().sorted(Comparator.comparingInt(CardModel::getValue)).collect(Collectors.toList());
        List<CardModel> cardTwo = card2.stream().sorted(Comparator.comparingInt(CardModel::getValue)).collect(Collectors.toList());
        if (cardOne.get(0).getValue() == 1 && cardOne.get(0).getType() == CardType.RO) {
            return true;
        } else if (cardTwo.get(0).getValue() == 1 && cardTwo.get(0).getType() == CardType.RO) {
            return false;
        }
        List<CardModel> cardOneRo = cardOne.stream().filter(c -> c.getType() == CardType.RO).collect(Collectors.toList());
        List<CardModel> cardTwoRo = cardTwo.stream().filter(c -> c.getType() == CardType.RO).collect(Collectors.toList());
        if (!cardOneRo.isEmpty() && cardTwoRo.isEmpty()) {
            return true;
        } else  if (cardOneRo.isEmpty() && !cardTwoRo.isEmpty()) {
            return false;
        } else {
            CardModel maxOneRo = cardOneRo.get(cardOneRo.size() - 1);
            CardModel maxTwoRo = cardTwoRo.get(cardTwoRo.size() - 1);
            if (maxOneRo.getValue() > maxTwoRo.getValue()) {
                return true;
            }
            
        }




    }

    private boolean checkLieng(CardModel c1, CardModel c2, CardModel c3) {
        return c1.getValue() < 13 && c1.getValue() + 1 == c2.getValue() && c2.getValue() + 1 == c3.getValue();
    }

    private boolean checkSap(CardModel c1, CardModel c2, CardModel c3) {
        return (c1.getValue() == c2.getValue() && c2.getValue() == c3.getValue());
    }

    private boolean checkAnh(CardModel c1, CardModel c2, CardModel c3) {
        return (c1.getValue() > 10 && c2.getValue() > 10 && c3.getValue() > 10);
    }

    private boolean checkType(CardModel c1, CardModel c2, CardModel c3) {
        return (c1.getType() == c2.getType() && c2.getType() == c3.getType());
    }

}
