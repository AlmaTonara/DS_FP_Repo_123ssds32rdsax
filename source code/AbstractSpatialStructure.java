package com.mycompany.dsfinalprojectapp;

public abstract class AbstractSpatialStructure implements SpatialStructure{
    // Protected variables of spatial environment width, height, and number of objects
    protected int width;
    protected int height;
    protected int entityCount = 0;
    
    // Initializes width, height, and entity count at 0
    public AbstractSpatialStructure(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    // Incase no height is given
    public AbstractSpatialStructure(){
        this.width = 2000;
        this.height = 2000;
    }
    
    // Basic getter methods
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getCount() {
        return entityCount;
    }
    
    // Checks if a given coordinate is valid
    protected boolean isValid(int x, int y){
        return (x >= 0 && y >= 0) && (x < width && y < height);
    }
}
