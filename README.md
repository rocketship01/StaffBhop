# StaffBhop

Lightweight HCF-style bunny hopping for staff. Toggle with one key, no dependencies.

## Overview

StaffBhop brings back HCF-style bunny hopping for staff. Press one key to toggle it, then jump and hold WASD to move at a fixed speed with responsive mid-air strafing. Creative mode only, permission-gated, and invisible to everyone else on the server.

Lightweight by design: no dependencies, no ProtocolLib, and no `PlayerMoveEvent` polling. When nobody has bhop enabled the plugin does effectively nothing.

## Features

- Toggle with the offhand-swap key (F by default) — no commands, no menus
- Fixed speed per jump, so you always have momentum
- Mid-air strafing
- Creative only
- Players without the permission keep their normal offhand swap

## Requirements

- Java 21
- Paper or Purpur 1.21.4 (see compatibility below)

## Installation

1. Drop `StaffBhop.jar` into your `plugins/` folder
2. Restart the server (`/reload` is not supported)
3. Give staff the `staffbhop.use` permission

## Configuration

```yaml
bhop:
  # Blocks per tick. Sprinting is ~0.13, sprint-jumping ~0.20.
  speed: 0.75
  # 1.0 = instant direction changes, 0.4 = smooth, 0 = off.
  air-control: 1

messages:
  # Supports legacy & colour codes. Leave empty for no message.
  enabled: "&aStaff Bhop enabled"
  disabled: "&cStaff Bhop disabled"
```

Restart the server to apply changes.

## Permissions

| Permission | Description | Default |
|---|---|---|
| `staffbhop.use` | Toggle and use bhop | `op` |

## Important

Holding `staffbhop.use` means the offhand-swap key is used for the toggle and will not swap items.

High `speed` values can trigger the server's movement checks. If you see `moved too quickly` in the console, lower `speed` or raise `movedTooQuicklyMultiplier` in `spigot.yml`.

## Compatibility

Tested on Purpur 1.21.4. Other 1.21.x versions likely work but are currently untested — reports are welcome.

Paper-only: the plugin relies on `PlayerJumpEvent` and `Player#getCurrentInput()`, which do not exist on CraftBukkit or Spigot. Folia is untested and not currently supported.

## Building

```bash
./gradlew build
```

The jar lands in `build/libs/`.

## Project structure

```
dev/rocketship01/staffbhop/
├── Main.java                  Plugin entry point
├── listeners/
│   ├── ToggleListener.java    Offhand-swap key toggles bhop state
│   └── BhopListener.java      Jump applies fixed horizontal speed
├── task/
│   └── AirControlTask.java    Per-tick mid-air steering
└── util/
    ├── BhopToggleState.java   Tracks who has bhop enabled
    └── Directions.java        WASD input to world direction
```

## Implementation notes

`PlayerInputEvent` only fires when input *changes*, not every tick, so holding W and space produces no events at all. The plugin uses `PlayerJumpEvent` for the jump impulse and reads `getCurrentInput()` on demand instead.

The jump impulse is applied one tick after the event, because the server sets the jump velocity after `PlayerJumpEvent` is handled and would otherwise overwrite it.

Bhop state is not persisted. It clears on quit and on server shutdown, which is intentional — nobody should log in with bhop silently enabled.

## Feedback

Found a bug? [Open an issue](https://github.com/rocketship01/StaffBhop/issues).

## License

MIT

## Author

rocketship01
