ADBugger is a desktop tool for debugging and QA of Android devices and emulators. It simplifies testing, debugging, and performance analysis, offering device management, automated testing, log analysis, and remote control capabilities to ensure smooth app performance across different setups.

*Core Functionality*

**Device Management:** List connected devices and emulators, and start and stop emulators.
**Package Management:** Install APKs, list installed apps, and grant and revoke permissions.
**ADB Commands:** Execute ADB commands on selected targets.
**Log Management:** View and filter Android device logs (Logcat).
**Input Simulation:** Send input events (buttons, keyboard) to devices.
**Port Forwarding:** List and reverse specific ports.
**One To All:** Execute commands simultaneously on multiple devices.
**Educating:** Display the underlying commands used.

<img width="600" alt="image" src="https://github.com/user-attachments/assets/500e92a4-384d-4876-adf9-6217af81ad85">

------

**Release notes:**

Version 1.0.3
- New Logcat section with the ability to filter logs by priority and tag.
- Support multiple target selection for executing commands.

Version 1.0.2
- Added support for installing APKs selected through the file picker.
- Enabled the addition of package names to favorites, which can now be viewed in a separate tab.

Version 1.0.1
- Added support for forwarding the input of Enter, Dot, and Comma keys.
- Introduced a new status line displaying the selected target and package name.
- Enhanced ADB Logger to allow copying all or specific logs.

Version 1.0.0
- Implemented functionality to list connected devices and running emulators.
- Added the ability to fetch a list of installed packages on the selected target.
- Enabled launching and terminating emulators.
- Added features to grant and remove permissions for specific packages.
- Provided options to send input events using buttons in the app or via the keyboard.
- Allowed listing and ADB reverse of specific ports.
- Enabled simultaneous execution of commands across all connected devices and emulators.
- Displayed detailed information about the commands being executed.
