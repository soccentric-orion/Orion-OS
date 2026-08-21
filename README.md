# Orion-OS

## 1. Project Summary

Orion-OS is Orion's containerized Yocto/OpenEmbedded build system for the ST STM32MP1 family.

- STM32MP157F-DK2 Discovery is the default board and `stm32mp15-disco` is its machine.
- Yocto execution stays inside Docker and uses `kas` to compose pinned external layers and target configuration.
- Its output is embedded Linux intended to host Orion's client, HAL, configuration service, and other platform components.
- This leaf does not implement those sibling applications.

The root Makefile builds images, opens shells, creates SDK and eSDK artifacts, rebuilds images, packages and signs SWUpdate bundles, deploys artifacts, flashes WIC images with bmap checks, and cleans build or Docker state.

- `kas/common.yaml` supplies shared repositories, distro settings, systemd, image types, and cache behavior.
- `kas/boards/orion.yaml` supplies board-specific machine and boot details.
- `kas/repos/` records `poky`, ST, SWUpdate, and Orion layer locations.

Dedicated Docker contexts, SWUpdate key-generation tooling, shell completion, feature inventory, layout guidance, changelog, and repository map support the workflow.

- There is no conventional source-unit test directory here.
- Validation is configuration and build oriented: board checks, linting, validation scripts, and actual image/SDK builds are the meaningful evidence.
- Docker, layer-repository access, disk space, and target hardware are prerequisites for the complete workflow.

## 2. Feature Status

| # | Name / Description | Implemented? | Test Coverage? | Manual Test |
|---:|---|---|---|---|
| 1 | Dockerized Yocto/kas build environment | Implemented | Unknown: environment dependent | [ ] Manual verification required |
| 2 | Default board and STM32MP1 machine config | Implemented | Partial: board checks documented | [ ] Manual verification required |
| 3 | Shared kas repository/layer composition | Implemented | Unknown | [ ] Manual verification required |
| 4 | Yocto image and WIC deployment output | Implemented | Unknown: build not evidenced here | [ ] Manual verification required |
| 5 | SDK and eSDK generation | Implemented | Unknown | [ ] Manual verification required |
| 6 | Interactive kas/BitBake shell | Implemented | Unknown | [ ] Manual verification required |
| 7 | SWUpdate packaging and signing | Implemented | Unknown: device workflow unverified | [ ] Manual verification required |
| 8 | bmaptool flash workflow | Implemented | Unknown | [ ] Manual verification required |
| 9 | systemd and meta-orion image composition | Implemented | Unknown: external layer required | [ ] Manual verification required |
| 10 | Cleanup, deployment, completion, validation | Implemented | Unknown | [ ] Manual verification required |

## 3. Common Commands

```bash
make image orion
make shell orion
make sdk orion
make swu
make flash orion DEVICE=/dev/sdX
```
