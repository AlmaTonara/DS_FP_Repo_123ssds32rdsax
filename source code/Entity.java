package com.mycompany.dsfinalprojectapp;

// Base entity or object code
public class Entity {
    // Stores coordinates for itself
    private int x;
    private int y;
    
    // Initialize with a position
    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    // Updates position
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    // Basic Getter Methods
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    // Supplementary method
    @Override
    public String toString() {
        return "Entity(" + x + ", " + y + ")";
    }
}
