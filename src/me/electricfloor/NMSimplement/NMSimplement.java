package me.electricfloor.NMSimplement;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface NMSimplement {
	
	/**
	 * <h1>Sends a title and/or a subtitle to a specified player.<br></h1>
	 * Supports all versions from 1.8 to 1.12<br><br>
	 * 
	 * <b>player:</b> the message sent to this player<br>
	 * <b>title:</b> the text of the title<br>
	 * <b>color:</b> color of the title <- see {@link TitleColor}<br>
	 * <b>style:</b> style of the title <- see {@link TitleStyle}<br>
	 * <b>subtitle:</b> the text of the subtitle<br>
	 * <b>subColor:</b> color of the subtitle <- see {@link TitleColor}<br>
	 * <b>subStyle:</b> style of the subtitle <- see {@link TitleStyle}<br>
	 * <b>time:</b> Duration for the display<br><br>
	 * 
	 * @param player
	 * @param title
	 * @param color
	 * @param style
	 * @param subtitle
	 * @param subColor
	 * @param subStyle
	 * @param time
	 * @author SunStorm
	 */
	void sendTitle(Player player, String title, TitleColor color, TitleStyle style,  String subtitle, TitleColor subColor, TitleStyle subStyle, int time);

	/**
	 * <h1>Sends a message to the player int the actionbar</h1><br><br>
	 * 
	 * <b>player:</b> the message sent to this player<br>
	 * <b>message:</b> the message<br><br>
	 * @param player
	 * @param message
	 * 
	 * @author SunStorm
	 */
	void sendActionbar(Player player, String message);
	
	/**
	 * <h1>Displays the specified effect at the location</h1><br><br>
	 * 
	 * <b>player:</b> The effect will sent to this player<br>
	 * <b>effectID:</b> ID of the effect. ID's can be found in Protocol wiki<br>
	 * <b>loc:</b> the effect will appears here<br>
	 * <b>data:</b> more options for effect, on block break -> block ID<br>
	 * <b>sound:</b> disable relative sound volume<br><br>
	 * 
	 * @param player
	 * @param effectID
	 * @param loc
	 * @param data
	 * @param sound
	 * @see <a href="http://wiki.vg/Protocol#Effect">wiki.vg</a>
	 * @author SunStorm
	 */
	void blockBreakEffect(Player player, int effectID, Location loc, int data, boolean sound);
}
