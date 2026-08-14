# Orion-OS — Yocto embedded Linux for ST STM32MP1

## Overview

Orion-OS is the Yocto/OpenEmbedded build system for the **Orion** platform —
the **ST STM32MP1** microprocessor (Cortex-A7 + Cortex-M4 co-processor).
Default board: **STM32MP157F-DK2 Discovery** (machine `stm32mp15-disco`). Built
with [kas](https://kas.readthedocs.io/) + Docker against the
`meta-st-stm32mp` (scarthgap) layer set, with A/B OTA updates via
[swupdate](https://sbabic.github.io/swupdate/).

## Quick Start

```bash
make kas-image            # Build the KAS Docker image (once)
make image orion          # Build the Yocto image (STM32MP157F-DK2)
```

## Build Targets

```
make image  orion          Build Yocto SD card image
make build  orion          Alias for image
make rebuild-image orion   cleansstate image recipe, then rebuild
make sdk    orion          Cross-toolchain SDK installer → deploy/sdk/
make esdk   orion          Extensible SDK installer     → deploy/esdk/
make shell  orion          Interactive kas shell
make swu                    Build .swu OTA package
make flash  orion DEVICE=/dev/sdX   Flash image to SD card
```

## Boot Flow (STM32MP157F-DK2)

```
ROM → FSBL (TF-A) → OP-TEE → U-Boot → Linux (Image + stm32mp157f-dk2.dtb)
                             → systemd → Orion-HAL, Orion services
```

## A/B OTA Layout

Partition table (`meta/meta-orion/wic/sdimage-stm32mp1.wks`):
```
  p1  /boot   FAT32   FIP, U-Boot, Image, DTB, extlinux
  p2  /       rootfs-a (active initially)
  p3  standby rootfs-b (OTA target)
  p4  /home   persistent, grown on first boot by orion-data-init
```

SWUpdate scripts switch slots by filesystem label and update extlinux.conf +
U-Boot env (upgrade_available/bootcount/bootlimit) for firmware-level
rollback. `make swu` builds an `.swu` package; signing via
`scripts/generate-swu-keys.sh` (RSA-4096).

## Layer Structure

| Layer               | Source                        | Purpose                              |
|---------------------|-------------------------------|--------------------------------------|
| `poky`              | poky (scarthgap)              | OE core                              |
| `meta-oe` / `meta-python` | meta-openembedded (scarthgap) | Package set + OP-TEE deps   |
| `meta-st-stm32mp`   | STMicroelectronics/meta-st-stm32mp (scarthgap) | STM32MP1 BSP |
| `meta-swupdate`     | sbabic/meta-swupdate (scarthgap) | swupdate OTA framework           |
| `meta-orion`        | soccentric-orion/meta-orion (local, in `meta/`) | Orion image, HAL, swu config |

## Repo Notes

- `build/`, `cache/`, `sstate-cache/`, `layers/` are gitignored bind-mounted
  host dirs.
- `meta/meta-orion` is committed **locally** in this repo (see `.gitignore`);
  upstream layers are cloned by kas into `layers/` and must never be
  hand-edited.
- STM32MP1 proprietary GPU binaries are covered by the STMicroelectronics
  EULA, accepted via `ACCEPT_EULA_stm32mp15-disco` in `kas/common.yaml`.
