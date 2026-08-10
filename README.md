# Orion-OS

Containerized Yocto/OpenEmbedded build system using `kas` + Docker for the
**ST STM32MP1** platform (default board: STM32MP157F-DK2 Discovery), with A/B
OTA updates via SWUpdate.

## Quick Start

```bash
# Build default board (STM32MP157F-DK2)
make image

# Explicit board selection
make image orion

# Open shell in build container
make shell orion

# Flash latest WIC image
make flash orion DEVICE=/dev/sdX
```

## Prerequisites

- Docker 20.10+
- GNU Make 4.0+
- curl
- bmaptool (for `make flash`)

## KAS Layout

- `kas/common.yaml`: shared distro/target/cache settings
- `kas/boards/*.yaml`: machine-specific board files
- `kas/repos/*.yaml`: external layer repository definitions

Layer repos: `poky`, `meta-openembedded`, `meta-st-stm32mp`, `meta-swupdate`,
`meta-orion`.

## Common Targets

- `make build [board]` / `make image [board]`
- `make sdk [board]` / `make esdk [board]`
- `make shell [board]`
- `make flash [board] DEVICE=/dev/sdX`
- `make swu` (OTA package)
- `make help`

See `CLAUDE.md` / `REPO_MAP.md` for full details.
