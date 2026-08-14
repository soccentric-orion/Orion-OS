# Orion-OS

**Remote:** `git@github.com:soccentric-orion/Orion-OS.git`
**Parent project:** [Orion](../Orion.md) (target hardware: **ST STM32MP1** microprocessor, Cortex-A7 + Cortex-M4 co-processor.)
**Leaf type:** `<Project>-OS`
**Status:** REAL — actively built out

Yocto-based embedded Linux OS for the **ST STM32MP1** platform. Built with
`kas` + Docker against the `meta-st-stm32mp` (scarthgap) layer set, with A/B
OTA via `swupdate`.

**Board:** default `orion` (machine `stm32mp15-disco`, STM32MP157F-DK2
Discovery). Platform customization lives in `meta/meta-orion` (committed
locally); upstream layers (`poky`, `meta-openembedded`, `meta-st-stm32mp`,
`meta-swupdate`) are checked out by kas into a **gitignored** `layers/`
directory and must never be hand-edited.

**Targets:** `make build`, `make image`, `make shell [board]`, `make swu`,
`make sdk`/`esdk`, `make flash`, `make deploy-*`, `make clean`/`clean-all`.

**Boot flow (STM32MP157F-DK2):**
```
ROM → FSBL (TF-A) → OP-TEE → U-Boot → Linux (Image + stm32mp157f-dk2.dtb)
                             → systemd → Orion-HAL, Orion services
```

**A/B OTA layout:** `boot` (FAT32), `rootfs-a`, `rootfs-b` (ext4, switched by
swupdate via filesystem label + U-Boot env), `data` (`/home`, persistent).
See `meta/meta-orion/wic/sdimage-stm32mp1.wks`.

**Local layer:** `meta/meta-orion` is committed in-repo (see `.gitignore`),
matching the Acadia/Arches/Zion/Joshua/Pinnacle/Sequoia/Vela/Polaris
convention.

---
Part of the `Orion` project in the `project-managers` fleet. See
`docs/project-managers.md` for the full 9-project / 8-leaf hierarchy and shared
conventions.
