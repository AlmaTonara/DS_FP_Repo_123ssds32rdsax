package com.mycompany.dsfinalprojectapp;

import java.util.ArrayList;

/* Spatial structure interface
A spatial structure must contain at least:
- Insert an entity/object
- Remove an entity/object based on coordinates
- Update an entity/object's position
- Access a coordinate's data
- Access a range of coordinate's data
- Basic information retrieval/getter methods
*/

// Spatial structure interface
public interface SpatialStructure {
    // Insert an entity, their coordinates are initially stored in the entity itself
    boolean insert(Entity entity);
    
    // Remove based on coordinate
    boolean remove(int x, int y);
    
    // Move based on coordinate
    boolean move(int oldX, int oldY, int newX, int newY);
    
    // Access an entity in a coordinate
    Entity get(int x, int y);
    
    // Accesses all entities in a coordinate
    ArrayList<Entity> getRange(int x1, int y1, int x2, int y2);
    
    // Basic getter methods
    int getWidth();
    
    int getHeight();
    
    int getCount();
}
