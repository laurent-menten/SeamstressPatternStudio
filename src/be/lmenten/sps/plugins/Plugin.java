/* ************************************************************************* *
 *    Seamstress Pattern Studio    +    Copyright (c)2022+ Laurent Menten    *
 * ************************************************************************* *
 * This program is free software: you can redistribute it and/or modify it   *
 * under the terms of the GNU General Public License as published by the     *
 * Free Software Foundation, either version 3 of the License, or (at your    *
 * option) any later version.                                                *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General *
 * Public License for more details.                                          *
 *                                                                           *
 * You should have received a copy of the GNU General Public License along   *
 * with this program. If not, see <https://www.gnu.org/licenses/>.           *
 * ************************************************************************* */

package be.lmenten.sps.plugins;

import be.lmenten.sps.SeamstressPatternStudio;
import be.lmenten.utils.settings.Settings;
import javafx.scene.Node;

import java.beans.Transient;
import java.io.Serializable;

public interface Plugin
	extends Serializable
{
	/**
	 * <p>
	 * The plugin initialization method.
	 * This method is called immediately after the Application class is
	 * loaded, constructed and initialized.
	 *
	 * <p>
	 * NOTE: This method is not called on the JavaFX Application thread. A
	 * plugin must not try to alter the Scene or Stage in this method. A
	 * plugin may construct other JavaFX objects in this method.
	 *
	 * @param app the Application instance
	 * @throws Exception if something goes wrong
	 */
	void init( SeamstressPatternStudio app )
		throws Exception;

	/**
	 * <p>
	 * The main entry point of the plugin. The start method is called after
	 * the init method has returned, and after the application is ready
	 * to begin running.
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application thread.
	 *
	 * @throws Exception if something goes wrong
	 */
	void start()
		throws Exception;

	/**
	 * <p>
	 * This method is called when the plugin should stop, and provides a
	 * convenient place to prepare for application exit and destroy
	 * resources.
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application thread.
	 *
	 * @throws Exception if something goes wrong
	 */
	void stop()
		throws Exception;

	Settings getPluginSettings();

	/**
	 *
	 * @return the settings panel root node
	 */
	Node getSettingsPanel();
}
