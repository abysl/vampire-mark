{
  description = "KMP Template";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
    devshell.url = "github:numtide/devshell";
    flake-utils.url = "github:numtide/flake-utils";
    android.url = "github:tadfisher/android-nixpkgs";
  };

  outputs = { self, nixpkgs, devshell, flake-utils, android }:
    flake-utils.lib.eachSystem [ "x86_64-linux" "x86_64-darwin" "aarch64-darwin" ] (system:
      let
        commonAndroidSdkPackages = sdkPkgs: with sdkPkgs; [
          build-tools-34-0-0
          cmdline-tools-latest
          platform-tools
          platforms-android-34
          emulator
        ];

        testAndroidSdkPackages = sdkPkgs: with sdkPkgs; [
          emulator
        ];

        commonBuildInputs = with pkgs; [
          gradle
          jdk17
          git
	      ruby
        ];

        overlay = final: prev: {
          android-sdk = android.sdk.${system} commonAndroidSdkPackages;
          test-android-sdk = android.sdk.${system} (sdkPkgs: commonAndroidSdkPackages sdkPkgs ++ testAndroidSdkPackages sdkPkgs);
        };

        pkgs = import nixpkgs {
          inherit system;
          config.allowUnfree = true;
          overlays = [
            devshell.overlays.default
            overlay
          ];
        };

        devShells = {
          develop = pkgs.mkShell {
            name = "accelerator-android-dev";
            motd = ''
              Entered the Android app development environment.
            '';
            buildInputs = commonBuildInputs ++ [ pkgs.android-sdk pkgs.jetbrains.idea-ultimate pkgs.aseprite ];
          };

          ci = pkgs.mkShell {
            name = "accelerator-android-ci";
            motd = ''
              Entered the Android app ci environment.
            '';
            buildInputs = commonBuildInputs ++ [ pkgs.android-sdk ];
          };

          test = pkgs.mkShell {
            name = "accelerator-android-test";
            motd = ''
              Entered the Android app test environment.
            '';
            buildInputs = commonBuildInputs ++ [ pkgs.test-android-sdk ];
          };
        };

      in
      {
        inherit overlay;
        inherit devShells;
        defaultPackage = devShells.develop;
      }
    );
}
