package com.mycompany.dsfinalprojectapp;

import java.util.ArrayList;

public class TwoD extends AbstractSpatialStructure{
    // Stores entities in a 2D grid
    private Entity[][] grid;
    
    // Initializes size (A lot of memory if size is too big)
    public TwoD(int width, int height){
        super(width, height);
        grid = new Entity[width][height];
    }
    
    public TwoD(){
        super();
        grid = new Entity[2000][2000];
    }
    
    // Checks if cell is occupied, an O(1) operation
    private boolean isOccupied(int x, int y){
        // Also checks if it is inside the grid
        return this.isValid(x, y) && grid[x][y] != null;
    }
    
    // Direct insertion
    @Override
    public boolean insert(Entity entity){
        int x = entity.getX();
        int y = entity.getY();
        
        if (!this.isValid(x, y) || this.isOccupied(x, y)){
            return false;
        }
        
        grid[x][y] = entity;
        this.entityCount++;
        return true;
    }
    
    // Direct removal
    @Override
    public boolean remove(int x, int y){
        if (this.isOccupied(x, y)){
            grid[x][y] = null;
            this.entityCount--;
            return true;
        }
        return false;
    }
    
    // Basically combines insertion and removal
    @Override
    public boolean move(int oldX, int oldY, int newX, int newY){
        if (!this.isValid(oldX, oldY) || !this.isValid(newX, newY)){
            return false;
        }

        if (!this.isOccupied(oldX, oldY) || this.isOccupied(newX, newY)){
            return false;
        }

        Entity movingEntity = grid[oldX][oldY];
        grid[oldX][oldY] = null;

        movingEntity.setXY(newX, newY);
        grid[newX][newY] = movingEntity;

        return true;
    }
    
    // Direct access
    @Override
    public Entity get(int x, int y){
        if (this.isOccupied(x, y)){
            return grid[x][y];
        }
        return null;
    }
    
    @Override
    public ArrayList<Entity> getRange(int x1, int y1, int x2, int y2){
        // Creates a list of entities to be returned
        ArrayList<Entity> entities = new ArrayList<Entity>();
        
        // Creates/clarifies the range to be queried
        int minX = Math.max(0, Math.min(x1, x2));
        int maxX = Math.min(this.width - 1, Math.max(x1, x2));
        int minY = Math.max(0, Math.min(y1, y2));
        int maxY = Math.min(this.height - 1, Math.max(y1, y2));
        
        // Scans every cell within range and return any entities by inserting it into the arraylist
        for (int x = x1; x <= x2; x++){
            for (int y = y1; y <= y2; y++){
                if (x >= minX && x <= maxX && y >= minY && y <= maxY && grid[x][y] != null) {
                    entities.add(grid[x][y]);
                }
            }
        }
        
        // Returns all found entities
        return entities;
    }
}
