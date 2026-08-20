# Dice Roller

A modern, tactile Android application featuring dynamic geometric morphing dice and an interactive decision oracle built with Kotlin and Jetpack Compose (Material 3).

## Features

- **Fluid Geometric Morphing**: Smooth continuous shape morphing animations (circle, squircle, triangle, square, pentagon, hexagon) using cubic Bézier paths matching Google Material 3 design specifications.
- **Interactive "Maybe" Oracle (Yes / No)**: 3D flipping card decision maker with Thumb Up (YES) and Thumb Down (NO) animations, dynamic emerald green (YES) and crimson red (NO) screen color transitions, and tactile haptics.
- **Top Tab Switcher**: Seamless segmented control for fast switching between **Dice** and **Maybe** modes with crossfade animations.
- **Special "6" Celebration Effect**: Distinct celebratory multi-pulse waveform vibration and a 1-second dynamic background inversion flash (Light ↔ Dark) when rolling a **6**.
- **Multiple Number Systems**:
  - **Western**: Standard Latin numerals (`1`, `2`, `3`, `4`, `5`, `6`).
  - **Eastern**: Eastern Arabic / Persian numerals (`۱`, `۲`, `۳`, `۴`, `۵`, `۶`).
  - **Roman**: Classic Roman numerals (`I`, `II`, `III`, `IV`, `V`, `VI`) with dynamic optical font scaling.
- **Dark Mode & Light Mode**: Complete theme customization (System Default, Light Mode, Dark Mode) with AMOLED pure black canvas and adaptive dice/number contrast inversion.
- **Dedicated Settings Screen**: Searchable settings interface with responsive theme selection bottom sheet.
- **Interactive Segmented Controls**: Quick-switching between numeral systems with haptic feedback.
- **Realistic Haptic Feedback**: Tactile click vibrations for each rotation step, decision flips, and roll confirmations.
- **Custom Adaptive Icon**: Designed with Material 3 vector assets for modern Android home screens.
- **Modern Canvas Rendering**: Hardware-accelerated custom Jetpack Compose Canvas graphics.

## Tech Stack

- **Language**: Kotlin 100%
- **UI Framework**: Jetpack Compose (Material 3)
- **Animation**: Compose Animation API (`Animatable`, `FastOutSlowInEasing`, Spring specs)
- **Graphics**: Compose `Canvas` with cubic Bézier vector path geometry
- **Persistence**: Android `SharedPreferences` for user theme preferences
- **Build System**: Gradle (Kotlin DSL `.gradle.kts`)
- **Testing**: Robolectric & JUnit 4

## Architecture

- **Declarative UI**: Single-activity architecture powered by Jetpack Compose with edge-to-edge system insets.
- **State-Driven Animation Engine**: Unidirectional data flow managing dice rolling states, 3D flip physics, morphing progress, and number system formatting.
- **Component Modularity**:
  - `MainHomeScreen`: Coordinates top tab switching (Dice vs Maybe) and settings entry.
  - `DiceRollerScreen`: Handles interactive UI states, layout constraints, segmented controls, and dice roll animations.
  - `MaybeScreen`: Interactive Yes/No decision engine with 3D card flips and dynamic ambient color transitions.
  - `SettingsScreen`: Searchable settings interface with theme modal bottom sheet and live preview.
  - `DiceShapes`: Mathematical Bézier path generator and shape interpolator for numbers 1 to 6.
  - `HapticHelper`: Multi-pulse celebratory vibration waveforms for special roll outcomes.
  - `PreferencesManager`: Local persistence manager for user preferences.

### Project Structure

```text
com/deeperorbit/diceroller/
├── domain/
│   ├── DiceRoller.kt             # Dice rolling mechanics & animation timing calculations
│   ├── MainTab.kt                # Main active modes (Dice vs Maybe)
│   ├── MaybeEngine.kt            # Yes/No decision mechanics & flip sequence generator
│   ├── MaybeOutcome.kt           # Oracle outcomes (YES, NO)
│   ├── NumberSystem.kt           # Numeral system definitions & formatters (Western, Eastern, Roman)
│   ├── PreferencesManager.kt     # Local storage for Theme and system preferences
│   └── ThemeMode.kt              # Supported theme modes (System, Light, Dark)
├── graphics/
│   └── DiceShapes.kt             # Bézier polygon morphing & Canvas draw routines
├── ui/
│   ├── MainHomeScreen.kt         # Root coordinator with TopTabSelector and Settings icon
│   ├── DiceRollerScreen.kt       # Dice roller interface & controls
│   ├── DiceRollerState.kt        # State holder managing roll lifecycle, morph progress & haptics
│   ├── MaybeScreen.kt            # Maybe / Try your luck interface with Green/Red ambient transitions
│   ├── MaybeState.kt             # State holder managing 3D flip physics and outcome colors
│   ├── SettingsScreen.kt         # Searchable settings interface & theme modal bottom sheet
│   ├── HapticHelper.kt           # Celebratory waveform vibrations for rolling 6
│   ├── components/
│   │   ├── TopTabSelector.kt     # Top pill segmented tabs (Dice / Maybe)
│   │   ├── DiceDisplay.kt        # Interactive morphing dice shape & number rendering
│   │   ├── MaybeDisplay.kt       # 3D flipping card with Thumb Up (YES) & Thumb Down (NO)
│   │   ├── RollButton.kt         # Pill action button with custom dice vector badge
│   │   ├── MaybeButton.kt        # "Try your luck" action button with badge
│   │   └── NumberSystemSelector.kt # Segmented control with custom numeral badges
│   └── theme/                    # Material 3 colors, typography & light/dark theme setups
└── MainActivity.kt               # Entry point with edge-to-edge layout & animated transitions
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17 or higher
- Android SDK with `minSdk` 24 and `targetSdk` 36

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/deeper-orbit/dice-roller.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Run the app on an Android device or emulator:
   ```bash
   ./gradlew installDebug
   ```

## License

MIT
