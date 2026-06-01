package com.mycompany.dsfinalprojectapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

// A linear storage of objects / entities
public class OneD extends AbstractSpatialStructure{
    // Utilizes a LinkedList
    private LinkedList<Entity> list = new LinkedList<>();
    
    // Initializes Attributes
    public OneD(int width, int height){
        super(width, height);
        this.list = new LinkedList<>();
    }
    
    public OneD(){
        super();
        list = new LinkedList<>();
    }
        
    // Iterates through the structure to find collision
    private boolean isOccupied(int x, int y){
        if (this.isValid(x, y)){
            for (Entity element : this.list){
                if (element.getX() == x && element.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }
    
    // Insert
    @Override
    public boolean insert(Entity entity){
        // Checks if coordinate is within the "suggested" range
        if (!isValid(entity.getX(), entity.getY())){
            return false;
        }
        
        // Checks if there are collission
        for (Entity e : list){
            if (e.getX() == entity.getX() && e.getY() == entity.getY()) {
                return false;
            }
        }
        
        // Adds them if no collision occur
        this.list.add(entity);
        this.entityCount++;
        return true;
    }
    
    // Remove, similar way of working with insert, just uses an iterator to make things cleaner
    @Override
    public boolean remove(int x, int y){
        if (!isValid(x, y)){
            return false;
        }
        
        Iterator<Entity> it = list.iterator();
        
        while (it.hasNext()){
            Entity element = it.next();
            if (element.getX() == x && element.getY() == y){
                it.remove();
                entityCount--;
                return true;
            }
        }
        
        return false;
    }
    
    // Basically a combination of insert and remove
    @Override
    public boolean move(int oldX, int oldY, int newX, int newY){
        if (!this.isValid(oldX, oldY) || !this.isValid(newX, newY)){
            return false;
        }
        
        if (this.isOccupied(newX, newY)) {
            return false;
        }
        
        for (Entity element : this.list){
            if (element.getX() == oldX && element.getY() == oldY){
                Entity e = get(oldX, oldY);
                remove(oldX, oldY);
                e.setXY(newX, newY);
                insert(e);
                return true;
            }
        }
        return false;
    }
    
    // Iterates once to find an entity with a given coordinate
    @Override
    public Entity get(int x, int y){
        if (this.isValid(x, y)){
            for (Entity element : this.list){
                if (element.getX() == x && element.getY() == y){
                    return element;
                }
            }
        }
        return null;
    }
    
    // Takes in a range, iterates once through the list, and returns all entities within range
    @Override
    public ArrayList<Entity> getRange(int x1, int y1, int x2, int y2){
        ArrayList<Entity> result = new ArrayList<>();
        
        // Just makes the box neater
        int minX = Math.max(0, Math.min(x1, x2));
        int maxX = Math.min(this.width - 1, Math.max(x1, x2));
        int minY = Math.max(0, Math.min(y1, y2));
        int maxY = Math.min(this.height - 1, Math.max(y1, y2));

        for (Entity element : this.list) {
            int ex = element.getX();
            int ey = element.getY();
            if (ex >= minX && ex <= maxX && ey >= minY && ey <= maxY) {
                result.add(element);
            }
        }

        return result;
    }
}
