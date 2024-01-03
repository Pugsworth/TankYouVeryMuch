# Roadmap

## 1.0.0
- [/] Stackable fluid tank
  - [ ] Good textures that fit the style of Minecraft.
- [/] Bucket interaction
- [ ] Faucet item


# Planned Features

- Fully functional fluid tank that can be stacked.  
    When fluids enter the tank, they will fall to the lowest tank in the stack.  
    - The tank will generate a light level depending on the fluid inside.


- Buckets can insert and extract from tanks.  
    They will only extract from a tank that has fluid in it.  
    That means the top is naturally the input and the bottom is the output.  
    - (Config) Add new bucket and bottle items for the existing fluids (honey, slime, etc)


- Faucets can be used to extract fluids from the tank into another fluid accepting tank (using the [Fabric Transfer API](https://fabricmc.net/wiki/tutorial:transfer-api)  
    - Can be used to create a fluid source block if the stored fluid can be a source block.
    - Should they try to fill the space with source blocks (generate ponds)?


---

