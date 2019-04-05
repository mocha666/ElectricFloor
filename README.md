# ElectricFloor
## Minecraft Bukkit minigame plugin.
### Project stopped for good reasons.
I'm so sad about this but I gave up this project finally.
This project was a lot for me, but for some good reasons I can't made it
into a working thing, however I learnt a lot about this, I gained sooooo
much experience, but I should recode the whole project from scratch
because of the mistakes.

The main reason is the HORRIBLE code I wrote. This project is the 2nd
project I ever started since I learn programming, and the core of it is just
a big mess. Everything is way too much overcomplicated, and nothing is
finished. I also fell into feature creep, wich killed the project, since there
were no "1.0" for it, I always added more and more things, based on a
sphagetti-code core.

Sad.
I'm really sad, but I don't have time.
This was a piece of my hearth, I was doing this project for almost 2 years.

### Old description:

The essence of ElectricFloor to make a good short minigame, without changing the server.

#### Features:
* **Custom** language support
* **MultiVersion** from 1.8 to 1.12
* **Command Interface**
* **Rewarding**
* **Custom arena**
* **Wand** for setup
* **Debug** mode
* **ActionBar**
* **Titles**
* **Fireworks**

#### Future plan:
* Publish on [Spigot](https://spigotmc.org)
* Join NPC menu
* **AutoStarter**
* **Stats**

#### Gameplay:

You should avoid stepping on electric floor - Run until you can, once you step on a block, it gains more energy - when it reach the max, the block become deadly. (red)

#### Setup

1. ~~Download the jar from [Spigot](https://spigotmc.org)~~ Not available

2. Put the jar int your server's plugin folder
If you don't have [Essentials](https://dev.bukkit.org/projects/essentials), install it.

3. Restart your server.

4. Set the requied places via /ef set <location>
     - **lobby** waiting place
     - **start** set this in the middle of your arena.
     Players will be spreaded areound this point.
     - **lost** Loosers place
     - **win1** Winner
     - **win2** 2nd
     - **win3** 3nd
     
5. Setup the arena.
     * If you have [WorldEdit](https://dev.bukkit.org/projects/worldedit) installed, type //wand
     * First select one corner by left click on the block, then select the opposite corner by right clicking on the block.
     * Now type /ef set arena
     * REMEMBER: **NO UNDO** implemented.
     
6. Start the event
     - If you set up everything good, now you can announce the event. by /event bc. This checks the setup.

#### Commands

* **Event**
     - /event \- Event command help
     - /event bc \- Announce the event, also checks the setup.
     - /event start \- Start the event. Only if anounced before.
     - /event stop \- Force to stop the event.
     - /event kick <player> \- Kick the specified player from the event.
     - /event join \- Join the event.
     - /event leave \- Leave the event. Only if not started yet, or you lose already. Winners can't exit :)
* **ElectricFloor alias EF**
     - /ef \- EF command help
     - /ef reload \- Reloading the plugin, and the config.
     - /ef debug \- Debug mode. Alpha
     - /ef set \- Set menu help
          * **Possible locations:**
          * <lobby | start | lost | win1 | win2 | win3>
