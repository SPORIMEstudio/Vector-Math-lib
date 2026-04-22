package sporimestudio.vml;

/**
 *@SPORIMEstudio
 * A representation of a 2D vector using float-point coordinates.
 * Provides a comprehensive suite of operations for 2D linear algebra.
 */
public class Vector2f {

    public float x;
    public float y;

    public static final Vector2f ZERO  = new Vector2f(0, 0);
    public static final Vector2f UP    = new Vector2f(0, 1);
    public static final Vector2f DOWN  = new Vector2f(0, -1);
    public static final Vector2f LEFT  = new Vector2f(-1, 0);
    public static final Vector2f RIGHT = new Vector2f(1, 0);

    /** Initializes a vector at (0,0). */
    public Vector2f() {
        this.x = 0;
        this.y = 0;
    }

    /** * @param x The horizontal component.
     * @param y The vertical component.
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** * @param v The vector to copy values from.
     */
    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /** @return The x component. */
    public float getX() { return this.x; }
    /** @param x The new x component. */
    public void setX(float x) { this.x = x; }

    /** @return The y component. */
    public float getY() { return this.y; }
    /** @param y The new y component. */
    public void setY(float y) { this.y = y; }

    /** * Sets both coordinates.
     * @param x New x value.
     * @param y New y value.
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /** * Calculates the angle of this vector relative to the positive X-axis.
     * @return The angle in radians, ranging from -PI to PI.
     */
    public float getAngle() {
        return (float) Math.atan2(y, x);
    }

    /** * Calculates the angle between two vectors.
     * @param v The target vector.
     * @return The angle in radians.
     */
    public float angleTo(Vector2f v) {
        float magProduct = this.magnitude() * v.magnitude();
        if (magProduct == 0) return 0;
        return (float) Math.acos(this.dot(v) / magProduct);
    }

    /** * Rotates the vector by a given angle.
     * @param angle The angle in radians.
     * @return A new rotated Vector2f.
     */
    public Vector2f rotate(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Vector2f(x * cos - y * sin, x * sin + y * cos);
    }

    /** * Rotates this specific instance (in-place).
     * @param angle The angle in radians.
     * @return This vector (modified) for chaining.
     */
    public Vector2f selfRotate(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float nx = x * cos - y * sin;
        float ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }


    /** * Projects this vector onto another vector.
     *      * @param v The vector to project onto.
     * @return A new Vector2f representing the projection.
     */
    public Vector2f project(Vector2f v) {
        float dot = this.dot(v);
        float magSq = v.magnitudeSq();
        if (magSq == 0) return new Vector2f(0, 0);
        return v.scale(dot / magSq);
    }

    /** * Reflects this vector across a surface normal (e.g., a bounce).
     *      * @param normal The surface normal (should be normalized).
     * @return A new reflected Vector2f.
     */
    public Vector2f reflect(Vector2f normal) {
        float dot = this.dot(normal);
        Vector2f scaledNormal = normal.scale(2.0f * dot);
        return this.sub(scaledNormal);
    }

    /**
     * Returns a vector perpendicular to this one (rotated 90 degrees).
     * @return A new perpendicular Vector2f.
     */
    public Vector2f perpendicular() {
        return new Vector2f(-y, x);
    }


    /** * @param v Vector to add.
     * @return A new Vector2f representing the sum.
     */
    public Vector2f add(Vector2f v) {
        return new Vector2f(this.x + v.x, this.y + v.y);
    }

    /** * @param v Vector to subtract.
     * @return A new Vector2f representing the difference.
     */
    public Vector2f sub(Vector2f v) {
        return new Vector2f(this.x - v.x, this.y - v.y);
    }

    /** * @param scalar Value to multiply by.
     * @return A new scaled Vector2f.
     */
    public Vector2f scale(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    /** * @param scalar Value to divide by.
     * @return A new scaled Vector2f.
     */
    public Vector2f div(float scalar) {
        if (scalar == 0) return new Vector2f(0, 0);
        return new Vector2f(this.x / scalar, this.y / scalar);
    }

    /** * Computes the dot product.
     * @param v Other vector.
     * @return The scalar dot product.
     */
    public float dot(Vector2f v) {
        return this.x * v.x + this.y * v.y;
    }

    
    /** @return The length (magnitude) of the vector. */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /** @return The squared length of the vector (faster for comparisons). */
    public float magnitudeSq() {
        return x * x + y * y;
    }

    /** * Returns a unit vector (length 1).
     * @return Normalized vector or (0,0) if original magnitude is zero.
     */
    public Vector2f normalize() {
        float mag = magnitude();
        if (mag == 0) return new Vector2f(0, 0);
        return new Vector2f(x / mag, y / mag);
    }

    /** * Linear interpolation (Lerp) between two vectors.
     * @param start The starting vector.
     * @param end The goal vector.
     * @param alpha Interpolation factor (usually 0.0 to 1.0).
     * @return Interpolated Vector2f.
     */
    public static Vector2f lerp(Vector2f start, Vector2f end, float alpha) {
        return new Vector2f(
            start.x + (end.x - start.x) * alpha,
            start.y + (end.y - start.y) * alpha
        );
    }

    /** * Calculates the Euclidean distance between two vectors.
     * @param v The other vector.
     * @return The distance as a float.
     */
    public float distance(Vector2f v) {
        float dx = x - v.x;
        float dy = y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector2f)) return false;
        Vector2f other = (Vector2f) obj;
        return Float.compare(x, other.x) == 0 && Float.compare(y, other.y) == 0;
    }

    @Override
    public String toString() {
        return String.format("Vector2f(%.2f, %.2f)", x, y);
    }
}