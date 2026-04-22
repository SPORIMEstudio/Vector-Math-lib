package sporimestudio.vml;

/**
 * @SPORIMEstudio
 * A representation of a 3D vector using float-point coordinates (x, y, z).
 * Used for 3D positioning, velocities, and surface normals.
 */
public class Vector3f {

    public float x, y, z;
    
    public static final Vector3f ZERO    = new Vector3f(0, 0, 0);
    public static final Vector3f UP      = new Vector3f(0, 1, 0);
    public static final Vector3f DOWN    = new Vector3f(0, -1, 0);
    public static final Vector3f FORWARD = new Vector3f(0, 0, 1);
    public static final Vector3f BACK    = new Vector3f(0, 0, -1);
    public static final Vector3f LEFT    = new Vector3f(-1, 0, 0);
    public static final Vector3f RIGHT   = new Vector3f(1, 0, 0);

    /** Initializes a vector at (0,0,0). */
    public Vector3f() {
        this.x = 0; this.y = 0; this.z = 0;
    }

    /** * @param x Horizontal component.
     * @param y Vertical component.
     * @param z Depth component.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x; this.y = y; this.z = z;
    }

    /** @param v The vector to copy. */
    public Vector3f(Vector3f v) {
        this.x = v.x; this.y = v.y; this.z = v.z;
    }

    /** * Adds another vector to this one.
     * @param v The vector to add.
     * @return A new Vector3f sum.
     */
    public Vector3f add(Vector3f v) {
        return new Vector3f(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /** * Subtracts another vector from this one.
     * @param v The vector to subtract.
     * @return A new Vector3f difference.
     */
    public Vector3f sub(Vector3f v) {
        return new Vector3f(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    /** * Scales the vector by a scalar value.
     * @param scalar The multiplier.
     * @return A new scaled Vector3f.
     */
    public Vector3f scale(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }


    /** * Computes the dot product. Useful for lighting calculations.
     * @param v Other vector.
     * @return The scalar dot product.
     */
    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /** * Computes the cross product. Result is a vector perpendicular to both inputs.
     * 
     * @param v The second vector.
     * @return A new Vector3f perpendicular to this and v.
     */
    public Vector3f cross(Vector3f v) {
        return new Vector3f(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    /** * Returns the length (magnitude) of the vector.
     * @return The distance from (0,0,0).
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /** * Returns a unit vector (length 1).
     * @return A normalized Vector3f.
     */
    public Vector3f normalize() {
        float mag = magnitude();
        if (mag == 0) return new Vector3f(0, 0, 0);
        return scale(1.0f / mag);
    }

    /** * Reflects this vector across a surface normal.
     * 
     * @param normal The surface normal (unit length).
     * @return The reflected Vector3f.
     */
    public Vector3f reflect(Vector3f normal) {
        float d = this.dot(normal);
        return this.sub(normal.scale(2.0f * d));
    }

    /** * Linearly interpolates between two vectors.
     * @param start Start position.
     * @param end End position.
     * @param alpha Factor between 0 and 1.
     * @return Interpolated Vector3f.
     */
    public static Vector3f lerp(Vector3f start, Vector3f end, float alpha) {
        return new Vector3f(
            start.x + (end.x - start.x) * alpha,
            start.y + (end.y - start.y) * alpha,
            start.z + (end.z - start.z) * alpha
        );
    }

    @Override
    public String toString() {
        return String.format("Vector3f(%.2f, %.2f, %.2f)", x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) obj;
        return Float.compare(x, other.x) == 0 && 
               Float.compare(y, other.y) == 0 && 
               Float.compare(z, other.z) == 0;
    }
}