# Dice Roller

A simple Android dice roller built with Kotlin and Jetpack Compose.

## Features

- **Fluid Geometric Morphing**: Smooth continuous shape morphing animations (circle, squircle, triangle, square, pentagon, hexagon) using cubic Bézier paths matching Google Material 3 design specifications.
- **Multiple Number Systems**:
  - **Western**: Standard Latin numerals (`1`, `2`, `3`, `4`, `5`, `6`).
  - **Eastern**: Eastern Arabic / Persian numerals (`۱`, `۲`, `۳`, `۴`, `۵`, `۶`).
  - **Roman**: Classic Roman numerals (`I`, `II`, `III`, `IV`, `V`, `VI`) with dynamic optical font scaling.
- **Interactive Segmented Controls**: Quick-switching between numeral systems with haptic feedback.
- **Realistic Haptic Feedback**: Tactile click vibrations for each rotation step and roll confirmation.
- **Custom Adaptive Icon**: Designed with Material 3 vector assets for modern Android home screens.
- **Modern Canvas Rendering**: Hardware-accelerated custom Jetpack Compose Canvas graphics.

## Tech Stack

- **Language**: Kotlin 100%
- **UI Framework**: Jetpack Compose (Material 3)
- **Animation**: Compose Animation API (`Animatable`, `FastOutSlowInEasing`, Spring specs)
- **Graphics**: Compose `Canvas` with cubic Bézier vector path geometry
- **Build System**: Gradle (Kotlin DSL `.gradle.kts`)
- **Testing**: Robolectric & JUnit 4

## Architecture

- **Declarative UI**: Single-activity architecture powered by Jetpack Compose.
- **State-Driven Animation Engine**: Unidirectional data flow managing dice rolling states, morphing progress, and number system formatting.
- **Component Modularity**:
  - `DiceRollerScreen`: Handles interactive UI states, layout constraints, segmented controls, and dice roll animations.
  - `DiceShapes`: Mathematical Bézier path generator and shape interpolator for numbers 1 to 6.
  - `Theme`: Material Design 3 color schemes and typography setup.

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17 or higher
- Android SDK with `minSdk` 24 and `targetSdk` 36

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/dice-roller.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Run the app on an Android device or emulator:
   ```bash
   ./gradlew installDebug
   ```

## License

MIT
