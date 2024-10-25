# Google Photos Unlimited
A Zygisk module which gives unlimited Google Photos storage.

To use this module you must have one of the following (latest versions):

- [Magisk](https://github.com/topjohnwu/Magisk) with Zygisk enabled
- [KernelSU](https://github.com/tiann/KernelSU) with [Zygisk Next](https://github.com/Dr-TSNG/ZygiskNext) module installed
- [APatch](https://github.com/bmax121/APatch) with [Zygisk Next](https://github.com/Dr-TSNG/ZygiskNext) module installed

## About module

It injects a classes.dex file to modify fields in the android.os.Build class. Also, it creates a hook in the native code to modify system properties. These are spoofed only into Google Photos.

## About 'custom.app_replace.list' file

You can customize the included default [example.app_replace.list](https://raw.githubusercontent.com/Rev4N1/GPhotos-Unlimited/main/example.app_replace.list) from the module directory (/data/adb/modules/unlimitedphotos) then rename it to custom.app_replace.list to systemlessly replace any additional conflicting custom ROM spoof injection app paths to disable them.

## Troubleshooting

Make sure Google Photos (com.google.android.apps.photos) is NOT on the Magisk DenyList if Enforce DenyList is enabled since this interferes with the module; the module does prevent this using scripts but it only happens once during each reboot.

### Failing to work (on KernelSU/APatch)

- Disable Zygisk Next
- Reboot
- Enable Zygisk Next
- Reboot again

### Read module logs

You can read module logs using one of these commands directly after boot:

`adb shell "logcat | grep 'FGP/'"` or `su -c "logcat | grep 'FGP/'"`

Add a "verboseLogs" entry with a value of "0", "1", "2", "3" or "100" to your custom.fgp.json to enable higher logging levels; "100" will dump all Build fields, and all the system properties that Photos is checking.

## About spoofing Advanced Settings

The advanced spoofing options add granular control over what exactly gets spoofed, allowing one to disable the parts that may conflict with other kinds of spoofing modules. See more in the Details area below.

<details>
<summary><strong>Details</strong></summary>

- Other than for the "verboseLogs" entry (see above), they are all 0 (disabled) or 1 (enabled).

- The "spoofBuild" entry (default 1) controls spoofing the Build Fields from the fingerprint; the "spoofProps" entry (default 1) controls spoofing the System Properties from the fingerprint; the "spoofProvider" entry (default 1) controls spoofing the Keystore Provider, and the "spoofSignature" entry (default 0) controls spoofing the ROM Signature.

</details>
 

## Credits

Module scripts were adapted from those of osm0sis's Play Integrity Fork (PIFork) module, please see the commit history of [osm0sis's Play Integrity Fork](https://github.com/osm0sis/PlayIntegrityFork) for proper attribution.
