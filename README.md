![vml](https://github.com/SPORIMEstudio/Vector-Math-lib/blob/main/banner/vml_banner.png)

**VectorMathLib** is a lightweight, intuitive linear algebra library designed for handling vector operations in 2D and 3D spaces. Whether you're working on a game engine, physics simulation, or data visualization, this library provides the essential building blocks for spatial math.

> [!IMPORTANT]
> **Project Status: Incomplete / Alpha** > This library is currently under active development. Some features may be missing or subject to breaking changes. Contributions are welcome!

---

## 🚀 Features

* **2D & 3D Vector Support:** Full implementation for `Vec2` and `Vec3` structures.
* **Essential Operations:** Addition, subtraction, scalar multiplication, and division.
* **Advanced Math:** Dot products, cross products, normalization, and magnitude calculations.
* **Performance Focused:** Optimized for minimal overhead and high-speed computations.

## 🛠 Installation

Currently, since the project is in development, you can clone the repository directly:

```bash
git clone [https://github.com/yourusername/VectorMathy.git](https://github.com/yourusername/VectorMathy.git)
```

### 💻 Quick Start (Java)

Here is how to perform common operations using the VectorMathy library.

## 1. Basic 2D & 3D Vector Math

Initialize vectors and perform arithmetic like addition, scaling, and normalization.

```Java
import sporimestudio.vml.*;

public class Main {
    public static void main(String[] args) {
        // 2D Operations
        Vector2f position = new Vector2f(10.0f, 5.0f);
        Vector2f velocity = Vector2f.RIGHT.scale(2.0f); // (2.0, 0.0)
        
        Vector2f nextPos = position.add(velocity);
        System.out.println("Next Position: " + nextPos); // Vector2f(12.00, 5.00)

        // 3D Operations
        Vector3f cameraPos = new Vector3f(0.0f, 5.0f, -10.0f);
        Vector3f target = Vector3f.ZERO;
        
        // Calculate direction and normalize it
        Vector3f lookDir = target.sub(cameraPos).normalize();
        System.out.println("Normalized Look Direction: " + lookDir);
    }
}
```

## 2. Rotations with Quaternions

Quaternions allow for smooth, gimbal-lock-free rotations in 3D space.

```Java
// Create a rotation from Euler angles (Pitch, Yaw, Roll)
Quaternion rotation = Quaternion.fromEuler(0, (float)Math.toRadians(90), 0);

// Rotate a 3D vector (e.g., turning a forward vector 90 degrees right)
Vector3f forward = Vector3f.FORWARD;
Vector3f rotatedVector = rotation.rotate(forward);

System.out.println("Rotated Vector: " + rotatedVector); // Should point roughly (1, 0, 0)
```

## 3. Linear Interpolation (Lerp)

Perfect for smooth movement or camera transitions over time.


```Java

Vector3f start = new Vector3f(0, 0, 0);
Vector3f end = new Vector3f(100, 50, 25);
float progress = 0.5f; // 50% of the way

Vector3f intermediate = Vector3f.lerp(start, end, progress);
System.out.println("Midpoint: " + intermediate); // Vector3f(50.00, 25.00, 12.50)
```

## 4. Advanced 2D Features

Your library also supports advanced 2D physics concepts like projection and reflection.

```Java

Vector2f ballVelocity = new Vector2f(5.0f, -5.0f);
Vector2f groundNormal = Vector2f.UP; // (0, 1)

// Reflect the ball velocity off the ground (a bounce)
Vector2f bounceVelocity = ballVelocity.reflect(groundNormal);
System.out.println("Bounced Velocity: " + bounceVelocity); // Vector2f(5.00, 5.00)
```

## Common Constants

Instead of creating new instances for standard directions, use these built-in static constants:

### Vector2f: `ZERO`, `UP`, `DOWN`, `LEFT`, `RIGHT`

### Vector3f: `ZERO`, `UP`, `DOWN`, `FORWARD` , `BACK` , `LEFT`, `RIGHT`


🗺 Roadmap

* [ ] Matrix transformation support (4x4 Matrices).

* [ ] Quaternion integration for 3D rotations.

* [ ] Comprehensive Unit Testing suite.

* [ ] Documentation for all API methods.
 
## 🤝 Contributing

I would love your help to make VectorMathy complete! If you find a bug or have a feature request, please:

### Fork the repository.

Create a new branch (git checkout -b feature/AmazingFeature).

Commit your changes.

Open a Pull Request.

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.
