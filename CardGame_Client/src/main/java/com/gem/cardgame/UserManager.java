/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.model.CardResult;
import com.gem.cardgame.model.GameEventModel;
import com.gem.cardgame.objenum.PositionEnum;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.model.UserEventModel;
import com.gem.cardgame.model.UserModel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;

/**
 *
 * @author gem
 */
public class UserManager {

    private final HashMap<Integer, UserModel> usersMap;
    private final List<UserModel> users;

    public UserManager() {
        usersMap = new HashMap<>();
        users = new ArrayList<>();
    }
    
    public List<UserModel> getUserInfo() {
        return users;
    }
    
    public List<UserModel> getUsers() {
        return new ArrayList<>(usersMap.values());
    }
    
    public void updateResult(List<CardResult> results) {
        for (CardResult result : results) {
            updateResultToUser(result);
        }
    }

    private void updateResultToUser(CardResult result) {
        usersMap.forEach(((key, value) -> {
            if (value.getUserId().equals(result.getUserId())) {
                usersMap.get(key).setResult(result);
            }
        }));
    }
    
    public void setUsers(List<UserEventModel> userEventModels) {
        usersMap.clear();
        Optional<UserEventModel> currentUser = userEventModels.stream().filter(user -> user.getUserID().equals(CurrentSessionUtils.USER_ID)).findFirst();
        CurrentSessionUtils.USER_INDEX = currentUser.get().getIndex();
        if (currentUser.get().isHost()) {
            CurrentSessionUtils.IS_HOST = true;
            CurrentSessionUtils.IS_YOUR_TURN = true;
        } else {
            CurrentSessionUtils.IS_HOST = false;
            CurrentSessionUtils.IS_YOUR_TURN = false;
        }
        int currentUserIndex = validateUser(currentUser.get().getUserID());
        if (currentUserIndex == -1) {
            users.add(convertToUser(currentUser.get()));
        }
        int userIndex = currentUser.get().getIndex();
        usersMap.put(0, convertToUser(currentUser.get()));
        int maxIndex = 0;
        for (int i = userIndex + 1; i < userEventModels.size(); i++) {
            maxIndex = i - userIndex;
            UserModel user = convertToUser(userEventModels.get(i));
            usersMap.put(maxIndex, user);
            if (validateUser(user.getUserId()) == -1) {
                users.add(user);
            }
        }
        for (int i = 0; i < userIndex; i++) {
            int index = i + maxIndex + 1;
            UserModel user = convertToUser(userEventModels.get(i));
            usersMap.put(index, user);
            if (validateUser(user.getUserId()) == -1) {
                users.add(user);
            }
        }
    }
    
    public void updateStateUser(GameEventModel model) {
        usersMap.forEach(((key, value) -> {
            if (value.getUserId().equals(model.getUserId())) {
                usersMap.get(key).setStatus(model.getState());
            }
        }));
    }
    
    private UserModel convertToUser(UserEventModel userEventModel) {
        UserModel userObj = new UserModel(userEventModel);
        userObj.setImg(Utils.getInstance().getImage("person_icon.png"));
        userObj.setSize(200, 250);
        return userObj;
    }
    
