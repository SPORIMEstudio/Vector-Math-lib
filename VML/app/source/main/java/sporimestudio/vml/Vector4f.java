package sporimestudio.vml;

/**
 * A representation of a 4D vector using float-point coordinates (x, y, z, w).
 * Primarily used for homogeneous coordinates in matrix transformations or RGBA color data.
 */
public class Vector4f {

    public float x, y, z, w;

    /** Initializes a vector at (0,0,0,0). */
    public Vector4f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    /**
     * @param x The horizontal component.
     * @param y The vertical component.
     * @param z The depth component.
     * @param w The fourth component (often used for weight or perspective).
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Copy constructor.
     * @param v The vector to copy values from.
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Helper constructor to upgrade a Vector3f to 4D.
     * @param v The 3D vector.
     * @param w The w component to add.
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }


    /**
     * Adds another vector to this one.
     * @param v The vector to add.
     * @return A new Vector4f sum.
     */
    public Vector4f add(Vector4f v) {
        return new Vector4f(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
    }

    /**
     * Subtracts another vector from this one.
     * @param v The vector to subtract.
     * @return A new Vector4f difference.
     */
    public Vector4f sub(Vector4f v) {
        return new Vector4f(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
    }

    /**
     * Scales the vector components by a scalar value.
     * @param scalar The multiplier.
     * @return A new scaled Vector4f.
     */
    public Vector4f scale(float scalar) {
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    /**
     * Divides the vector components by a scalar value.
     * @param scalar The divisor.
     * @return A new scaled Vector4f or (0,0,0,0) if divisor is zero.
     */
    public Vector4f div(float scalar) {
        if (scalar == 0) return new Vector4f(0, 0, 0, 0);
        return new Vector4f(this.x / scalar, this.y / scalar, this.z / scalar, this.w / scalar);
    }

    

    /**
     * Computes the dot product of two 4D vectors.
     * @param v Other vector.
     * @return The scalar dot product.
     */
    public float dot(Vector4f v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * @return The length (magnitude) of the 4D vector.
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * @return The squared length of the vector (faster for comparisons).
     */
    public float magnitudeSq() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Returns a unit vector (length 1).
     * @return A normalized Vector4f or (0,0,0,0) if magnitude is zero.
     */
    public Vector4f normalize() {
        float mag = magnitude();
        if (mag == 0) return new Vector4f(0, 0, 0, 0);
        return scale(1.0f / mag);
    }

    

    /**
     * Performs a perspective divide, dividing x, y, and z by the w component.
     * This is used to convert 4D clip-space coordinates to 3D space.
     * @return A new Vector3f containing the projected coordinates.
     */
    public Vector3f projectTo3D() {
        if (w == 0) return new Vector3f(x, y, z);
        return new Vector3f(x / w, y / w, z / w);
    }

    /**
     * Linearly interpolates between two vectors.
     * @param start The starting vector.
     * @param end The goal vector.
     * @param alpha Interpolation factor (0.0 to 1.0).
     * @return A new interpolated Vector4f.
     */
    public static Vector4f lerp(Vector4f start, Vector4f end, float alpha) {
        return new Vector4f(
            start.x + (end.x - start.x) * alpha,
            start.y + (end.y - start.y) * alpha,
            start.z + (end.z - start.z) * alpha,
            start.w + (end.w - start.w) * alpha
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) obj;
        return Float.compare(x, other.x) == 0 && 
               Float.compare(y, other.y) == 0 && 
               Float.compare(z, other.z) == 0 &&
               Float.compare(w, other.w) == 0;
    }

    @Override
    public String toString() {
        return String.format("Vector4f(%.2f, %.2f, %.2f, %.2f)", x, y, z, w);
    }
}