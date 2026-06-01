package com.mycompany.dsfinalprojectapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashGrid extends AbstractSpatialStructure {
    // Creates the hashpmap, using long as key
    private Map<Long, ArrayList<Entity>> map;
    
    // Fixed chunk size
    private int chunkSize = 64;

    // Initializes attributes
    public HashGrid(int width, int height) {
        super(width, height);
        this.map = new HashMap<>();
    }
    
    public HashGrid(){
        super();
        this.map = new HashMap<>();
    }

    // Takes in a coordinate, and determines which chunk it belongs in using division
    private long getChunkX(int x) {
        return x / chunkSize;
    }

    private long getChunkY(int y) {
        return y / chunkSize;
    }

    // Hashing formula to flatten all chunks
    private long getKey(long cx, long cy) {
        long gridWidth = (this.width + chunkSize - 1) / chunkSize;
        return cx + cy * gridWidth;
    }

    // Checks if coordinate is within range
    private boolean isValidWorld(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // Insertion
    @Override
    public boolean insert(Entity entity) {
        // Retrieves coordinate of new entity
        int x = entity.getX();
        int y = entity.getY();

        // Determines if it is a valid position
        if (!isValidWorld(x, y)) {
            return false;
        }

        // Finds the chunk the coordinate belongs too
        long cx = getChunkX(x);
        long cy = getChunkY(y);
        long key = getKey(cx, cy);

        // Checks of the chunk exists, if it doesn't make it, if it does, retrieve it
        ArrayList<Entity> bucket = map.computeIfAbsent(key, k -> new ArrayList<>());
        
        // Checks for collision inside the chunk
        for (Entity e : bucket) {
            if (e.getX() == x && e.getY() == y) {
                return false;
            }
        }

        // Adds them
        bucket.add(entity);
        entityCount++;

        return true;
    }

    // Remove
    @Override
    public boolean remove(int x, int y) {
        // Checks if coordinate is valid
        if (!isValidWorld(x, y)) {
            return false;
        }

        // Retrieves chunk
        long cx = getChunkX(x);
        long cy = getChunkY(y);
        long key = getKey(cx, cy);

        // Checks if chunk exists
        ArrayList<Entity> bucket = map.get(key);
        if (bucket == null) {
            return false;
        }

        // Finds the entity in the chunk, remove it if found
        Iterator<Entity> it = bucket.iterator();

        while (it.hasNext()) {
            Entity e = it.next();

            if (e.getX() == x && e.getY() == y) {
                it.remove();
                entityCount--;

                if (bucket.isEmpty()) {
                    map.remove(key);
                }

                return true;
            }
        }

        return false;
    }

    // Update position
    @Override
    public boolean move(int oldX, int oldY, int newX, int newY) {
        // checks if both coordinates are valid
        if (!isValidWorld(oldX, oldY) || !isValidWorld(newX, newY)) {
            return false;
        }

        // Retrieves coordinates
        long oldCx = getChunkX(oldX);
        long oldCy = getChunkY(oldY);
        long oldKey = getKey(oldCx, oldCy);

        // Tries to find it
        ArrayList<Entity> oldBucket = map.get(oldKey);
        if (oldBucket == null) {
            return false;
        }
        
        // Retrieves coordinate
        long newCx = getChunkX(newX);
        long newCy = getChunkY(newY);
        long newKey = getKey(newCx, newCy);
        
        // Checks if it is occupied or not
        ArrayList<Entity> newBucket = map.get(newKey);
        if (newBucket != null) {
            for (Entity e : newBucket) {
                if (e.getX() == newX && e.getY() == newY) {
                    return false;
                }
            }
        }

        // if all is good, removes it from the old coordinate, and inserts it into the new
        Iterator<Entity> it = oldBucket.iterator();

        while (it.hasNext()) {
            Entity e = it.next();

            if (e.getX() == oldX && e.getY() == oldY) {

                it.remove();
                entityCount--;

                if (oldBucket.isEmpty()) {
                    map.remove(oldKey);
                }

                e.setXY(newX, newY);

                insert(e);

                return true;
            }
        }

        return false;
    }

    // Point access, mainly for quality of life
    @Override
    public Entity get(int x, int y) {
        // Retrieves coordinates
        if (!isValidWorld(x, y)) {
            return null;
        }

        // Get chunk
        long cx = getChunkX(x);
        long cy = getChunkY(y);
        long key = getKey(cx, cy);

        ArrayList<Entity> bucket = map.get(key);

        // Checks if chunk exists
        if (bucket == null) {
            return null;
        }

        // Return it
        for (Entity e : bucket) {
            if (e.getX() == x && e.getY() == y) {
                return e;
            }
        }

        return null;
    }

    // Get range
    @Override
    public ArrayList<Entity> getRange(int x1, int y1, int x2, int y2) {
        // Prepares a list to be returned
        ArrayList<Entity> result = new ArrayList<>();

        // Creates range
        int minX = Math.max(0, Math.min(x1, x2));
        int maxX = Math.min(width - 1, Math.max(x1, x2));
        int minY = Math.max(0, Math.min(y1, y2));
        int maxY = Math.min(height - 1, Math.max(y1, y2));

        // Turns coordinate range into chunk coordinate range
        long cx1 = getChunkX(minX);
        long cy1 = getChunkY(minY);
        long cx2 = getChunkX(maxX);
        long cy2 = getChunkY(maxY);

        // Finds all chunks within the range
        for (long cx = cx1; cx <= cx2; cx++) {
            for (long cy = cy1; cy <= cy2; cy++) {
                long key = getKey(cx, cy);
                
                ArrayList<Entity> bucket = map.get(key);

                if (bucket == null) {
                    continue;
                }

                // Grabs everything from every found chunk
                for (Entity e : bucket) {
                    int x = e.getX();
                    int y = e.getY();

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        result.add(e);
                    }
                }
            }
        }

        return result;
    }
}