    private int validateUser(String userId) {
        int index = -1;
        for (int i = 0; i < users.size(); i ++) {
            UserModel user = users.get(i);
            if (user.getUserId() == null ? userId == null : user.getUserId().equals(userId)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void drawAll(Graphics2D g2, SizeObj screenSize) {
        SizeObj objSize = caculatorSize(screenSize);
        if (!usersMap.isEmpty()) {
            UserModel obj = usersMap.get(0);
            obj.setPositionEnum(PositionEnum.BOTTOM);
            drawUser(g2, obj, objSize, bottomCenterPosition(screenSize, objSize));
        }
        switch (usersMap.size()) {
            case 2 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj, objSize, topCenterPosition(screenSize, objSize));
            }
            case 3 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj, objSize, rightTopPosition(screenSize, objSize));
                UserModel obj2 = usersMap.get(2);
                obj2.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj2, objSize, leftTopPosition(objSize));
            }
            case 4 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj, objSize, rightCenterPosition(screenSize, objSize));
                UserModel obj1 = usersMap.get(2);
                obj1.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj1, objSize, topCenterPosition(screenSize, objSize));
                UserModel obj2 = usersMap.get(3);
                obj2.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj2, objSize, leftCenterPosition(screenSize, objSize));
            }
            case 5 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserModel obj1 = usersMap.get(2);
                obj1.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj1, objSize, rightTopPosition(screenSize, objSize));
                
                UserModel obj2 = usersMap.get(3);
                obj2.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj2, objSize, topCenterPosition(screenSize, objSize));
                
                UserModel obj3 = usersMap.get(4);
                obj3.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj3, objSize, leftCenterPosition(screenSize, objSize));
            }
            case 6 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserModel obj1 = usersMap.get(2);
                obj1.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj1, objSize, rightTopPosition(screenSize, objSize));
                
                UserModel obj2 = usersMap.get(3);
                obj2.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj2, objSize, topCenterPosition(screenSize, objSize));
                
                UserModel obj3 = usersMap.get(4);
                obj3.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj3, objSize, leftTopPosition(objSize));
                
                UserModel obj4 = usersMap.get(5);
                obj4.setPositionEnum(PositionEnum.BOTTOMLEFT);
                drawUser(g2, obj4, objSize, leftBottomPosition(screenSize, objSize));
                
            }
            case 7 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserModel obj1 = usersMap.get(2);
                obj1.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj1, objSize, rightCenterPosition(screenSize, objSize));
                
                UserModel obj2 = usersMap.get(3);
                obj2.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj2, objSize, rightTopPosition(screenSize, objSize));
                
                UserModel obj3 = usersMap.get(4);
                obj3.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj3, objSize, topCenterPosition(screenSize, objSize));
                
                UserModel obj4 = usersMap.get(5);
                obj4.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj4, objSize, leftTopPosition(objSize));
                
                UserModel obj5 = usersMap.get(6);
                obj5.setPositionEnum(PositionEnum.BOTTOMLEFT);
                drawUser(g2, obj5, objSize, leftBottomPosition(screenSize, objSize));

            }
            case 8 ->  {
                UserModel obj = usersMap.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserModel obj1 = usersMap.get(2);
                obj1.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj1, objSize, rightCenterPosition(screenSize, objSize));
                
                UserModel obj2 = usersMap.get(3);
                obj2.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj2, objSize, rightTopPosition(screenSize, objSize));
                
                UserModel obj3 = usersMap.get(4);
                obj3.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj3, objSize, topCenterPosition(screenSize, objSize));
                
                UserModel obj4 = usersMap.get(5);
                obj4.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj4, objSize, leftTopPosition(objSize));
                
                UserModel obj5 = usersMap.get(6);
                obj5.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj5, objSize, leftCenterPosition(screenSize, objSize));
                
                UserModel obj6 = usersMap.get(7);
                obj6.setPositionEnum(PositionEnum.BOTTOMLEFT);
                drawUser(g2, obj6, objSize, leftBottomPosition(screenSize, objSize));
            }
            default -> {
            }
        }
    }
    
    // bottom center
    private PositionObj bottomCenterPosition(SizeObj screenSize, SizeObj objSize) {
        float firstX = screenSize.getWidth() / 2 - (objSize.getWidth()/2);
        float firstY = screenSize.getHeight() - (objSize.getHeight() * 1.5f);
        return new PositionObj(firstX, firstY);
    }
    
    // top center
    private PositionObj topCenterPosition(SizeObj screenSize, SizeObj objSize) {
        float x = screenSize.getWidth() / 2 - (objSize.getWidth()/2);
        float y = objSize.getHeight()/2;
        return new PositionObj(x, y);
    }
    
    // left top
    private PositionObj leftTopPosition(SizeObj objSize) {
        float x = objSize.getWidth() * 1.5f;
        float y = objSize.getHeight() * 2;
        return new PositionObj(x, y);
    }
    
    // right top
    private PositionObj rightTopPosition(SizeObj screenSize, SizeObj objSize) {
        float x = screenSize.getWidth() - objSize.getWidth() * 2.5f;
        float y = objSize.getHeight() * 2;
        return new PositionObj(x, y);
    }
    
    //left bottom
    private PositionObj leftBottomPosition(SizeObj screenSize, SizeObj objSize) {
        float x = objSize.getWidth() * 1.5f;
        float y = screenSize.getHeight() - objSize.getHeight() * 3.5f;
        return new PositionObj(x, y);
    }
    
    // right bottom
    private PositionObj rightBottomPosition(SizeObj screenSize, SizeObj objSize) {
        float x = screenSize.getWidth() - objSize.getWidth() * 2.5f;
        float y = screenSize.getHeight() - objSize.getHeight() * 3.5f;
        return new PositionObj(x, y);
    }
    
    // left center
    private PositionObj leftCenterPosition(SizeObj screenSize, SizeObj objSize) {
        float x = objSize.getWidth() * 0.8f;
        float y = screenSize.getHeight()/2 - objSize.getHeight();
        return new PositionObj(x, y);
    }
    
    // rightCenter
    private PositionObj rightCenterPosition(SizeObj screenSize, SizeObj objSize) {
        float x = screenSize.getWidth() - objSize.getWidth() * 2f;
        float y = screenSize.getHeight()/2 - objSize.getHeight();
        return new PositionObj(x, y);
    }
    
    private void drawUser(Graphics2D g2, UserModel user, SizeObj objSize, PositionObj position) {
        user.setPosition(position);
        user.setSize(objSize);
        user.draw(g2);
        
    }
    
    private SizeObj caculatorSize(SizeObj size) {
        float height = size.getHeight()/10;
        float width = (int) (height*0.8);
        return new SizeObj(width, height);
    }

}
