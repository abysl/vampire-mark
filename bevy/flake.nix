{
  description = "Bevy Project Environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
        };

        rust-toolchain = pkgs.symlinkJoin {
          name = "rust-toolchain";
          paths = with pkgs; [ rustc cargo ];
        };

        linux-deps = with pkgs; [
          pkg-config udev alsaLib lutris xorg.libXcursor
          xorg.libXrandr xorg.libXi vulkan-tools vulkan-headers
          vulkan-loader vulkan-validation-layers
          clang mold
        ];

        macos-deps = with pkgs; [
          darwin.xcode
        ];

        windows-deps = with pkgs; [
          # Add Windows specific dependencies here
        ];

        platform-deps = if pkgs.lib.strings.hasPrefix "x86_64-linux" system then linux-deps
                        else if pkgs.lib.strings.hasPrefix "x86_64-windows" system then windows-deps
                        else macos-deps;

      in rec {
        abyssal-realms = pkgs.rustPlatform.buildRustPackage rec {
          name = "abyssal-realms";
          src = ./.;
          cargoSha256 = "sha256-EU+Uf/7Qsut2gaa4EF1DLdBlZzMzx4+i1zeiZWo0ZPg=";  # replace with the actual hash
          nativeBuildInputs = with pkgs; [
            pkg-config
          ] ++ platform-deps;
        };

        devShell = pkgs.mkShell {
          name = "bevy-dev-shell";
          buildInputs = with pkgs; [
            rust-toolchain
            rust-analyzer
            aseprite
            jetbrains.rust-rover
            rustPlatform.rustLibSrc
          ] ++ platform-deps;
          shellHook = ''
            export PATH="${rust-toolchain}/bin:$PATH"
            export RUST_SRC_PATH="${pkgs.rustPlatform.rustLibSrc}"
            export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath platform-deps}:$LD_LIBRARY_PATH
          '';
        };

        defaultPackage = abyssal-realms;
      }
    );
}
