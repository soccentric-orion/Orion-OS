# Changelog

## [Unreleased]

### Added — feature-complete generic x86-64 OS build

- **Full build orchestration**: `Makefile` (`image`/`build`/`rebuild-image`/
  `sdk`/`esdk`/`shell`/`swu`/`flash`/`deploy-*`/`clean*`/`completion`),
  `docker/{yocto,sdk,esdk}` build containers, `scripts/generate-swu-keys.sh`,
  `scripts/kas-container`, `orion-make-completion.bash`.
- **`kas/`** — `common.yaml`, `boards/orion.yaml` (genericx86-64), and repos
  (`poky`, `meta-openembedded`, `meta-swupdate`, `meta-orion`).
- **Complete `meta/meta-orion` Yocto layer** (68 files):
  - `recipes-core/images/orion-image.bb` + `orion-image-common.inc` + 20
    per-domain image variants
  - `recipes-core/orion-data-init`, `orion-services`, `systemd`, `os-release`
  - `recipes-core/packagegroup-orion-middleware` / `-cloud`
  - `recipes-connectivity/orion-connectivity`, `orion-timesync`
  - `recipes-bsp/orion-hal`, `recipes-support/expand-rootfs`, `swupdate`
  - `recipes-swupdate/orion-swupdate-config` (A/B OTA runtime)
  - `wic/sdimage-x86.wks`, `swupdate/sw-description` + pre/post-install
- **`CLAUDE.md`**, **`REPO_MAP.md`**, **`CHANGELOG.md`**, `.gitignore`.

### Notes

- Orion-OS is a hardware-neutral generic x86-64 template, following the shared
  Acadia/Arches template. Add a board config + vendor BSP to target a specific
  platform.
