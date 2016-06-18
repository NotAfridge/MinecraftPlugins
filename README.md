# Ullarahs Minecraft Plugins
Various minecraft plugins used on a private server.

 * Clone the repo
 * Run 'mvn'
   * Shaded jars will be created in a /jar/ directory
 * Place the plugin jars you want to use in your /plugins/ directory
 * Pat yourself on the back
 
*Don't come complaining to me if things aren't working.  
These plugins can be used for developmental purposes.*

**WorldEdit, WorldGuard and Vault may be required for some plugins.**

[The wiki may contain more information.](https://github.com/Ullarah/MinecraftPlugins/wiki)

| Plugin    | Status          | Description                           |
| --------- | --------------- | ------------------------------------- |
| uAuction  | Requires Recode | Simple item auctions.                 |
| uBeacon   | In Progress     | Rainbow and Custom beacons.           |
| uChest    | Ready           | Various useful chests.                |
| uJoinQuit | Ready           | Custom Join/Quit messages.            |
| uLottery  | Ready           | Passive death lottery.                |
| uMagic    | Ready           | Magical hoe to change various blocks. |
| uPostal   | Ready           | Item Postal service between players.  |
| uRocket   | Requires Recode | Rocket Boots for player flying.       |
| uTab      | Ready           | Tab list header and footer.           |
| uTeleport | Ready           | Teleport history for players.         |
| uWatch    | Do Not Use      | Player information and tracker.       |
| uWild     | In Progress     | Various animal and mob changes.       |

## Future Possibilities?
- uChest:
  - [ ] Change materials.yml into components
    - [ ] Seperate each item, with custom folder for unique items
    - [ ] Conversion amount for each type
    - [ ] Donation chest random inclusion setting
  - [ ] Add new chest called Enchantment Chest
    - [ ] Set as hopper type single entry only
    - [ ] Random enchantments based on config
      - [ ] Possible unsafe enhancts setting
  - [ ] Place lockout feature on all chests