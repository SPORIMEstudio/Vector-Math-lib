package sporimestudio.vml;

/**
 * A representation of a Quaternion used for 3D rotations.
 * Quaternions consist of a scalar part (w) and a vector part (x, y, z).
 */
public class Quaternion {

    public float x, y, z, w;

    /** Initializes an Identity Quaternion (no rotation). */
    public Quaternion() {
        this.x = 0; this.y = 0; this.z = 0; this.w = 1;
    }

    /**
     * @param x The x component of the vector part.
     * @param y The y component of the vector part.
     * @param z The z component of the vector part.
     * @param w The scalar component.
     */
    public Quaternion(float x, float y, float z, float w) {
        this.x = x; this.y = y; this.z = z; this.w = w;
    }


    /**
     * Creates a Quaternion from Euler angles (Pitch, Yaw, Roll).
     * @param pitch Rotation around the X axis in radians.
     * @param yaw   Rotation around the Y axis in radians.
     * @param roll  Rotation around the Z axis in radians.
     * @return A new Quaternion representing the rotation.
     */
    public static Quaternion fromEuler(float pitch, float yaw, float roll) {
        float cy = (float) Math.cos(roll * 0.5f);
        float sy = (float) Math.sin(roll * 0.5f);
        float cp = (float) Math.cos(yaw * 0.5f);
        float sp = (float) Math.sin(yaw * 0.5f);
        float cr = (float) Math.cos(pitch * 0.5f);
        float sr = (float) Math.sin(pitch * 0.5f);

        return new Quaternion(
            sr * cp * cy - cr * sp * sy,
            cr * sp * cy + sr * cp * sy,
            cr * cp * sy - sr * sp * cy,
            cr * cp * cy + sr * sp * sy
        );
    }

    /**
     * Converts this Quaternion to Euler angles.
     * @return A Vector3f where x=Pitch, y=Yaw, z=Roll (in radians).
     */
    public Vector3f toEuler() {
        Vector3f angles = new Vector3f();

        // Roll (x-axis rotation)
        double sinr_cosp = 2 * (w * x + y * z);
        double cosr_cosp = 1 - 2 * (x * x + y * y);
        angles.x = (float) Math.atan2(sinr_cosp, cosr_cosp);

        // Pitch (y-axis rotation)
        double sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            angles.y = (float) Math.copySign(Math.PI / 2, sinp); // use 90 degrees if out of range
        else
            angles.y = (float) Math.asin(sinp);

        // Yaw (z-axis rotation)
        double siny_cosp = 2 * (w * z + x * y);
        double cosy_cosp = 1 - 2 * (y * y + z * z);
        angles.z = (float) Math.atan2(siny_cosp, cosy_cosp);

        return angles;
    }


    /**
     * Creates a rotation that looks from 'eye' towards 'target'.
     * @param eye    The position of the camera/object.
     * @param target The position to look at.
     * @param up     The up direction (usually Vector3f.UP).
     * @return A rotation Quaternion representing the "look-at" orientation.
     */
    public static Quaternion lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f forward = target.sub(eye).normalize();
        Vector3f right = up.cross(forward).normalize();
        Vector3f actualUp = forward.cross(right);

        // This algorithm converts a rotation matrix to a quaternion
        float m00 = right.x; float m01 = actualUp.x; float m02 = forward.x;
        float m10 = right.y; float m11 = actualUp.y; float m12 = forward.y;
        float m20 = right.z; float m21 = actualUp.z; float m22 = forward.z;

        float tr = m00 + m11 + m22;
        float qw, qx, qy, qz;

        if (tr > 0) {
            float s = (float) Math.sqrt(tr + 1.0) * 2;
            qw = 0.25f * s;
            qx = (m21 - m12) / s;
            qy = (m02 - m20) / s;
            qz = (m10 - m01) / s;
        } else if ((m00 > m11) & (m00 > m22)) {
            float s = (float) Math.sqrt(1.0 + m00 - m11 - m22) * 2;
            qw = (m21 - m12) / s;
            qx = 0.25f * s;
            qy = (m01 + m10) / s;
            qz = (m02 + m20) / s;
        } else if (m11 > m22) {
            float s = (float) Math.sqrt(1.0 + m11 - m00 - m22) * 2;
            qw = (m02 - m20) / s;
            qx = (m01 + m10) / s;
            qy = 0.25f * s;
            qz = (m12 + m21) / s;
        } else {
            float s = (float) Math.sqrt(1.0 + m22 - m00 - m11) * 2;
            qw = (m10 - m01) / s;
            qx = (m02 + m20) / s;
            qy = (m12 + m21) / s;
            qz = 0.25f * s;
        }
        return new Quaternion(qx, qy, qz, qw).normalize();
    }

    /**
     * Multiplies this quaternion by another. Combines two rotations.
     * @param q The rotation to apply second.
     * @return A new combined Quaternion.
     */
    public Quaternion mul(Quaternion q) {
        return new Quaternion(
            x * q.w + w * q.x + y * q.z - z * q.y,
            y * q.w + w * q.y + z * q.x - x * q.z,
            z * q.w + w * q.z + x * q.y - y * q.x,
            w * q.w - x * q.x - y * q.y - z * q.z
        );
    }

    /**
     * Rotates a Vector3f by this quaternion.
     * @param v The vector to rotate.
     * @return The resulting rotated Vector3f.
     */
    public Vector3f rotate(Vector3f v) {
        Quaternion vecQuat = new Quaternion(v.x, v.y, v.z, 0);
        Quaternion resQuat = this.mul(vecQuat).mul(this.conjugate());
        return new Vector3f(resQuat.x, resQuat.y, resQuat.z);
    }

    /**
     * @return The inverse rotation.
     */
    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    /**
     * Linear interpolation (Lerp) for quaternions. 
     * Faster but less accurate than Slerp for large angles.
     * @param a     Start orientation.
     * @param b     End orientation.
     * @param alpha Interpolation factor (0.0 to 1.0).
     * @return The interpolated Quaternion.
     */
    public static Quaternion lerp(Quaternion a, Quaternion b, float alpha) {
        float dot = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
        float alphaInv = 1.0f - alpha;
        Quaternion res = new Quaternion();

        // If dot is negative, quaternions point in opposite directions; flip one.
        if (dot < 0) {
            res.x = alphaInv * a.x + alpha * -b.x;
            res.y = alphaInv * a.y + alpha * -b.y;
            res.z = alphaInv * a.z + alpha * -b.z;
            res.w = alphaInv * a.w + alpha * -b.w;
        } else {
            res.x = alphaInv * a.x + alpha * b.x;
            res.y = alphaInv * a.y + alpha * b.y;
            res.z = alphaInv * a.z + alpha * b.z;
            res.w = alphaInv * a.w + alpha * b.w;
        }
        return res.normalize();
    }

    @Override
    public String toString() {
        return String.format("Quaternion(x:%.2f, y:%.2f, z:%.2f, w:%.2f)", x, y, z, w);
    }
}