<div style="text-align: center">

<img src="src/main/resources/assets/tankyouverymuch/icon.png" width="128">

# Tank You Very Much

<!-- todo: replace 494721 with your CurseForge project id -->
[![Release](https://img.shields.io/github/v/release/axieum/fabric-example-mod?style=for-the-badge&include_prereleases&sort=semver)][releases]
[![Available For](https://img.shields.io/badge/dynamic/json?label=Available%20For&style=for-the-badge&color=34aa2f&query=gameVersionLatestFiles%5B0%5D.gameVersion&url=https%3A%2F%2Faddons-ecs.forgesvc.net%2Fapi%2Fv2%2Faddon%2F494721)][curseforge]
[![Downloads](https://img.shields.io/badge/dynamic/json?label=Downloads&style=for-the-badge&color=f16436&query=downloadCount&url=https%3A%2F%2Faddons-ecs.forgesvc.net%2Fapi%2Fv2%2Faddon%2F494721)][curseforge:files]-

</div>

---

## Description

---
This mod is intended to do one thing very well: Provide stackable fluid tanks that can be placed in the world for more compact fluid storage.  
It is built on the [Fabric][fabric] modding framework for [Minecraft][minecraft] Java Edition from 1.19.2 onwards.

I will be migrating to the [Quilt](https://quiltmc.org/) modding framework once it is stable enough to do so.

## FAQ

---
- Port to Forge?
    - Not on my own time. If someone wants to port it, I encourage them to do so.


- Why Fabric/Quilt?
    - I have tinkered with Forge in the past without really releasing anything.
      When I got back into it, I saw the new mod-loaders and decided to try Fabric out.  
      I like Fabric and Quilt's approach to creating mods in addition to the bloated and slow nature of Forge, so I decided to just stick with it.  
      This also allows me to only focus on one thing at a time rather than trying to make mod(s) for multiple mod-loaders on multiple versions of Minecraft.


- Backport to X.XX version of Minecraft?
    - While I would like to backport to versions all the way to 1.12.2 for players to enjoy, that's not feasible.  
      If you are a developer and want to take a stab at backporting, feel free to fork the repo and do so.  
      See [CONTRIBUTING.md][contributing] for more information.


- This exists in X mod already!
    - I know there's already various mods that either are or include fluid tanks.  
      This was my major entry into learning true Fabric modding and I just wanted as simple of a fluid tank as possible.


- Why is this made with Kotlin/Why not Java?
    - Due to my job, I have been working a lot with Kotlin and falling in love with it.  
      Combine that with the infamous Java verbosity and the decision is pretty easy.  
      Some aspects will still need to be written in Java (for now). Stuff like mixins cannot be done in Kotlin right now.  
      I know Java has caught up to Kotlin in terms of features, but I believe that Kotlin is just a better JVM language overall.


## License

---
This mod is licensed under the [GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/).  
See [LICENSE.md](LICENSE.md)

[contributing]: .github/CONTRIBUTING.md
[curseforge]: https://curseforge.com/minecraft/mc-mods/modid
[curseforge:files]: https://curseforge.com/minecraft/mc-mods/modid/files
[fabric]: https://fabricmc.net/
[licence]: https://creativecommons.org/publicdomain/zero/1.0
[minecraft]: https://minecraft.net/
[releases]: https://github.com/axieum/fabric-example-mod/releases
[security]: .github/SECURITY.md


## Contributing

---
Please do!  
This is a mod I've made primarily for myself and my modpacks, but if you have something you'd like to contribute, feel free to open a pull request.  
Please see [Contribution Guidelines](CONTRIBUTING.md) for more information.
