# DuskHats

DuskHats is a lightweight, server-side Fabric mod for Minecraft 26.2 that adds
the `/hat` command. Players can equip the item in their main hand in the head
slot without installing the mod on their clients.

## Features

- Available to every player by default.
- Works with any item that can be held in the main hand.
- Safely returns the previous head-slot item to the inventory.
- Swaps the previous item into the main hand when the inventory is full.
- Plays the generic armor equip sound and displays a colored success message.
- Contains no client-only code or client-side entrypoint.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API for Minecraft 26.2
- Java 25 or newer

## Building

```shell
./gradlew build
```

The compiled mod will be generated in `build/libs/`.

## Installation

1. Install Fabric Loader on the Minecraft 26.2 server.
2. Place Fabric API and the DuskHats JAR in the server's `mods` directory.
3. Restart the server and run `/hat` while holding an item in the main hand.

Players do not need to install DuskHats or Fabric API on their clients.

## License

DuskHats is available under the MIT License.
