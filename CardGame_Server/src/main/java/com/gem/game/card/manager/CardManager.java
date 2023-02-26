package com.gem.game.card.manager;


import com.gem.game.card.model.CardModel;
import com.gem.game.card.model.CardResult;
import com.gem.game.card.model.CardType;
import com.gem.game.card.model.ResultType;
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
            score += cardModel.getValue();
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
