![](demo.gif)

# gilfoyle 🤓

A CLI to interactively remove unused apps from your Android device.

## Usage

```shell script
~$ gilfoyle
150 third-party applications found

Package name: com.my.app
>  1) View
   2) Uninstall
```

## Install

- Download this repository
- Open `config.json` and set `adb_path`
- Finally, run `java -jar gilfoyle.main.jar` 

## Test

Before executing the test

- One device must be connected via adb
- `config.json` should have valid adb path