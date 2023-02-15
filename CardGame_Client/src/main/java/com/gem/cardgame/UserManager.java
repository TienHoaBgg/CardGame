/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.obj.PositionEnum;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.obj.UserObj;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gem
 */
public class UserManager {

    public List<UserObj> users;

    public UserManager() {
        users = new ArrayList<>();

    }
    
    public void addUser(String userId, String userName) {
        if (validateUser(userId)) { return; }
        UserObj userObj = new UserObj(userId, userName);
        userObj.setImg(Utils.getInstance().getImage("person_icon.png"));
        userObj.setSize(200, 250);
        if (userId.equals(CurrentSessionUtils.USER_ID)) {
            users.add(0, userObj);
        } else {
            users.add(userObj);
        }
    }

    private boolean validateUser(String userId) {
        boolean isExists = false;
        for (UserObj user : users) {
            if (user.getUserId().equals(userId)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }
    
    public void drawAll(Graphics2D g2, SizeObj screenSize) {
        SizeObj objSize = caculatorSize(screenSize);
        if (!users.isEmpty()) {
            UserObj obj = users.get(0);
            obj.setPositionEnum(PositionEnum.BOTTOM);
            drawUser(g2, obj, objSize, bottomCenterPosition(screenSize, objSize));
        }
        switch (users.size()) {
            case 2 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj, objSize, topCenterPosition(screenSize, objSize));
            }
            case 3 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj, objSize, rightTopPosition(screenSize, objSize));
                UserObj obj2 = users.get(2);
                obj2.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj2, objSize, leftTopPosition(objSize));
            }
            case 4 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj, objSize, rightCenterPosition(screenSize, objSize));
                UserObj obj1 = users.get(2);
                obj1.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj1, objSize, topCenterPosition(screenSize, objSize));
                UserObj obj2 = users.get(3);
                obj2.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj2, objSize, leftCenterPosition(screenSize, objSize));
            }
            case 5 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserObj obj1 = users.get(2);
                obj1.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj1, objSize, rightTopPosition(screenSize, objSize));
                
                UserObj obj2 = users.get(3);
                obj2.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj2, objSize, topCenterPosition(screenSize, objSize));
                
                UserObj obj3 = users.get(4);
                obj3.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj3, objSize, leftCenterPosition(screenSize, objSize));
            }
            case 6 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserObj obj1 = users.get(2);
                obj1.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj1, objSize, rightTopPosition(screenSize, objSize));
                
                UserObj obj2 = users.get(3);
                obj2.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj2, objSize, topCenterPosition(screenSize, objSize));
                
                UserObj obj3 = users.get(4);
                obj3.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj3, objSize, leftTopPosition(objSize));
                
                UserObj obj4 = users.get(5);
                obj4.setPositionEnum(PositionEnum.BOTTOMLEFT);
                drawUser(g2, obj4, objSize, leftBottomPosition(screenSize, objSize));
                
            }
            case 7 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserObj obj1 = users.get(2);
                obj1.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj1, objSize, rightCenterPosition(screenSize, objSize));
                
                UserObj obj2 = users.get(3);
                obj2.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj2, objSize, rightTopPosition(screenSize, objSize));
                
                UserObj obj3 = users.get(4);
                obj3.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj3, objSize, topCenterPosition(screenSize, objSize));
                
                UserObj obj4 = users.get(5);
                obj4.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj4, objSize, leftTopPosition(objSize));
                
                UserObj obj5 = users.get(6);
                obj5.setPositionEnum(PositionEnum.BOTTOMLEFT);
                drawUser(g2, obj5, objSize, leftBottomPosition(screenSize, objSize));

            }
            case 8 ->  {
                UserObj obj = users.get(1);
                obj.setPositionEnum(PositionEnum.BOTTOMRIGHT);
                drawUser(g2, obj, objSize, rightBottomPosition(screenSize, objSize));
                
                UserObj obj1 = users.get(2);
                obj1.setPositionEnum(PositionEnum.RIGHT);
                drawUser(g2, obj1, objSize, rightCenterPosition(screenSize, objSize));
                
                UserObj obj2 = users.get(3);
                obj2.setPositionEnum(PositionEnum.TOPRIGHT);
                drawUser(g2, obj2, objSize, rightTopPosition(screenSize, objSize));
                
                UserObj obj3 = users.get(4);
                obj3.setPositionEnum(PositionEnum.TOP);
                drawUser(g2, obj3, objSize, topCenterPosition(screenSize, objSize));
                
                UserObj obj4 = users.get(5);
                obj4.setPositionEnum(PositionEnum.TOPLEFT);
                drawUser(g2, obj4, objSize, leftTopPosition(objSize));
                
                UserObj obj5 = users.get(6);
                obj5.setPositionEnum(PositionEnum.LEFT);
                drawUser(g2, obj5, objSize, leftCenterPosition(screenSize, objSize));
                
                UserObj obj6 = users.get(7);
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
    
    private void drawUser(Graphics2D g2, UserObj user, SizeObj objSize, PositionObj position) {
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
