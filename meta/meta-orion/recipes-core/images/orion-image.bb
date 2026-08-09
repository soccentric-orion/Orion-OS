SUMMARY = "Orion OS — production image (generic x86-64)"
DESCRIPTION = "Full image with A/B OTA, gRPC/Protobuf, Orion-HAL, hardware \
interface tooling, and connectivity."
HOMEPAGE = "https://github.com/SiliconCentric/Orion-OS"
LICENSE = "MIT"

require recipes-extended/images/core-image-full-cmdline.bb
IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL:append:genericx86-64 = " swupdate swupdate-www orion-swupdate-config"

IMAGE_INSTALL:append = " orion-data-init"
IMAGE_INSTALL:append = " expand-rootfs"

IMAGE_INSTALL:append = " \
    orion-connectivity iw rfkill bluez5-dev \
"

IMAGE_INSTALL:append = " packagegroup-orion-middleware sudo"
# IMAGE_INSTALL:append = " packagegroup-orion-cloud"

IMAGE_INSTALL:append = " \
    i2c-tools python3-smbus python3-smbus2 \
    spitools python3-spidev \
    libgpiod libgpiod-tools python3-gpiod \
    picocom minicom python3-pyserial \
    usbutils pciutils nvme-cli sg3-utils hdparm mtd-utils \
    can-utils \
    lshw ethtool devmem2 cpupower watchdog sysstat iotop strace \
    v4l-utils evtest python3-periphery vim \
"

IMAGE_INSTALL:append = " orion-hal orion-services"

IMAGE_FEATURES += "tools-debug"
IMAGE_INSTALL:append = " gdbserver perf trace-cmd valgrind"

IMAGE_FEATURES += "ptest-pkgs"

inherit extrausers
EXTRA_USERS_PARAMS = "\
    useradd -M -s /bin/bash -G sudo orion; \
    usermod -L orion; \
"
