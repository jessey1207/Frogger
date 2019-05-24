public class BoundingBox { 
    private double x1, y1;   // lower left
    private double x2, y2;   // upper right
   
    /** Create a bounding box. 
     * 
     * @param x1 coordinate.
     * @param y1 coordinate.
     * @param x2 coordinate.
     * @param y2 coordinate.
     */
    public BoundingBox(float x1, float y1, float x2, float y2) {
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
    }

    /** Determine if this bounding box intersects another bounding box.
     * 
     * @param s other bounding box.
     * @return if intersect occurs.
     */
    public boolean intersects(BoundingBox s) {
        BoundingBox r = this;
        return (r.x2 >= s.x1 && r.y2 >= s.y1 && s.x2 >= r.x1 && s.y2 >= r.y1);
    }

}

