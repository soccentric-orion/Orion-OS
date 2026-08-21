# Orion-OS Build and Deployment

## 1. Purpose and Scope
Orion-OS is the Dockerized Yocto/OpenEmbedded build for the STM32MP1 platform. The root Makefile composes kas configuration from `kas/boards/orion.yaml`, `kas/common.yaml`, and pinned layer descriptors, and defaults to the `orion` board and `orion-image` image target. The documented machine is `stm32mp15-disco` for STM32MP157F-DK2.

## 2. Prerequisites
- Docker, curl, GNU Make, and sufficient disk/RAM for Yocto downloads and sstate.
- Network access to fetch kas-container 4.5, Yocto layers, and source downloads; SSH credentials if private layers require them.
- `bmaptool` for flashing and a Linux host with permission to access the target block device.
- An STM32MP157F-DK2 or compatible target for boot, peripheral, OTA, and recovery checks.

## 3. Setup
```bash
make help
make kas-image
make ensure-image
```
The Makefile builds the `orion-kas:latest` container through `docker/yocto`, downloads the pinned kas-container script on first use, and uses `build/`, `cache/downloads`, and `cache/sstate-cache` by default. Override `BOARD`, `KAS_VERSION`, image names, or directory variables when needed. Board syntax is positional, for example `make image orion`.

## 4. Build and Compile
```bash
make image orion
make build orion
make rebuild-image orion
make shell orion
```
`image` aliases `build`; `build` runs `kas-container build kas/boards/orion.yaml` and stages WIC artifacts under `deploy/images`. `rebuild-image` cleans the image recipe's sstate before rebuilding. `shell` opens a kas shell for BitBake work. `make all` aliases `build` with the default board.

## 5. Test and Lint
There is no conventional unit-test target in this Makefile. Validate configuration and builds through `make help`, `make shell orion`, BitBake task output, board checks, and the repository's documented validation scripts where present. Treat successful image generation as build evidence only; it is not a boot or peripheral test.

## 6. Packaging and Artifacts
```bash
make sdk orion
make esdk orion
make swu orion SWU_VERSION=1.2.3
```
`make sdk` creates a host SDK installer and stages it under `deploy/sdk/<board>_<timestamp>/`; `esdk` stages an extensible SDK under `deploy/esdk/...`. `swu` packages the latest rootfs, kernel, and DTB into `build/<board>-<version>.swu` and stages it under `deploy/swu/...`. If `SWU_PRIV_KEY` exists, the description is RSA-signed; otherwise the result is explicitly unsigned. The image deploy stage copies `.wic.bz2` and `.wic.bmap` into `deploy/images/...`.

## 7. Deploy, Install, and Run
```bash
make flash orion DEVICE=/dev/sdX
make sdk-image orion
make esdk-image orion
```
`flash` requires an actual block device and a generated `.wic.bz2`; it uses `bmaptool` and can destroy the selected device, so identify `DEVICE` manually. `sdk-image` and `esdk-image` build SDK installers, Docker SDK images, and stage artifacts. No remote/cloud deployment target is defined; boot the flashed media on the board, install the SDK only where appropriate, and apply `.swu` through the target's SWUpdate procedure.

## 8. Cleanup
```bash
make clean
make clean-build
make clean-docker
make clean-all
```
`clean`/`clean-build` remove `build/`; `clean-docker` removes kas, SDK, and eSDK images; `clean-all` additionally removes downloads and sstate caches. These caches are expensive to recreate. `make completion` modifies the user's shell configuration and is optional.

## 9. Hardware, Cloud, and Manual Verification Caveats
The complete workflow is hardware and network dependent. Verify boot media, console, storage, Ethernet, systemd services, SDK usability, signed/unsigned OTA policy, SWUpdate rollback, and power-loss recovery on the exact board revision. Layer fetches may require credentials; signing requires control of the private RSA key. No cloud deployment is implemented by the Makefile, and no successful build alone proves the image boots or updates safely.
